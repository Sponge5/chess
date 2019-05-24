package client.GUI;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import logic.PlayerColor;
import logic.PosXY;

import java.util.concurrent.LinkedBlockingQueue;

public class BoardPane extends GridPane {
    private Button[][] squareButtons;
    private EventHandler<MouseEvent> gridButtonHandler;
    private Integer[][] state;
    private PosXY[] move;
    private PlayerColor playerColor;
    private LinkedBlockingQueue<Boolean> outMoveReady;

    /*TODO flip board based on color */
    public BoardPane(Integer[][] state, PlayerColor color){
        this.state = state;
        this.playerColor = color;
        this.move = new PosXY[2];
        this.setAlignment(Pos.CENTER);
        this.squareButtons = new Button[8][8];
        this.gridButtonHandler = mouseEvent -> setMove(mouseEvent);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.squareButtons[i][j] = new Button("");
                this.squareButtons[i][j].setMinSize(MinDims.BUTTON.width, MinDims.BUTTON.height);
                this.squareButtons[i][j].setUserData(new PosXY(i, j));
                this.squareButtons[i][j].setOnMouseClicked(this.gridButtonHandler);
                this.squareButtons[i][j].setStyle("-fx-font: 30 arial;");
                if((i+j) % 2 != 0)
                    this.squareButtons[i][j].setBackground(new Background(
                            new BackgroundFill(Paint.valueOf("grey"),
                                    null, null)));
                else
                    this.squareButtons[i][j].setBackground(new Background(
                            new BackgroundFill(Paint.valueOf("white"),
                                    null, null)));
                this.add(this.squareButtons[i][j], j, i);
            }
        }
        this.setButtons();
        char c = 'a';
        for (int i = 0; i < 8; i++) {
            Label label = new Label(String.valueOf(c));
            Label label1 = new Label(String.valueOf(i+1));
            label.setMinSize(MinDims.BUTTON.width, MinDims.BUTTON.height);
            label1.setMinSize(MinDims.BUTTON.width, MinDims.BUTTON.height);
            label.setAlignment(Pos.CENTER);
            label1.setAlignment(Pos.CENTER);
            label.setStyle("-fx-font: 30 arial;");
            label1.setStyle("-fx-font: 30 arial;");
            this.add(label1, 8, 7-i);
            this.add(label, i, 8);
            c++;
        }
        this.outMoveReady = new LinkedBlockingQueue<Boolean>(1);
    }

    /**
     * Set move based on mouseEvent and wait for move in moveReady
     * @param mouseEvent
     */
    public void setMove(MouseEvent mouseEvent){
        if(this.move[0] != null && this.move[1] != null){
            this.move[0] = null;
            this.move[1] = null;
        }
        if (this.move[0] == null) {
            this.move[0] = (PosXY) ((Node) mouseEvent.getSource()).getUserData();
        }else{
            this.move[1] = (PosXY) ((Node) mouseEvent.getSource()).getUserData();
            /* get move out to Runner */
            try {
                this.outMoveReady.put(true);
            }catch (Exception e){
                /*TODO exception is probably interruption, could handle it*/
                e.printStackTrace();
            }
        }
    }
    public void setMoveButtons(){
        Integer x1 = this.move[0].getX(),
                x2 = this.move[1].getX(),
                y1 = this.move[0].getY(),
                y2 = this.move[1].getY();
        String pieceTxt = this.squareButtons[x1][y1].getText();
        for (Node node :
                this.getChildren()) {
            if (GridPane.getRowIndex(node).equals(x1) && GridPane.getColumnIndex(node).equals(y1))
                ((Button) node).setText("");
            if (GridPane.getRowIndex(node).equals(x2) && GridPane.getColumnIndex(node).equals(y2))
                ((Button) node).setText(pieceTxt);
        }
    }
    public void setButtons(){
        for (int i = 0; i < this.state.length; i++) {
            for (int j = 0; j < this.state[i].length; j++) {
                switch(this.state[i][j]) {
                    case 1:
                        this.squareButtons[i][j].setText(new String(Character.toChars(ChessSymbols.WHITE_PAWN.val)));
                        break;
                    case 2:
                        this.squareButtons[i][j].setText(new String(Character.toChars(ChessSymbols.WHITE_KNIGHT.val)));
                        break;
                    case 3:
                        this.squareButtons[i][j].setText(new String(Character.toChars(ChessSymbols.WHITE_BISHOP.val)));
                        break;
                    case 4:
                        this.squareButtons[i][j].setText(new String(Character.toChars(ChessSymbols.WHITE_ROOK.val)));
                        break;
                    case 5:
                        this.squareButtons[i][j].setText(new String(Character.toChars(ChessSymbols.WHITE_QUEEN.val)));
                        break;
                    case 6:
                        this.squareButtons[i][j].setText(new String(Character.toChars(ChessSymbols.WHITE_KING.val)));
                        break;
                    case -1:
                        this.squareButtons[i][j].setText(new String(Character.toChars(ChessSymbols.BLACK_PAWN.val)));
                        break;
                    case -2:
                        this.squareButtons[i][j].setText(new String(Character.toChars(ChessSymbols.BLACK_KNIGHT.val)));
                        break;
                    case -3:
                        this.squareButtons[i][j].setText(new String(Character.toChars(ChessSymbols.BLACK_BISHOP.val)));
                        break;
                    case -4:
                        this.squareButtons[i][j].setText(new String(Character.toChars(ChessSymbols.BLACK_ROOK.val)));
                        break;
                    case -5:
                        this.squareButtons[i][j].setText(new String(Character.toChars(ChessSymbols.BLACK_QUEEN.val)));
                        break;
                    case -6:
                        this.squareButtons[i][j].setText(new String(Character.toChars(ChessSymbols.BLACK_KING.val)));
                        break;
                    default:
                        this.squareButtons[i][j].setText("");
                        break;
                }
            }
        }
    }
    public LinkedBlockingQueue<Boolean> getOutMoveReady() {
        return outMoveReady;
    }
    public PosXY[] getMove() {
        return move;
    }
    public void setMove(PosXY[] move) {
        this.move = move;
    }
}
