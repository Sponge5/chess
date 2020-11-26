import logic.PlayerColor;
import logic.PosXY;
import logic.pieces.Queen;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueenTests {
    @Test
    void queenMoveValid(){
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
        Queen queen = new Queen(PlayerColor.WHITE, 4, 3);
        assertTrue(queen.moveValid(new PosXY(3, 2), state));
        assertFalse(queen.moveValid(new PosXY(2, 4), state));
    }
}
