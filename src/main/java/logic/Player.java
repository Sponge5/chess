package logic;

import logic.pieces.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    private ArrayList<Piece> pieces;
    private PlayerColor color;
    private Time time;

    /* random move */
    public PosXY[] getComputerMove(Integer state[][]){
        PosXY[] ret = null;
        Random rand = new Random();
        ArrayList<Piece> tempPieces = new ArrayList<Piece>(this.pieces);
        while(ret == null && !tempPieces.isEmpty()) {
            System.out.println("[Player] tempPieces.size() = " + tempPieces.size());
            Piece p = tempPieces.remove(rand.nextInt(tempPieces.size()));
            ret = p.getRandomMove(state);
        }
        return ret;
    }
    /* checks if move[] is legal for current state */
    public Boolean isMoveLegal(PosXY move[], Integer state[][]){
        for (Piece p :
                this.pieces) {
            if (p.getPosXY().equals(move[0])) {
                return p.moveValid(move[1], state);
            }
        }
        return false;
    }
    /* applies move to state[][] */
    public Integer[][] move(PosXY from, PosXY to, Integer state[][]) {
        Piece p = getPiece(from);
        if(p == null)
            return null;
        return this.move(p, to, state);
    }
    public Integer[][] move(Piece p, PosXY to, Integer[][] state) {
        return p.move(to, state);
    }
    public void enemyMove(PosXY to){
        Piece p = getPiece(to);
        if(p != null){
            this.pieces.remove(p);
        }
    }
    public Player(PlayerColor color, Integer[][] state){
        this.color = color;
        this.pieces = new ArrayList<Piece>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (
                        color.equals(PlayerColor.WHITE) ?
                                state[i][j]
                                : -state[i][j]){
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
}
