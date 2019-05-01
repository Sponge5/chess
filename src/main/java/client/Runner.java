package client;

import client.GUI.BoardGUI;
import client.GUI.MenuScreen;
import client.communication.Utils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Runner extends Application{
    private Integer port;
    private MenuScreen ms;
    private BoardGUI boardGUI;

    public static void main(String[] args) throws Exception {
        launch();
    }

    public void start(final Stage stage) throws Exception {
        this.ms = new MenuScreen();
        this.ms.setEh(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                System.out.println("Text entered in TextField");
                setPort(stage);
            }
        });
        ms.setTf(new TextField("Enter localhost port here"));
        ms.getTf().setOnAction(ms.getEh());
        this.ms.mainMenu(stage);
        //Thread.sleep(5000);
        //System.out.println(this.port.toString());
    }

    private void setPort(final Stage stage){
        try {
            /* TODO get port from user to connect to in the GUI*/
            /* TODO if single game, run server from here */
            this.port = Integer.valueOf(ms.getTf().getText());
            runGame(stage);
        }catch(Exception e){
            this.ms.getTf().setOnAction(this.ms.getEh());
            this.ms.setTf(new TextField("Try again"));
            this.ms.getPane().add(this.ms.getTf(), 0, 0);
            this.ms.getTf().setOnAction(this.ms.getEh());
        }
    }

    private void runGame(Stage stage){
        /* Garbage collector should clean ms */
        this.ms = null;
        try {
            new BoardGUI(null).start(stage);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(this.port.toString());
        new Thread(new Utils(this.port)).start();
    }
}
