import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(320,320);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setGridLinesVisible(true);
        Button button;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                button = new Button("");
                button.setMinSize(40,40);
                gridPane.add(button, i, j);
            }
        }
        Scene scene = new Scene(gridPane, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
