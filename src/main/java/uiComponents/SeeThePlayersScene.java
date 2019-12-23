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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import models.*;
import rest.Requester;
import rest.ServerConnectionHandler;
import rest.models.GeneralResponse;
import utils.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SeeThePlayersScene implements Initializable {
    @FXML
    public Button leaveButton;
    @FXML
    public Button startGameButton;
    @FXML
    public Button dismissPersonButton;
    @FXML
    public Label firstNameLabel;
    @FXML
    public Label firstStateLabel;
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
    public CheckBox secondPlayerCheckBox;
    @FXML
    public CheckBox thirdPlayerCheckBox;
    @FXML
    public CheckBox fourthPlayerCheckBox;
    @FXML
    public Label lobbyCodeTitle;
    @FXML
    public Label lobbyCode;



    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #817277; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #817277; -fx-opacity: 0.85;";

    // Label array to change the usernames and states of the players
    Label [] labelsName = new Label[4];
    Label [] labelsState = new Label[4];
    CheckBox [] checkBoxes = new CheckBox[3];

    boolean leaved = false;

    // To send a request at every second
    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
        // Update the lobby info at every second
        @Override
        public void handle(ActionEvent event) {
            // Get the lobby data every seconds and when the game starts move to the game screen
            update();
        }
    }));

    private boolean showError = true;

    // Start game button listener
    public void startGame(ActionEvent event) throws Exception {
        // ----------------------------------
        // ----------------------------------
        // Send request to server if get success move to game screen move to game screen
        // ----------------------------------
        // ----------------------------------
        Lobby lobby = DataHandler.getInstance().getLobby();
        boolean allReady = true;

        for (int i = 0; i < lobby.users.length; i++){
            if (!lobby.users[i].isReady)
                allReady = false;
        }

        if (allReady){
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



        // To stop requests if the response is okey
        timeLine.stop();
        SceneHandler.getInstance().moveToGame();
    }

    // Dismiss button listener
    public void dismissThePerson(ActionEvent event) throws Exception {
        // Get the checkbox inputs
        boolean secondPlayer = secondPlayerCheckBox.isSelected();
        boolean thirdPlayer = thirdPlayerCheckBox.isSelected();
        boolean fourthPlayer = fourthPlayerCheckBox.isSelected();
        // ----------------------------------
        // ----------------------------------
        // servera dismiss data gönder
        //moveToMainMenu((Stage)dismissPersonButton.getScene().getWindow());
        // ----------------------------------
        // ----------------------------------
    }

    // Exit lobby request

    public void leave(ActionEvent event) throws Exception {
        leaveHelper();
    }

    public void leaveHelper(){
        // Leave lobby request
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
                                // To stop requests
                                timeLine.stop();
                                dataHandler.setLobby(null);
                                leaved = true;
                                SceneHandler.getInstance().moveToMainMenu();;
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
        // If game starts
        if (dataHandler.getLobby().gameId != null){
            timeLine.stop();
            // Get game request
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

        // Make the start game inactive if everyone is not ready and the number of players is less than three
        startGameButton.setDisable(true);

        Lobby lobby = DataHandler.getInstance().getLobby();
        boolean allReady = true;

        for (int i = 0; i < lobby.users.length; i++){
            if (!lobby.users[i].isReady)
                allReady = false;
        }

        if(allReady && lobby.users.length > 2)
            startGameButton.setDisable(false);

        // Get lobby data request
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
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (!leaved)
                                    showErrorMessage(lobbyResponse.message);
                            }
                        });
                    }
                } else {
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

        // Enable or disable the checkboxes
        checkBoxes[0] = secondPlayerCheckBox;
        checkBoxes[1] = thirdPlayerCheckBox;
        checkBoxes[2] = fourthPlayerCheckBox;

        for(int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].setDisable(true);
        }

        for(int i = 1; i < lobby.users.length; i++){
            if (lobby.users[i] != null && lobby.users[i].username != null)
                checkBoxes[i-1].setDisable(false);
        }

        try {
            firstActiveStatus.setImage(null);
            firstReadyStatus.setImage(null);
            //Change status of the users
            if(dataHandler.getLobby().users.length > 0 &&  dataHandler.getLobby().users[0] !=  null)
            {
                FileInputStream inputStream = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream2 = new FileInputStream(Constants.CHECK_IMAGE);
                FileInputStream inputStream3 = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream4 = new FileInputStream(Constants.CHECK_IMAGE);
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
                FileInputStream inputStream3 = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream4 = new FileInputStream(Constants.CHECK_IMAGE);
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
                FileInputStream inputStream3 = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream4 = new FileInputStream(Constants.CHECK_IMAGE);
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
                FileInputStream inputStream3 = new FileInputStream(Constants.CROSS_IMAGE);
                FileInputStream inputStream4 = new FileInputStream(Constants.CHECK_IMAGE);
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

        // Clear the labels
        for (int i = 0; i < labelsName.length; i++)
            labelsName[i].setText("");

/*        for (int i = 0; i < labelsState.length; i++)
            labelsState[i].setText("");*/

        // Update the labels with the response
        for (int i = 0; i < lobby.users.length; i++) {
            labelsName[i].setText(lobby.users[i].username);
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
        startGameButton.setOnMouseEntered(e -> startGameButton.setStyle(HOVERED_BUTTON_STYLE));
        startGameButton.setOnMouseExited(e -> startGameButton.setStyle(IDLE_BUTTON_STYLE));
        dismissPersonButton.setOnMouseEntered(e -> dismissPersonButton.setStyle(HOVERED_BUTTON_STYLE));
        dismissPersonButton.setOnMouseExited(e -> dismissPersonButton.setStyle(IDLE_BUTTON_STYLE));

        Settings settings = DataHandler.getInstance().getSettings();
        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        tts.read("The lobby code: "+ DataHandler.getInstance().getLobby().code);

        // Set on close operation
        SceneHandler.getInstance().getStageMain().setOnCloseRequest(e->{
            leaveHelper();
            leaved = true;
        });

        final boolean[] first = {true};

        // Set the focused property
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
        startGameButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first[0])
                    tts.read("Start Game");
                startGameButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                startGameButton.setStyle(IDLE_BUTTON_STYLE);
            first[0] = false;
        });
        dismissPersonButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first[0])
                    tts.read("Dismiss the person");
                dismissPersonButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                dismissPersonButton.setStyle(IDLE_BUTTON_STYLE);
            first[0] = false;
        });

        // Show the lobby code
        lobbyCode.setText("The lobby code: "+ DataHandler.getInstance().getLobby().code);
        lobbyCodeTitle.setText("People in the Lobby: " );

        // Initialize the label arrays
        labelsName[0] = firstNameLabel;
        labelsName[1] = secondNameLabel;
        labelsName[2] = thirdNameLabel;
        labelsName[3] = fourthNameLabel;

        labelsState[0] = firstStateLabel;

        secondPlayerCheckBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (secondPlayerCheckBox.isSelected() && !first[0] && !secondNameLabel.getText().equals(""))
                    tts.read("Do not dismiss" + secondNameLabel.getText());
                else if(!secondNameLabel.getText().equals(""))
                    tts.read("Dismiss " + secondNameLabel.getText());
            }
            first[0] = false;
        });
        thirdPlayerCheckBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (thirdPlayerCheckBox.isSelected() && !first[0] && !thirdNameLabel.getText().equals(""))
                    tts.read("Do not dismiss " + thirdNameLabel.getText());
                else if(!thirdNameLabel.getText().equals(""))
                    tts.read("Dismiss" + thirdNameLabel.getText());
            }
            first[0] = false;
        });
        fourthPlayerCheckBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (fourthPlayerCheckBox.isSelected() && !first[0] && !fourthNameLabel.getText().equals(""))
                    tts.read("Do not dismiss" + fourthNameLabel.getText());
                else if(!fourthNameLabel.getText().equals(""))
                    tts.read("Dismiss " + fourthNameLabel.getText());
            }
            first[0] = false;
        });

        // Set the key listeners
        SceneHandler.getInstance().getStageMain().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                Game game = DataHandler.getInstance().getGame();
                DataHandler dataHandler = DataHandler.getInstance();
                // Read the code of the lobby
                if(keyCode.equals(KeyCode.C))
                {
                    tts.read("The code is " + dataHandler.getLobby().code);
                }
                // Read the people info in the lobby
                if(keyCode.equals(KeyCode.P) && settings.isAudioDescription())
                {
                    if(dataHandler.getLobby().users.length > 1 &&  dataHandler.getLobby().users[1] !=  null)
                    {
                        if (!dataHandler.getLobby().users[1].isReady || !dataHandler.getLobby().users[1].isActive){
                            tts.read(dataHandler.getLobby().users[1].username + " is not active");
                        }
                        else{
                            tts.read(dataHandler.getLobby().users[1].username + " is active");
                        }
                    }
                    if(dataHandler.getLobby().users.length > 2 &&  dataHandler.getLobby().users[2] !=  null)
                    {
                        if (!dataHandler.getLobby().users[2].isReady || !dataHandler.getLobby().users[1].isActive){
                            tts.read(dataHandler.getLobby().users[2].username + " is not active");
                        }
                        else{
                            tts.read(dataHandler.getLobby().users[2].username + " is active");
                        }
                    }
                    if(dataHandler.getLobby().users.length > 2 &&  dataHandler.getLobby().users[2] !=  null)
                    {
                        if (!dataHandler.getLobby().users[2].isReady || !dataHandler.getLobby().users[2].isActive){
                            tts.read(dataHandler.getLobby().users[2].username + " is not active");
                        }
                        else{
                            tts.read(dataHandler.getLobby().users[2].username + " is active");
                        }
                    }
                }
                event.consume();
            }
        });
        // Get the lobby data and initialize the people in the lobby
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
            alert.initOwner(SceneHandler.getInstance().getStageMain());
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
        startGameButton.setDisable(true);
        dismissPersonButton.setDisable(true);
    }

    private void enableItems(){
        leaveButton.setDisable(false);
        startGameButton.setDisable(false);
        dismissPersonButton.setDisable(false);
    }
}
