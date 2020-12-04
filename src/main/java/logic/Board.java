package logic;


import logic.pieces.*;

import java.util.ArrayList;
import java.util.Random;

/*TODO castling */

/*TODO check */
/*TODO blocking check */
/*TODO discovered check */
/*TODO check mate */

/*TODO en passant */
public class Board {
    private ArrayList<Piece> pieces;
    private Integer[][] state;
    private boolean isOver;

    public Board(){
        /* default setup */
        this.isOver = false;
        this.state = new Integer[][]{
                {-4,-2,-3,-5,-6,-3,-2,-4},
                {-1,-1,-1,-1,-1,-1,-1,-1},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 0, 0, 0, 0, 0, 0},
                { 1, 1, 1, 1, 1, 1, 1, 1},
                { 4, 2, 3, 5, 6, 3, 2, 4}
        };
        this.pieces = new ArrayList<>();
        setPieces();
    }

    public Board(Integer[][] state){
        this.isOver = false;
        this.state = state;
        setPieces();
    }

    public void setPieces(){
        this.pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (this.state[i][j]){
                    case 1:
                        this.pieces.add(new Pawn(PlayerColor.WHITE, i, j));
                        break;
                    case -1:
                        this.pieces.add(new Pawn(PlayerColor.BLACK, i, j));
                        break;
                    case 2:
                        this.pieces.add(new Knight(PlayerColor.WHITE, i, j));
                        break;
                    case -2:
                        this.pieces.add(new Knight(PlayerColor.BLACK, i, j));
                        break;
                    case 3:
                        this.pieces.add(new Bishop(PlayerColor.WHITE, i, j));
                        break;
                    case -3:
                        this.pieces.add(new Bishop(PlayerColor.BLACK, i, j));
                        break;
                    case 4:
                        this.pieces.add(new Rook(PlayerColor.WHITE, i, j));
                        break;
                    case -4:
                        this.pieces.add(new Rook(PlayerColor.BLACK, i, j));
                        break;
                    case 5:
                        this.pieces.add(new Queen(PlayerColor.WHITE, i, j));
                        break;
                    case -5:
                        this.pieces.add(new Queen(PlayerColor.BLACK, i, j));
                        break;
                    case 6:
                        this.pieces.add(new King(PlayerColor.WHITE, i, j));
                        break;
                    case -6:
                        this.pieces.add(new King(PlayerColor.BLACK, i, j));
                        break;
                }
            }
        }
    }

    public Move getComputerMove(PlayerColor color){
        Move ret = null;
        Random rand = new Random();
        ArrayList<Piece> tempPieces = new ArrayList<>(this.pieces);
        while(ret == null && !tempPieces.isEmpty()) {
            Piece p = tempPieces.remove(rand.nextInt(tempPieces.size()));
            if(p.getColor().equals(color.otherColor()))
                continue;
            ret = p.getRandomMove(this.state);
        }
        return ret;
    }

    public Boolean isMoveLegal(PlayerColor color, Move move){
        for (Piece p :
                this.pieces) {
            if (p.getPos().equals(move.getSrc()) && p.getColor().equals(color)) {
                /* check if destination is attacked for king move*/
                if(p instanceof King){
                    if(destAttacked(color, move.getDest())) return false;
                    if(moveIsCastle(p, move)) return true;
                }
                return p.moveValid(move.getDest(), this.state);
            }
        }
        return false;
    }

    /**
     * moveIsCastle is a bypass function for Piece.moveValid() which means
     * we need to take care of all edgecases
     * @param p
     * @param move
     * @return
     */
    private Boolean moveIsCastle(Piece p, Move move){
        if(p.getHasMoved().equals(true)) return false;
        Piece rook;
        if(p.getColor().equals(PlayerColor.WHITE)){
            if(move.getDest().getX().equals(7) && move.getDest().getY().equals(2)){
                /* find corresponding rook */
                rook = getRook(PlayerColor.WHITE, new PosXY(7, 0));
                if(rook == null) return false;
                if(destAttacked(PlayerColor.WHITE, new PosXY(7, 3))) return false;
                if(destAttacked(PlayerColor.WHITE, new PosXY(7, 2))) return false;
                /* TODO this fucking doesn't return true or IDK */
                return true;
            } else if (move.getDest().getX().equals(6) && move.getDest().getY().equals(7)) {
                rook = getRook(PlayerColor.WHITE, new PosXY(7,7));
                if(rook == null) return false;
                if(destAttacked(PlayerColor.WHITE, new PosXY(7, 5))) return false;
                if(destAttacked(PlayerColor.WHITE, new PosXY(7, 6))) return false;
                return true;
            }
        }else if(p.getColor().equals(PlayerColor.BLACK)){
            if(move.getDest().getX().equals(2) && move.getDest().getY().equals(0)){
                rook = getRook(PlayerColor.BLACK, new PosXY(0,0));
                if(rook == null) return false;
                if(destAttacked(PlayerColor.BLACK, new PosXY(0, 3))) return false;
                if(destAttacked(PlayerColor.BLACK, new PosXY(0, 2))) return false;
                return true;
            }else if (move.getDest().getX().equals(6) && move.getDest().getY().equals(7)){
                rook = getRook(PlayerColor.BLACK, new PosXY(0,7));
                if(rook == null) return false;
                if(destAttacked(PlayerColor.BLACK, new PosXY(0, 5))) return false;
                if(destAttacked(PlayerColor.BLACK, new PosXY(0, 6))) return false;
                return true;
            }
        }
        return false;
    }

    private Piece getRook(PlayerColor color, PosXY pos){
        for(Piece p: this.pieces) {
            if (p instanceof Rook
                    && p.getColor().equals(color)
                    && !p.getHasMoved()
                    && p.getPos().equals(pos))
                return p;
        }
        return null;
    }

    /**
     * @param color color of our piece
     * @param dest destination of our piece
     * @return true if enemy piece is attacking square at dest
     */
    public Boolean destAttacked(PlayerColor color, PosXY dest){
        String destAttStr = new String("[Board] square " + dest.toString() + " attacked by ");
        for (Piece p : this.pieces) {
            if(p.getColor().equals(color))
                continue;
            /* for pawn only diagonal moves */
            if(p instanceof Pawn){
                Integer[][] newState = new Integer[this.state.length][];
                for (int i = 0; i < this.state.length; i++) {
                    newState[i] = this.state[i].clone();
                }
                newState[dest.getX()][dest.getY()] = color.equals(PlayerColor.WHITE) ? 1 : -1;
                if(p.moveValid(dest, newState)) {
                    System.out.println(destAttStr + p.toString());
                    return true;
                }
            }else if(p.moveValid(dest, this.state)) {
                System.out.println(destAttStr + p.toString());
                return true;
            }
        }
        return false;
    }

    /**
     * executes move on this.state and this.pieces
     * @param move input move
     */
    public void move(Move move){
        Piece sourcePiece = getPiece(move.getSrc());
        Piece destPiece = getPiece(move.getDest());
        if(sourcePiece == null){
            System.out.println("[Board] Couldn't find source piece");
            return;
        }
        this.pieces.remove(sourcePiece);
        /* castling */
        if(moveIsCastle(sourcePiece, move)) {
            move.setIsSpecial(true);
            castle(move);
        }
        this.state = sourcePiece.move(move.getDest(), this.state);
        sourcePiece.setPos(move.getDest());
        /* Change Pawn */
        if(sourcePiece instanceof Pawn &&
                ((sourcePiece.getColor().equals(PlayerColor.WHITE)
                        && move.getDest().getX().equals(0))
                        ||(sourcePiece.getColor().equals(PlayerColor.BLACK)
                        && move.getDest().getX().equals(7)))){
            sourcePiece = new Queen(sourcePiece.getColor(),
                    move.getDest().getX(), move.getDest().getY());
            this.state[move.getDest().getX()][move.getDest().getY()] =
                    sourcePiece.getColor().equals(PlayerColor.WHITE) ?
                            5 : -5;
            move.setIsSpecial(true);
        }
        this.pieces.add(sourcePiece);
        if(!(destPiece == null))
            this.pieces.remove(destPiece);
    }

    /**
     * update this.state and this.pieces for castle
     * @param move
     */
    public void castle(Move move){
        Piece rook;
        PosXY rookDest;
        if(this.state[move.getSrc().getX()][move.getSrc().getY()] == PieceValue.KING.value){
            if(move.getDest().getX().equals(7) && move.getDest().getY().equals(2)){
                /* find corresponding rook */
                rook = getRook(PlayerColor.WHITE, new PosXY(7,0));
                if(rook == null) return;
                this.pieces.remove(rook);
                rookDest = new PosXY(7, 3);
                this.state = rook.move(rookDest, this.state);
                rook.setPos(rookDest);
                this.pieces.add(rook);
            } else if (move.getDest().getX().equals(7) && move.getDest().getY().equals(6)) {
                rook = getRook(PlayerColor.WHITE, new PosXY(7,7));
                if(rook == null) return;
                this.pieces.remove(rook);
                rookDest = new PosXY(7, 5);
                this.state = rook.move(rookDest, this.state);
                rook.setPos(rookDest);
                this.pieces.add(rook);
            }
        }else if(this.state[move.getSrc().getX()][move.getSrc().getY()] == -PieceValue.KING.value){
            if(move.getDest().getX().equals(0) && move.getDest().getY().equals(2)){
                rook = getRook(PlayerColor.BLACK, new PosXY(0,0));
                if(rook == null) return;
                this.pieces.remove(rook);
                rookDest = new PosXY(0, 3);
                this.state = rook.move(rookDest, this.state);
                rook.setPos(rookDest);
                this.pieces.add(rook);
            }else if (move.getDest().getX().equals(0) && move.getDest().getY().equals(6)){
                rook = getRook(PlayerColor.BLACK, new PosXY(0,7));
                this.pieces.remove(rook);
                rookDest = new PosXY(0, 5);
                this.state = rook.move(rookDest, this.state);
                rook.setPos(rookDest);
                this.pieces.add(rook);
            }
        }
    }

    public Piece getPiece(PosXY pos){
        for (Piece p:
                this.pieces) {
            if(p.getPos().equals(pos))
                return p;
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(String.format("%2d", this.state[i][0]));
            for (int j = 1; j < 8; j++) {
                sb.append(String.format(" %2d", this.state[i][j]));
            }
            sb.append("\n");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }
    public Integer[][] getState() {
        return this.state;
    }
    public boolean isOver() {
        return this.isOver;
    }
    public void setState(Integer[][] state) {
        this.state = state;
    }
}
