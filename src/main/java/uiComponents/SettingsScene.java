package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.DataHandler;
import models.Settings;


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsScene implements Initializable {
    @FXML
    public Button cancelButton;
    @FXML
    public Button saveButton;
    @FXML
    public CheckBox soundEffectsCheckBox;
    @FXML
    public CheckBox audioDescriptionCheckBox;
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #1a1d26; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #1a1d26; -fx-opacity: 0.85;";

    // Save button listener
    public void save(ActionEvent event) throws Exception {
        // Get the inputs
        Settings settings = DataHandler.getInstance().getSettings();
        settings.setSoundEffects(soundEffectsCheckBox.isSelected());
        settings.setAudioDescription(audioDescriptionCheckBox.isSelected());
        String username = usernameField.getText();
        String password = passwordField.getText();

        // If there is an input at username of password then confirmation pop up
        if (!username.equals("") || !password.equals("")) {
            disableItems();
            final boolean[] first = {true};
            Reader tts = AudioDescriptionHandler.getInstance().getReader();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save Settings");
            alert.setHeaderText(null);
            alert.initOwner(saveButton.getScene().getWindow());
            alert.setGraphic(null);
            alert.initOwner(saveButton.getScene().getWindow());

            String text;

            // Change the text according to the input
            if (!username.equals("") && !password.equals("")) {
                text = "Do you want to change your username and password?";
            } else if (username.equals("")) {
                text = "Do you want to change your password?";
            } else {
                text = "Do you want to change your username?";
            }

            alert.setContentText(text);

            if (settings.isAudioDescription())
                tts.read(text + " Press enter to say yes or no. No");


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


            Optional<ButtonType> result = alert.showAndWait();

            // Get the result
            if (result.get() == buttonYes){

                // ---------------------------------------------------
                // ---------------------------------------------------
                // Send a request to the server and if get the success update the main.user then return to the main menu
                // ---------------------------------------------------
                // ---------------------------------------------------

                SceneHandler.getInstance().moveToMainMenu();
            } else {
                alert.close();
            }
            enableItems();
        }
        else {
            if (DataHandler.getInstance().getSettings().isSoundEffects())
                SceneHandler.getInstance().playMusic();
            else
                if (SceneHandler.getInstance().getMediaPlayer() != null)
                    SceneHandler.getInstance().getMediaPlayer().stop();
            SceneHandler.getInstance().moveToMainMenu();
        }

    }

    // Cancel button listener
    public void cancel(ActionEvent event) throws Exception {
        // Do nothing and move to main menu
        SceneHandler.getInstance().moveToMainMenu();
    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //usernameField.setText(DataHandler.getInstance().getUser().userName); // Show username at the text field

        // Setting the mouse entered and exited listeners for hover effect
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle(HOVERED_BUTTON_STYLE));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle(IDLE_BUTTON_STYLE));
        saveButton.setOnMouseEntered(e -> saveButton.setStyle(HOVERED_BUTTON_STYLE));
        saveButton.setOnMouseExited(e -> saveButton.setStyle(IDLE_BUTTON_STYLE));

        // Add focused properties to read the audio descriptions
        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        Settings settings = DataHandler.getInstance().getSettings();
        saveButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
                    tts.read("Save");
                saveButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                saveButton.setStyle(IDLE_BUTTON_STYLE);
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
        soundEffectsCheckBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
                    if (soundEffectsCheckBox.isSelected())
                        tts.read("Do you want to stop the sound effects");
                    else
                        tts.read("Do you want to enable the sound effects");

                soundEffectsCheckBox.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                soundEffectsCheckBox.setStyle(IDLE_BUTTON_STYLE);
        });
        audioDescriptionCheckBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription())
                    if (audioDescriptionCheckBox.isSelected())
                        tts.read("Do you want to stop the audio description");
                    else
                        tts.read("Do you want to enable the audio description");

                audioDescriptionCheckBox.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                audioDescriptionCheckBox.setStyle(IDLE_BUTTON_STYLE);
        });
        usernameField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription()){
                tts.read("change the user name");
            }
        });
        passwordField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && settings.isAudioDescription()){
                tts.read("change the password");
            }
        });

        // Select the check boxes according to the settings data
        soundEffectsCheckBox.setSelected(DataHandler.getInstance().getSettings().isSoundEffects());
        audioDescriptionCheckBox.setSelected(DataHandler.getInstance().getSettings().isAudioDescription());

    }

    private void disableItems(){
        saveButton.setDisable(true);
        cancelButton.setDisable(true);

    }

    private void enableItems() {
        saveButton.setDisable(false);
        cancelButton.setDisable(false);
    }
}
