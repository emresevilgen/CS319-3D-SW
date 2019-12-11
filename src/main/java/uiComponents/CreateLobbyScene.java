package uiComponents;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Lobby;
import models.Mode;
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

/**
 * Create Lobby Scene
 */
public class CreateLobbyScene implements Initializable {


    boolean isCreator = true;
    @FXML
    public Button cancelButton;
    @FXML
    public Button createButton;
    @FXML
    public CheckBox gettingLootCheckBox;
    @FXML
    public CheckBox shufflePlacesCheckBox;
    @FXML
    public CheckBox secretSkillsCheckBox;
    @FXML
    public CheckBox invalidMovePenaltyCheckBox;
    @FXML
    public TextField lobbyNameField;


    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #74747c; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #74747c; -fx-opacity: 0.85;";

    // Create button listener
    public void create(ActionEvent event) throws Exception {

        // Create mode with checkbox values and get the lobby name
        Mode mode = new Mode();
        mode.loot = gettingLootCheckBox.isSelected();
        mode.shufflePlaces = shufflePlacesCheckBox.isSelected();
        mode.invalidMovePenalty = invalidMovePenaltyCheckBox.isSelected();
        mode.secretSkills = secretSkillsCheckBox.isSelected();

        String lobbyName = lobbyNameField.getText();

        // Create lobby request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<GeneralResponse<Lobby>> call = apiService.createLobby(Main.user.userName, lobbyName, Main.user.token, mode);

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
                            if (userGeneralResponse.success) {
                                Main.lobby = userGeneralResponse.payload;
                                moveToSeeThePlayers((Stage) createButton.getScene().getWindow(), true);
                            }
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
    }

    // Cancel button listener
    public void cancel(ActionEvent event) throws Exception {
        moveToMainMenu((Stage) cancelButton.getScene().getWindow());
    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting the mouse entered and exited listeners for hover effect
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle(HOVERED_BUTTON_STYLE));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle(IDLE_BUTTON_STYLE));
        createButton.setOnMouseEntered(e -> createButton.setStyle(HOVERED_BUTTON_STYLE));
        createButton.setOnMouseExited(e -> createButton.setStyle(IDLE_BUTTON_STYLE));

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
