import logic.PlayerColor;
import logic.PosXY;
import logic.pieces.Pawn;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PawnTests {
    @Test
    void getAllDestsTest1(){
        Pawn pawn = new Pawn(PlayerColor.WHITE, 6, 0);
        ArrayList<PosXY> dests = pawn.getAllDestinations();
        assertTrue(dests.contains(new PosXY(4, 0)));
        assertTrue(dests.contains(new PosXY(5, 0)));
        assertTrue(dests.contains(new PosXY(5, 1)));
        /* all others should be false */
        assertFalse(dests.contains(new PosXY(4, 1)));
        assertFalse(dests.contains(new PosXY(6, 1)));
    }
    @Test
    void getAllDestsTest2(){
        Pawn pawn = new Pawn(PlayerColor.WHITE, 0, 3);
        ArrayList<PosXY> dests = pawn.getAllDestinations();
        assertTrue(dests.isEmpty());
    }
    @Test
    void getAllDestsTest3(){
        Pawn pawn = new Pawn(PlayerColor.WHITE, 3, 3);
        ArrayList<PosXY> dests = pawn.getAllDestinations();
        assertTrue(dests.contains(new PosXY(2, 3)));
        assertTrue(dests.contains(new PosXY(2, 2)));
        assertTrue(dests.contains(new PosXY(2, 4)));
        assertFalse(dests.contains(new PosXY(1, 3)));
        assertFalse(dests.contains(new PosXY(3, 2)));
        assertFalse(dests.contains(new PosXY(3, 4)));
        assertFalse(dests.contains(new PosXY(4, 3)));
        assertFalse(dests.contains(new PosXY(4, 2)));
        assertFalse(dests.contains(new PosXY(4, 4)));
    }
    @Test
    void getAllDestsTest4(){
        Pawn pawn = new Pawn(PlayerColor.BLACK, 3, 3);
        ArrayList<PosXY> dests = pawn.getAllDestinations();
        assertTrue(dests.contains(new PosXY(4, 3)));
        assertTrue(dests.contains(new PosXY(4, 2)));
        assertTrue(dests.contains(new PosXY(4, 4)));
        assertFalse(dests.contains(new PosXY(2, 3)));
        assertFalse(dests.contains(new PosXY(2, 2)));
        assertFalse(dests.contains(new PosXY(2, 4)));
        assertFalse(dests.contains(new PosXY(1, 3)));
        assertFalse(dests.contains(new PosXY(3, 2)));
        assertFalse(dests.contains(new PosXY(3, 4)));
    }
    @Test
    void moveValidTest1(){
        Integer[][] state = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        Pawn pawn = new Pawn(PlayerColor.WHITE, 4, 3);
        assertTrue(pawn.moveValid(new PosXY(3, 3), state));
        assertFalse(pawn.moveValid(new PosXY(3, 2), state));
        assertFalse(pawn.moveValid(new PosXY(3, 4), state));
        assertFalse(pawn.moveValid(new PosXY(2, 3), state));
        assertFalse(pawn.moveValid(new PosXY(4, 2), state));
        assertFalse(pawn.moveValid(new PosXY(4, 4), state));
        assertFalse(pawn.moveValid(new PosXY(5, 3), state));
        assertFalse(pawn.moveValid(new PosXY(5, 2), state));
        assertFalse(pawn.moveValid(new PosXY(5, 4), state));
    }
}
