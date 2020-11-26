import logic.Board;
import logic.Move;
import logic.PlayerColor;
import logic.PosXY;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTests {
    private Board board;
    private PlayerColor color;

    @Test
    void queenSideCastlingWhite() {
        Integer[][] state = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 6, 0, 0, 0}
        };
        this.board = new Board(state);
        this.color = PlayerColor.WHITE;
        Move move = new Move(new PosXY(7, 4), new PosXY(7, 2));
        assertTrue(this.board.isMoveLegal(this.color, move));
    }

    @Test
    void kingSideCastlingWhite() {
        Integer[][] state = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 6, 0, 0, 4}
        };
        this.board = new Board(state);
        this.color = PlayerColor.WHITE;
        Move move = new Move(new PosXY(7, 4), new PosXY(7, 6));
        assertTrue(this.board.isMoveLegal(this.color, move));
    }

    @Test
    void kingSideCastlingBlack() {
        Integer[][] state = {
                {0, 0, 0, 0, -6, 0, 0, -4},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        this.board = new Board(state);
        this.color = PlayerColor.WHITE;
        Move move = new Move(new PosXY(0, 4), new PosXY(0, 6));
        assertTrue(this.board.isMoveLegal(this.color, move));
    }

    @Test
    void queenSideCastlingBlack() {
        Integer[][] state = {
                {-4, 0, 0, 0, -6, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        this.board = new Board(state);
        this.color = PlayerColor.WHITE;
        Move move = new Move(new PosXY(0, 4), new PosXY(0, 2));
        assertTrue(this.board.isMoveLegal(this.color, move));
    }
}
