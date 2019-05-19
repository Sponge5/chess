package server;

import client.communication.Utils;
import logic.Board;
import logic.Player;
import logic.PlayerColor;
import logic.PosXY;

import java.net.ServerSocket;

public class Runner implements Runnable{
    private Boolean twoClients;
    private ServerSocket serverSocket;
    private Connection firstCon, secondCon, connection;

    public Runner(Boolean remote){
        this.twoClients = remote;
        try {
            this.serverSocket = new ServerSocket(8888);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Runner(int port, Boolean remote){
        this.twoClients = remote;
        try {
            this.serverSocket = new ServerSocket(port);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void run() {
        try {
            if(this.twoClients){
                this.firstCon = new Connection(this.serverSocket, twoClients,
                        PlayerColor.WHITE);
                this.secondCon = new Connection(this.serverSocket, twoClients,
                        PlayerColor.BLACK);
                this.firstCon.start();
                this.secondCon.start();
                twoPlayerGame();
            }else{
                this.connection = new Connection(this.serverSocket, !twoClients,
                        PlayerColor.WHITE);
                this.connection.run();
            }
            this.serverSocket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void twoPlayerGame() throws Exception{
        while(true) {
            PosXY[] move = this.firstCon.getInMove().take();
            this.secondCon.getOutMove().put(move);
            if(this.firstCon.getBoard().isOver())
                break;
            move = this.secondCon.getInMove().take();
            this.firstCon.getOutMove().put(move);
            if(this.secondCon.getBoard().isOver())
                break;
        }
    }
    public int getPort(){
        return this.serverSocket.getLocalPort();
    }
    public String getAddress(){
        return this.serverSocket.toString();
    }
}
