package uiComponents;

import audioDescription.TextToSpeech;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
    TextToSpeech tts = new TextToSpeech();

    // Sign up button listener
    public void signUp(ActionEvent event) throws Exception {
        SceneHandler.getInstance().moveToSignUp();
    }

    // Sign in button listener
    public void signIn(ActionEvent actionEvent) {
        // Get the inputs
        String username = usernameField.getText();
        String password = passwordField.getText();

        // For loading indicator
        progress = new ProgressIndicator();
        progress.setMaxSize(100, 100);
        progress.setLayoutX(910);
        progress.setLayoutY(490);

        ((AnchorPane)signInButton.getScene().getRoot()).getChildren().add(progress);

        Thread progressThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start loading animation
        progressThread.start();

        Requester requester = ServerConnectionHandler.getInstance().getRequester();
        GeneralResponse<User> user = requester.login(username, password);
        if (user != null) {
            if (user.success) {
                DataHandler.getInstance().setUser(user.payload);
                SceneHandler.getInstance().moveToMainMenu();
            }
            else
                showErrorMessage(user.message);
        }
        else {
            showErrorMessage("There is something wrong with the connection");
        }
    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Settings settings = DataHandler.getInstance().getSettings();

        // Setting the mouse entered and exited listeners for hover effect
        signInButton.setOnMouseEntered(e -> { signInButton.setStyle(HOVERED_BUTTON_STYLE); });

        signInButton.setOnMouseExited(e -> signInButton.setStyle(IDLE_BUTTON_STYLE));

        signUpButton.setOnMouseEntered(e -> { signUpButton.setStyle(HOVERED_BUTTON_STYLE); });

        signUpButton.setOnMouseExited(e -> signUpButton.setStyle(IDLE_BUTTON_STYLE));

        exitButton.setOnMouseEntered(e -> { exitButton.setStyle(HOVERED_BUTTON_STYLE); });

        exitButton.setOnMouseExited(e -> exitButton.setStyle(IDLE_BUTTON_STYLE));
    }

    // Exit button listener
    public void exit(ActionEvent actionEvent) {
        SceneHandler.getInstance().exit();
    }

    int index = 2;
    public void onKeyPress(KeyEvent event)
    {
        if(event.getCode().equals(KeyCode.TAB))
        {
            if(index == 6)
                index=1;
            index++;
            //System.out.println((((Button)event.getSource()).getParent().getChildrenUnmodifiable().get(index)));

            //System.out.println(((Button)event.getSource()).getText());
            // System.out.println(((Button)event.getTarget()).getText());
            //tts.read(((Button)event.getTarget()).getText());

            switch(index)
            {
                case 2: tts.read("Enter  user name"); break;
                case 3: tts.read("Enter password"); break;
                case 4: tts.read("Sign in"); break;
                case 5: tts.read("If you don't have an account, please sign up."); break;
                case 6: tts.read("Exit"); break;
            }
        }
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
