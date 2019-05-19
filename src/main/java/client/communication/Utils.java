package client.communication;

import logic.PosXY;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Utils implements Runnable{
    private int port;
    private Socket clientSocket;
    private BufferedReader inFromServer;
    private DataOutputStream outToServer;
    private LinkedBlockingQueue<PosXY[]> outMove;
    private LinkedBlockingQueue<PosXY[]> inMove;
    private LinkedBlockingQueue<Boolean> updateGUI;
    private Boolean remote;
    private Boolean computer;

    public Utils(int port, Boolean remote, Boolean computer){
        this.port = port;
        this.remote = remote;
        this.computer = computer;
        this.outMove = new LinkedBlockingQueue<PosXY[]>(1);
        this.inMove = new LinkedBlockingQueue<PosXY[]>(1);
        this.updateGUI = new LinkedBlockingQueue<Boolean>(1);

    }
    public void run() {
        try {
            connect();
            gameCommunication();
            this.clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void connect() throws Exception {
        this.clientSocket = new Socket("localhost", this.port);
        this.outToServer = new DataOutputStream(
                this.clientSocket.getOutputStream()
        );
        this.inFromServer = new BufferedReader(
                new InputStreamReader(this.clientSocket.getInputStream())
        );
    }
    public void gameCommunication() throws Exception {
        while(true){
            System.out.println("Client waiting for move from GUI...");
            sendMove();
            System.out.println("Client waiting for move from server...");
            recvMove();
        }
    }
    public void sendMove() throws Exception{
        while(true) {
            //move is valid according to board logic
            PosXY move[] = this.outMove.take();
            this.outToServer.writeBytes(move[0].toString() + " " + move[1].toString() + "\n");
            String response = this.inFromServer.readLine();
            if (response.equals("ok")) {
                //move was accepted -> update GUI
                this.updateGUI.put(true);
                break;
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
    public LinkedBlockingQueue<Boolean> getUpdateGUI() {
        return updateGUI;
    }
    public LinkedBlockingQueue<PosXY[]> getInMove() {
        return inMove;
    }
}
