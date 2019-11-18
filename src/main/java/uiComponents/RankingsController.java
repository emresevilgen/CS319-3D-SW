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

public class RankingsController {
    public Button backButton;
    public Label firstNameLabel;
    public Label firstNoLabel;
    public Label secondNameLabel;
    public Label secondNoLabel;
    public Label thirdNoLabel;
    public Label fourthNameLabel;
    public Label fourthNoLabel;
    public Label fifthNameLabel;
    public Label fifthNoLabel;


    public void back(ActionEvent event) throws Exception {
        moveToMainMenu((Stage)backButton.getScene().getWindow());
    }
}
