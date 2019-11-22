package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
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

import static uiComponents.SceneChanger.*;

public class CreateLobbyController implements Initializable {


    boolean isCreator = true;
    @FXML
    public Button cancelButton;
    @FXML
    public Button createButton;
    @FXML
    public CheckBox gettingLootCheckBox;
    @FXML
    public CheckBox bargainCheckBox;
    @FXML
    public CheckBox shufflePlacesCheckBox;
    @FXML
    public CheckBox secretSkillsCheckBox;
    @FXML
    public CheckBox invalidMovePenaltyCheckBox;
    @FXML
    public TextField lobbyNameField;



    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #74747c; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #74747c; -fx-opacity: 0.85;";

    public void create(ActionEvent event) throws Exception {
        boolean gettingLoot = gettingLootCheckBox.isSelected();
        boolean bargain = bargainCheckBox.isSelected();
        boolean shufflePlaces = shufflePlacesCheckBox.isSelected();
        boolean secretSkills = secretSkillsCheckBox.isSelected();
        boolean invalidModePenalty = invalidMovePenaltyCheckBox.isSelected();
        String lobbyName = lobbyNameField.getText();

        // Send a request to the server with the data and if get success move to see the players

        moveToSeeThePlayers((Stage) createButton.getScene().getWindow(), isCreator);
    }

    public void cancel(ActionEvent event) throws Exception {
        moveToMainMenu((Stage) cancelButton.getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle(HOVERED_BUTTON_STYLE));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle(IDLE_BUTTON_STYLE));
        createButton.setOnMouseEntered(e -> createButton.setStyle(HOVERED_BUTTON_STYLE));
        createButton.setOnMouseExited(e -> createButton.setStyle(IDLE_BUTTON_STYLE));

    }
}
