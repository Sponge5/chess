package logic;

public abstract class Piece implements PieceIF{
    private PlayerColor color;
    private PosXY posXY;

    public Piece(PlayerColor color, Integer x, Integer y){
        this.color = color;
        this.posXY = new PosXY(x,y);
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
}
