package logic;

public class Move {
    private PosXY src;
    private PosXY dest;

    public Move(){
        this.src = null;
        this.dest = null;
    }
    public Move(Integer srcX, Integer srcY, Integer destX, Integer destY){
        this.src = new PosXY(srcX, srcY);
        this.dest = new PosXY(destX, destY);
    }
    public Move(PosXY src, PosXY dest){
        this.src = src;
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "Move from: " + this.src + " to: " + this.dest;
    }

    public PosXY getSrc() {
        return src;
    }
    public void setSrc(PosXY src) {
        this.src = src;
    }
    public PosXY getDest() {
        return dest;
    }
    public void setDest(PosXY dest) {
        this.dest = dest;
    }
}
