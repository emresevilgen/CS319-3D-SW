package uiComponents;

import com.google.gson.Gson;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Lobby;
import rest.ApiClient;
import rest.ApiInterface;
import rest.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static uiComponents.SceneChanger.moveToMainMenu;
import static uiComponents.SceneChanger.moveToSeeThePlayers;

public class SeeThePlayers2Scene implements Initializable {
    @FXML
    public Button leaveButton;
    @FXML
    public Button readyButton;
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
            update();
         }
    }));

    // Be ready button listener
    public void beReady(ActionEvent event) throws Exception {
        //------------------------
        //------------------------
        //send request to the server
        //------------------------
        //------------------------
    }

    // Leave button listener
    public void leave(ActionEvent event) throws Exception {
        //------------------------
        //------------------------
        // send a request to the server and move to the main menu
        //------------------------
        //------------------------

        // To stop the requests and move to main menu
        timeLine.stop();
        moveToMainMenu((Stage)leaveButton.getScene().getWindow());

    }

    // Send request and update the lobby object
    public void update() {

        // Get the lobby data and when the game starts move to the game screen
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GeneralResponse<Lobby>> call = apiService.getLobby(Main.user.userName, Main.user.token, Main.lobby.lobbyId);
        call.enqueue(new Callback<GeneralResponse<Lobby>>() {
            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<Lobby>> call, Response<GeneralResponse<Lobby>> response) {
                if (response.body() != null) {

                    // Get the response
                    GeneralResponse<Lobby> userGeneralResponse = response.body();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // If success update lobby and move to see the players
                            if (userGeneralResponse.success)
                                Main.lobby = userGeneralResponse.payload;
                            // Otherwise error message
                            else {
                                showErrorMessage(userGeneralResponse.message);
                            }
                        }
                    });
                }
                // When the response's body is null
                else {
                    try {
                        String errorResponse = response.errorBody().string();
                        GeneralResponse userGeneralResponse =  new Gson().fromJson(errorResponse, GeneralResponse.class);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                showErrorMessage(userGeneralResponse.message);

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // When connection is lost
            @Override
            public void onFailure(Call<GeneralResponse<Lobby>> call, Throwable t) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        showErrorMessage("There is something wrong with the connection");

                    }
                });
            }
        });


        // Clear the labels
        for (int i = 0; i < labelsName.length; i++)
            labelsName[i].setText("");

        for (int i = 0; i < labelsState.length; i++)
            labelsState[i].setText("");

        // Update the labels with the response
        for (int i = 0; i < Main.lobby.lobbyUsers.length; i++) {
            labelsName[i].setText(Main.lobby.lobbyUsers[i].username);
            if (Main.lobby.lobbyUsers[i].username.equals(Main.lobby.lobbyAdmin)) {
                labelsState[i].setText("Waiting for others");
            }
            else {
                if (Main.lobby.lobbyUsers[i].isReady)
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
        leaveButton.setOnMouseEntered(e -> leaveButton.setStyle(HOVERED_BUTTON_STYLE));
        leaveButton.setOnMouseExited(e -> leaveButton.setStyle(IDLE_BUTTON_STYLE));
        readyButton.setOnMouseEntered(e -> readyButton.setStyle(HOVERED_BUTTON_STYLE));
        readyButton.setOnMouseExited(e -> readyButton.setStyle(IDLE_BUTTON_STYLE));

        // Initialize the label arrays
        labelsName[0] = firstNameLabel;
        labelsName[1] = secondNameLabel;
        labelsName[2] = thirdNameLabel;
        labelsName[3] = fourthNameLabel;

        labelsState[0] = firstStateLabel;
        labelsState[1] = secondStateLabel;
        labelsState[2] = thirdStateLabel;
        labelsState[3] = fourthStateLabel;

        // Get the lobby data and initialize the people,
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
