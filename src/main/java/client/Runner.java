package client;

import client.GUI.MainGUI;
import client.communication.Utils;

public class Runner{
    public static void main(String[] args) throws Exception {
        /* TODO get port from user to connect to in the GUI*/
        /* TODO if single game, run server from here */
        MainGUI.main(args);
        int port = 8888;
        try {
            System.out.println("Connecting to server");
            Utils.connect(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
