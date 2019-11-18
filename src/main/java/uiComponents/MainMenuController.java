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

public class MainMenuController {

     public Button createLobbyButton;
     public Button joinLobbyButton;
     public Button seeRankingsButton;
     public Button settingsButton;
     public Button creditsButton;

    public void moveToCreateLobby(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) createLobbyButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File(Constants.CREATE_LOBBY_FXML));
        root = loader.load(fileInputStream);

        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    public void moveToJoinLobby(ActionEvent event) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(joinLobbyButton.getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("This is a Dialog"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);

        dialog.show();
    }

    public void moveToRankings(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) seeRankingsButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File(Constants.RANKINGS_FXML));
        root = loader.load(fileInputStream);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveToSettings(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) settingsButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File(Constants.SETTINGS));
        root = loader.load(fileInputStream);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveToCredits(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) creditsButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File(Constants.CREDITS_FXML));
        root = loader.load(fileInputStream);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
