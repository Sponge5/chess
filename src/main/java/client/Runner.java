package client;

import java.util.logging.Logger;
import java.util.logging.Level;
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

/* TODO BoardPane gets desynchronized from Board */
/**
 * Runner class for client
 *
 * The gui thread runs as this one, creates new thread(s) for communication
 */
public class Runner extends Application{
    private static final Logger LOGGER = Logger.getLogger("[client/Runner]");
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

    /**
     * Start GUI and setup logic, communication
     * @param stage the window to make changes to
     */
    public void start(final Stage stage) {
        this.ms = new MenuScreen();
        this.ms.setVsComputerBtn(new Button("Player vs Computer"));
        this.ms.getVsComputerBtn().setOnMouseClicked((MouseEvent) -> {
            LOGGER.log(Level.ALL, "Game vs computer clicked");
            setup(stage, false, true, PlayerColor.WHITE);
        });
        this.ms.setTwoPlayerBtn(new Button("Player vs Player"));
        this.ms.getTwoPlayerBtn().setOnMouseClicked((mouseEvent -> {
            LOGGER.log(Level.ALL, "Game vs player clicked");
            setup(stage, false, false, PlayerColor.WHITE);
        }));
        this.ms.setAddressTextField(new TextField("Enter server address here"));
        this.ms.setAddressTFEventHandler((ActionEvent) -> {
            LOGGER.log(Level.ALL, "Address entered in TextField");
            setAddress(stage);
        });
        this.ms.getAddressTextField().setOnAction(this.ms.getAddressTFEventHandler());
        this.ms.setPortTextField(new TextField("Enter port number here"));
        this.ms.setPortTFEventHandler((ActionEvent) -> {
            LOGGER.log(Level.ALL, "Port entered in TextField");
            setPort(stage);
        });
        this.ms.setBlackBtn(new RadioButton("Play as black"));
        this.ms.getPortTextField().setOnAction(this.ms.getPortTFEventHandler());
        this.ms.setConnectAndPlayBtn(new Button("Connect and Play"));
        this.ms.getConnectAndPlayBtn().setOnMouseClicked((MouseEvent) -> {
            LOGGER.log(Level.ALL, "Connect and Play clicked");
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
            LOGGER.log(Level.INFO, "Running server locally");
            server.Runner server = new server.Runner(this.remote);
            this.port = server.getPort();
            LOGGER.log(Level.INFO, "Server address:\t" + server.getAddress());
            LOGGER.log(Level.INFO, "Server port:\t" + this.port);
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
     * @throws Exception (Interrupted Exception) from LinkedBlockingQueue
     */
    void runGame() throws Exception{
        Runnable updateGUI = () -> this.gameScreen.getBoardPane().setMoveButtons();
        /* game vs remote player */
        if(this.remote) {
            /* we are black -> enemy starting */
            if (this.color.equals(PlayerColor.BLACK)) {
                LOGGER.log(Level.ALL, "Waiting for move from server");
                this.move = this.comm.getInMove().take();
                LOGGER.log(Level.ALL, "Setting move in logic");
                this.gameScreen.getBoardPane().setMove(this.move);
                LOGGER.log(Level.ALL, "Updating GUI");
                Platform.runLater(updateGUI);
            }
            while (!this.board.isOver()) {
                remoteRound(updateGUI);
            }
        }
        /* local game */
        else {
            while(!this.board.isOver()) {
                localRound(updateGUI);
            }
        }
    }

    private void remoteRound(Runnable updateGUI) throws Exception{
        LOGGER.log(Level.ALL, "Waiting for move from GUI");
        getMove();
        LOGGER.log(Level.INFO, this.move.toString());
        this.board.move(this.move);
        LOGGER.log(Level.ALL, "Putting player move out to communication");
        this.comm.getOutMove().put(this.move);
        LOGGER.log(Level.ALL, "Waiting for server confirmation");
        this.comm.getMoveAcceptedByServer().take();
        LOGGER.log(Level.ALL, "Updating GUI");
        Platform.runLater(updateGUI);
        LOGGER.log(Level.INFO, "\n" + this.board.toString());
        LOGGER.log(Level.ALL, "Waiting for move from server");
        this.move = this.comm.getInMove().take();
        LOGGER.log(Level.ALL, "Setting move in logic");
        this.gameScreen.getBoardPane().setMove(this.move);
        LOGGER.log(Level.ALL, "Updating GUI");
        Platform.runLater(updateGUI);
    }

    private void localRound(Runnable updateGUI) throws Exception{
        LOGGER.log(Level.ALL, "Waiting for move from GUI");
        this.getMove();
        LOGGER.log(Level.INFO, this.move.toString());
        this.board.move(this.move);
        if (!this.gameScreen.getBoardPane().isStateEqual(this.board.getState())) {
            System.out.println("DESYNCED!!!!!!");
        }
        LOGGER.log(Level.ALL, "Putting player move out to communication");
        this.comm.getOutMove().put(this.move);
        LOGGER.log(Level.ALL, "Waiting for server confirmation");
        this.comm.getMoveAcceptedByServer().take();
        LOGGER.log(Level.ALL, "Updating GUI");
        Platform.runLater(updateGUI);
        LOGGER.log(Level.INFO, "\n" + this.board.toString());
        if (this.board.isOver())
            return;
        this.color = this.color.otherColor();
        if (this.computer) {
            LOGGER.log(Level.ALL, "Getting move from AI");
            this.move = this.board.getComputerMove(this.color);
            LOGGER.log(Level.INFO, this.move.toString());
            this.board.move(this.move);
            LOGGER.log(Level.ALL, "Putting computer move out to communication");
            this.comm.getOutMove().put(this.move);
            LOGGER.log(Level.ALL, "Setting move in logic");
            this.gameScreen.getBoardPane().setMove(this.move);
            LOGGER.log(Level.ALL, "Waiting for server confirmation");
            this.comm.getMoveAcceptedByServer().take();
            LOGGER.log(Level.ALL, "Updating GUI");
            Platform.runLater(updateGUI);
            LOGGER.log(Level.INFO, "\n" + this.board.toString());
        } else {
            LOGGER.log(Level.ALL, "Waiting for move from GUI");
            getMove();
            LOGGER.log(Level.INFO, this.move.toString());
            this.board.move(this.move);
            LOGGER.log(Level.ALL, "Putting player move out to communication");
            this.comm.getOutMove().put(move);
            LOGGER.log(Level.ALL, "Waiting for server confirmation");
            this.comm.getMoveAcceptedByServer().take();
            LOGGER.log(Level.ALL, "Updating GUI");
            Platform.runLater(updateGUI);
            LOGGER.log(Level.INFO, "\n" + this.board.toString());
        }
        this.color = this.color.otherColor();
    }

    /**
     * service running in parallel with GUI without blocking it
     */
    void setupGameService(){
        this.task = new Task<>() {
            protected Void call() throws Exception {
                runGame();
                return null;
            }
        };
        this.gameService = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return task;
            }
        };
    }

    public Boolean receivedGameOver(PosXY[] move){
        return move[0].getX().equals(-1) &&
                move[0].getY().equals(-1) &&
                move[1].getX().equals(-1) &&
                move[1].getY().equals(-1);
    }

    /**
     * retrieve a valid move from GUI
     * @throws Exception Interrupted exception from linked blocking queue
     */
    private void getMove() throws Exception {
        do {
            this.gameScreen.getBoardPane().getOutMoveReady().take();
            this.move = this.gameScreen.getBoardPane().getMove();
        } while (!this.board.isMoveLegal(this.color, this.move));
    }

    private void setAddress(final Stage stage){
        try {
            /* get address from user to connect to in the GUI*/
            this.address = this.ms.getAddressTextField().getText();
        }catch(Exception e){
            LOGGER.log(Level.WARNING, "Address number wrong");
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
