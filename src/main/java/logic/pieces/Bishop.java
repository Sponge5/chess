package logic.pieces;

import logic.Piece;
import logic.PlayerColor;
import logic.PosXY;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(PlayerColor color, Integer x, Integer y) {
        super(color, x, y);
    }

    @Override
    public Integer[][] move(PosXY to, Integer[][] state) {
        state[to.getX()][to.getY()] = this.getColor().equals(PlayerColor.WHITE) ?
                3 : -3;
        return super.move(to, state);
    }

    public Boolean moveValid(PosXY to, Integer[][] state) {
        if(!this.getAllDestinations().contains(to))
            return false;
        if(state[to.getX()][to.getY()] > 0 && this.getColor().equals(PlayerColor.WHITE))
            return false;
        else if(state[to.getX()][to.getY()] < 0 && this.getColor().equals(PlayerColor.BLACK))
            return false;
        int x = this.getX();
        int y = this.getY();
        for (int i = 1; i < 7; i++) {
            if(to.getX() < x)
                x--;
            else
                x++;
            if(to.getY() < y)
                y--;
            else
                y++;
            if(this.getPosXY().equals(to))
                return true;
            if(!state[x][y].equals(0))
                return false;
        }
        return false;
    }

    public ArrayList<PosXY> getAllDestinations() {
        ArrayList<PosXY> ret = new ArrayList<PosXY>();
        int x = this.getX();
        int y = this.getY();
        for (int i = 1; i < 7; i++) {
            if(x-i >= 0 && y-i >= 0)
                ret.add(new PosXY(x-i, y-i));
            if(x-i >= 0 && y+i <= 7)
                ret.add(new PosXY(x-i, y+i));
            if(x+i <= 7 && y-i >= 0)
                ret.add(new PosXY(x+i, y-i));
            if(x+i <= 7 && y+i <= 7)
                ret.add(new PosXY(x+i, y+i));
        }
        return ret;
    }
}
