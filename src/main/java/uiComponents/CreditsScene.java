package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
    }
}