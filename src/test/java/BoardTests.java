import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import logic.Board;
import logic.PlayerColor;
import logic.PosXY;
import org.junit.jupiter.api.Test;


public class BoardTests {
    @Test
    void queenLogicCorrect(){
        Integer[][] state = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 5, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        Board board = new Board(state);
        PosXY[] move = new PosXY[2];
        move[0] = new PosXY(4, 3);
        move[1] = new PosXY(0, 7);
        assertTrue(board.isMoveLegal(PlayerColor.WHITE, move));
    }
}
