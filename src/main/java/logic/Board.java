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
        System.out.println("[Board] state:\n" + this.toString());
        return color.equals(PlayerColor.WHITE) ?
                this.whitePlayer.getComputerMove(this.state):
                this.blackPlayer.getComputerMove(this.state);
    }
    /*TODO check if position is attacked in board for King*/
    /*TODO en passant */
    /*TODO castling */
    public Boolean isMoveLegal(PlayerColor color, PosXY[] move){
        if(color.equals(PlayerColor.WHITE))
            return this.whitePlayer.isMoveLegal(move, this.state);
        return this.blackPlayer.isMoveLegal(move, this.state);
    }
    public Integer[][] getState() {
        return state;
    }
    public void setState(Integer[][] state) {
        this.state = state;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(String.format("%2d", state[i][0]));
            for (int j = 1; j < 8; j++) {
                sb.append(String.format(" %2d", state[i][j]));
            }
            sb.append("\n");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }
    public boolean isOver() {
        return this.isOver;
    }
    public void move(PlayerColor color, PosXY[] move){
        if(color.equals(PlayerColor.WHITE))
            this.state = whitePlayer.move(move[0], move[1], this.state);
        else
            this.state = blackPlayer.move(move[0], move[1], this.state);
    }
}
