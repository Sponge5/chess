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

import java.util.concurrent.CountDownLatch;

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
                System.out.println("Game vs computer clicked");
                runGame(stage, false, true, PlayerColor.WHITE);
            }
        };
        this.ms.setTwoPlayerBtn(new Button("Player vs Player"));
        EventHandler<MouseEvent> twoPlayerBtnEh = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Game vs player clicked");
                runGame(stage, false, false, PlayerColor.WHITE);
            }
        };
        this.ms.setTf(new TextField("Enter localhost port here"));
        this.ms.setEh(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                System.out.println("PortNum entered in TextField");
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
    private void runGame(Stage stage, Boolean remote,
                         Boolean computer, final PlayerColor color) {
        /* Garbage collector should clean ms */
        this.ms = null;
        /* setup runner */
        this.color = color;
        this.computer = computer;
        this.remote = remote;
        this.board = new Board();
        if(!this.remote) {
            System.out.println("Running server locally");
            server.Runner server = new server.Runner(this.remote);
            this.port = server.getPort();
            System.out.println("Server address:\t" + server.getAddress());
            System.out.println("Server port:\t" + this.port);
            new Thread(server).start();
        }
        this.comm = new ClientCommunication(this.port, this.remote, this.color);
        new Thread(this.comm).start();
        this.gameScreen = new GameScreen(stage, this.board.getState(), this.color);
        setupGameService();
        this.gameService.start();
    }

    /**
     * service running in parallel with GUI without blocking it
     */
    void setupGameService(){
        /* get move from GUI, check, update gui,
         * send to server, wait for response, update gui */
        this.task = new Task<Void>() {
            protected Void call() throws Exception {
                final CountDownLatch latch = new CountDownLatch(1);
                Runnable updateGUI = new Runnable() {
                    public void run() {
                        try{
                            gameScreen.getBoardPane().setMoveButtons();
                        }finally{
                            latch.countDown();
                        }
                    }
                };
                /* local game */
                if(!remote){
                    PlayerColor secondColor = color.equals(PlayerColor.WHITE) ?
                            PlayerColor.BLACK : PlayerColor.WHITE;
                    while(true){
                        getMove();
                        Platform.runLater(updateGUI);
                        latch.await();
                        comm.getOutMove().put(move);
                        if(computer){
                            move = board.getComputerMove(secondColor);
                            comm.getOutMove().put(move);
                            Platform.runLater(updateGUI);
                            latch.await();
                        }
                    }
                }
                /* game vs remote player */
                else {
                    if (color.equals(PlayerColor.BLACK)) {
                        move = comm.getInMove().take();
                        gameScreen.getBoardPane().setMove(move);
                        Platform.runLater(updateGUI);
                    }
                    while (true) {
                        getMove();
                        Platform.runLater(updateGUI);
                        latch.await();
                        comm.getOutMove().put(move);
                        move = comm.getInMove().take();
                        /* end game received from server */
                        if (receivedGameOver(move))
                            break;
                        gameScreen.getBoardPane().setMove(move);
                        Platform.runLater(updateGUI);
                    }
                }
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
            runGame(stage, true, false, PlayerColor.WHITE);
        }catch(Exception e){
            this.ms.getTf().setOnAction(this.ms.getEh());
            this.ms.setTf(new TextField("Try again"));
            this.ms.getPane().add(this.ms.getTf(), 0, 0);
            this.ms.getTf().setOnAction(this.ms.getEh());
        }
    }
}
