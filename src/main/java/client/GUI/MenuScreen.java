package client.GUI;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;

public class MenuScreen{
    private Scene scene;
    private Text titleText;
    private GridPane pane;
    private Button button1, button2, button3, button4;
    private TextField tf;
    private EventHandler<ActionEvent> eh;

    public void mainMenu(Stage stage){
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.titleText = new Text("Main Menu");
        this.pane.add(this.titleText, 0, 0);
        this.button1 = new Button("New Game");
        this.button1.setOnMouseClicked(newHandler(1, stage));
        this.pane.add(this.button1, 0, 1);
        this.button2 = new Button("Load Game");
        this.button2.setOnMouseClicked(newHandler(2, stage));
        this.pane.add(this.button2, 0, 2);
        this.button3 = new Button("Watch Game");
        this.button3.setOnMouseClicked(newHandler(3, stage));
        this.pane.add(this.button3, 0, 3);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
        stage.show();
        this.button4 = new Button("Back to Main Menu");
        this.button4.setOnMouseClicked(newHandler(0, stage));
    }

    private EventHandler<MouseEvent> newHandler(int i, final Stage stage){
        switch(i){
            case 0:
                return new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("BackToMainMenu Button clicked");
                        mainMenu(stage);
                    }
                };
            case 1:
                return new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("NewGame Button clicked");
                        newGameMenu(stage);
                    }
                };
            case 2:
                return new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("LoadGame Button clicked");
                        loadGameMenu(stage);
                    }
                };
            case 3:
                return new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("WatchGame Button clicked");
                        watchGameMenu(stage);
                    }
                };
            case 4:
                return new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("LocalGame Button clicked");
                        localGameMenu(stage);
                    }
                };
            default:
                return null;
        }
    }

    private void newGameMenu(final Stage stage){
        this.titleText = new Text("New Game Menu");
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.button1 = new Button("Local Game");
        this.button1.setOnMouseClicked(newHandler(4, stage));
        this.button2 = new Button("Remote Game");
        this.pane.add(this.titleText, 0, 0);
        this.pane.add(this.button1, 0, 1);
        this.pane.add(this.button2, 0, 2);
        this.pane.add(this.button4, 0, 3);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
    }

    private void loadGameMenu(final Stage stage){
        this.titleText = new Text("Load Game Menu");
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.pane.add(this.titleText, 0, 0);
        this.pane.add(this.button4, 0, 1);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
    }

    private void watchGameMenu(final Stage stage){
        this.titleText = new Text("Watch Game Menu");
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.pane.add(this.titleText, 0, 0);
        this.pane.add(this.button4, 0, 1);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
    }

    private void localGameMenu(final Stage stage){
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.pane.add(this.tf, 0, 0);
        this.pane.add(this.button4, 0, 1);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
    }

    public GridPane getPane(){
        return this.pane;
    }
    public TextField getTf(){
        return this.tf;
    }
    public void setTf(TextField tf){
        this.tf = tf;
    }
    public EventHandler<ActionEvent> getEh(){
        return this.eh;
    }
    public void setEh(EventHandler<ActionEvent> eh){
        this.eh = eh;
    }
}
