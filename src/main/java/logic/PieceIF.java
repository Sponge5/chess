package logic;

public interface PieceIF {
    Integer[][] move(PosXY to, Integer state[][]);
    Boolean moveValid(PosXY to, Integer state[][]);
}
