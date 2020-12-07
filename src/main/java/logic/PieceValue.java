package logic;

public enum PieceValue {
    PAWN (1),
    KNIGHT (2),
    BISHOP (3),
    ROOK (4),
    QUEEN (5),
    KING (6);

    public int n;

    PieceValue(int value){
        this.n = value;
    }

    @Override
    public String toString() {
        switch(this){
            case PAWN:
                return "Pawn";
            case KNIGHT:
                return "Knight";
            case BISHOP:
                return "Bishop";
            case ROOK:
                return "Rook";
            case QUEEN:
                return "Queen";
            case KING:
                return "King";
        }
        return null;
    }
}
