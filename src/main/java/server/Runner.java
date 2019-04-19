package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Runner {
    public static void main(String[] args) throws Exception {
        try{
            ServerSocket welcomeSocket = new ServerSocket(8888);
            System.out.println(welcomeSocket.getLocalPort());

            Socket connectionSocket = welcomeSocket.accept();

            BufferedReader inFromClient = new BufferedReader(
                    new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(
                    connectionSocket.getOutputStream());
            String clientSentence = inFromClient.readLine();
            String capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
