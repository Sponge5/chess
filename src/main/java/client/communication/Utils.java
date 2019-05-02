package client.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Utils implements Runnable{
    private int port;
    private Socket clientSocket;
    private BufferedReader inFromServer;
    private DataOutputStream outToServer;

    public void sendState(Integer[][] state){
        System.out.println(state.toString());
        //outToServer.writeBytes(state.toString());
    }

    public void connect() throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        this.clientSocket = new Socket("localhost", this.port);
        this.outToServer = new DataOutputStream(
                this.clientSocket.getOutputStream()
        );
        this.inFromServer = new BufferedReader(
                new InputStreamReader(this.clientSocket.getInputStream())
        );

        String sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        String modifiedSentence = this.inFromServer.readLine();
        System.out.println(modifiedSentence);
        clientSocket.close();
    }

    public void run() {
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Utils(int port){
        this.port = port;
    }
}
