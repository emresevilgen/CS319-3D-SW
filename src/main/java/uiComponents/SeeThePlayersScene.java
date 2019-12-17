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
import javafx.stage.Stage;
import javafx.util.Duration;
import models.DataHandler;
import models.Lobby;
import rest.ApiClient;
import rest.ApiInterface;
import rest.Requester;
import rest.ServerConnectionHandler;
import rest.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public Label secondStateLabel;
    @FXML
    public Label thirdNameLabel;
    @FXML
    public Label thirdStateLabel;
    @FXML
    public Label fourthNameLabel;
    @FXML
    public Label fourthStateLabel;
    @FXML
    public CheckBox secondPlayerCheckBox;
    @FXML
    public CheckBox thirdPlayerCheckBox;
    @FXML
    public CheckBox fourthPlayerCheckBox;
    @FXML
    public Label lobbyCodeTitle;

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

        // To stop requests
        timeLine.stop();
        SceneHandler.getInstance().moveToMainMenu();

    }


    // Send request and update the lobby object
    public void update() {

        Requester requester = ServerConnectionHandler.getInstance().getRequester();
        DataHandler dataHandler = DataHandler.getInstance();
        Lobby lobby = requester.getLobby(dataHandler.getUser().userName, dataHandler.getUser().userName, dataHandler.getLobby().lobbyId);
        if (lobby != null)
            dataHandler.setLobby(lobby);
        else
            lobby = dataHandler.getLobby();

        // Clear the labels
        for (int i = 0; i < labelsName.length; i++)
            labelsName[i].setText("");

        for (int i = 0; i < labelsState.length; i++)
            labelsState[i].setText("");

        // Update the labels with the response
        for (int i = 0; i < lobby.lobbyUsers.length; i++) {
            labelsName[i].setText(lobby.lobbyUsers[i].username);
            if (lobby.lobbyUsers[i].username.equals(lobby.lobbyAdmin)) {
                labelsState[i].setText("Waiting for others");
            }
            else {
                if (lobby.lobbyUsers[i].isReady)
                    labelsState[i].setText("Ready");
                else
                    labelsState[i].setText("Not ready");
            }

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
        lobbyCodeTitle.setText("People in the Lobby with the code " + DataHandler.getInstance().getLobby().lobbyCode + ":");

        // Initialize the label arrays
        labelsName[0] = firstNameLabel;
        labelsName[1] = secondNameLabel;
        labelsName[2] = thirdNameLabel;
        labelsName[3] = fourthNameLabel;

        labelsState[0] = firstStateLabel;
        labelsState[1] = secondStateLabel;
        labelsState[2] = thirdStateLabel;
        labelsState[3] = fourthStateLabel;

        // Get the lobby data and initialize the people in the lobby
        update();
    }

    // Error message
    private void showErrorMessage(String errorMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }
}
