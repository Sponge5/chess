package client;

import client.GUI.BoardGUI;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Player;
import server.Board;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Runner extends Application{
    /* has gui, player and sockets */
    BoardGUI gui;
    Player player;

    public void start(Stage stage) throws Exception {
        /* menu screen */
        /**TODO
         * New game
         *      vs Computer
         *      vs player on 1 pc
         *      vs player on the internet
         * Load game
         * Watch game (PGN)
         */
        menuSetup(stage);
        /* Board GUI setup */
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void connect() throws Exception {
        /**TODO
         * if game selected is on 1 machine, create thread with server running in background
         */
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("localhost", 8888);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        modifiedSentence = inFromServer.readLine();
        System.out.println(modifiedSentence);
        clientSocket.close();
    }
    public void menuSetup(final Stage stage){
        /**TODO
         * if button selected, create new scene based on button
         */
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(5);

        Text titleText = new Text("Game Menu");
        pane.add(titleText, 0, 0);
        Button newGameButton = new Button("New Game");
        newGameButton.setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("Button click");
                        /*TODO setup board correctly */
                        gui = new BoardGUI(null);
                        Scene scene = new Scene(gui, 400, 400);
                        stage.setScene(scene);
                    }
                });
        pane.add(newGameButton, 0, 1);
        Button loadGameButton = new Button("Load Game");
        pane.add(loadGameButton, 0, 2);
        Button watchGameButton = new Button("Watch Game");
        pane.add(watchGameButton, 0, 3);

        Scene scene = new Scene(pane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
