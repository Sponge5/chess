package logic;

import logic.pieces.*;

import java.sql.Time;
import java.util.ArrayList;

public class Player implements PlayerIF {
    private PlayerColor color;
    ArrayList<Piece> pieces;
    Integer[][] state;
    Time time;

    public Boolean move(PosXY from, PosXY to) {
        Piece p = getPiece(from);
        if(p == null) return false;
        return this.move(p, to);
    }

    public Boolean move(Piece p, PosXY to) {
        return p.move(to);
    }

    Piece getPiece(PosXY from){
        for (Piece p:
             this.pieces) {
            if(p.posXY.equals(from))
                return p;
        }
        return null;
    }

    public Player(PlayerColor color, Integer[][] state){
        this.color = color;
        this.state = state;
        this.pieces = new ArrayList<Piece>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (this.state[i][j]){
                    case 1:
                    case -1:
                        this.pieces.add(new Pawn(color, i, j));
                        break;
                    case 2:
                    case -2:
                        this.pieces.add(new Knight(color, i, j));
                        break;
                    case 3:
                    case -3:
                        this.pieces.add(new Bishop(color, i, j));
                        break;
                    case 4:
                    case -4:
                        this.pieces.add(new Rook(color, i, j));
                        break;
                    case 5:
                    case -5:
                        this.pieces.add(new Queen(color, i, j));
                        break;
                    case 6:
                    case -6:
                        this.pieces.add(new King(color, i, j));
                        break;
                }
            }
        }
    }

    public void setState(Integer[][] state) {
        this.state = state;
    }
}
