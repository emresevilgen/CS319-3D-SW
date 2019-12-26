package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
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
import javafx.util.Duration;
import models.DataHandler;
import models.Game;
import models.Lobby;
import models.Settings;
import rest.Requester;
import rest.ServerConnectionHandler;
import rest.models.GeneralResponse;
import utils.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public ImageView firstReadyStatus;
    @FXML
    public ImageView firstActiveStatus;
    @FXML
    public Label secondNameLabel;
    @FXML
    public ImageView secondActiveStatus;
    @FXML
    public ImageView secondReadyStatus;
    @FXML
    public Label thirdNameLabel;
    @FXML
    public ImageView thirdActiveStatus;
    @FXML
    public ImageView thirdReadyStatus;
    @FXML
    public Label fourthNameLabel;
    @FXML
    public ImageView fourthActiveStatus;
    @FXML
    public ImageView fourthReadyStatus;
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
        // Be ready request
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
        leaveHelper();
    }

    public void leaveHelper(){
        // Leave lobby listener
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

        // If game is started then get game data
        if (dataHandler.getLobby().gameId != null){
            timeLine.stop();
            Thread requestThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Requester requester = ServerConnectionHandler.getInstance().getRequester();
                    GeneralResponse<Game> gameResponse = requester.getGameData(dataHandler.getUser().userName, dataHandler.getUser().token);
                    if (gameResponse != null) {
                        if (gameResponse.success) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    DataHandler.getInstance().setGame(gameResponse.payload);
                                    SceneHandler.getInstance().moveToGame();
                                    timeLine.stop();
                                }
                            });
                        }
                        else {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    showErrorMessage(gameResponse.message);
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
            return;
        }

        // If the user becomes admin
        if (dataHandler.getLobby().admin.equals(dataHandler.getUser().userName)){
            timeLine.stop();
            SceneHandler.getInstance().moveToSeeThePlayers(true);
            return;
        }

        // Get lobby info request
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
            firstActiveStatus.setImage(null);
            firstReadyStatus.setImage(null);
            //Change status of the users
            if(dataHandler.getLobby().users.length > 0 &&  dataHandler.getLobby().users[0] !=  null)
            {
                FileInputStream inputStream = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream2 = new FileInputStream(Constants.CHECK_IMAGE);
                FileInputStream inputStream3 = new FileInputStream(Constants.RED_IMAGE);
                FileInputStream inputStream4 = new FileInputStream(Constants.GREEN_IMAGE);
                if (!dataHandler.getLobby().users[0].isReady ){
                    Image crossImage = new Image(inputStream);
                    firstReadyStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream2);
                    firstReadyStatus.setImage(checkImage);
                }

                if (!dataHandler.getLobby().users[0].isActive){
                    Image crossImage = new Image(inputStream3);
                    firstActiveStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream4);
                    firstActiveStatus.setImage(checkImage);
                }

            }
            secondActiveStatus.setImage(null);
            secondReadyStatus.setImage(null);
            if(dataHandler.getLobby().users.length > 1 &&  dataHandler.getLobby().users[1] !=  null)
            {
                FileInputStream inputStream = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream2 = new FileInputStream(Constants.CHECK_IMAGE);
                FileInputStream inputStream3 = new FileInputStream(Constants.RED_IMAGE);
                FileInputStream inputStream4 = new FileInputStream(Constants.GREEN_IMAGE);
                if (!dataHandler.getLobby().users[1].isReady ){
                    Image crossImage = new Image(inputStream);
                    secondReadyStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream2);
                    secondReadyStatus.setImage(checkImage);
                }

                if (!dataHandler.getLobby().users[1].isActive){
                    Image crossImage = new Image(inputStream3);
                    secondActiveStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream4);
                    secondActiveStatus.setImage(checkImage);
                }
            }

            thirdActiveStatus.setImage(null);
            thirdReadyStatus.setImage(null);
            if(dataHandler.getLobby().users.length > 2 &&  dataHandler.getLobby().users[2] !=  null)
            {
                FileInputStream inputStream = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream2 = new FileInputStream(Constants.CHECK_IMAGE);
                FileInputStream inputStream3 = new FileInputStream(Constants.RED_IMAGE);
                FileInputStream inputStream4 = new FileInputStream(Constants.GREEN_IMAGE);
                if (!dataHandler.getLobby().users[2].isReady ){
                    Image crossImage = new Image(inputStream);
                    thirdReadyStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream2);
                    thirdReadyStatus.setImage(checkImage);
                }

                if (!dataHandler.getLobby().users[2].isActive){
                    Image crossImage = new Image(inputStream3);
                    thirdActiveStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream4);
                    thirdActiveStatus.setImage(checkImage);
                }
            }
            fourthActiveStatus.setImage(null);
            fourthReadyStatus.setImage(null);
            if(dataHandler.getLobby().users.length == 4 &&  dataHandler.getLobby().users[3] !=  null)
            {
                FileInputStream inputStream = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream2 = new FileInputStream(Constants.CHECK_IMAGE);
                FileInputStream inputStream3 = new FileInputStream(Constants.RED_IMAGE);
                FileInputStream inputStream4 = new FileInputStream(Constants.GREEN_IMAGE);
                if (!dataHandler.getLobby().users[3].isReady ){
                    Image crossImage = new Image(inputStream);
                    fourthReadyStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream2);
                    fourthReadyStatus.setImage(checkImage);
                }

                if (!dataHandler.getLobby().users[3].isActive){
                    Image crossImage = new Image(inputStream3);
                    fourthActiveStatus.setImage(crossImage);
                }
                else{
                    Image checkImage = new Image(inputStream4);
                    fourthActiveStatus.setImage(checkImage);
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
        for (int i = 0; i < lobby.users.length; i++) {
            labelsName[i].setText(lobby.users[i].username);
        }
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

        // Set on close operation
        SceneHandler.getInstance().getStageMain().setOnCloseRequest(e->{
            leaveHelper();
            leaved = true;
        });

        final boolean[] first = {true};

        // Set the focused properties to read the audio descriptions
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
                    tts.read("Be ready");
                readyButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                readyButton.setStyle(IDLE_BUTTON_STYLE);
            first[0] = false;
        });

        //Write the lobby code
        lobbyCode.setText("The lobby code: "+ DataHandler.getInstance().getLobby().code);

        // Initialize the label arrays
        labelsName[0] = firstNameLabel;
        labelsName[1] = secondNameLabel;
        labelsName[2] = thirdNameLabel;
        labelsName[3] = fourthNameLabel;

        // Set the key listeners
        SceneHandler.getInstance().getStageMain().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                Game game = DataHandler.getInstance().getGame();
                DataHandler dataHandler = DataHandler.getInstance();

                // Read the lobby code
                if(keyCode.equals(KeyCode.C))
                {
                    tts.read("The code is " + dataHandler.getLobby().code);
                }
                // Read the players status
                if(keyCode.equals(KeyCode.P) && settings.isAudioDescription())
                {
                    if(dataHandler.getLobby().users.length > 0 &&  dataHandler.getLobby().users[0] !=  null )
                    {
                        String text ="";
                        if (!dataHandler.getLobby().users[0].isReady){
                            text =( dataHandler.getLobby().users[0].username + " is not ready ");
                        }
                        else{
                            text = text +(dataHandler.getLobby().users[0].username + " is ready ");
                        }
                        if ( !dataHandler.getLobby().users[0].isActive){
                            text = text + ( " and is not active");
                        }
                        else{
                            text = text + (" and is active");
                        }
                        tts.read(text);
                    }
                    if(dataHandler.getLobby().users.length > 1 &&  dataHandler.getLobby().users[1] !=  null)
                    {
                        String text ="";
                        if (!dataHandler.getLobby().users[1].isReady){
                            text =( dataHandler.getLobby().users[1].username + " is not ready ");
                        }
                        else{
                            text = text +(dataHandler.getLobby().users[1].username + " is ready ");
                        }
                        if ( !dataHandler.getLobby().users[1].isActive){
                            text = text + ( " and is not active");
                        }
                        else{
                            text = text + (" and is active");
                        }
                        tts.read(text);
                    }
                    if(dataHandler.getLobby().users.length > 2 &&  dataHandler.getLobby().users[2] !=  null )
                    {
                        String text ="";
                        if (!dataHandler.getLobby().users[2].isReady){
                            text =( dataHandler.getLobby().users[2].username + " is not ready ");
                        }
                        else{
                            text = text +(dataHandler.getLobby().users[2].username + " is ready ");
                        }
                        if ( !dataHandler.getLobby().users[2].isActive){
                            text = text + ( " and is not active");
                        }
                        else{
                            text = text + (" and is active");
                        }
                        tts.read(text);
                    }
                    if(dataHandler.getLobby().users.length > 3 &&  dataHandler.getLobby().users[3] !=  null )
                    {
                        String text ="";
                        if (!dataHandler.getLobby().users[3].isReady){
                            text =( dataHandler.getLobby().users[3].username + " is not ready ");
                        }
                        else{
                            text = text +(dataHandler.getLobby().users[3].username + " is ready ");
                        }
                        if ( !dataHandler.getLobby().users[3].isActive){
                            text = text + ( " and is not active");
                        }
                        else{
                            text = text + (" and is active");
                        }
                        tts.read(text);
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
