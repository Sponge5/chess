package client;

import client.GUI.GameScreen;
import client.GUI.MenuScreen;
import client.communication.ClientCommunication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.Board;
import logic.PlayerColor;
import logic.PosXY;

/*TODO adaptive GUI to window size */
public class Runner extends Application{
    private ClientCommunication comm;
    private int port;
    private MenuScreen ms;
    private GameScreen gameScreen;
    private Board board;
    private PosXY[] move;
    private PlayerColor color;
    private Service<Void> gameService;
    private Task<Void> task;
    private Boolean remote;
    private Boolean computer;

    public static void main(String[] args){
        launch();
    }
    /*TODO connect to remote game */
    public void start(final Stage stage) {
        this.ms = new MenuScreen();
        this.ms.setVsComputerBtn(new Button("Player vs Computer"));
        EventHandler<MouseEvent> vsCompBtnEh = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                System.out.println("[client/Runner] Game vs computer clicked");
                setupAndRun(stage, false, true, PlayerColor.WHITE);
            }
        };
        this.ms.setTwoPlayerBtn(new Button("Player vs Player"));
        EventHandler<MouseEvent> twoPlayerBtnEh = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                System.out.println("[client/Runner] Game vs player clicked");
                setupAndRun(stage, false, false, PlayerColor.WHITE);
            }
        };
        this.ms.setTf(new TextField("Enter localhost port here"));
        this.ms.setEh(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                System.out.println("[client/Runner] PortNum entered in TextField");
                setPort(stage);
            }
        });
        this.ms.getTf().setOnAction(this.ms.getEh());
        this.ms.getVsComputerBtn().setOnMouseClicked(vsCompBtnEh);
        this.ms.getTwoPlayerBtn().setOnMouseClicked(twoPlayerBtnEh);
        this.ms.mainMenu(stage);
    }

    /**
     * @param stage stage for GameScreen
     * @param remote true if playing online
     * @param computer true if playing vs computer
     * @param color color of player moving pieces of his GUI
     */
    private void setupAndRun(Stage stage, Boolean remote,
                             Boolean computer, final PlayerColor color) {
        /* Garbage collector should clean ms */
        this.ms = null;
        /* setupAndRun runner */
        this.color = color;
        this.computer = computer;
        this.remote = remote;
        this.board = new Board();
        if(!this.remote) {
            System.out.println("[client/Runner] Running server locally");
            server.Runner server = new server.Runner(this.remote);
            this.port = server.getPort();
            System.out.println("[client/Runner] Server address:\t" + server.getAddress());
            System.out.println("[client/Runner] Server port:\t" + this.port);
            new Thread(server).start();
        }
        this.comm = new ClientCommunication(this.port, this.remote, this.color);
        new Thread(this.comm).start();
        this.gameScreen = new GameScreen(stage, this.board.getState(), this.color);
        setupGameService();
        this.gameService.start();
    }

    void runGame() throws Exception{
        //final CountDownLatch latch = new CountDownLatch(1);
        Runnable updateGUI = new Runnable() {
            public void run() {
                //try{
                    gameScreen.getBoardPane().setMoveButtons();
                //}finally{
                //    latch.countDown();
                //}
            }
        };
        /* game vs remote player */
        if(this.remote) {
            if (color.equals(PlayerColor.BLACK)) {
                move = comm.getInMove().take();
                gameScreen.getBoardPane().setMove(move);
                Platform.runLater(updateGUI);
            }
            getMove();
            Platform.runLater(updateGUI);
            //latch.await();
            comm.getOutMove().put(move);
            move = comm.getInMove().take();
            gameScreen.getBoardPane().setMove(move);
            Platform.runLater(updateGUI);
        }
        /* local game */
        else {
            while(true) {
                getMove();
                System.out.println("[client/Runner] move: " + this.move[0].toString() + " " + this.move[1].toString());
                this.board.move(this.color, this.move);
                //latch.await();
                System.out.println("[client/Runner] putting player move out to communication");
                this.comm.getOutMove().put(move);
                System.out.println("[client/Runner] waiting for server confirmation");
                this.comm.getMoveAcceptedByServer().take();
                System.out.println("[client/Runner] updating GUI");
                Platform.runLater(updateGUI);
                System.out.println("[client/Runner] board:\n" + board.toString());
                if (this.computer) {
                    System.out.println("[client/Runner] getting move from AI");
                    this.move = board.getComputerMove(this.color.otherColor());
                    System.out.println("[client/Runner] move: " + this.move[0].toString() + " " + this.move[1].toString());
                    this.board.move(this.color.otherColor(), this.move);
                    System.out.println("[client/Runner] putting computer move out to communication");
                    this.comm.getOutMove().put(move);
                    this.gameScreen.getBoardPane().setMove(move);
                    System.out.println("[client/Runner] waiting for server confirmation");
                    this.comm.getMoveAcceptedByServer().take();
                    System.out.println("[client/Runner] updating GUI");
                    Platform.runLater(updateGUI);
                    //latch.await();
                    System.out.println("[client/Runner] board:\n" + board.toString());
                }
            }
        }
    }

    /**
     * service running in parallel with GUI without blocking it
     */
    void setupGameService(){
        /* get move from GUI, check, update gui,
         * send to server, wait for response, update gui */
        this.task = new Task<Void>() {
            protected Void call() throws Exception {
                runGame();
                return null;
            }
        };
        this.gameService = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return task;
            }
        };
    }

    public Boolean receivedGameOver(PosXY[] move){
        if(move[0].getX().equals(-1) &&
                move[0].getY().equals(-1) &&
                move[1].getX().equals(-1) &&
                move[1].getY().equals(-1))
            return true;
        return false;
    }

    /**
     * retrieve a valid move from GUI
     * @throws Exception
     */
    void getMove() throws Exception {
        while (true){
            this.gameScreen.getBoardPane().getOutMoveReady().take();
            this.move = this.gameScreen.getBoardPane().getMove();
            if (board.isMoveLegal(this.color, this.move)) {
                break;
            }
        }
    }

    private void setPort(final Stage stage){
        try {
            /* get port from user to connect to in the GUI*/
            this.port = Integer.valueOf(ms.getTf().getText());
            setupAndRun(stage, true, false, PlayerColor.WHITE);
        }catch(Exception e){
            this.ms.getTf().setOnAction(this.ms.getEh());
            this.ms.setTf(new TextField("[client/Runner] Try again"));
            this.ms.getPane().add(this.ms.getTf(), 0, 0);
            this.ms.getTf().setOnAction(this.ms.getEh());
        }
    }
}
