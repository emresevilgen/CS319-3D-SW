package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import com.google.gson.Gson;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.DataHandler;
import models.Game;
import models.Lobby;
import models.Settings;
import rest.ApiClient;
import rest.ApiInterface;
import rest.Requester;
import rest.ServerConnectionHandler;
import rest.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SeeThePlayers2Scene implements Initializable {
    @FXML
    public Button leaveButton;
    @FXML
    public Button readyButton;
    @FXML
    public Label firstNameLabel;
    @FXML
    public ImageView firstPlayerStatus;
    @FXML
    public Label secondNameLabel;
    @FXML
    public ImageView secondPlayerStatus;
    @FXML
    public Label thirdNameLabel;
    @FXML
    public ImageView thirdPlayerStatus;
    @FXML
    public Label fourthNameLabel;
    @FXML
    public ImageView fourthPlayerStatus;
    @FXML
    public Label lobbyCode;

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #817277; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #817277; -fx-opacity: 0.85;";

    // Label array to change the usernames and states of the players
    Label [] labelsName = new Label[4];
    Label [] labelsState = new Label[4];

    boolean leaved = false;

    // To send a request at every second
    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
        // Update the lobby info at every second
        @Override
        public void handle(ActionEvent event) {
            update();
        }
    }));

    private boolean showError = true;

    // Be ready button listener
    public void beReady(ActionEvent event) throws Exception {
        //------------------------
        //------------------------
        //send request to the server
        //------------------------
        //------------------------
        DataHandler dataHandler = DataHandler.getInstance();

        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Requester requester = ServerConnectionHandler.getInstance().getRequester();
                GeneralResponse<Lobby> lobbyResponse = requester.getReady(dataHandler.getUser().userName, dataHandler.getUser().token, true);
                if (lobbyResponse != null) {
                    if (lobbyResponse.success) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                DataHandler.getInstance().setLobby(lobbyResponse.payload);
                            }
                        });
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                showErrorMessage(lobbyResponse.message);
                            }
                        });
                    }
                }
                else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showErrorMessage("There is something wrong with the connection");
                        }
                    });
                }
            }
        });
        requestThread.start();


    }

    // Leave button listener
    public void leave(ActionEvent event) throws Exception {
        //------------------------
        //------------------------
        // send a request to the server and move to the main menu
        //------------------------
        //------------------------

        DataHandler dataHandler = DataHandler.getInstance();

        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Requester requester = ServerConnectionHandler.getInstance().getRequester();
                GeneralResponse<Lobby> lobbyResponse = requester.exitLobby(dataHandler.getUser().userName, dataHandler.getUser().token);
                if (lobbyResponse != null) {
                    if (lobbyResponse.success) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                dataHandler.setLobby(lobbyResponse.payload);
                                // To stop the requests and move to main menu
                                timeLine.stop();
                                leaved = true;
                                dataHandler.setLobby(null);
                                SceneHandler.getInstance().moveToMainMenu();
                            }
                        });
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                showErrorMessage(lobbyResponse.message);
                            }
                        });
                    }
                }
                else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showErrorMessage("There is something wrong with the connection");
                        }
                    });
                }
            }
        });
        requestThread.start();
    }

    // Send request and update the lobby object
    public void update() {

        DataHandler dataHandler = DataHandler.getInstance();

        if (dataHandler.getLobby().lobbyAdmin.equals(dataHandler.getUser().userName)){
            timeLine.stop();
            SceneHandler.getInstance().moveToSeeThePlayers(true);
            return;
        }

        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Requester requester = ServerConnectionHandler.getInstance().getRequester();
                GeneralResponse<Lobby> lobbyResponse = requester.getLobby(dataHandler.getUser().userName, dataHandler.getUser().token);
                if (lobbyResponse != null) {
                    if (lobbyResponse.success) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                DataHandler.getInstance().setLobby(lobbyResponse.payload);
                            }
                        });
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (!leaved)
                                    showErrorMessage(lobbyResponse.message);
                            }
                        });
                    }
                }
                else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (!leaved)
                                showErrorMessage("There is something wrong with the connection");
                        }
                    });
                }
            }
        });
        requestThread.start();

        try {


            //Change status of the users
            firstPlayerStatus.setImage(null);
            if(dataHandler.getLobby().lobbyUsers.length > 0 &&  dataHandler.getLobby().lobbyUsers[0] !=  null)
            {
                FileInputStream inputStream = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream2 = new FileInputStream(Constants.CHECK_IMAGE);
                if (!dataHandler.getLobby().lobbyUsers[0].isReady || !dataHandler.getLobby().lobbyUsers[0].isActive){
                    Image crossImage = new Image(inputStream);
                    firstPlayerStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream2);
                    firstPlayerStatus.setImage(checkImage);
                }
            }
            secondPlayerStatus.setImage(null);
            if(dataHandler.getLobby().lobbyUsers.length > 1 &&  dataHandler.getLobby().lobbyUsers[1] !=  null)
            {
                FileInputStream inputStream = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream2 = new FileInputStream(Constants.CHECK_IMAGE);
                if (!dataHandler.getLobby().lobbyUsers[1].isReady || !dataHandler.getLobby().lobbyUsers[1].isActive){
                    Image crossImage = new Image(inputStream);
                    secondPlayerStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream2);
                    secondPlayerStatus.setImage(checkImage);
                }
            }
            thirdPlayerStatus.setImage(null);
            if(dataHandler.getLobby().lobbyUsers.length > 2 &&  dataHandler.getLobby().lobbyUsers[2] !=  null)
            {
                FileInputStream inputStream = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream2 = new FileInputStream(Constants.CHECK_IMAGE);
                if (!dataHandler.getLobby().lobbyUsers[2].isReady || !dataHandler.getLobby().lobbyUsers[2].isActive){
                    Image crossImage = new Image(inputStream);
                    thirdPlayerStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream2);
                    thirdPlayerStatus.setImage(checkImage);
                }
            }
            fourthPlayerStatus.setImage(null);
            if(dataHandler.getLobby().lobbyUsers.length == 4 &&  dataHandler.getLobby().lobbyUsers[3] !=  null)
            {
                FileInputStream inputStream = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream2 = new FileInputStream(Constants.CHECK_IMAGE);
                if (!dataHandler.getLobby().lobbyUsers[3].isReady || !dataHandler.getLobby().lobbyUsers[3].isActive){
                    Image crossImage = new Image(inputStream);
                    fourthPlayerStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream2);
                    fourthPlayerStatus.setImage(checkImage);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Lobby lobby = dataHandler.getLobby();

        // Clear the labels
        for (int i = 0; i < labelsName.length; i++)
            labelsName[i].setText("");

        // Update the labels with the response
        for (int i = 0; i < lobby.lobbyUsers.length; i++) {
            labelsName[i].setText(lobby.lobbyUsers[i].username);
        }

        // -------------------
        // -------------------
        // if game starts then
        //         timeLine.stop();
        // -------------------
        // -------------------

    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Start sending requests
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

        // Setting the mouse entered and exited listeners for hover effect
        leaveButton.setOnMouseEntered(e -> leaveButton.setStyle(HOVERED_BUTTON_STYLE));
        leaveButton.setOnMouseExited(e -> leaveButton.setStyle(IDLE_BUTTON_STYLE));
        readyButton.setOnMouseEntered(e -> readyButton.setStyle(HOVERED_BUTTON_STYLE));
        readyButton.setOnMouseExited(e -> readyButton.setStyle(IDLE_BUTTON_STYLE));

        Settings settings = DataHandler.getInstance().getSettings();
        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        final boolean[] first = {true};

        leaveButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first[0])
                    tts.read("Leave the Lobby");
                leaveButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                leaveButton.setStyle(IDLE_BUTTON_STYLE);
            first[0] = false;
        });
        readyButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first[0])
                    tts.read("Start Game");
                readyButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                readyButton.setStyle(IDLE_BUTTON_STYLE);
            first[0] = false;
        });

        //Write the lobby code
        lobbyCode.setText("The lobby code: "+ DataHandler.getInstance().getLobby().lobbyCode );

        // Initialize the label arrays
        labelsName[0] = firstNameLabel;
        labelsName[1] = secondNameLabel;
        labelsName[2] = thirdNameLabel;
        labelsName[3] = fourthNameLabel;

        SceneHandler.getInstance().getStage().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                Game game = DataHandler.getInstance().getGame();
                DataHandler dataHandler = DataHandler.getInstance();

                if(keyCode.equals(KeyCode.C))
                {
                    tts.read("The code is " + dataHandler.getLobby().lobbyCode);
                }
                if(keyCode.equals(KeyCode.P) && settings.isAudioDescription())
                {
                    if(dataHandler.getLobby().lobbyUsers.length > 0 &&  dataHandler.getLobby().lobbyUsers[0] !=  null && !dataHandler.getLobby().lobbyUsers[0].username.equals(dataHandler.getUser().userName))
                    {
                        if (!dataHandler.getLobby().lobbyUsers[0].isReady || !dataHandler.getLobby().lobbyUsers[1].isActive){
                            tts.read(dataHandler.getLobby().lobbyUsers[0].username + " is not active");
                        }
                        else{
                            tts.read(dataHandler.getLobby().lobbyUsers[0].username + " is active");
                        }
                    }
                    if(dataHandler.getLobby().lobbyUsers.length > 1 &&  dataHandler.getLobby().lobbyUsers[1] !=  null && !dataHandler.getLobby().lobbyUsers[1].username.equals(dataHandler.getUser().userName))
                    {
                        if (!dataHandler.getLobby().lobbyUsers[1].isReady || !dataHandler.getLobby().lobbyUsers[1].isActive){
                            tts.read(dataHandler.getLobby().lobbyUsers[1].username + " is not active");
                        }
                        else{
                            tts.read(dataHandler.getLobby().lobbyUsers[1].username + " is active");
                        }
                    }
                    if(dataHandler.getLobby().lobbyUsers.length > 2 &&  dataHandler.getLobby().lobbyUsers[2] !=  null && !dataHandler.getLobby().lobbyUsers[2].username.equals(dataHandler.getUser().userName))
                    {
                        if (!dataHandler.getLobby().lobbyUsers[2].isReady || !dataHandler.getLobby().lobbyUsers[2].isActive){
                            tts.read(dataHandler.getLobby().lobbyUsers[2].username + " is not active");
                        }
                        else{
                            tts.read(dataHandler.getLobby().lobbyUsers[2].username + " is active");
                        }
                    }
                    if(dataHandler.getLobby().lobbyUsers.length > 3 &&  dataHandler.getLobby().lobbyUsers[3] !=  null && !dataHandler.getLobby().lobbyUsers[3].username.equals(dataHandler.getUser().userName))
                    {
                        if (!dataHandler.getLobby().lobbyUsers[3].isReady || !dataHandler.getLobby().lobbyUsers[3].isActive){
                            tts.read(dataHandler.getLobby().lobbyUsers[3].username + " is not active");
                        }
                        else{
                            tts.read(dataHandler.getLobby().lobbyUsers[3].username + " is active");
                        }
                    }
                }
                event.consume();
            }
        });

        // Get the lobby data and initialize the people,
        update();
    }

    // Error message
    private void showErrorMessage(String errorMsg){
        if (showError) {
            disableItems();

            final boolean[] first = {true};
            DataHandler dataHandler = DataHandler.getInstance();

            if (dataHandler.getSettings().isAudioDescription()) {
                AudioDescriptionHandler.getInstance().getReader().read("Error. " + errorMsg + " Press enter to say OK");
            }

            showError = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(errorMsg);
            //alert.initOwner(readyButton.getScene().getWindow());
            alert.getButtonTypes().forEach(buttonType -> {
                alert.getDialogPane().lookupButton(buttonType).focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue && dataHandler.getSettings().isAudioDescription() && !first[0])
                        AudioDescriptionHandler.getInstance().getReader().read(buttonType.getText());
                    first[0] = false;
                });
            });

            Optional<ButtonType> result = alert.showAndWait();
            showError = true;
            enableItems();
        }
    }

    private void disableItems(){
        leaveButton.setDisable(true);
        readyButton.setDisable(true);
    }

    private void enableItems(){
        leaveButton.setDisable(false);
        readyButton.setDisable(false);
    }
}
