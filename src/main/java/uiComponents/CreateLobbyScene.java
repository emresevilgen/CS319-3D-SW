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
import models.DataHandler;
import models.Lobby;
import models.Mode;
import models.Settings;
import rest.ApiInterface;
import rest.Requester;
import rest.ServerConnectionHandler;
import rest.models.GeneralResponse;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Create Lobby Scene
 */
public class CreateLobbyScene implements Initializable {


    boolean isCreator = true;
    @FXML
    public Button cancelButton;
    @FXML
    public Button createButton;
    @FXML
    public CheckBox gettingLootCheckBox;
    @FXML
    public CheckBox shufflePlacesCheckBox;
    @FXML
    public CheckBox secretSkillsCheckBox;
    @FXML
    public CheckBox invalidMovePenaltyCheckBox;
    @FXML
    public TextField lobbyNameField;


    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #74747c; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #74747c; -fx-opacity: 0.85;";

    // For loading animation
    private ProgressIndicator progress;

    // Create button listener
    public void create(ActionEvent event) throws Exception {

        // Create mode with checkbox values and get the lobby name
        Mode mode = new Mode();
        mode.loot = gettingLootCheckBox.isSelected();
        mode.shufflePlaces = shufflePlacesCheckBox.isSelected();
        mode.invalidMovePenalty = invalidMovePenaltyCheckBox.isSelected();
        mode.secretSkills = secretSkillsCheckBox.isSelected();

        String lobbyName = lobbyNameField.getText();

        DataHandler dataHandler = DataHandler.getInstance();

        showProgress();

        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Requester requester = ServerConnectionHandler.getInstance().getRequester();
                GeneralResponse<Lobby> lobby = requester.createLobby(dataHandler.getUser().userName, lobbyName, dataHandler.getUser().token, mode);
                if (lobby != null) {
                    if (lobby.success) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                DataHandler.getInstance().setLobby(lobby.payload);
                                SceneHandler.getInstance().moveToSeeThePlayers(true);
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

    // Cancel button listener
    public void cancel(ActionEvent event) throws Exception {
        SceneHandler.getInstance().moveToMainMenu();
    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        Settings settings = DataHandler.getInstance().getSettings();

        // Setting the mouse entered and exited listeners for hover effect
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle(HOVERED_BUTTON_STYLE));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle(IDLE_BUTTON_STYLE));
        createButton.setOnMouseEntered(e -> createButton.setStyle(HOVERED_BUTTON_STYLE));
        createButton.setOnMouseExited(e -> createButton.setStyle(IDLE_BUTTON_STYLE));


        lobbyNameField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription()){
                tts.read("Enter the lobby name:");
            }
        });
        cancelButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
                    tts.read("Cancel");
                cancelButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                cancelButton.setStyle(IDLE_BUTTON_STYLE);
        });
        createButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription()){
                if (settings.isAudioDescription())
                    tts.read("Create");
                createButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                createButton.setStyle(IDLE_BUTTON_STYLE);
        });

        gettingLootCheckBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription()) {
                    if (!gettingLootCheckBox.isSelected())
                        tts.read("Enable getting loot ");
                    else
                        tts.read("Disable getting loot ");
                }
                gettingLootCheckBox.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                gettingLootCheckBox.setStyle(IDLE_BUTTON_STYLE);
        });

        shufflePlacesCheckBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription()) {
                    if (!shufflePlacesCheckBox.isSelected())
                        tts.read("Enable shuffle places ");
                    else
                        tts.read("Disable shuffle places ");
                }
                shufflePlacesCheckBox.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                shufflePlacesCheckBox.setStyle(IDLE_BUTTON_STYLE);
        });

        secretSkillsCheckBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription()) {
                    if (!secretSkillsCheckBox.isSelected())
                        tts.read("Enable secret skills ");
                    else
                        tts.read("Disable secret skills ");

                }
                secretSkillsCheckBox.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                secretSkillsCheckBox.setStyle(IDLE_BUTTON_STYLE);
        });


        invalidMovePenaltyCheckBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue) {
                if (settings.isAudioDescription()) {
                    if (!invalidMovePenaltyCheckBox.isSelected())
                        tts.read("Enable invalid move penalty ");
                    else
                        tts.read("Disable invalid move penalty ");
                }
                invalidMovePenaltyCheckBox.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                invalidMovePenaltyCheckBox.setStyle(IDLE_BUTTON_STYLE);
        });

    }

    private void showProgress(){
        // For loading indicator
        progress = new ProgressIndicator();
        progress.setMaxSize(100, 100);
        progress.setLayoutX(910);
        progress.setLayoutY(490);

        ((AnchorPane)createButton.getScene().getRoot()).getChildren().add(progress);
        disableItems();

    }

    private void endProgress(){
        ((AnchorPane)createButton.getScene().getRoot()).getChildren().remove(progress);
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
        alert.initOwner(createButton.getScene().getWindow());
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
        createButton.setDisable(true);
        cancelButton.setDisable(true);
        lobbyNameField.setDisable(true);
        gettingLootCheckBox.setDisable(true);
        shufflePlacesCheckBox.setDisable(true);
        secretSkillsCheckBox.setDisable(true);
        invalidMovePenaltyCheckBox.setDisable(true);
    }

    private void enableItems(){
        createButton.setDisable(false);
        cancelButton.setDisable(false);
        lobbyNameField.setDisable(false);
        gettingLootCheckBox.setDisable(false);
        shufflePlacesCheckBox.setDisable(false);
        secretSkillsCheckBox.setDisable(false);
        invalidMovePenaltyCheckBox.setDisable(false);
    }
}
