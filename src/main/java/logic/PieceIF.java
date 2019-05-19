package logic;

import java.util.ArrayList;

public interface PieceIF {
    Integer[][] move(PosXY to, Integer state[][]);
    Boolean moveValid(PosXY to, Integer state[][]);
    ArrayList<PosXY> getAllDestinations();
    PosXY[] getRandomMove();
}
