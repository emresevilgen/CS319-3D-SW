package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.DataHandler;
import models.Settings;

import java.net.URL;
import java.util.ResourceBundle;


public class CreditsScene implements Initializable {
    public Button backButton;

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #000000; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #000000; -fx-opacity: 0.85;";

    // Back button listener
    public void back(ActionEvent actionEvent) {
        SceneHandler.getInstance().moveToMainMenu();
    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting the mouse entered and exited listeners for hover effect
        backButton.setOnMouseEntered(e -> backButton.setStyle(HOVERED_BUTTON_STYLE));
        backButton.setOnMouseExited(e -> backButton.setStyle(IDLE_BUTTON_STYLE));

        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        Settings settings = DataHandler.getInstance().getSettings();
        if(settings.isAudioDescription())
        {
            tts.read("group 3d sw demo  ");
        }

        backButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue  && settings.isAudioDescription()){
                tts.read("Back");
                backButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                backButton.setStyle(IDLE_BUTTON_STYLE);
        });
    }
}