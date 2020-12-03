package client.communication;

import logic.Move;
import logic.PlayerColor;
import logic.PosXY;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientCommunication implements Runnable{
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
        System.out.println("[ClientComm] construct address: " + address);
        System.out.println("[ClientComm] construct port: " + port.toString());
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
                System.out.println("[ClientCommunication] Socket() threw exception");
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
            System.out.println("[ClientCommunication] client sending move to server");
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
     * @throws Exception
     */
    public void recvMove() throws Exception{
        System.out.println("[ClientComm] waiting for line");
        String moveString = this.inFromServer.readLine();
        System.out.println("[ClientComm] received line " + moveString);
        this.inMove.put(posFromString(moveString));
    }

    /**
     * Convert string to Move
     * @param s 4 numbers separated by spaces
     * @return converted string as Move
     */
    public static Move posFromString(String s){
        String[] nums = s.split(" ");
        Move move = new Move(
                new PosXY(Integer.parseInt(nums[0]), Integer.parseInt(nums[1])),
                new PosXY(Integer.parseInt(nums[2]), Integer.parseInt(nums[3])));
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
