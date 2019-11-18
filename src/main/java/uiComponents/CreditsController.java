package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static uiComponents.SceneChanger.moveToMainMenu;

public class CreditsController {
    public Button backButton;

    public void back(ActionEvent actionEvent) {
        moveToMainMenu((Stage)backButton.getScene().getWindow());
    }
}