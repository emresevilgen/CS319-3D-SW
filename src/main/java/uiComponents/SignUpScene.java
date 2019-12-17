package uiComponents;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    public void signUp(ActionEvent actionEvent) throws InterruptedException {
        // Get the inputs
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        Requester requester = ServerConnectionHandler.getInstance().getRequester();
        User user = requester.signUp(name, username, password);
        if (user != null){
            requester.login(username, password);
            DataHandler.getInstance().setUser(user);
            SceneHandler.getInstance().moveToMainMenu();
        }

    }

    // Back button listener
    public void back(ActionEvent actionEvent) throws IOException {
        SceneHandler.getInstance().moveToSignIn();
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

