package client;

import client.GUI.GameScreen;
import client.GUI.MenuScreen;
import client.communication.ClientCommunication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Board;
import logic.Move;
import logic.PlayerColor;
import logic.PosXY;

/**
 * Runner class for client
 *
 * The gui thread runs as this one, creates new thread(s) for communication
 */
public class Runner extends Application{
    private ClientCommunication comm;
    private String address;
    private Integer port;
    private MenuScreen ms;
    private GameScreen gameScreen;
    private Board board;
    private Move move;
    private PlayerColor color;
    private Service<Void> gameService;
    private Task<Void> task;
    private Boolean remote;
    private Boolean computer;

    public static void main(String[] args){
        launch();
    }
    public void start(final Stage stage) {
        this.ms = new MenuScreen();
        this.ms.setVsComputerBtn(new Button("Player vs Computer"));
        this.ms.getVsComputerBtn().setOnMouseClicked((MouseEvent) -> {
            System.out.println("[client/Runner] Game vs computer clicked");
            setup(stage, false, true, PlayerColor.WHITE);
        });
        this.ms.setTwoPlayerBtn(new Button("Player vs Player"));
        this.ms.getTwoPlayerBtn().setOnMouseClicked((mouseEvent -> {
            System.out.println("[client/Runner] Game vs player clicked");
            setup(stage, false, false, PlayerColor.WHITE);
        }));
        this.ms.setAddressTextField(new TextField("Enter server address here"));
        this.ms.setAddressTFEventHandler((ActionEvent) -> {
            System.out.println("[client/Runner] AddressNum entered in TextField");
            setAddress(stage);
        });
        this.ms.getAddressTextField().setOnAction(this.ms.getAddressTFEventHandler());
        this.ms.setPortTextField(new TextField("Enter port number here"));
        this.ms.setPortTFEventHandler((ActionEvent) -> {
            System.out.println("[client/Runner] PortNum entered in TextField");
            setPort(stage);
        });
        this.ms.setBlackBtn(new RadioButton("Play as black"));
        this.ms.getPortTextField().setOnAction(this.ms.getPortTFEventHandler());
        this.ms.setConnectAndPlayBtn(new Button("Connect and Play"));
        this.ms.getConnectAndPlayBtn().setOnMouseClicked((MouseEvent) -> {
            System.out.println("[client/Runner] Connect and Play clicked");
            if(this.ms.getBlackBtn().isSelected()){
                setup(stage, true, false, PlayerColor.BLACK);
            }else{
                setup(stage, true, false, PlayerColor.WHITE);
            }
        });
        this.ms.mainMenu(stage);
    }

    /**
     * Sets up logic and this class
     * @param stage stage for GameScreen
     * @param remote true if playing online
     * @param computer true if playing vs computer
     * @param color color of player moving pieces of his GUI
     */
    private void setup(Stage stage, Boolean remote,
                       Boolean computer, final PlayerColor color) {
        /* Garbage collector should clean ms */
        this.ms = null;
        /* setup runner */
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
        this.comm = new ClientCommunication(this.address, this.port, this.remote, this.color);
        new Thread(this.comm).start();
        this.gameScreen = new GameScreen(stage, this.board.getState(), this.color);
        setupGameService();
        this.gameService.start();
    }

