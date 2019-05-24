package logic;


import javafx.scene.layout.Pane;
import logic.pieces.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
        this.pieces = new ArrayList<>();
        setPieces();
    }
    public void setPieces(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (state[i][j]){
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
    public PosXY[] getComputerMove(PlayerColor color){
        PosXY[] ret = null;
        Random rand = new Random();
        ArrayList<Piece> tempPieces = new ArrayList<Piece>(this.pieces);
        while(ret == null && !tempPieces.isEmpty()) {
            Piece p = tempPieces.remove(rand.nextInt(tempPieces.size()));
            if(p.getColor().equals(color.otherColor()))
                continue;
            ret = p.getRandomMove(state);
        }
        return ret;
    }
    /*TODO en passant */
    /*TODO castling */
    /*TODO check */
    /*TODO check mate */
    public Boolean isMoveLegal(PlayerColor color, PosXY[] move){
        for (Piece p :
                this.pieces) {
            if (p.getPosXY().equals(move[0]) && p.getColor().equals(color)) {
                /* check if destination is attacked for king move*/
                if(p instanceof King){
                    if(destAttacked(color, move[1]))
                        return false;
//                    /* castling */
//                    if(!((King) p).hasMoved && moveIsCastle(p, move))
//                        return true;
                }
                return p.moveValid(move[1], this.state);
            }
        }
        return false;
    }

    /**
     *
     * @param color color of our piece
     * @param dest destination of our piece
     * @return true if enemy piece is attacking square at dest
     */
    public Boolean destAttacked(PlayerColor color, PosXY dest){
        for (Piece p :
                this.pieces) {
            if(p.getColor().equals(color))
                continue;
            /* for pawn only diagonal moves */
            if(p instanceof Pawn){
                Integer[][] newState = new Integer[this.state.length][];
                for (int i = 0; i < this.state.length; i++) {
                    newState[i] = this.state[i].clone();
                }
                newState[dest.getX()][dest.getY()] = color.equals(PlayerColor.WHITE) ?
                        1 : -1;
                if(p.moveValid(dest, newState)) {
                    System.out.println("[Board] square " + dest.toString()
                            + " attacked by " + p.toString());
                    return true;
                }
            }else if(p.moveValid(dest, this.state)) {
                System.out.println("[Board] square " + dest.toString()
                        + " attacked by " + p.toString());
                return true;
            }
        }
        return false;
    }
    /*TODO return bool and check in runner */
    /*TODO let player choose pawn replacement */
    public void move(PosXY[] move){
        Piece sourcePiece = getPiece(move[0]);
        Piece destPiece = getPiece(move[1]);
        if(sourcePiece == null){
            System.out.println("[Board] Couldn't find source piece");
            return;
        }
        this.pieces.remove(sourcePiece);
        /* Change Pawn */
        this.state = sourcePiece.move(move[1], this.state);
        sourcePiece.setPosXY(move[1]);
        if(sourcePiece instanceof Pawn &&
                ((sourcePiece.getColor().equals(PlayerColor.WHITE)
                 && move[1].getX().equals(0))
                ||(sourcePiece.getColor().equals(PlayerColor.BLACK)
                 && move[1].getX().equals(7)))){
                sourcePiece = new Queen(sourcePiece.getColor(),
                        move[1].getX(), move[1].getY());
        }
        this.pieces.add(sourcePiece);
        if(destPiece == null)
            System.out.println("[Board] Couldn't find destination piece");
        else
            this.pieces.remove(destPiece);
    }

    Piece getPiece(PosXY pos){
        for (Piece p:
                this.pieces) {
            if(p.getPosXY().equals(pos))
                return p;
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(String.format("%2d", state[i][0]));
            for (int j = 1; j < 8; j++) {
                sb.append(String.format(" %2d", state[i][j]));
            }
            sb.append("\n");
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }
    public Integer[][] getState() {
        return state;
    }
    public boolean isOver() {
        return this.isOver;
    }
    public void setState(Integer[][] state) {
        this.state = state;
    }
}
