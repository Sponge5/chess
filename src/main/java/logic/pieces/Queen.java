package logic.pieces;

import logic.Piece;
import logic.PosXY;

public class Queen extends Piece {
    public Queen(Boolean color, Integer x, Integer y) {
        super(color, x, y);
    }

    public Boolean move(PosXY to) {
        return null;
    }

    public Boolean moveValid(PosXY to) {
        return null;
    }
}
