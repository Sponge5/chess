package logic;

import java.util.ArrayList;

public interface PieceIF {
    Integer[][] move(PosXY to, Integer[][] state);
    Boolean moveValid(PosXY to, Integer[][] state);

    /**
     * @return all destinations for given piece
     */
    ArrayList<PosXY> getAllDestinations();
    Move getRandomMove(Integer[][] state);
}
