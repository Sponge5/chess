package client.GUI;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import logic.PosXY;

public class BoardGUI{
    private Scene scene;
    private GridPane pane, boardPane;
    private EventHandler<MouseEvent> gridButtonHandler;
    private Button saveGame;
    private Integer[][] state;
    private PosXY[] move;

    public BoardGUI(Integer[][] state){
        this.state = state;
        this.move = new PosXY[2];
        this.move[0] = null;
        this.move[1] = null;
        this.gridButtonHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if(move[0] != null && move[1] != null){
                    move[0] = null;
                    move[1] = null;
                }
                if (move[0] == null) {
                    move[0] = (PosXY) ((Node) mouseEvent.getSource()).getUserData();
                }else{
                    move[1] = (PosXY) ((Node) mouseEvent.getSource()).getUserData();
                    System.out.println(move[0].toString() + "\n" + move[1].toString());
                }
            }
        };
    }

    public Integer[][] getState() {
        return state;
    }

    public void start(Stage stage) throws Exception {
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.boardPane = new GridPane();
        //this.boardPane.setGridLinesVisible(true);
        this.boardPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button = new Button("");
                button.setMinSize(MinDims.BUTTON.width, MinDims.BUTTON.height);
                button.setUserData(new PosXY(i, j));
                button.setOnMouseClicked(this.gridButtonHandler);
                this.boardPane.add(button, i, j);
            }
        }
        char c = 'a';
        for (int i = 0; i < 8; i++) {
            Label label = new Label(String.valueOf(c));
            Label label1 = new Label(String.valueOf(i));
            label.setMinSize(MinDims.BUTTON.width, MinDims.BUTTON.height);
            label1.setMinSize(MinDims.BUTTON.width, MinDims.BUTTON.height);
            label.setAlignment(Pos.CENTER);
            label1.setAlignment(Pos.CENTER);
            this.boardPane.add(label1, 8, 7-i);
            this.boardPane.add(label, i, 8);
            c++;
        }
        this.pane.add(this.boardPane, 0, 0);
        this.saveGame = new Button("Save Game");
        this.pane.add(this.saveGame, 0, 1);
        this.scene = new Scene(this.pane);
        stage.setScene(this.scene);
    }

    /**TODO get move from gui (square from, square to)
    */

    public void updateGUI(Integer[][] state){
        /*TODO poll each square and for each piece that doesn't
         *TODO correspond update button
         */
    }
}
