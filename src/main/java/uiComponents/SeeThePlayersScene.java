package uiComponents;

import audioDescription.AudioDescriptionHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import models.DataHandler;
import models.Lobby;
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


        try {
            FileInputStream inputStream = new FileInputStream(Constants.CROSS_IMAGE);
            FileInputStream inputStream2 = new FileInputStream(Constants.CHECK_IMAGE);
            secondPlayerStatus.setImage(null);
            //Change status of the users
            if(dataHandler.getLobby().lobbyUsers.length > 1 &&  dataHandler.getLobby().lobbyUsers[1] !=  null)
            {
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

/*        for (int i = 0; i < labelsState.length; i++)
            labelsState[i].setText("");*/

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
        startGameButton.setOnMouseEntered(e -> startGameButton.setStyle(HOVERED_BUTTON_STYLE));
        startGameButton.setOnMouseExited(e -> startGameButton.setStyle(IDLE_BUTTON_STYLE));
        dismissPersonButton.setOnMouseEntered(e -> dismissPersonButton.setStyle(HOVERED_BUTTON_STYLE));
        dismissPersonButton.setOnMouseExited(e -> dismissPersonButton.setStyle(IDLE_BUTTON_STYLE));

        // Show the lobby code
        lobbyCode.setText("The lobby code: "+ DataHandler.getInstance().getLobby().lobbyCode );
        lobbyCodeTitle.setText("People in the Lobby: " );

        // Initialize the label arrays
        labelsName[0] = firstNameLabel;
        labelsName[1] = secondNameLabel;
        labelsName[2] = thirdNameLabel;
        labelsName[3] = fourthNameLabel;

        labelsState[0] = firstStateLabel;

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
            alert.initOwner(startGameButton.getScene().getWindow());
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
