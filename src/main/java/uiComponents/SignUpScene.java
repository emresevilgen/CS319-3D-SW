package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.DataHandler;
import models.Settings;
import models.User;
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


public class SignUpScene implements Initializable{
    public Button signUpButton;
    public Button backButton;
    public TextField nameField;
    public TextField usernameField;
    public PasswordField passwordField;

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #b38632; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #b38632; -fx-opacity: 0.85;";

    // For loading animation
    private ProgressIndicator progress;

    // Sign up button listener
    public void signUp(ActionEvent actionEvent) throws InterruptedException {
        // Get the inputs
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        showProgress();

        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Requester requester = ServerConnectionHandler.getInstance().getRequester();
                GeneralResponse<User> user1 = requester.signUp(name, username, password);
                if (user1 != null) {
                    if (user1.success) {
                        GeneralResponse<User> user2 = requester.login(username, password);
                        if (user2 != null) {
                            if (user2.success) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        DataHandler.getInstance().setUser(user2.payload);
                                        SceneHandler.getInstance().moveToMainMenu();
                                    }
                                });
                            }
                            else {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        endProgress();
                                        showErrorMessage(user2.message);
                                    }
                                });                            }
                        }
                        else {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    endProgress();
                                    showErrorMessage("There is something wrong with the connection");
                                }
                            });
                        }
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                endProgress();
                                showErrorMessage(user1.message);
                            }
                        });
                    }
                }
                else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            endProgress();
                            showErrorMessage("There is something wrong with the connection");
                        }
                    });                }
            }
        });
        requestThread.start();
    }

    // Back button listener
    public void back(ActionEvent actionEvent) throws IOException {
        SceneHandler.getInstance().moveToSignIn();
    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Reader tts = AudioDescriptionHandler.getInstance().getReader();

        // Setting the mouse entered and exited listeners for hover effect
        backButton.setOnMouseEntered(e -> backButton.setStyle(HOVERED_BUTTON_STYLE));
        backButton.setOnMouseExited(e -> backButton.setStyle(IDLE_BUTTON_STYLE));
        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle(HOVERED_BUTTON_STYLE));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle(IDLE_BUTTON_STYLE));

        nameField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                tts.read("Enter name");
            }
        });

        usernameField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                tts.read("Enter user name");
            }
        });

        passwordField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                tts.read("Enter password");
            }
        });

        signUpButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                tts.read("Sign up");
                signUpButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                signUpButton.setStyle(IDLE_BUTTON_STYLE);
        });

        backButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                tts.read("Back");
                backButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                backButton.setStyle(IDLE_BUTTON_STYLE);
        });


    }

    private void showProgress(){
        // For loading indicator
        progress = new ProgressIndicator();
        progress.setMaxSize(100, 100);
        progress.setLayoutX(910);
        progress.setLayoutY(490);

        ((AnchorPane)signUpButton.getScene().getRoot()).getChildren().add(progress);

        signUpButton.setDisable(true);
        backButton.setDisable(true);
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        nameField.setDisable(true);

    }

    private void endProgress(){
        ((AnchorPane)signUpButton.getScene().getRoot()).getChildren().remove(progress);
        signUpButton.setDisable(false);
        backButton.setDisable(false);
        usernameField.setDisable(false);
        passwordField.setDisable(false);
        nameField.setDisable(false);
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

