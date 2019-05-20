package logic;

public class Board {
    private Player whitePlayer;
    private Player blackPlayer;
    private Integer[][] state;
    private boolean isOver;

    public Board(){
        /* default setup */
        this.isOver = false;
        this.state = new Integer[][]{
                {-4,-2,-3,-5,-6,-3,-2,-4},
                {-1,-1,-1,-1,-1,-1,-1,-1},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 1, 1, 1, 1, 1, 1, 1, 1},
                { 4, 2, 3, 5, 6, 3, 2, 4}
        };
        this.whitePlayer = new Player(PlayerColor.WHITE, this.state);
        this.blackPlayer = new Player(PlayerColor.BLACK, this.state);
    }
    public Board(Integer[][] state){
        this.state = state;
    }
    public PosXY[] getComputerMove(PlayerColor color){
        return color.equals(PlayerColor.WHITE) ?
                this.whitePlayer.getComputerMove():
                this.blackPlayer.getComputerMove();
    }
    /*TODO check if position is attacked in board for King*/
    /*TODO en passant */
    /*TODO castling */
    public Boolean isMoveLegal(PlayerColor color, PosXY[] move){
        if(color.equals(PlayerColor.WHITE))
            return this.whitePlayer.isMoveLegal(move);
        return this.blackPlayer.isMoveLegal(move);
    }
    public Integer[][] getState() {
        return state;
    }
    public void setState(Integer[][] state) {
        this.state = state;
    }
    public boolean isOver() {
        return this.isOver;
    }
    public void move(PlayerColor color, PosXY[] move){
        if(color.equals(PlayerColor.WHITE))
            whitePlayer.move(move[0], move[1]);
        else
            blackPlayer.move(move[0], move[1]);
    }
}
