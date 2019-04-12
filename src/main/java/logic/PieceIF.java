package logic;

public interface PieceIF {
    Boolean move(PosXY to);
    Boolean moveValid(PosXY to);
}
