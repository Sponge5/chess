package logic;

import java.util.ArrayList;

public interface PieceIF {

    /**
     * Apply move to state
     * @param to destination of move
     * @param state state to modify
     * @return modified state
     */
    Integer[][] move(PosXY to, Integer[][] state);

    /**
     * check if move of this piece is valid, doesn't take into account other pieces, that should be
     * covered by Board.isMoveLegal()
     * @param to destination for piece
     * @param state in which piece is moving
     * @return true if move is \in getAllDestinations
     */
    Boolean moveValid(PosXY to, Integer[][] state);

    /**
     * @return all destinations for given piece
     */
    ArrayList<PosXY> getAllDestinations();
    Move getRandomMove(Integer[][] state);
}