    /**
     * runs three types of game:
     *      remote
     *      local (vs computer)
     *      local (2 players)
     * @throws Exception
     */
    void runGame() throws Exception{
        Runnable updateGUI = () -> this.gameScreen.getBoardPane().setMoveButtons();
        /* game vs remote player */
        if(this.remote) {
            /* we are black -> enemy starting */
            if (this.color.equals(PlayerColor.BLACK)) {
                System.out.println("[client/Runner] waiting for move from server");
                this.move = this.comm.getInMove().take();
                System.out.println("[client/Runner] setting move in logic");
                this.gameScreen.getBoardPane().setMove(this.move);
                System.out.println("[client/Runner] updating GUI");
                Platform.runLater(updateGUI);
            }
            while (!this.board.isOver()) {
                System.out.println("[client/Runner] waiting for move from GUI");
                getMove();
                System.out.println("[client/Runner] " + this.move.toString());
                this.board.move(this.move);
                System.out.println("[client/Runner] putting player move out to communication");
                this.comm.getOutMove().put(move);
                System.out.println("[client/Runner] waiting for server confirmation");
                this.comm.getMoveAcceptedByServer().take();
                System.out.println("[client/Runner] updating GUI");
                Platform.runLater(updateGUI);
                System.out.println("[client/Runner] board:\n" + board.toString());
                System.out.println("[client/Runner] waiting for move from server");
                this.move = comm.getInMove().take();
                System.out.println("[client/Runner] setting move in logic");
                this.gameScreen.getBoardPane().setMove(move);
                System.out.println("[client/Runner] updating GUI");
                Platform.runLater(updateGUI);
            }
        }
        /* local game */
        else {
            while(!this.board.isOver()) {
                System.out.println("[client/Runner] waiting for move from GUI");
                getMove();
                System.out.println("[client/Runner] move:\n" + this.move.toString());
                this.board.move(this.move);
                System.out.println("[client/Runner] putting player move out to communication");
                this.comm.getOutMove().put(this.move);
                System.out.println("[client/Runner] waiting for server confirmation");
                this.comm.getMoveAcceptedByServer().take();
                System.out.println("[client/Runner] updating GUI");
                Platform.runLater(updateGUI);
                System.out.println("[client/Runner] board:\n" + this.board.toString());
                if(this.board.isOver())
                    break;
                this.color = this.color.otherColor();
                if (this.computer) {
                    System.out.println("[client/Runner] getting move from AI");
                    this.move = board.getComputerMove(this.color);
                    System.out.println("[client/Runner] move:\n" + this.move.toString());
                    this.board.move(this.move);
                    System.out.println("[client/Runner] putting computer move out to communication");
                    this.comm.getOutMove().put(move);
                    System.out.println("[client/Runner] setting move in logic");
                    this.gameScreen.getBoardPane().setMove(move);
                    System.out.println("[client/Runner] waiting for server confirmation");
                    this.comm.getMoveAcceptedByServer().take();
                    System.out.println("[client/Runner] updating GUI");
                    Platform.runLater(updateGUI);
                    System.out.println("[client/Runner] board:\n" + this.board.toString());
                }else{
                    System.out.println("[client/Runner] waiting for move from GUI");
                    getMove();
                    System.out.println("[client/Runner] move:\n" + this.move.toString());
                    this.board.move(this.move);
                    System.out.println("[client/Runner] putting player move out to communication");
                    this.comm.getOutMove().put(move);
                    System.out.println("[client/Runner] waiting for server confirmation");
                    this.comm.getMoveAcceptedByServer().take();
                    System.out.println("[client/Runner] updating GUI");
                    Platform.runLater(updateGUI);
                    System.out.println("[client/Runner] board:\n" + board.toString());
                }
                this.color = this.color.otherColor();
            }
        }
    }

    /**
     * service running in parallel with GUI without blocking it
     */
    void setupGameService(){
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
            if (this.board.isMoveLegal(this.color, this.move)) {
                break;
            }
        }
    }

    private void setAddress(final Stage stage){
        try {
            /* get address from user to connect to in the GUI*/
            this.address = this.ms.getAddressTextField().getText();
        }catch(Exception e){
            System.out.println("[client/Runner] address number wrong");
            this.ms.setAddressTextField(new TextField("Try again"));
            this.ms.getAddressTextField().setOnAction(this.ms.getAddressTFEventHandler());
            this.ms.remoteGameMenu(stage);
        }
    }
    private void setPort(final Stage stage){
        try {
            /* get port from user to connect to in the GUI*/
            this.port = Integer.valueOf(ms.getPortTextField().getText());
        }catch(Exception e){
            System.out.println("[client/Runner] Port number wrong");
            this.ms.setPortTextField(new TextField("Try again"));
            this.ms.getPortTextField().setOnAction(this.ms.getPortTFEventHandler());
            this.ms.remoteGameMenu(stage);
        }
    }
}
