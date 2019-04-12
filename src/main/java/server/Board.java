package server;

import logic.Player;

public class Board {
    /* solution
        Player implements PlayerIF
            color
            pieces
            time
        PlayerIF
            move(piece, pos)
            move(pos, pos)

        Piece (abstract class) implements PieceIF
            posX, posY
            toString() and move(x,y)
            abstract class that is extended by (rook, bishop, queen, knight, pawn, king)
     */
    Player whitePlayer;
    Player blackPlayer;
    Integer[][] state;

    Board(){
        /* default setup */
        this.state = new Integer[][]{
                {-4,-2,-3,-5,-6,-3,-2,-4},
                {-1,-1,-1,-1,-1,-1,-1,-1},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 1, 1, 1, 1, 1, 1, 1, 1},
                { 4, 2, 3, 6, 5, 3, 2, 4}
        };
        this.whitePlayer = new Player(true, this.state);
        this.blackPlayer = new Player(false, this.state);
    }
}
