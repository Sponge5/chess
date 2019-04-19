package client.GUI;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;

class MenuScreen {
    private Scene scene;
    private Text titleText;
    private GridPane pane;
    private Button button1, button2, button3;

    MenuScreen(Stage stage){
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.titleText = new Text("Game Menu");
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
    }

    private EventHandler<MouseEvent> newHandler(int i, final Stage stage){
        switch(i){
            case 1:
                return new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("NewGame Button clicked");
                        newGameClicked(stage);
                    }
                };
            case 2:
                return new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("LoadGame Button clicked");
                        loadGameClicked(stage);
                    }
                };
            case 3:
                return new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("WatchGame Button clicked");
                        watchGameClicked(stage);
                    }
                };
            default:
                return null;
        }
    }

    private void newGameClicked(final Stage stage){
        this.titleText = new Text("New Game Menu");
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.button1 = new Button("Local Game");
//        this.button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            public void handle(MouseEvent mouseEvent) {
//                System.out.println("stuff");
//            }
//        });
        this.button2 = new Button("Remote Game");
        this.pane.add(this.titleText, 0, 0);
        this.pane.add(this.button1, 0, 1);
        this.pane.add(this.button2, 0, 2);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
    }

    private void loadGameClicked(final Stage stage){
        this.titleText = new Text("Load Game Menu");
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.pane.add(this.titleText, 0, 0);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
    }

    private void watchGameClicked(final Stage stage){
        this.titleText = new Text("Watch Game Menu");
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.pane.add(this.titleText, 0, 0);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
    }

}
