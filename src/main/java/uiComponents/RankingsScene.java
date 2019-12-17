package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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

        // ------------------------------------
        // ------------------------------------
        // Request data from the server and set the rankings labels
        // ------------------------------------
        // ------------------------------------

    }
}
