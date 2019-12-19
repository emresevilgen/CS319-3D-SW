package uiComponents;

import com.google.gson.Gson;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.DataHandler;
import models.Lobby;
import models.LobbyUser;
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
import java.util.ResourceBundle;

public class SeeThePlayersScene implements Initializable {
    @FXML
    public Button deleteLobbyButton;
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

    // Start game button listener
    public void startGame(ActionEvent event) throws Exception {
        // ----------------------------------
        // ----------------------------------
        // Send request to server if get success move to game screen move to game screen
        // ----------------------------------
        // ----------------------------------

        // To stop requests
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

    public void deleteLobby(ActionEvent event) throws Exception {
        // ----------------------------------
        // ----------------------------------
        // servera delete data gönder
        // ----------------------------------
        // ----------------------------------

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



        // To stop requests
        timeLine.stop();
        SceneHandler.getInstance().moveToMainMenu();


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
        deleteLobbyButton.setOnMouseEntered(e -> deleteLobbyButton.setStyle(HOVERED_BUTTON_STYLE));
        deleteLobbyButton.setOnMouseExited(e -> deleteLobbyButton.setStyle(IDLE_BUTTON_STYLE));
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

        DataHandler dataHandler = DataHandler.getInstance();


        try {
            FileInputStream inputStream = new FileInputStream(Constants.CROSS_IMAGE);
            FileInputStream inputStream2 = new FileInputStream(Constants.CHECK_IMAGE);

            //Change status of the users
            if(dataHandler.getLobby().lobbyUsers.length >= 1 &&  dataHandler.getLobby().lobbyUsers[1] !=  null)
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

            if(dataHandler.getLobby().lobbyUsers.length >= 2 &&  dataHandler.getLobby().lobbyUsers[2] !=  null)
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

        // Get the lobby data and initialize the people in the lobby
        update();
    }

    // Error message
    private void showErrorMessage(String errorMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMsg);
        alert.initOwner(startGameButton.getScene().getWindow());
        alert.showAndWait();
    }
}
