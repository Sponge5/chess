package client.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BoardGUI{
    private Scene scene;
    private GridPane pane, boardPane;
    private Integer[][] state;
    private Button saveGame;

    public BoardGUI(Integer[][] state){
        this.state = state;
    }

    public Integer[][] getState() {
        return state;
    }

    public void start(Stage stage) throws Exception {
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.boardPane = new GridPane();
        this.boardPane.setGridLinesVisible(true);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button = new Button("");
                button.setMinSize(MinDims.BUTTON.width, MinDims.BUTTON.height);
                this.boardPane.add(button, i, j);
            }
        }
        this.pane.add(this.boardPane, 0, 0);
        this.saveGame = new Button("Save Game");
        this.pane.add(this.saveGame, 0, 1);
        this.scene = new Scene(this.pane);
        stage.setScene(this.scene);
    }

    public void updateGUI(Integer[][] state){
        /*TODO poll each square and for each piece that doesn't
         *TODO correspond update button
         */
    }
}
