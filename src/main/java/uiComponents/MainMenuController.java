package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

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
        TextInputDialog dialog = new TextInputDialog("Lobby Code");
        dialog.setTitle("Join to the Existing Lobby");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);

        dialog.setContentText("Enter the code of the lobby:");
        String lobbyCode = null;
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
           lobbyCode = result.get();
        }
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
