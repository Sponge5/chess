package logic;

public class PosXY {
    private Integer x;
    private Integer y;

    public PosXY(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }
    public PosXY(String pos){
        this.x = 9 - Character.getNumericValue(pos.charAt(1));
        switch(pos.charAt(0)){
            case 'A':
                this.y = 0;
                break;
            case 'B':
                this.y = 1;
                break;
            case 'C':
                this.y = 2;
                break;
            case 'D':
                this.y = 3;
                break;
            case 'E':
                this.y = 4;
                break;
            case 'F':
                this.y = 5;
                break;
            case 'G':
                this.y = 6;
                break;
            case 'H':
                this.y = 7;
                break;
            default:
                this.y = -1;
        }
    }
    public PosXY(){
        this.x = -1;
        this.y = -1;
    }

    @Override
    public String toString() {
        String pos;
        switch(this.y.intValue()){
            case 0:
                pos = "A";
                break;
            case 1:
                pos = "B";
                break;
            case 2:
                pos = "C";
                break;
            case 3:
                pos = "D";
                break;
            case 4:
                pos = "E";
                break;
            case 5:
                pos = "F";
                break;
            case 6:
                pos = "G";
                break;
            case 7:
                pos = "H";
                break;
            default:
                pos = "";
        }
        return pos + (8 - this.x);
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
