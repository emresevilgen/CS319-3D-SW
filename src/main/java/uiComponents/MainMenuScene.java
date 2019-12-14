package uiComponents;

import audioDescription.TextToSpeech;
import com.google.gson.Gson;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Lobby;
import models.Settings;
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
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static uiComponents.SceneChanger.*;

public class MainMenuScene implements Initializable {

    @FXML
     public Button createLobbyButton;
    @FXML
    public Button joinLobbyButton;
    @FXML
    public Button seeRankingsButton;
    @FXML
    public Button settingsButton;
    @FXML
    public Button creditsButton;
    @FXML
    public Button signOutButton;
    @FXML
    public Button exitButton;

    TextToSpeech tts = new TextToSpeech();

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #b38632; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #b38632; -fx-opacity: 0.85;";

    // Create button listener
    public void createLobby(ActionEvent event) throws Exception {
        moveToCreateLobby((Stage)createLobbyButton.getScene().getWindow());
    }

    // Join button listener
    public void joinLobby(ActionEvent event) throws Exception {

        // Show pop up to get the lobby code
        TextInputDialog dialog = new TextInputDialog("Lobby Code");
        dialog.setTitle("Join to the Existing Lobby");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        ((Stage)dialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true); // always at the top

        dialog.setContentText("Enter the code of the lobby:");
        String lobbyCode = null;

        Optional<String> result = dialog.showAndWait(); // Show pop up

        // Get input and check if it is valid then send request
        if (result.isPresent()){
            lobbyCode = result.get();

            // Enter Lobby Request
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<GeneralResponse<Lobby>> call = apiService.enterLobby(Main.user.userName, Main.user.token, lobbyCode);
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
                                    moveToSeeThePlayers((Stage) joinLobbyButton.getScene().getWindow(), false);
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
    }

    // Rankings button listener
    public void rankings(ActionEvent event) throws Exception {
        moveToRankings((Stage)seeRankingsButton.getScene().getWindow());

    }

    // Settings button listener
    public void settings(ActionEvent event) throws Exception {
        moveToSettings((Stage)settingsButton.getScene().getWindow());

    }

    // Credits button listener
    public void credits(ActionEvent event) throws Exception {
        moveToCredits((Stage)creditsButton.getScene().getWindow());

    }

    // Exit button listener
    public void exit(ActionEvent event) throws Exception {
        // Show confirmation pop up
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.setTitle("Exit Game");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Do you want to exit?");
        // Add options
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonNo, buttonYes);

        // Get the result
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            SceneChanger.exit((Stage)exitButton.getScene().getWindow()); // Exit
        } else {
            alert.close(); // Cancel
        }
    }

    // Signout button listener
    public void signOut(ActionEvent actionEvent) {
        // Show confirmation pop up
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.setTitle("Sign Out");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Do you want to sign out?");
        // Add options
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonNo, buttonYes);

        // Get the result
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            // ----------------------
            //----------------------
            // send a request to the server
            // ----------------------
            //----------------------
            Main.lobby = null;
            Main.game = null;
            Main.user = null;
            moveToSignIn((Stage)signOutButton.getScene().getWindow()); // Move to sign in
        } else {
            alert.close(); // Cancel
        }

    }

    int index = 2;
    public void onKeyPress(KeyEvent event)
    {
        if(event.getCode().equals(KeyCode.TAB))
        {
            if(index == 8)
                index=1;
            index++;
            System.out.println((((Button)event.getSource()).getParent().getChildrenUnmodifiable().get(index)));
            //System.out.println(((Button)event.getSource()).getText());
           // System.out.println(((Button)event.getTarget()).getText());
            //tts.read(((Button)event.getTarget()).getText());

            if(index == 2)
                tts.read("Create a Lobby");
            else if(index ==3)
                tts.read("Join to the Existing Lobby");
            else if(index==4)
                tts.read("See the Rankings");
            else if(index==5)
                tts.read("Settings");
            else if(index==6)
                tts.read("Credits");
            else if(index==7)
                tts.read("Exit");
            else if(index==8)
                tts.read("Sign Out");
        }
    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting the mouse entered and exited listeners for hover effect
        createLobbyButton.setOnMouseEntered(e -> createLobbyButton.setStyle(HOVERED_BUTTON_STYLE));
        createLobbyButton.setOnMouseExited(e -> createLobbyButton.setStyle(IDLE_BUTTON_STYLE));
        joinLobbyButton.setOnMouseEntered(e -> joinLobbyButton.setStyle(HOVERED_BUTTON_STYLE));
        joinLobbyButton.setOnMouseExited(e -> joinLobbyButton.setStyle(IDLE_BUTTON_STYLE));
        seeRankingsButton.setOnMouseEntered(e -> seeRankingsButton.setStyle(HOVERED_BUTTON_STYLE));
        seeRankingsButton.setOnMouseExited(e -> seeRankingsButton.setStyle(IDLE_BUTTON_STYLE));
        settingsButton.setOnMouseEntered(e -> settingsButton.setStyle(HOVERED_BUTTON_STYLE));
        settingsButton.setOnMouseExited(e -> settingsButton.setStyle(IDLE_BUTTON_STYLE));
        creditsButton.setOnMouseEntered(e -> creditsButton.setStyle(HOVERED_BUTTON_STYLE));
        creditsButton.setOnMouseExited(e -> creditsButton.setStyle(IDLE_BUTTON_STYLE));
        signOutButton.setOnMouseEntered(e -> signOutButton.setStyle(HOVERED_BUTTON_STYLE));
        signOutButton.setOnMouseExited(e -> signOutButton.setStyle(IDLE_BUTTON_STYLE));
        exitButton.setOnMouseEntered(e -> exitButton.setStyle(HOVERED_BUTTON_STYLE));
        exitButton.setOnMouseExited(e -> exitButton.setStyle(IDLE_BUTTON_STYLE));

        TextToSpeech tts = new TextToSpeech();
        tts.read("Create a Lobby");//fxml i yüklemeden söylüyor
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
