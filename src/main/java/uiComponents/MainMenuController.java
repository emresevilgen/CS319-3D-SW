package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;

import static uiComponents.SceneChanger.*;

public class MainMenuController {

     public Button createLobbyButton;
     public Button joinLobbyButton;
     public Button seeRankingsButton;
     public Button settingsButton;
     public Button creditsButton;

    public void createLobby(ActionEvent event) throws Exception {
        moveToCreateLobby((Stage)createLobbyButton.getScene().getWindow());

    }

    public void joinLobby(ActionEvent event) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(joinLobbyButton.getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("This is a Dialog"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);

        dialog.show();
    }

    public void rankings(ActionEvent event) throws Exception {
        moveToRankings((Stage)seeRankingsButton.getScene().getWindow());

    }

    public void settings(ActionEvent event) throws Exception {
        moveToSettings((Stage)settingsButton.getScene().getWindow());

    }

    public void credits(ActionEvent event) throws Exception {
        moveToCredits((Stage)creditsButton.getScene().getWindow());

    }
}
