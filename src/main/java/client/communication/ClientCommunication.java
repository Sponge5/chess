package client.communication;

import logic.Move;
import logic.PlayerColor;
import logic.PosXY;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ClientCommunication implements Runnable{
    private static final Logger LOGGER = Logger.getLogger("[ClientCommunication]");
    private String address;
    private Integer port;
    private Socket clientSocket;
    private BufferedReader inFromServer;
    private DataOutputStream outToServer;
    private LinkedBlockingQueue<Move> outMove;
    private LinkedBlockingQueue<Move> inMove;
    private LinkedBlockingQueue<Boolean> moveAcceptedByServer;
    private Boolean remote;
    private PlayerColor color;

    /**
     * Constructor
     * @param address server IP address
     * @param port server port being listened at
     * @param remote true for remote game, false for local
     * @param color of player playing on client
     */
    public ClientCommunication(String address, Integer port, Boolean remote, PlayerColor color){
        LOGGER.log(Level.INFO, "Construct address: " + address);
        LOGGER.log(Level.INFO, "Construct port: " + port.toString());
        this.address = address;
        this.port = port;
        this.remote = remote;
        this.color = color;
        this.outMove = new LinkedBlockingQueue<>(1);
        this.inMove = new LinkedBlockingQueue<>(1);
        this.moveAcceptedByServer = new LinkedBlockingQueue<>(1);
    }

    /**
     * runs specific communication, for local game there's 2 players on 1 client
     */
    public void run() {
        try {
            connect();
            if(this.remote)
                remoteGame();
            else
                localGame();
            this.clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * connect to server and setup stream communication
     * @throws Exception if Socket() fails - maybe port occupied
     */
    private void connect() throws Exception {
        if(this.address == null){
            this.clientSocket = new Socket("localhost", this.port);
        }else {
            try {
                this.clientSocket = new Socket(this.address, this.port);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Socket() threw exception");
                this.clientSocket = new Socket("localhost", this.port);
            }
        }
        this.outToServer = new DataOutputStream(
                this.clientSocket.getOutputStream());
        this.inFromServer = new BufferedReader(
                new InputStreamReader(this.clientSocket.getInputStream()));
    }

    /**
     * sending move to and receiving move from server
     * @throws Exception
     */
    public void remoteGame() throws Exception {
        if(this.color.equals(PlayerColor.BLACK)) {
            recvMove();
        }
        while(true){
            sendMove();
            recvMove();
        }
    }

    /**
     * only sending move to server
     * @throws Exception
     */
    public void localGame() throws Exception{
        while(true){
            sendMove();
        }
    }

    /**
     * keeps sending moves until one is accepted
     * @throws Exception
     */
    public void sendMove() throws Exception{
        while(true) {
            /* move is valid according to board logic */
            Move move = this.outMove.take();
            LOGGER.log(Level.ALL, "Client sending move to server");
            this.outToServer.writeBytes(move.getSrc().toString() + " " + move.getDest().toString() + "\n");
            String response = this.inFromServer.readLine();
            if (response.equals("ok")) {
                this.moveAcceptedByServer.put(true);
                break;
            }else{
                this.moveAcceptedByServer.put(false);
            }
        }
    }

    /**
     * Receive move from server
     * @throws Exception readLine() interrupt
     */
    public void recvMove() throws Exception{
        LOGGER.log(Level.ALL, "Waiting for line");
        String moveString = this.inFromServer.readLine();
        LOGGER.log(Level.INFO, "Received line " + moveString);
        this.inMove.put(moveFromString(moveString));
    }

    /**
     * Convert string to Move
     * @param s two position in string form (e.g. "A1 A3")
     * @return converted string as Move
     */
    public static Move moveFromString(String s){
        String[] poss = s.split(" ");
        Move move = new Move(new PosXY(poss[0]), new PosXY(poss[1]));
        return move;
    }

    public LinkedBlockingQueue<Move> getOutMove() {
        return outMove;
    }
    public LinkedBlockingQueue<Move> getInMove() {
        return inMove;
    }
    public LinkedBlockingQueue<Boolean> getMoveAcceptedByServer() {
        return moveAcceptedByServer;
    }
}
