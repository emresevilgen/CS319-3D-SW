package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.DataHandler;


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
        DataHandler.getInstance().getSettings().setSoundEffects(soundEffectsCheckBox.isSelected());
        DataHandler.getInstance().getSettings().setAudioDescription(audioDescriptionCheckBox.isSelected());
        String username = usernameField.getText();
        String password = passwordField.getText();

        //System.out.println("username:::::" +username);

        // If there is an input at username of password then confirmation pop up
        if (!username.equals("") || !password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save Settings");
            alert.setHeaderText(null);
            alert.setGraphic(null);

            // Change the text according to the input
            if (!username.equals("") && !password.equals("")) {
                alert.setContentText("Do you want to change your username and password?");
            } else if (username.equals("")) {
                alert.setContentText("Do you want to change your password?");
            } else {
                alert.setContentText("Do you want to change your username?");
            }

            Optional<ButtonType> result = alert.showAndWait();

            // Get the result
            if (result.get() == ButtonType.YES){

                // ---------------------------------------------------
                // ---------------------------------------------------
                // Send a request to the server and if get the success update the main.user then return to the main menu
                // ---------------------------------------------------
                // ---------------------------------------------------

                SceneHandler.getInstance().moveToMainMenu();
            } else {
                alert.close();
            }
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

        Reader tts = AudioDescriptionHandler.getInstance().getReader();

        saveButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && DataHandler.getInstance().getSettings().isAudioDescription()){
                tts.read("Save");
                saveButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                saveButton.setStyle(IDLE_BUTTON_STYLE);
        });
        cancelButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && DataHandler.getInstance().getSettings().isAudioDescription()){
                tts.read("Cancel");
                cancelButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                cancelButton.setStyle(IDLE_BUTTON_STYLE);
        });
        soundEffectsCheckBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && DataHandler.getInstance().getSettings().isAudioDescription()){
                tts.read("Do you want sound effects"); //????
                soundEffectsCheckBox.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                soundEffectsCheckBox.setStyle(IDLE_BUTTON_STYLE);
        });
        audioDescriptionCheckBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && DataHandler.getInstance().getSettings().isAudioDescription()){
                tts.read("do you want audio description "); //????
                audioDescriptionCheckBox.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                audioDescriptionCheckBox.setStyle(IDLE_BUTTON_STYLE);
        });
        usernameField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && DataHandler.getInstance().getSettings().isAudioDescription()){
                tts.read("change the user name");
            }
        });
        passwordField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue && DataHandler.getInstance().getSettings().isAudioDescription()){
                tts.read("change the password");
            }
        });


        // Select the check boxes according to the settings data
        soundEffectsCheckBox.setSelected(DataHandler.getInstance().getSettings().isSoundEffects());
        audioDescriptionCheckBox.setSelected(DataHandler.getInstance().getSettings().isAudioDescription());

    }
}
