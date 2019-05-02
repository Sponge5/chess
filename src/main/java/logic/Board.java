package logic;

public class Board implements Runnable{
    private Player whitePlayer;
    private Player blackPlayer;
    private Integer[][] state;

    public Board(){
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
    public Board(Integer[][] state){
        this.state = state;
    }

    public Integer[][] getState() {
        return state;
    }

    public void setState(Integer[][] state) {
        this.state = state;
    }

    public void run() {
        System.out.println("Running Board logic");
    }
}
