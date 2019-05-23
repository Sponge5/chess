package logic;

import java.util.ArrayList;
import java.util.Random;

public abstract class Piece implements PieceIF{
    private PlayerColor color;
    private PosXY posXY;

    public Piece(PlayerColor color, Integer x, Integer y){
        this.color = color;
        this.posXY = new PosXY(x,y);
    }

    public Integer[][] move(PosXY to, Integer[][] state) {
        state[this.getX()][this.getY()] = 0;
        this.posXY = to;
        return state;
    }

    public PosXY[] getRandomMove(Integer[][] state) {
        PosXY[] ret = new PosXY[2];
        ret[0] = this.getPosXY();
        ArrayList<PosXY> dests = this.getAllDestinations();
        Random rand = new Random();
        while(!dests.isEmpty()){
            ret[1] = dests.remove(rand.nextInt(dests.size()));
            if(this.moveValid(ret[1], state))
                return ret;
        }
        return null;
    }

    public PlayerColor getColor() {
        return color;
    }
    public void setColor(PlayerColor color) {
        this.color = color;
    }
    public Integer getX(){
        return this.posXY.getX();
    }
    public Integer getY(){
        return this.posXY.getY();
    }
    public void setPosXY(PosXY posXY) {
        this.posXY = posXY;
    }
    public PosXY getPosXY() {
        return posXY;
    }
    public void setX(Integer x){
        this.posXY.setX(x);
    }
    public void setY(Integer y){
        this.posXY.setY(y);
    }

    @Override
    public String toString() {
        return  this.posXY.toString() + " " + this.color.toString();
    }
}
