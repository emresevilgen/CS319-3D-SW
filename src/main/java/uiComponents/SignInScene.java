package uiComponents;

import audioDescription.TextToSpeech;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.DataHandler;
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

    // Sign up button listener
    public void signUp(ActionEvent event) throws Exception {
        SceneHandler.getInstance().moveToSignUp();
    }

    // Sign in button listener
    public void signIn(ActionEvent actionEvent) throws InterruptedException {
        // Get the inputs
        String username = usernameField.getText();
        String password = passwordField.getText();

        // For loading indicator
        progress = new ProgressIndicator();
        progress.setMaxSize(100, 100);
        progress.setLayoutX(910);
        progress.setLayoutY(490);

        ((AnchorPane)signInButton.getScene().getRoot()).getChildren().add(progress);

        // Thread progressThread = new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         try {
        //           Thread.sleep(1000);
        //       } catch (InterruptedException e) {
        //           e.printStackTrace();
        //       }
        //   }
        // });

        // Start loading animation
        // progressThread.start();

        Requester requester = ServerConnectionHandler.getInstance().getRequester();
        User user = requester.login(username, password);
        if (user != null) {
            DataHandler.getInstance().setUser(user);
            SceneHandler.getInstance().moveToMainMenu();
        }
    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting the mouse entered and exited listeners for hover effect
        signInButton.setOnMouseEntered(e -> {
            signInButton.setStyle(HOVERED_BUTTON_STYLE);
            tts.read("Sign in");

        });

        signInButton.setOnMouseExited(e -> signInButton.setStyle(IDLE_BUTTON_STYLE));

        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle(HOVERED_BUTTON_STYLE));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle(IDLE_BUTTON_STYLE));
        exitButton.setOnMouseEntered(e -> exitButton.setStyle(HOVERED_BUTTON_STYLE));
        exitButton.setOnMouseExited(e -> exitButton.setStyle(IDLE_BUTTON_STYLE));

        usernameField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) { // focus
                tts.read("Enter user name");
            }
        });

        passwordField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) { // focus
                tts.read("Enter password");
            }
        });

        signInButton.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) { // focus
                tts.read("Sign in");
            }
        });

        exitButton.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) { // focus
                tts.read("Exit");
            }
        });

        signUpButton.focusedProperty().addListener((ov, oldV, newV) -> {
            if (newV) { // focus
                tts.read("If you don't have an account, please sign up.");
            }
        });




    }

    // Exit button listener
    public void exit(ActionEvent actionEvent) {
        SceneHandler.getInstance().exit();
    }

    TextToSpeech tts = new TextToSpeech();

}
