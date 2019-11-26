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
import javafx.scene.control.CheckBox;
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

import static uiComponents.SceneChanger.*;

public class SeeThePlayersController implements Initializable {
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


    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #817277; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #817277; -fx-opacity: 0.85;";
    Label [] labelsName = new Label[4];
    Label [] labelsState = new Label[4];


    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {

            // get the lobby data every seconds and when the game starts move to the game screen
            update();
        }
    }));

    public void startGame(ActionEvent event) throws Exception {
        // send request to server if get success move to game screen move to game screen
        timeLine.stop();
        moveToGame((Stage)startGameButton.getScene().getWindow());
    }

    public void dismissThePerson(ActionEvent event) throws Exception {
        boolean secondPlayer = secondPlayerCheckBox.isSelected();
        boolean thirdPlayer = thirdPlayerCheckBox.isSelected();
        boolean fourthPlayer = fourthPlayerCheckBox.isSelected();

        // servera dismiss data gönder
        //moveToMainMenu((Stage)dismissPersonButton.getScene().getWindow());
    }

    public void deleteLobby(ActionEvent event) throws Exception {
        // servera delete data gönder

        timeLine.stop();
        moveToMainMenu((Stage)deleteLobbyButton.getScene().getWindow());


    }

    public void update() {

        // get the lobby data and when the game starts move to the game screen
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GeneralResponse<Lobby>> call = apiService.getLobby(Main.user.userName, Main.user.token, Main.lobby.lobbyId);
        call.enqueue(new Callback<GeneralResponse<Lobby>>() {
            @Override
            public void onResponse(Call<GeneralResponse<Lobby>> call, Response<GeneralResponse<Lobby>> response) {
                if (response.body() != null) {

                    GeneralResponse<Lobby> userGeneralResponse = response.body();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (userGeneralResponse.success) {
                                Main.lobby = userGeneralResponse.payload;
                            }
                            else {
                                showErrorMessage(userGeneralResponse.message);

                            }
                        }
                    });

                } else {
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

        for (int i = 0; i < labelsName.length; i++)
            labelsName[i].setText("");

        for (int i = 0; i < labelsState.length; i++)
            labelsState[i].setText("");


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


        // if game starts then
        //         timeLine.stop();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();
        deleteLobbyButton.setOnMouseEntered(e -> deleteLobbyButton.setStyle(HOVERED_BUTTON_STYLE));
        deleteLobbyButton.setOnMouseExited(e -> deleteLobbyButton.setStyle(IDLE_BUTTON_STYLE));
        startGameButton.setOnMouseEntered(e -> startGameButton.setStyle(HOVERED_BUTTON_STYLE));
        startGameButton.setOnMouseExited(e -> startGameButton.setStyle(IDLE_BUTTON_STYLE));
        dismissPersonButton.setOnMouseEntered(e -> dismissPersonButton.setStyle(HOVERED_BUTTON_STYLE));
        dismissPersonButton.setOnMouseExited(e -> dismissPersonButton.setStyle(IDLE_BUTTON_STYLE));

        lobbyCodeTitle.setText("People in the Lobby with the code " + Main.lobby.lobbyCode + ":");

        labelsName[0] = firstNameLabel;
        labelsName[1] = secondNameLabel;
        labelsName[2] = thirdNameLabel;
        labelsName[3] = fourthNameLabel;

        labelsState[0] = firstStateLabel;
        labelsState[1] = secondStateLabel;
        labelsState[2] = thirdStateLabel;
        labelsState[3] = fourthStateLabel;

        // get the lobby data and initilize the people in the lobby
        update();
    }

    private void showErrorMessage(String errorMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }
}
