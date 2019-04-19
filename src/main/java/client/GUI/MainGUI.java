package client.GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainGUI extends Application {
    public void start(Stage stage) throws Exception {
        stage.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                System.out.println(mouseEvent.getTarget().toString());
            }
        });
        stage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                System.out.println("uhh");
            }
        });
        /**New game
         *      vs Computer
         *      vs player on 1 pc
         *      vs player on the internet
         * Load game (PGN)
         * Watch game (PGN)
         */
        System.out.println("Setting menu screen");
        MenuScreen menuScreen = new MenuScreen(stage);
        /* TODO catch messages from MenuScreen gui */
    }

    public static void main(String[] args) {
        launch(args);
    }
}
