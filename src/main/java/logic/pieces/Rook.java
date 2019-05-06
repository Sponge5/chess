package logic.pieces;

import logic.Piece;
import logic.PlayerColor;
import logic.PosXY;

public class Rook extends Piece {
    public Rook(PlayerColor color, Integer x, Integer y) {
        super(color, x, y);
    }

    public Boolean move(PosXY to) {
        return null;
    }

    public Boolean moveValid(PosXY to) {
        return null;
    }
}
