package client.GUI;

import client.communication.Utils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.Board;
import logic.PlayerColor;
import logic.PosXY;

import java.util.concurrent.LinkedBlockingQueue;

public class BoardGUI {
    private Thread cliThread,
                    servThread;
    private int port;
    private Scene scene;
    private GridPane pane, boardPane;
    private EventHandler<MouseEvent> gridButtonHandler;
    private Button saveGame;
    private Board board;
    private Integer[][] state;
    private Button[][] buttons;
    private PosXY[] move;
    private LinkedBlockingQueue<PosXY[]> moves;
    private PlayerColor color;

    public BoardGUI(Integer[][] state, Board board, LinkedBlockingQueue<PosXY[]> moves){
        if(state == null)
            this.state = board.getState();
        else
            this.state = state;
        for (int i = 0; i < this.state.length; i++) {
            for (int j = 0; j < this.state[i].length; j++) {
                System.out.printf(this.state[i][j] + " ");
            }
            System.out.println();
        }
        this.board = board;
        this.moves = moves;
        this.move = new PosXY[2];
        this.move[0] = null;
        this.move[1] = null;
        this.buttons = new Button[8][8];
        this.gridButtonHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                setMove(mouseEvent);
            }
        };
    }

    public void runGame2Players() throws InterruptedException {
        boolean whiteTurn = true;
        /*TODO 1. get move from gui
          TODO 2. process it in board
          TODO 3. send to server
          TODO 4. wait for server response (move ok, not ok)
          TODO 5. if move not ok -> 1.
          TODO 6. wait for new state(move?) from server
          TODO 7. update gui
          TODO 8. 1.
         */
        while(!this.board.isOver()){
            System.out.println("we here");
            PosXY move[] = this.moves.take();
            if(!this.board.isMoveLegal(whiteTurn, move))
                System.out.println("we here");
        }
    }

    public void setMove(MouseEvent mouseEvent){
        if(move[0] != null && move[1] != null){
            move[0] = null;
            move[1] = null;
        }
        if (move[0] == null) {
            move[0] = (PosXY) ((Node) mouseEvent.getSource()).getUserData();
        }else{
            move[1] = (PosXY) ((Node) mouseEvent.getSource()).getUserData();
            //TODO test in board, if correct, move is stored in board
            try {
                moves.put(move);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Integer[][] getState() {
        return state;
    }

    public void start(Stage stage, Boolean remote, Boolean computer, int port) throws Exception {
        this.color = PlayerColor.WHITE;
        this.port = port;
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.boardPane = new GridPane();
        this.boardPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button = new Button("");
                button.setMinSize(MinDims.BUTTON.width, MinDims.BUTTON.height);
                button.setUserData(new PosXY(i, j));
                button.setOnMouseClicked(this.gridButtonHandler);
                button.setStyle("-fx-font: 18 arial;");
                this.buttons[i][j] = button;
                this.boardPane.add(buttons[i][j], j, i);
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
            label.setStyle("-fx-font: 16 arial;");
            label1.setStyle("-fx-font: 16 arial;");
            this.boardPane.add(label1, 8, 7-i);
            this.boardPane.add(label, i, 8);
            c++;
        }
        this.pane.add(this.boardPane, 0, 0);
        this.saveGame = new Button("Save Game");
        this.pane.add(this.saveGame, 0, 1);
        this.scene = new Scene(this.pane);
        stage.setScene(this.scene);
        stage.show();
        if(!remote) {
            /* run server locally */
            server.Runner server = new server.Runner();
            this.port = server.getPort();
            this.servThread = new Thread(server);
            this.servThread.start();
        }
        this.cliThread = new Thread(new Utils(this.port));
        this.cliThread.start();
        //if(computer){
        //    //TODO run game vs computer
        //    //runGameVsComputer();
        //}else if(remote){
        //    //TODO run game vs player
        //    //runGameVsPlayer();
        //}else {
        //    //two players 1 pc
        //    runGame2Players();
        //}
        updateGUI();
    }

    public void updateGUI(){
        for (int i = 0; i < this.state.length; i++) {
            for (int j = 0; j < this.state[i].length; j++) {
                switch(this.state[i][j]) {
                    case 1:
                        this.buttons[i][j].setText(new String(Character.toChars(ChessSymbols.WHITE_PAWN.val)));
                        break;
                    case 2:
                        this.buttons[i][j].setText(new String(Character.toChars(ChessSymbols.WHITE_KNIGHT.val)));
                        break;
                    case 3:
                        this.buttons[i][j].setText(new String(Character.toChars(ChessSymbols.WHITE_BISHOP.val)));
                        break;
                    case 4:
                        this.buttons[i][j].setText(new String(Character.toChars(ChessSymbols.WHITE_ROOK.val)));
                        break;
                    case 5:
                        this.buttons[i][j].setText(new String(Character.toChars(ChessSymbols.WHITE_QUEEN.val)));
                        break;
                    case 6:
                        this.buttons[i][j].setText(new String(Character.toChars(ChessSymbols.WHITE_KING.val)));
                        break;
                    case -1:
                        this.buttons[i][j].setText(new String(Character.toChars(ChessSymbols.BLACK_PAWN.val)));
                        break;
                    case -2:
                        this.buttons[i][j].setText(new String(Character.toChars(ChessSymbols.BLACK_KNIGHT.val)));
                        break;
                    case -3:
                        this.buttons[i][j].setText(new String(Character.toChars(ChessSymbols.BLACK_BISHOP.val)));
                        break;
                    case -4:
                        this.buttons[i][j].setText(new String(Character.toChars(ChessSymbols.BLACK_ROOK.val)));
                        break;
                    case -5:
                        this.buttons[i][j].setText(new String(Character.toChars(ChessSymbols.BLACK_QUEEN.val)));
                        break;
                    case -6:
                        this.buttons[i][j].setText(new String(Character.toChars(ChessSymbols.BLACK_KING.val)));
                        break;
                    default:
                        this.buttons[i][j].setText("");
                        break;
                }
            }
        }
    }
    public Board getBoard(){
        return this.board;
    }
    public LinkedBlockingQueue<PosXY[]> getMoves(){
        return this.moves;
    }
}
