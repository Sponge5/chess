package logic;

public abstract class Piece implements PieceIF{
    Boolean color;
    PosXY posXY;

    public Piece(Boolean color, Integer x, Integer y){
        this.color = color;
        this.posXY = new PosXY(x,y);
    }
}
