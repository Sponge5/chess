package server;

import client.communication.ClientCommunication;
import logic.Board;
import logic.PlayerColor;
import logic.PosXY;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Connection extends Thread{
    private ServerSocket serverSocket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private Socket conSocket;
    private Boolean twoClients;
    private LinkedBlockingQueue<PosXY[]> inMove, outMove;
    private Board board;
    private PlayerColor color;

    public Connection(ServerSocket serverSocket, Boolean twoClients, PlayerColor color) {
        this.serverSocket = serverSocket;
        this.twoClients = twoClients;
        this.board = new Board();
        this.color = color;
        this.inMove = new LinkedBlockingQueue<PosXY[]>(1);
        this.outMove = new LinkedBlockingQueue<PosXY[]>(1);
    }
    public void run() {
        try {
            this.conSocket = this.serverSocket.accept();
            this.inFromClient = new BufferedReader(
                    new InputStreamReader(this.conSocket.getInputStream()));
            this.outToClient = new DataOutputStream(
                    this.conSocket.getOutputStream());
            if(this.twoClients){
                while(true){
                    System.out.println("[Connection] Server waiting for move from client...");
                    recvMove();
                    System.out.println("[Connection] Server sending move to client");
                    sendMove();
                }
            }else{
                /* single client */
                while (true) {
                    System.out.println("[Connection] Server waiting for move from client...");
                    recvMove();
                    System.out.println("[Connection] board:\n" + this.board.toString());
                    this.color = this.color.equals(PlayerColor.WHITE) ?
                            PlayerColor.BLACK : PlayerColor.WHITE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void recvMove() throws Exception {
        PosXY[] move;
        while (true) {
            String moveString = this.inFromClient.readLine();
            System.out.println("[Connection] Server received move:\n" + moveString);
            move = ClientCommunication.posFromString(moveString);
            if (this.board.isMoveLegal(this.color, move)) {
                this.board.move(move);
                this.outToClient.writeBytes("ok\n");
                break;
            }
            this.outToClient.writeBytes("nope\n");
        }
        if(this.twoClients)
            this.inMove.put(move);
    }
    private void sendMove() throws Exception{
        PosXY[] move = this.outMove.take();
        this.outToClient.writeBytes(move[0].toString() + " " + move[1].toString());
    }
    public LinkedBlockingQueue<PosXY[]> getInMove() {
        return inMove;
    }
    public LinkedBlockingQueue<PosXY[]> getOutMove() {
        return outMove;
    }
    public Board getBoard() {
        return board;
    }
}
