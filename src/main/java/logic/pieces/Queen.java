package logic.pieces;

import logic.Piece;
import logic.PlayerColor;
import logic.PosXY;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(PlayerColor color, Integer x, Integer y) {
        super(color, x, y);
    }

    public Integer[][] move(PosXY to, Integer[][] state) {
        state[to.getX()][to.getY()] = this.getColor().equals(PlayerColor.WHITE) ?
                5 : -5;
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
        if(x == to.getX()){
            while(true){
                if(y < to.getY())
                    ++y;
                else
                    --y;
                if(y == to.getY())
                    return true;
                if(!state[x][y].equals(0))
                    return false;
            }
        }else if(y == to.getY()){
            while(true){
                if(x < to.getX())
                    ++x;
                else
                    --x;
                if(x == to.getX())
                    return true;
                if(!state[x][y].equals(0))
                    return false;
            }
        }
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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(x == i || y == j)
                    ret.add(new PosXY(i, j));
            }
        }
        for (int i = 1; i < 7; i++) {
            if(x-i >= 0){
                if(y-i >= 0)
                    ret.add(new PosXY(x-i, y-i));
                if(y+i <= 7)
                    ret.add(new PosXY(x-i, y+i));
            }
            if(x+i <= 7){
                if(y-i >= 0)
                    ret.add(new PosXY(x+i, y-i));
                if(y+i <= 7)
                    ret.add(new PosXY(x+i, y+i));
            }
        }
        return ret;
    }
}
