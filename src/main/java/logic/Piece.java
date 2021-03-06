package logic;

import java.util.ArrayList;
import java.util.Random;

public abstract class Piece implements PieceIF{
    private PlayerColor color;
    private PosXY pos;
    private Boolean hasMoved;

    public Piece(PlayerColor color, Integer x, Integer y){
        this.color = color;
        this.pos = new PosXY(x,y);
        this.hasMoved = false;
    }

    /**
     * Apply move to given state
     * @param to position for piece to move to
     * @param state state to change
     * @return new state
     */
    public Integer[][] move(PosXY to, Integer[][] state) {
        state[to.getX()][to.getY()] = state[this.getX()][this.getY()];
        state[this.getX()][this.getY()] = 0;
        this.pos = to;
        if(this.hasMoved.equals(false))
            this.hasMoved = true;
        return state;
    }

    /**
     * Get random move out of available destinations
     * @param state given state for move validation
     * @return a valid random move
     */
    public Move getRandomMove(Integer[][] state) {
        Move ret = new Move();
        ret.setSrc(this.pos);
        ArrayList<PosXY> dests = this.getAllDestinations();
        Random rand = new Random();
        while(!dests.isEmpty()){
            ret.setDest(dests.remove(rand.nextInt(dests.size())));
            if(this.moveValid(ret.getDest(), state))
                return ret;
        }
        return null;
    }

    /**
     * Get all valid moves from available destinations
     * @param state given state for move validation
     * @return a valid random move
     */
    public ArrayList<PosXY> getAllValidDests(Integer[][] state){
        ArrayList<PosXY> dests = this.getAllDestinations();
        ArrayList<PosXY> ret = new ArrayList<>();
        for(PosXY dest: dests){
            if(this.moveValid(dest, state))
                ret.add(dest);
        }
        return ret;
    }

    /**
     * @return true if square contains piece of same color as this
     */
    public Boolean squareOccupied(PosXY to, Integer[][] state){
        if(this.getColor().equals(PlayerColor.WHITE)){
            if(state[to.getX()][to.getY()] > 0)
                return true;
        }else{
            if(state[to.getX()][to.getY()] < 0)
                return true;
        }
        return false;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }
    public Integer getX(){
        return this.pos.getX();
    }
    public Integer getY(){
        return this.pos.getY();
    }
    public PlayerColor getColor() {
        return color;
    }
    public PosXY getPos() {
        return pos;
    }
    public Boolean getHasMoved(){
        return this.hasMoved;
    }
    public void setPos(PosXY pos) {
        this.pos = pos;
    }
    @Override
    public String toString() {
        return  this.pos.toString() + " " + this.color.toString();
    }
}
