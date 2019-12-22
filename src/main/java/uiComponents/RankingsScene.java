package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.DataHandler;
import models.Settings;

import java.net.URL;
import java.util.ResourceBundle;

public class RankingsScene implements Initializable {
    @FXML
    public Button backButton;
    @FXML
    public Label firstNameLabel;
    @FXML
    public Label firstNoLabel;
    @FXML
    public Label secondNameLabel;
    @FXML
    public Label secondNoLabel;
    @FXML
    public Label thirdNameLabel;
    @FXML
    public Label thirdNoLabel;
    @FXML
    public Label fourthNameLabel;
    @FXML
    public Label fourthNoLabel;
    @FXML
    public Label fifthNameLabel;
    @FXML
    public Label fifthNoLabel;

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #8c8686; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #8c8686; -fx-opacity: 0.85;";

    // Back button listener
    public void back(ActionEvent event) throws Exception {
        SceneHandler.getInstance().moveToMainMenu();
    }

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setting the mouse entered and exited listeners for hover effect
        backButton.setOnMouseEntered(e -> backButton.setStyle(HOVERED_BUTTON_STYLE));
        backButton.setOnMouseExited(e -> backButton.setStyle(IDLE_BUTTON_STYLE));

        Settings settings = DataHandler.getInstance().getSettings();
        Reader tts = AudioDescriptionHandler.getInstance().getReader();


        // ------------------------------------
        // ------------------------------------
        // Request data from the server and set the rankings labels
        // ------------------------------------
        // ------------------------------------

        if(settings.isAudioDescription())
        {
            tts.read( "1st player is " + firstNameLabel.getText()+ ". 2nd is " + secondNameLabel.getText()
                    + ". 3rd player is " + thirdNameLabel.getText()+ ". 4th player is " + fourthNameLabel.getText()
                    + ". 5th is  " + fifthNameLabel.getText() + ". \n Back");
        }

        final boolean[] first = {true};
        backButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first[0])
                    tts.read("Back");
                backButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                backButton.setStyle(IDLE_BUTTON_STYLE);
            first[0] = false;
        });

    }
}
