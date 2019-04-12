package logic;

public interface PlayerIF {
    Boolean move(PosXY from, PosXY to);
    Boolean move(Piece p, PosXY to);
}
