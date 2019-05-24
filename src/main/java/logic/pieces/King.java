package logic.pieces;

import logic.Piece;
import logic.PlayerColor;
import logic.PosXY;

import java.util.ArrayList;
import java.util.Random;

public class King extends Piece {
    public Boolean hasMoved;

    public King(PlayerColor color, Integer x, Integer y) {
        super(color, x, y);
        this.hasMoved = false;
    }

    @Override
    public Integer[][] move(PosXY to, Integer[][] state) {
        state[to.getX()][to.getY()] = this.getColor().equals(PlayerColor.WHITE) ?
                6 : -6;
        return super.move(to, state);
    }

    public Boolean moveValid(PosXY to, Integer[][] state) {
        if(!this.getAllDestinations().contains(to))
            return false;
        if(state[to.getX()][to.getY()] < 0 && this.getColor().equals(PlayerColor.BLACK))
            return false;
        if(state[to.getX()][to.getY()] > 0 && this.getColor().equals(PlayerColor.WHITE))
            return false;
        return true;
    }

    public ArrayList<PosXY> getAllDestinations() {
        ArrayList<PosXY> ret = new ArrayList<PosXY>();
        int x = this.getX();
        int y = this.getY();
        if(x-1 >= 0){
            ret.add(new PosXY(x-1, y));
            if(y-1 >= 0)
                ret.add(new PosXY(x-1, y-1));
            if(y+1 <= 7)
                ret.add(new PosXY(x-1, y+1));
        }
        if(x+1 <= 7){
            ret.add(new PosXY(x+1, y));
            if(y-1 >= 0)
                ret.add(new PosXY(x + 1, y - 1));
            if(y+1 <= 7)
                ret.add(new PosXY(x + 1, y + 1));
        }
        if (y - 1 >= 0)
            ret.add(new PosXY(x, y-1));
        if(y+1 <= 7)
            ret.add(new PosXY(x, y+1));
        return ret;
    }
    @Override
    public String toString() {
        return super.toString() + " King";
    }
}
