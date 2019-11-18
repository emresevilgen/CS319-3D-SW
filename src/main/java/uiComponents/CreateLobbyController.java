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

import static uiComponents.SceneChanger.*;

public class CreateLobbyController {
    boolean isCreator = true;
    public Button cancelButton;
    public Button createButton;

    public void create(ActionEvent event) throws Exception {
        moveToSeeThePlayers((Stage) createButton.getScene().getWindow(), isCreator);
    }

    public void cancel(ActionEvent event) throws Exception {
        moveToMainMenu((Stage) cancelButton.getScene().getWindow());
    }

}
