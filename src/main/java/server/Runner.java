package server;

import client.communication.Utils;
import logic.Board;
import logic.PlayerColor;
import logic.PosXY;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Runner implements Runnable{
    private ServerSocket serverSocket;
    private Socket conSocket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private PlayerColor color;
    private PosXY move[];
    private Board board;

    public Runner(){
        try {
            this.serverSocket = new ServerSocket(8888);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public int getPort(){
        return this.serverSocket.getLocalPort();
    }

    private void setup() throws Exception{
        /* communication */
        this.conSocket = this.serverSocket.accept();
        this.inFromClient = new BufferedReader(
                new InputStreamReader(this.conSocket.getInputStream()));
        this.outToClient = new DataOutputStream(
                this.conSocket.getOutputStream());
        /* logic */
        //default board
        this.board = new Board();
        this.move = new PosXY[2];
        this.color = PlayerColor.WHITE;
    }

    private void runGame() throws Exception{
        while(true){
            /* recv move
             *TODO check validity
             *TODO send confirmation
             *TODO send move to other client (or back)
             */
            recvMove();
            //this.outToClient.writeBytes(capitalizedSentence);
        }
    }
    public void recvMove() throws Exception{
        while(true) {
            String moveString = this.inFromClient.readLine();
            this.move = Utils.posFromString(moveString);
            if(this.board.isMoveLegal(this.color, this.move)){
                this.outToClient.writeBytes("ok\n");
                break;
            }
            this.outToClient.writeBytes("nope\n");
        }
    }

    public void run() {
        try {
            setup();
            runGame();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
