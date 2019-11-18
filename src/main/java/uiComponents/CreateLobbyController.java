package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;

public class CreateLobbyController {
    boolean isCreator = true;
    public Button cancelButton;
    public Button createButton;

    public void moveToMainMenu(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) cancelButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File(Constants.MAIN_MENU_FXML));
        root = loader.load(fileInputStream);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveToSeeThePlayers(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) createButton.getScene().getWindow();
        if(isCreator)
        {
            FXMLLoader loader = new FXMLLoader();
            FileInputStream fileInputStream = new FileInputStream(new File(Constants.SEE_PLAYERS_FXML));
            root = loader.load(fileInputStream);
        }
        else
        {
            FXMLLoader loader = new FXMLLoader();
            FileInputStream fileInputStream = new FileInputStream(new File(Constants.SEE_PLAYERS_2_FXML));
            root = loader.load(fileInputStream);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
