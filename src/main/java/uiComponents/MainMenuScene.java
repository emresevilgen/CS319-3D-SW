package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.DataHandler;
import models.Lobby;
import models.Settings;
import rest.Requester;
import rest.ServerConnectionHandler;
import rest.models.GeneralResponse;

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

    Reader tts = AudioDescriptionHandler.getInstance().getReader();
    Settings settings = DataHandler.getInstance().getSettings();

    // For loading animation
    private ProgressIndicator progress;

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
        dialog.initOwner(joinLobbyButton.getScene().getWindow());
        ((Stage)dialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true); // always at the top

        if(settings.isAudioDescription())
        {
            tts.read("Enter the code of the lobby");
        }

        dialog.setContentText("Enter the code of the lobby:");

        Optional<String> result = dialog.showAndWait(); // Show pop up

        DataHandler dataHandler = DataHandler.getInstance();

        // Get input and check if it is valid then send request
        if (result.isPresent()){
            String lobbyCode = result.get();

            showProgress();

            Thread requestThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Requester requester = ServerConnectionHandler.getInstance().getRequester();
                    GeneralResponse<Lobby> lobby = requester.enterLobby(dataHandler.getUser().userName, dataHandler.getUser().token, lobbyCode);
                    if (lobby != null) {
                        if (lobby.success) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    DataHandler.getInstance().setLobby(lobby.payload);
                                    SceneHandler.getInstance().moveToSeeThePlayers(false);
                                }
                            });
                        }
                        else {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    endProgress();
                                    showErrorMessage(lobby.message);
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
        SceneHandler.getInstance().exit();
    }

    // Signout button listener
    public void signOut(ActionEvent actionEvent) {
        // Show confirmation pop up
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.setTitle("Sign Out");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.initOwner(signOutButton.getScene().getWindow());
        alert.setContentText("Do you want to sign out?");
        if(settings.isAudioDescription())
        {
            tts.read("Do you want to sign out?");
        }
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
            DataHandler.getInstance().setUser(null);
            SceneHandler.getInstance().moveToSignIn(); // Move to sign in
        } else {
            alert.close(); // Cancel
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


        createLobbyButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription()){
                tts.read("Create a Lobby");
                createLobbyButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                createLobbyButton.setStyle(IDLE_BUTTON_STYLE);
        });

        joinLobbyButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription()){
                tts.read("Join to the Existing Lobby");
                joinLobbyButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                joinLobbyButton.setStyle(IDLE_BUTTON_STYLE);
        });

        seeRankingsButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription()){
                tts.read("See the Rankings");
                seeRankingsButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                seeRankingsButton.setStyle(IDLE_BUTTON_STYLE);
        });

        settingsButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription()){
                tts.read("Settings");
                settingsButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                settingsButton.setStyle(IDLE_BUTTON_STYLE);
        });

        creditsButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription()){
                tts.read("Credits");
                creditsButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                creditsButton.setStyle(IDLE_BUTTON_STYLE);
        });

        exitButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription()){
                tts.read("Exit");
                exitButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                exitButton.setStyle(IDLE_BUTTON_STYLE);
        });
        signOutButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription()){
                tts.read("Sign Out");
                signOutButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                signOutButton.setStyle(IDLE_BUTTON_STYLE);
        });
    }

    private void showProgress(){
        // For loading indicator
        progress = new ProgressIndicator();
        progress.setMaxSize(100, 100);
        progress.setLayoutX(910);
        progress.setLayoutY(490);

        ((AnchorPane)joinLobbyButton.getScene().getRoot()).getChildren().add(progress);

        createLobbyButton.setDisable(true);
        joinLobbyButton.setDisable(true);
        seeRankingsButton.setDisable(true);
        settingsButton.setDisable(true);
        creditsButton.setDisable(true);
        signOutButton.setDisable(true);
        exitButton.setDisable(true);

    }

    private void endProgress(){
        ((AnchorPane)joinLobbyButton.getScene().getRoot()).getChildren().remove(progress);

        createLobbyButton.setDisable(false);
        joinLobbyButton.setDisable(false);
        seeRankingsButton.setDisable(false);
        settingsButton.setDisable(false);
        creditsButton.setDisable(false);
        signOutButton.setDisable(false);
        exitButton.setDisable(false);
    }

    // Error message
    private void showErrorMessage(String errorMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMsg);
        alert.initOwner(joinLobbyButton.getScene().getWindow());
        alert.showAndWait();

    }


}
