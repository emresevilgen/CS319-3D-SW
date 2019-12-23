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
import java.util.Set;

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

    // For loading animation
    private ProgressIndicator progress;

    // Create button listener
    public void createLobby(ActionEvent event) throws Exception {
        SceneHandler.getInstance().moveToCreateLobby();
    }

    // Join button listener
    public void joinLobby(ActionEvent event) throws Exception {
        disableItems();
        // Show pop up to get the lobby code
        TextInputDialog dialog = new TextInputDialog("Lobby Code");
        dialog.setTitle("Join to the Existing Lobby");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.initOwner(joinLobbyButton.getScene().getWindow());

        final boolean[] first = {true};

        Settings settings = DataHandler.getInstance().getSettings();
        Reader tts = AudioDescriptionHandler.getInstance().getReader();

        // Add listeners to the dialog buttons and audio description read
        dialog.setContentText("Enter the code of the lobby");
        dialog.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && settings.isAudioDescription() && !first[0])
                AudioDescriptionHandler.getInstance().getReader().read("Enter the code of the lobby");
            first[0] = false;
        });
        dialog.getDialogPane().getButtonTypes().forEach(buttonType -> {
            dialog.getDialogPane().lookupButton(buttonType).focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue && settings.isAudioDescription() && !first[0])
                    AudioDescriptionHandler.getInstance().getReader().read(buttonType.getText());
                first[0] = false;
            });
        });

        Optional<String> result = dialog.showAndWait(); // Show pop up
        enableItems();

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
        disableItems();
        SceneHandler.getInstance().exit();
        enableItems();
    }

    // Signout button listener
    public void signOut(ActionEvent actionEvent) {
        disableItems();
        final boolean[] first = {true};
        Settings settings = DataHandler.getInstance().getSettings();
        Reader tts = AudioDescriptionHandler.getInstance().getReader();

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
            tts.read("Do you want to sign out? Press enter to say yes or no. No");
        }

        // Add options
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonNo, buttonYes);

        alert.getButtonTypes().forEach(buttonType -> {
            alert.getDialogPane().lookupButton(buttonType).focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue && settings.isAudioDescription() && !first[0])
                    AudioDescriptionHandler.getInstance().getReader().read(buttonType.getText());
                first[0] = false;
            });
        });

        // Get the result
        Optional<ButtonType> result = alert.showAndWait();
        enableItems();


        if (result.get() == buttonYes){
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

        Settings settings = DataHandler.getInstance().getSettings();
        Reader tts = AudioDescriptionHandler.getInstance().getReader();

        createLobbyButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
                    tts.read("Create a Lobby");
                createLobbyButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                createLobbyButton.setStyle(IDLE_BUTTON_STYLE);
        });

        joinLobbyButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
                    tts.read("Join to the Existing Lobby");
                joinLobbyButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                joinLobbyButton.setStyle(IDLE_BUTTON_STYLE);
        });

        seeRankingsButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
                    tts.read("See the Rankings");
                seeRankingsButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                seeRankingsButton.setStyle(IDLE_BUTTON_STYLE);
        });

        settingsButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
                    tts.read("Settings");
                settingsButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                settingsButton.setStyle(IDLE_BUTTON_STYLE);
        });

        creditsButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
                    tts.read("Credits");
                creditsButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                creditsButton.setStyle(IDLE_BUTTON_STYLE);
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
        signOutButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
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
        disableItems();

    }

    private void endProgress(){
        ((AnchorPane)joinLobbyButton.getScene().getRoot()).getChildren().remove(progress);
        enableItems();
    }

    // Error message
    private void showErrorMessage(String errorMsg){
        disableItems();

        final boolean[] first = {true};
        DataHandler dataHandler = DataHandler.getInstance();

        // Read error
        if (dataHandler.getSettings().isAudioDescription()) {
            AudioDescriptionHandler.getInstance().getReader().read("Error. " + errorMsg + " Press enter to say OK");
        }

        // Show alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMsg);
        alert.initOwner(createLobbyButton.getScene().getWindow());
        alert.getButtonTypes().forEach(buttonType -> {
            alert.getDialogPane().lookupButton(buttonType).focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(dataHandler.getSettings().isAudioDescription() && !first[0])
                    AudioDescriptionHandler.getInstance().getReader().read(buttonType.getText());
                first[0] = false;
            });
        });
        alert.showAndWait();


        enableItems();

    }

    private void disableItems(){
        createLobbyButton.setDisable(true);
        joinLobbyButton.setDisable(true);
        seeRankingsButton.setDisable(true);
        settingsButton.setDisable(true);
        creditsButton.setDisable(true);
        signOutButton.setDisable(true);
        exitButton.setDisable(true);
    }

    private void enableItems(){
        createLobbyButton.setDisable(false);
        joinLobbyButton.setDisable(false);
        seeRankingsButton.setDisable(false);
        settingsButton.setDisable(false);
        creditsButton.setDisable(false);
        signOutButton.setDisable(false);
        exitButton.setDisable(false);
    }


}
