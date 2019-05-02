package client;

import client.GUI.BoardGUI;
import client.GUI.MenuScreen;
import client.communication.Utils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.Board;

import java.util.concurrent.LinkedBlockingQueue;


public class Runner extends Application{
    private Integer port;
    private MenuScreen ms;
    private BoardGUI boardGUI;
    private Board board;
    private Thread  logicThread,
                    cliThread,
                    servThread;

    private void runGame(Stage stage, Boolean remote, Boolean computer){
        /* Garbage collector should clean ms */
        this.ms = null;
        this.boardGUI = new BoardGUI(null);
        try {
            this.boardGUI.start(stage);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!remote) {
            /* queue for server port */
            LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
            /* run server */
            server.Runner server = new server.Runner();
            this.port = server.getPort();
            this.servThread = new Thread(server);
            this.servThread.start();
        }
        this.cliThread = new Thread(new Utils(this.port));
        this.cliThread.start();
        this.logicThread = new Thread(new Board());
        this.logicThread.start();
    }

    public static void main(String[] args) throws Exception {
        launch();
    }

    public void start(final Stage stage) {
        this.ms = new MenuScreen();
        this.ms.setVsComputerBtn(new Button("Player vs Computer"));
        EventHandler<MouseEvent> vsCompBtnEh = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Game vs computer clicked");
                runGame(stage, false, true);
            }
        };
        this.ms.setTwoPlayerBtn(new Button("Player vs Player"));
        EventHandler<MouseEvent> twoPlayerBtnEh = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Game vs player clicked");
                runGame(stage, false, false);
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

    private void setPort(final Stage stage){
        try {
            /* get port from user to connect to in the GUI*/
            this.port = Integer.valueOf(ms.getTf().getText());
            runGame(stage, true, false);
        }catch(Exception e){
            this.ms.getTf().setOnAction(this.ms.getEh());
            this.ms.setTf(new TextField("Try again"));
            this.ms.getPane().add(this.ms.getTf(), 0, 0);
            this.ms.getTf().setOnAction(this.ms.getEh());
        }
    }
}
