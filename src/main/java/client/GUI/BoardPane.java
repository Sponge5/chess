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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import logic.Move;
import logic.PlayerColor;
import logic.PosXY;

import java.util.concurrent.LinkedBlockingQueue;

/* TODO change tile color on click */
/* TODO flip board based on color */
public class BoardPane extends GridPane {
    private Button[][] squareButtons;
    private EventHandler<MouseEvent> gridButtonHandler;
    private Integer[][] state;
    private Move move;
    private LinkedBlockingQueue<Boolean> outMoveReady;

    /**
     * Constructor builds BoardPane based on starting board state
     * @param state starting state
     * @param color color of player
     */
    public BoardPane(Integer[][] state, PlayerColor color){
        this.state = state;
        this.move = new Move();
        this.setAlignment(Pos.CENTER);
        this.squareButtons = new Button[8][8];
        this.gridButtonHandler = this::setMove;
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
        this.outMoveReady = new LinkedBlockingQueue<>(1);
    }

    /**
     * Set move based on mouseEvent and wait for move in outMoveReady
     * @param mouseEvent click on button in grid
     */
    public void setMove(MouseEvent mouseEvent){
        if(this.move == null || this.move.getDest() != null){
            this.move = new Move();
        }
        if (this.move.getSrc() == null) {
            this.move.setSrc((PosXY) ((Node) mouseEvent.getSource()).getUserData());
            this.squareButtons[this.move.getSrc().getX()][this.move.getSrc().getY()].setBackground(
                    new Background(new BackgroundFill(Paint.valueOf("green"), null, null)));
        }else{
            this.move.setDest((PosXY) ((Node) mouseEvent.getSource()).getUserData());
            String color = (this.move.getSrc().getX() + this.move.getSrc().getY()) % 2 != 0 ?
                "grey" : "white";
            this.squareButtons[this.move.getSrc().getX()][this.move.getSrc().getY()].setBackground(
                    new Background(new BackgroundFill(Paint.valueOf(color), null, null)));
            /* get move out to Runner */
            try {
                this.outMoveReady.put(true);
            }catch (Exception e){
                /*TODO exception is probably interruption, could handle it*/
                e.printStackTrace();
            }
        }
    }

    /**
     * Changes text of buttons based on this.move
     */
    public void setMoveButtons(){
        if(this.move.getIsSpecial()){
            setButtons();
        }else{
            Integer x1 = this.move.getSrc().getX(),
                    x2 = this.move.getDest().getX(),
                    y1 = this.move.getSrc().getY(),
                    y2 = this.move.getDest().getY();
            String pieceTxt = this.squareButtons[x1][y1].getText();
            this.squareButtons[x1][y1].setText("");
            this.squareButtons[x2][y2].setText(pieceTxt);
        }
    }

    /**
     * Set buttons text based on this.state
     */
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

    /**
     * compare other state with inner state
     * @param otherState different state to compare
     * @return true if states are equal, false if not
     */
    public Boolean isStateEqual(Integer[][] otherState){
        for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){
                if(!this.state[i][j].equals(otherState[i][j]))
                    return false;
            }
        }
        return true;
    }

    public LinkedBlockingQueue<Boolean> getOutMoveReady() {
        return outMoveReady;
    }
    public Move getMove() {
        return move;
    }
    public void setMove(Move move) {
        this.move = move;
    }
    public Integer[][] getState() {
        return state;
    }
}
