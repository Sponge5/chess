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
        return new Integer[0][];
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
        return null;
    }

    public PosXY[] getRandomMove() {
        return new PosXY[0];
    }
}
