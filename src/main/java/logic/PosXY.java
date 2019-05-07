package logic;

public class PosXY {
    private Integer x;
    private Integer y;

    public PosXY(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }
    public PosXY(){
        this.x = -1;
        this.y = -1;
    }

    @Override
    public String toString() {
        return this.x.toString() + " " + this.y.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this.x.equals(((PosXY) obj).getX()) && this.y.equals(((PosXY) obj).getY()))
            return true;
        return false;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
