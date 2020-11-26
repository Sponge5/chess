package server;

import client.communication.ClientCommunication;
import logic.Board;
import logic.Move;
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
    private LinkedBlockingQueue<Move> inMove, outMove;
    private Board board;
    private PlayerColor color;

    public Connection(ServerSocket serverSocket, Boolean twoClients, PlayerColor color) {
        this.serverSocket = serverSocket;
        this.twoClients = twoClients;
        this.board = new Board();
        this.color = color;
        this.inMove = new LinkedBlockingQueue<>(1);
        this.outMove = new LinkedBlockingQueue<>(1);
    }
    public void run() {
        try {
            System.out.println("[Connection @ " + this.serverSocket.getLocalPort() +
                    "] waiting for connection from client");
            this.conSocket = this.serverSocket.accept();
            System.out.println("[Connection @ " + this.serverSocket.getLocalPort() +
                    "] accepted connection from client");
            this.inFromClient = new BufferedReader(
                    new InputStreamReader(this.conSocket.getInputStream()));
            this.outToClient = new DataOutputStream(
                    this.conSocket.getOutputStream());
            if(this.twoClients){
                if(this.color.equals(PlayerColor.BLACK)){
                    sendMove();
                }
                while(true){
                    System.out.println("[Connection @ " + this.serverSocket.getLocalPort() +
                    "] Server waiting for move from client...");
                    recvMove();
                    sendMove();
                }
            }else{
                /* single client */
                while (true) {
                    System.out.println("[Connection @ " + this.serverSocket.getLocalPort() +
                    "] Server waiting for move from client...");
                    recvMove();
                    this.color = this.color.equals(PlayerColor.WHITE) ?
                            PlayerColor.BLACK : PlayerColor.WHITE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void recvMove() throws Exception {
        Move move;
        while (true) {
            String moveString = this.inFromClient.readLine();
            System.out.println("[Connection @ " + this.serverSocket.getLocalPort() +
                    "] Server received move:\n" + moveString);
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
        Move move = this.outMove.take();
        System.out.println("[Connection @ " + this.serverSocket.getLocalPort() +
                "] Server sending move to client");
        this.outToClient.writeBytes(move.getSrc().toString() + " " + move.getDest().toString());
    }
    public LinkedBlockingQueue<Move> getInMove() {
        return inMove;
    }
    public LinkedBlockingQueue<Move> getOutMove() {
        return outMove;
    }
    public Board getBoard() {
        return board;
    }
}
