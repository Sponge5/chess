package logic;

import logic.pieces.*;

import java.sql.Time;
import java.util.ArrayList;

public class Player implements PlayerIF {
    Boolean color;
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

    public Player(Boolean color, Integer[][] state){
        this.color = color;
        this.state = state;
        this.pieces = new ArrayList<Piece>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(!color) state[i][j] *= -1;
                switch (state[i][j]){
                    case 1:
                        /* pawns */
                        this.pieces.add(new Pawn());
                        break;
                    case 2:
                        /* knights */
                        this.pieces.add(new Knight());
                        break;
                    case 3:
                        /* bishops */
                        this.pieces.add(new Bishop());
                        break;
                    case 4:
                        /* rooks */
                        this.pieces.add(new Rook());
                        break;
                    case 5:
                        /* queen */
                        this.pieces.add(new Queen());
                        break;
                    case 6:
                        /* king */
                        this.pieces.add(new King());
                        break;
                }
            }
        }
    }
}
