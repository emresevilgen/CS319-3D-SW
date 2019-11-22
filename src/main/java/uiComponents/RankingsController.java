package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static uiComponents.SceneChanger.moveToMainMenu;

public class RankingsController implements Initializable {
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

    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #8c8686; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #8c8686; -fx-opacity: 0.85;";

    public void back(ActionEvent event) throws Exception {
        moveToMainMenu((Stage)backButton.getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnMouseEntered(e -> backButton.setStyle(HOVERED_BUTTON_STYLE));
        backButton.setOnMouseExited(e -> backButton.setStyle(IDLE_BUTTON_STYLE));

        // Request data from the server and set the rankings labels
    }
}
