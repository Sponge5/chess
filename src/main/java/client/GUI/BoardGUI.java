package client.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class BoardGUI extends GridPane {
    Integer[][] state;

    public BoardGUI(Integer[][] state){
        this.state = state;
        this.setMinSize(320,320);
        this.setAlignment(Pos.CENTER);
        this.setGridLinesVisible(true);
        Button button;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                button = new Button("");
                button.setMinSize(40,40);
                this.add(button, i, j);
            }
        }
    }
}
