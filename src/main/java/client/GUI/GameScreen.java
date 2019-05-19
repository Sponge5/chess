package client.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.PlayerColor;

public class GameScreen {
    private Scene scene;
    private GridPane pane;
    private BoardPane boardPane;
    private Button saveGame;

    public GameScreen(Stage stage, Integer[][] state, PlayerColor color){
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.boardPane = new BoardPane(state, color);
        this.pane.add(this.boardPane, 0, 0);
        this.saveGame = new Button("Save Game");
        this.pane.add(this.saveGame, 0, 1);
        this.scene = new Scene(this.pane);
        stage.setScene(this.scene);
        stage.show();
    }

    public BoardPane getBoardPane() {
        return boardPane;
    }

}
