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
                { 4, 2, 3, 6, 5, 3, 2, 4}
        };
        this.whitePlayer = new Player(PlayerColor.WHITE, this.state);
        this.blackPlayer = new Player(PlayerColor.BLACK, this.state);
    }

    public boolean isMoveLegal(boolean color, PosXY[] move){
        if(color == PlayerColor.WHITE.color) {
            //white player
            return false;
        }else{
           //black player
           return true;
        }
        //return false;
    }
    public Board(Integer[][] state){
        this.state = state;
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
}
