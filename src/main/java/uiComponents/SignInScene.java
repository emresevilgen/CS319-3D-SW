package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.DescriptionReader;
import audioDescription.Reader;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import models.DataHandler;
import models.Settings;
import models.User;
import rest.Requester;
import rest.ServerConnectionHandler;
import rest.models.GeneralResponse;


import java.net.URL;
import java.util.EventListener;
import java.util.Optional;
import java.util.ResourceBundle;

public class SignInScene implements Initializable{

    @FXML
    public Button signUpButton;
    @FXML
    public Button signInButton;
    @FXML
    public Button exitButton;
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #b38632; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #b38632; -fx-opacity: 0.85;";

    // For loading animation
    private ProgressIndicator progress;

    private boolean readCheck = true;
    private boolean firstTime = true;

    // Sign up button listener
    public void signUp(ActionEvent event) throws Exception {
        SceneHandler.getInstance().moveToSignUp();
    }

    // Sign in button listener
    public void signIn(ActionEvent actionEvent) {
        // Get the inputs
        String username = usernameField.getText();
        String password = passwordField.getText();

        showProgress();

        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Requester requester = ServerConnectionHandler.getInstance().getRequester();
                GeneralResponse<User> user = requester.login(username, password);
                if (user != null) {
                    if (user.success) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                DataHandler.getInstance().setUser(user.payload);
                                SceneHandler.getInstance().moveToMainMenu();
                            }
                        });
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                endProgress();
                                showErrorMessage(user.message);
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
                    });
                }
            }
        });
        requestThread.start();


    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Settings settings = DataHandler.getInstance().getSettings();
        Reader tts = AudioDescriptionHandler.getInstance().getReader();

        if (settings.isAudioDescription()){
            tts.read("Welcome to the Seven Wonders Game. \n Enter user name.");
        }

        // Setting the mouse entered and exited listeners for hover effect
        signInButton.setOnMouseEntered(e -> { signInButton.setStyle(HOVERED_BUTTON_STYLE); });
        signInButton.setOnMouseExited(e -> signInButton.setStyle(IDLE_BUTTON_STYLE));
        signUpButton.setOnMouseEntered(e -> { signUpButton.setStyle(HOVERED_BUTTON_STYLE); });
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle(IDLE_BUTTON_STYLE));
        exitButton.setOnMouseEntered(e -> { exitButton.setStyle(HOVERED_BUTTON_STYLE); });
        exitButton.setOnMouseExited(e -> exitButton.setStyle(IDLE_BUTTON_STYLE));

        usernameField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription() && !firstTime){
                tts.read("Enter  user name");
            }
            firstTime = false;
        });

        passwordField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription()){
                tts.read("Enter password");
            }
        });

        signInButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
                    tts.read("Sign in");
                signInButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                signInButton.setStyle(IDLE_BUTTON_STYLE);
        });

        signUpButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
                    tts.read("If you don't have an account, please sign up");
                signUpButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                signUpButton.setStyle(IDLE_BUTTON_STYLE);
        });

        exitButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
                    tts.read("Exit");
                exitButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                exitButton.setStyle(IDLE_BUTTON_STYLE);
        });
    }

    // Exit button listener
    public void exit(ActionEvent actionEvent) {
        SceneHandler.getInstance().exit();
    }



    private void showProgress(){
        // For loading indicator
        progress = new ProgressIndicator();
        progress.setMaxSize(100, 100);
        progress.setLayoutX(910);
        progress.setLayoutY(490);

        ((AnchorPane)signInButton.getScene().getRoot()).getChildren().add(progress);

        disableItems();
    }

    private void endProgress(){
        ((AnchorPane)signInButton.getScene().getRoot()).getChildren().remove(progress);
        enableItems();
    }

    // Error message
    private void showErrorMessage(String errorMsg){
        disableItems();

        final boolean[] first = {true};
        DataHandler dataHandler = DataHandler.getInstance();

        if (dataHandler.getSettings().isAudioDescription()) {
            AudioDescriptionHandler.getInstance().getReader().read("Error. " + errorMsg + " Press enter to say OK");
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMsg);
        alert.initOwner(signInButton.getScene().getWindow());
        alert.getButtonTypes().forEach(buttonType -> {
            alert.getDialogPane().lookupButton(buttonType).focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue && dataHandler.getSettings().isAudioDescription() && !first[0])
                    AudioDescriptionHandler.getInstance().getReader().read(buttonType.getText());
                first[0] = false;
            });
        });
        alert.showAndWait();

        enableItems();

    }

    private void disableItems(){
        signInButton.setDisable(true);
        signUpButton.setDisable(true);
        exitButton.setDisable(true);
        usernameField.setDisable(true);
        passwordField.setDisable(true);
    }

    private void enableItems(){
        signInButton.setDisable(false);
        signUpButton.setDisable(false);
        exitButton.setDisable(false);
        usernameField.setDisable(false);
        passwordField.setDisable(false);
    }

}
