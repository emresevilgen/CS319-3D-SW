package uiComponents;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static uiComponents.SceneChanger.moveToMainMenu;
import static uiComponents.SceneChanger.moveToSettings;

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
        Main.settings.setSoundEffects(soundEffectsCheckBox.isSelected());
        Main.settings.setAudioDescription(audioDescriptionCheckBox.isSelected());
        String username = usernameField.getText();
        String password = passwordField.getText();

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

                moveToMainMenu((Stage)saveButton.getScene().getWindow());
            } else {
                alert.close();
            }
        }
    }

    // Cancel button listener
    public void cancel(ActionEvent event) throws Exception {
        // Do nothing and move to main menu
        moveToMainMenu((Stage)cancelButton.getScene().getWindow());
    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //usernameField.setText(Main.user.userName); // Show username at the text field

        // Setting the mouse entered and exited listeners for hover effect
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle(HOVERED_BUTTON_STYLE));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle(IDLE_BUTTON_STYLE));
        saveButton.setOnMouseEntered(e -> saveButton.setStyle(HOVERED_BUTTON_STYLE));
        saveButton.setOnMouseExited(e -> saveButton.setStyle(IDLE_BUTTON_STYLE));

        // Select the check boxes according to the settings data
        soundEffectsCheckBox.setSelected(Main.settings.isSoundEffects());
        audioDescriptionCheckBox.setSelected(Main.settings.isAudioDescription());

    }
}
