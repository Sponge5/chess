package server;

import logic.Move;
import logic.PlayerColor;

import java.net.ServerSocket;

public class Runner implements Runnable{
    private Boolean twoClients;
    private ServerSocket serverSocket;
    private ServerSocket serverSocket2;
    private Connection firstCon, secondCon, connection;
    private int defaultPort = 8888;
    private int defaultPort2 = 8889;

    public Runner(Boolean remote){
        this.twoClients = remote;
        try {
            this.serverSocket = new ServerSocket(this.defaultPort);
            if(remote)
                this.serverSocket2 = new ServerSocket(this.defaultPort2);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void run() {
        try {
            if(this.twoClients){
                this.firstCon = new Connection(this.serverSocket, twoClients,
                        PlayerColor.WHITE);
                this.secondCon = new Connection(this.serverSocket2, twoClients,
                        PlayerColor.BLACK);
                this.firstCon.start();
                this.secondCon.start();
                twoPlayerGame();
            }else{
                this.connection = new Connection(this.serverSocket, twoClients,
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
            System.out.println("[server/Runner] waiting for move from first connection");
            Move move = this.firstCon.getInMove().take();
            System.out.println("[server/Runner] " + move.toString());
            this.secondCon.getOutMove().put(move);
            if(this.firstCon.getBoard().isOver())
                break;
            System.out.println("[server/Runner] waiting for move from second connection");
            move = this.secondCon.getInMove().take();
            System.out.println("[server/Runner] " + move.toString());
            this.firstCon.getOutMove().put(move);
            if(this.secondCon.getBoard().isOver())
                break;
        }
    }
    public int getPort(){
        return this.serverSocket.getLocalPort();
    }
    public String getAddress(){
        return this.serverSocket.getLocalSocketAddress().toString();
    }
}
