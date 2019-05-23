package client.GUI;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;

public class MenuScreen{
    private Scene scene;
    private Text titleText;
    private GridPane pane;
    private Button mainMenuBtn;
    private Button vsComputerBtn;
    private Button twoPlayerBtn;
    private Button connectAndPlayBtn;
    private TextField portTextField;
    private TextField addressTextField;
    private EventHandler<ActionEvent> portTFEventHandler;
    private EventHandler<ActionEvent> addressTFEventHandler;

    public void mainMenu(Stage stage){
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.titleText = new Text("Main Menu");
        this.pane.add(this.titleText, 0, 0);
        Button newGameBtn = new Button("New Game");
        newGameBtn.setOnMouseClicked(newHandler(1, stage));
        this.pane.add(newGameBtn, 0, 1);
        Button loadGameBtn = new Button("Load Game");
        loadGameBtn.setOnMouseClicked(newHandler(2, stage));
        this.pane.add(loadGameBtn, 0, 2);
        Button watchGameBtn = new Button("Watch Game");
        watchGameBtn.setOnMouseClicked(newHandler(3, stage));
        this.pane.add(watchGameBtn, 0, 3);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
        stage.show();
        this.mainMenuBtn = new Button("Back to Main Menu");
        this.mainMenuBtn.setOnMouseClicked(newHandler(0, stage));
    }

    private EventHandler<MouseEvent> newHandler(int i, final Stage stage){
        switch(i){
            case 0:
                return (MouseEvent) -> {
                        System.out.println("[MenuScreen] BackToMainMenu Button clicked");
                        mainMenu(stage);
                };
            case 1:
                return (MouseEvent) -> {
                        System.out.println("[MenuScreen] NewGame Button clicked");
                        newGameMenu(stage);
                };
            case 2:
                return (MouseEvent) -> {
                        System.out.println("[MenuScreen] LoadGame Button clicked");
                        loadGameMenu(stage);
                };
            case 3:
                return (MouseEvent) -> {
                        System.out.println("[MenuScreen] WatchGame Button clicked");
                        watchGameMenu(stage);
                };
            case 4:
                return (MouseEvent) -> {
                        System.out.println("[MenuScreen] LocalGame Button clicked");
                        localGameMenu(stage);
                };
            case 5:
                return (MouseEvent) -> {
                        System.out.println("[MenuScreen] RemoteGame Button clicked");
                        remoteGameMenu(stage);
                };
            default:
                return null;
        }
    }

    private void newGameMenu(final Stage stage){
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.titleText = new Text("New Game Menu");
        this.pane.add(this.titleText, 0, 0);
        Button localGameBtn = new Button("Local Game");
        localGameBtn.setOnMouseClicked(newHandler(4, stage));
        this.pane.add(localGameBtn, 0, 1);
        Button remoteGameBtn = new Button("Remote Game");
        remoteGameBtn.setOnMouseClicked(newHandler(5, stage));
        this.pane.add(remoteGameBtn, 0, 2);
        this.pane.add(this.mainMenuBtn, 0, 3);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
    }

    private void loadGameMenu(final Stage stage){
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.titleText = new Text("Load Game Menu");
        this.pane.add(this.titleText, 0, 0);
        this.pane.add(this.mainMenuBtn, 0, 1);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
    }

    private void watchGameMenu(final Stage stage){
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.titleText = new Text("Watch Game Menu");
        this.pane.add(this.titleText, 0, 0);
        this.pane.add(this.mainMenuBtn, 0, 1);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
    }

    private void localGameMenu(final Stage stage){
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.titleText = new Text("Local Game Menu");
        this.pane.add(this.titleText, 0, 0);
        this.pane.add(this.vsComputerBtn, 0, 1);
        this.pane.add(this.twoPlayerBtn, 0, 2);
        this.pane.add(this.mainMenuBtn, 0, 3);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
    }

    public void remoteGameMenu(final Stage stage){
        this.pane = new GridPane();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.setVgap(5);
        this.pane.add(this.addressTextField, 0, 0);
        this.pane.add(this.portTextField, 0, 1);
        this.pane.add(this.connectAndPlayBtn, 0, 2);
        this.pane.add(this.mainMenuBtn, 0, 3);
        this.scene = new Scene(this.pane, MinDims.SCENE.width, MinDims.SCENE.height);
        stage.setScene(this.scene);
    }

    public Button getVsComputerBtn(){
        return this.vsComputerBtn;
    }
    public Button getTwoPlayerBtn(){
        return this.twoPlayerBtn;
    }
    public TextField getPortTextField(){
        return this.portTextField;
    }
    public void setPortTextField(TextField portTextField){
        this.portTextField = portTextField;
    }
    public TextField getAddressTextField() {
        return addressTextField;
    }
    public void setAddressTextField(TextField addressTextField) {
        this.addressTextField = addressTextField;
    }
    public Button getConnectAndPlayBtn() {
        return connectAndPlayBtn;
    }
    public EventHandler<ActionEvent> getPortTFEventHandler() {
        return portTFEventHandler;
    }
    public void setPortTFEventHandler(EventHandler<ActionEvent> portTFEventHandler) {
        this.portTFEventHandler = portTFEventHandler;
    }
    public EventHandler<ActionEvent> getAddressTFEventHandler() {
        return addressTFEventHandler;
    }
    public void setAddressTFEventHandler(EventHandler<ActionEvent> addressTFEventHandler) {
        this.addressTFEventHandler = addressTFEventHandler;
    }
    public void setConnectAndPlayBtn(Button connectAndPlayBtn) {
        this.connectAndPlayBtn = connectAndPlayBtn;
    }
    public void setVsComputerBtn(Button vsComputerBtn) {
        this.vsComputerBtn = vsComputerBtn;
    }
    public void setTwoPlayerBtn(Button twoPlayerBtn) {
        this.twoPlayerBtn = twoPlayerBtn;
    }
}
