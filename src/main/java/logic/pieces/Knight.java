package logic.pieces;

import logic.Piece;
import logic.PlayerColor;
import logic.PosXY;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(PlayerColor color, Integer x, Integer y) {
        super(color, x, y);
    }

    public Integer[][] move(PosXY to, Integer[][] state) {
        state[to.getX()][to.getY()] = this.getColor().equals(PlayerColor.WHITE) ?
                2 : -2;
        return super.move(to, state);
    }

    public Boolean moveValid(PosXY to, Integer[][] state) {
        if(state[to.getX()][to.getY()] > 0 && this.getColor().equals(PlayerColor.WHITE))
            return false;
        if(state[to.getX()][to.getY()] < 0 && this.getColor().equals(PlayerColor.BLACK))
            return false;
        if(this.getAllDestinations().contains(to))
            return true;
        return false;
    }

    public ArrayList<PosXY> getAllDestinations() {
        ArrayList<PosXY> ret = new ArrayList<PosXY>();
        int x = this.getX();
        int y = this.getY();
        if(x-2 >= 0) {
            if (y - 1 >= 0)
                ret.add(new PosXY(x - 2, y - 1));
            if (y + 1 <= 7)
                ret.add(new PosXY(x - 2, y + 1));
        }
        if(x-1 >= 0){
            if(y-2 >= 0)
                ret.add(new PosXY(x-1, y-2));
            if(y+2 <= 7)
                ret.add(new PosXY(x-1, y+2));
        }
        if(x+1 <= 7){
            if(y-2 >= 0)
                ret.add(new PosXY(x+1, y-2));
            if(y+2 <= 7)
                ret.add(new PosXY(x+1, y+2));
        }
        if(x+2 <= 7){
            if(y-1 >= 0)
                ret.add(new PosXY(x+2, y-1));
            if(y+1 <= 7)
                ret.add(new PosXY(x+2, y+1));
        }
        return ret;
    }
    @Override
    public String toString() {
        return super.toString() + " Knight";
    }
}
