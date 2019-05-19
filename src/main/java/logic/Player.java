package logic;

import logic.pieces.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    private PlayerColor color;
    private ArrayList<Piece> pieces;
    private Integer[][] state;
    private Time time;

    /*TODO update piece upon move as well*/

    /* random move */
    public PosXY[] getComputerMove(){
        Random rand = new Random();
        Piece p = this.pieces.get(rand.nextInt(this.pieces.size()));
        return p.getRandomMove();
    }
    /* checks if move[] is legal for current state */
    public Boolean isMoveLegal(PosXY move[]){
        for (Piece p :
                this.pieces) {
            if (p.getPosXY().equals(move[0])) {
                return p.moveValid(move[1], this.state);
            }
        }
        return false;
    }
    /* applies move to state[][] */
    public void move(PosXY from, PosXY to) {
        Piece p = getPiece(from);
        if(p == null)
            this.state = null;
        this.move(p, to);
    }
    public void move(Piece p, PosXY to) {
        this.state = p.move(to, this.state);
    }

    public Player(PlayerColor color, Integer[][] state){
        this.color = color;
        this.state = state;
        this.pieces = new ArrayList<Piece>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (
                        color.equals(PlayerColor.WHITE) ?
                                this.state[i][j]
                                : -this.state[i][j]){
                    case 1:
                        this.pieces.add(new Pawn(color, i, j));
                        break;
                    case 2:
                        this.pieces.add(new Knight(color, i, j));
                        break;
                    case 3:
                        this.pieces.add(new Bishop(color, i, j));
                        break;
                    case 4:
                        this.pieces.add(new Rook(color, i, j));
                        break;
                    case 5:
                        this.pieces.add(new Queen(color, i, j));
                        break;
                    case 6:
                        this.pieces.add(new King(color, i, j));
                        break;
                }
            }
        }
    }
    Piece getPiece(PosXY pos){
        for (Piece p:
                this.pieces) {
            if(p.getPosXY().equals(pos))
                return p;
        }
        return null;
    }
    public void setState(Integer[][] state) {
        this.state = state;
    }
}
