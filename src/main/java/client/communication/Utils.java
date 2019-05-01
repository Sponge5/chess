package client.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Utils implements Runnable{
    private int port;

    public void connect() throws Exception {
        /**TODO
         * if game selected is on 1 machine, create thread with server running in background
         */
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", this.port);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        String modifiedSentence = inFromServer.readLine();
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
