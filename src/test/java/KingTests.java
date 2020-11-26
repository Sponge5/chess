import logic.PlayerColor;
import logic.PosXY;
import logic.pieces.King;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KingTests {
    @Test
    void getAllDestsTest1(){
        King king = new King(PlayerColor.WHITE, 3, 4);
        ArrayList<PosXY> dests = king.getAllDestinations();
        assertTrue(dests.contains(new PosXY(2, 3)));
        assertTrue(dests.contains(new PosXY(2, 4)));
        assertTrue(dests.contains(new PosXY(2, 5)));
        assertTrue(dests.contains(new PosXY(3, 3)));
        assertTrue(dests.contains(new PosXY(3, 5)));
        assertTrue(dests.contains(new PosXY(4, 3)));
        assertTrue(dests.contains(new PosXY(4, 4)));
        assertTrue(dests.contains(new PosXY(4, 5)));
        /* all others should be false */
        assertFalse(dests.contains(new PosXY(0, 0)));
        assertFalse(dests.contains(new PosXY(2, 2)));
        assertFalse(dests.contains(new PosXY(1, 3)));
    }
}
