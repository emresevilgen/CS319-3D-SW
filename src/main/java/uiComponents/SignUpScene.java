package uiComponents;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.User;
import rest.ApiClient;
import rest.ApiInterface;
import rest.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static uiComponents.SceneChanger.moveToMainMenu;
import static uiComponents.SceneChanger.moveToSignIn;

public class SignUpScene implements Initializable{
    public Button signUpButton;
    public Button backButton;
    public TextField nameField;
    public TextField usernameField;
    public PasswordField passwordField;

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #b38632; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #b38632; -fx-opacity: 0.85;";

    // Sign up button listener
    public void signUp(ActionEvent actionEvent) {
        // Get the inputs
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Sign up request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GeneralResponse<User>> call = apiService.signUp(name, username, password);
        call.enqueue(new Callback<GeneralResponse<User>>() {
            // If the connection is valid
            @Override
            public void onResponse(Call<GeneralResponse<User>> call, Response<GeneralResponse<User>> response) {
                if (response.body() != null) {
                    // Get the response
                    GeneralResponse<User> userGeneralResponse = response.body();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // If success update lobby and move to see the players
                            if (userGeneralResponse.success) {
                                Main.user = userGeneralResponse.payload;
                                moveToMainMenu((Stage) signUpButton.getScene().getWindow());
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
            public void onFailure(Call<GeneralResponse<User>> call, Throwable t) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        showErrorMessage("There is something wrong with the connection");

                    }
                });
            }
        });

    }

    // Error message
    private void showErrorMessage(String errorMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }

    // Back button listener
    public void back(ActionEvent actionEvent) throws IOException {
        moveToSignIn((Stage)backButton.getScene().getWindow() );
    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting the mouse entered and exited listeners for hover effect
        backButton.setOnMouseEntered(e -> backButton.setStyle(HOVERED_BUTTON_STYLE));
        backButton.setOnMouseExited(e -> backButton.setStyle(IDLE_BUTTON_STYLE));
        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle(HOVERED_BUTTON_STYLE));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle(IDLE_BUTTON_STYLE));
    }
}

