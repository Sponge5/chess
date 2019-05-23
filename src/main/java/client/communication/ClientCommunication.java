package client.communication;

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
    private LinkedBlockingQueue<PosXY[]> outMove;
    private LinkedBlockingQueue<PosXY[]> inMove;
    private LinkedBlockingQueue<Boolean> moveAcceptedByServer;
    private Boolean remote;
    private PlayerColor color;

    public ClientCommunication(String address, Integer port, Boolean remote, PlayerColor color){
        this.address = address;
        this.port = port;
        this.remote = remote;
        this.color = color;
        this.outMove = new LinkedBlockingQueue<PosXY[]>(1);
        this.inMove = new LinkedBlockingQueue<PosXY[]>(1);
        this.moveAcceptedByServer = new LinkedBlockingQueue<Boolean>(1);

    }
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
     * @throws Exception
     */
    public void connect() throws Exception {
        if(!(this.address == null)){
            try{
                this.clientSocket = new Socket(this.address, this.port);
            }catch (Exception e){
                System.out.println("[ClientCommunication] Socket() threw exception");
                this.clientSocket = new Socket("localhost", this.port);
            }
        }else
            this.clientSocket = new Socket("localhost", this.port);
        this.outToServer = new DataOutputStream(
                this.clientSocket.getOutputStream()
        );
        this.inFromServer = new BufferedReader(
                new InputStreamReader(this.clientSocket.getInputStream())
        );
    }

    /**
     * sending move and receiving move from server
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
            PosXY move[] = this.outMove.take();
            System.out.println("[ClientCommunication] client sending move to server");
            this.outToServer.writeBytes(move[0].toString() + " " + move[1].toString() + "\n");
            String response = this.inFromServer.readLine();
            if (response.equals("ok")) {
                this.moveAcceptedByServer.put(true);
                break;
            }else{
                this.moveAcceptedByServer.put(false);
            }
        }
    }
    public void recvMove() throws Exception{
        String moveString = this.inFromServer.readLine();
        this.inMove.put(posFromString(moveString));
    }
    public static PosXY[] posFromString(String s){
        String[] nums = s.split(" ");
        PosXY[] move = new PosXY[2];
        move[0] = new PosXY(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
        move[1] = new PosXY(Integer.parseInt(nums[2]), Integer.parseInt(nums[3]));
        return move;
    }

    public LinkedBlockingQueue<PosXY[]> getOutMove() {
        return outMove;
    }
    public LinkedBlockingQueue<PosXY[]> getInMove() {
        return inMove;
    }

    public LinkedBlockingQueue<Boolean> getMoveAcceptedByServer() {
        return moveAcceptedByServer;
    }
}
