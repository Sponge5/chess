package logic;

/**
 * special moves: castling, pawn transform
 */
public class Move {
    private PosXY src;
    private PosXY dest;
    private Boolean isSpecial;

    public Move(){
        this.src = null;
        this.dest = null;
        this.isSpecial = false;
    }
    public Move(PosXY src, PosXY dest){
        this.src = src;
        this.dest = dest;
        this.isSpecial = false;
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
    public Boolean getIsSpecial(){
        return this.isSpecial;
    }
    public void setIsSpecial(Boolean val){
        this.isSpecial = val;
    }
}