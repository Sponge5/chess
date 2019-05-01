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


public class Runner extends Application{
    private Integer port;
    private MenuScreen ms;
    private BoardGUI boardGUI;

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

    private void runGame(Stage stage, Boolean remote, Boolean computer){
        /* Garbage collector should clean ms */
        this.ms = null;
        this.boardGUI = new BoardGUI(null);
        try {
            this.boardGUI.start(stage);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(remote) {
            /* TODO connect to server */
            System.out.println(this.port.toString());
            new Thread(new Utils(this.port)).start();
        }else{
            /* run server */
            new Thread(new server.Runner()).start();
            /* TODO get server port and connect to it */
            this.port = 8888;
            new Thread(new Utils(this.port)).start();
        }
    }
}
