package logic;

public class PosXY {
    private Integer x;
    private Integer y;

    public PosXY(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return this.x.toString() + ", " + this.y.toString();
    }
}
