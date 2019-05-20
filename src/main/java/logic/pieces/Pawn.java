package logic.pieces;

import logic.Piece;
import logic.PlayerColor;
import logic.PosXY;

import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(PlayerColor color, Integer x, Integer y) {
        super(color, x, y);
    }

    public Integer[][] move(PosXY to, Integer[][] state) {
        state[to.getX()][to.getY()] = this.getColor().equals(PlayerColor.WHITE) ?
                1 : -1;
        return super.move(to, state);
    }

    public Boolean moveValid(PosXY to, Integer[][] state) {
        if(this.getY().equals(to.getY())){
            if(this.getColor().equals(PlayerColor.WHITE)) {
                if (this.getX().equals(to.getX()+1)){
                    //basic move
                    return true;
                }else if(this.getX().equals(to.getX()+2) && this.getX().equals(6)){
                    //double move
                    return true;
                }
            }else{
                if(this.getX().equals(to.getX()-1)){
                    //basic black move
                    return true;
                }else if(this.getX().equals(to.getX()-2) && this.getX().equals(1)){
                    //double black move
                    return true;
                }
            }
        }else if(this.getX().equals(to.getX()-1)
                && (this.getY().equals(to.getY()+1)
                || this.getY().equals(to.getY()-1))){
            //take
            return true;
        }
        return false;
    }

    public ArrayList<PosXY> getAllDestinations() {
        ArrayList<PosXY> ret = new ArrayList<PosXY>();
        int x = this.getX();
        int y = this.getY();
        if(x+1 <= 7) {
            ret.add(new PosXY(x + 1, y));
            if(y-1 >= 0)
                ret.add(new PosXY(x+1, y-1));
            if(y+1 <= 7)
                ret.add(new PosXY(x+1, y+1));
        }
        if(x+2 <= 7)
            ret.add(new PosXY(x+2, y));
        if(x-1 >= 0){
            ret.add(new PosXY(x-1, y));
            if(y-1 >= 0)
                ret.add(new PosXY(x-1, y-1));
            if(y+1 <= 7)
                ret.add(new PosXY(x-1, y+1));
        }
        if(x-2 >= 0)
            ret.add(new PosXY(x-2, y));
        return ret;
    }
}
