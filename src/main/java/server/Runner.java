package server;

import logic.Board;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Runner implements Runnable{
    private ServerSocket serverSocket;
    private Socket conSocket;
    private Board board;
    private Thread logicThread;

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
        this.conSocket = this.serverSocket.accept();
        BufferedReader inFromClient = new BufferedReader(
                new InputStreamReader(this.conSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(
                this.conSocket.getOutputStream());
        String clientSentence = inFromClient.readLine();
        String capitalizedSentence = clientSentence.toUpperCase() + '\n';
        outToClient.writeBytes(capitalizedSentence);
    }

    public void run() {
        try {
            setup();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
