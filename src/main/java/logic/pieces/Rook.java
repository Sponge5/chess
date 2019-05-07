package logic.pieces;

import logic.Piece;
import logic.PlayerColor;
import logic.PosXY;

public class Rook extends Piece {
    public Rook(PlayerColor color, Integer x, Integer y) {
        super(color, x, y);
    }

    public Integer[][] move(PosXY to, Integer[][] state) {
        return new Integer[0][];
    }

    public Boolean moveValid(PosXY to, Integer[][] state) {
        return null;
    }
}
