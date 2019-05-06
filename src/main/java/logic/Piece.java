package logic;

public abstract class Piece implements PieceIF{
    PlayerColor color;
    PosXY posXY;

    public Piece(PlayerColor color, Integer x, Integer y){
        this.color = color;
        this.posXY = new PosXY(x,y);
    }
}
