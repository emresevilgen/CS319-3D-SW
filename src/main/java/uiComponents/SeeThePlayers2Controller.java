package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
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

import static uiComponents.SceneChanger.moveToMainMenu;

public class SeeThePlayers2Controller {
    public Button leaveButton;
    public Button readyButton;
    public Label seePlayersLabel;
    public Label firstNameLabel;
    public Label firstStateLabel;
    public Label secondNameLabel;
    public Label secondStateLabel;
    public Label thirdNameLabel;
    public Label thirdStateLabel;
    public Label fourthNameLabel;
    public Label fourthStateLabel;


    public void beReady(ActionEvent event) throws Exception {
        // change to game panel
        moveToMainMenu((Stage)readyButton.getScene().getWindow());
    }

    public void leave(ActionEvent event) throws Exception {
        moveToMainMenu((Stage)leaveButton.getScene().getWindow());
    }
}
