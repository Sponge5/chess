package logic;

public enum PlayerColor {
    WHITE (true),
    BLACK (false);

    public boolean color;

    PlayerColor(boolean color) {
        this.color = color;
    }

    @Override
    public String toString() {
        if(this.color){
            return new String("white");
        }
        return new String("black");
    }
    public PlayerColor otherColor(){
        return this.equals(PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
    }
}


