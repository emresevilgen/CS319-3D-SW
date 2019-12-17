package uiComponents;

import audioDescription.TextToSpeech;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.DataHandler;
import models.Lobby;
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
import java.util.Optional;
import java.util.ResourceBundle;

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

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #b38632; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #b38632; -fx-opacity: 0.85;";

    // Create button listener
    public void createLobby(ActionEvent event) throws Exception {
        SceneHandler.getInstance().moveToCreateLobby();
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
            Requester requester = ServerConnectionHandler.getInstance().getRequester();
            DataHandler dataHandler = DataHandler.getInstance();
            Lobby lobby = requester.enterLobby(dataHandler.getUser().userName, dataHandler.getUser().token, lobbyCode);
            if (lobby != null) {
                dataHandler.setLobby(lobby);
                SceneHandler.getInstance().moveToSeeThePlayers(false);
            }
        }
    }

    // Rankings button listener
    public void rankings(ActionEvent event) throws Exception {
        SceneHandler.getInstance().moveToRankings();
    }

    // Settings button listener
    public void settings(ActionEvent event) throws Exception {
        SceneHandler.getInstance().moveToSettings();
    }

    // Credits button listener
    public void credits(ActionEvent event) throws Exception {
        SceneHandler.getInstance().moveToCredits();
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
            SceneHandler.getInstance().exit(); // Exit
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
            DataHandler.getInstance().setLobby(null);
            DataHandler.getInstance().setGame(null);
            DataHandler.getInstance().setUser(null);
            SceneHandler.getInstance().moveToSignIn(); // Move to sign in
        } else {
            alert.close(); // Cancel
        }

    }


    TextToSpeech tts = new TextToSpeech();
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

            switch(index)
            {
                case 2: tts.read("Create a Lobby"); break;
                case 3: tts.read("Join to the Existing Lobby"); break;
                case 4: tts.read("See the Rankings"); break;
                case 5: tts.read("Settings"); break;
                case 6: tts.read("Credits"); break;
                case 7: tts.read("Exit"); break;
                case 8:  tts.read("Sign Out"); break;
            }
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
}
