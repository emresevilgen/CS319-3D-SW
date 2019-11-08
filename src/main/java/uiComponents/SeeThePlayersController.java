package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

public class SeeThePlayersController {
    public Button deleteLobbyButton;
    public Button startGameButton;
    public Button dismissPersonButton;
    public Label seePlayersLabel;
    public Label secondNameLabel;
    public Label secondStateLabel;
    public Label thirdNameLabel;
    public Label thirdStateLabel;
    public Label fourthNameLabel;
    public Label fourthStateLabel;

    public void moveToMainMenu(ActionEvent event) throws Exception {
        //servera data
        Stage stage;
        Parent root;

        stage = (Stage) deleteLobbyButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"fxml"+File.separator+"MainMenu.fxml"));
        root = loader.load(fileInputStream);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveToGame(ActionEvent event) throws Exception {
       // change to game panel
    }

    public void dismissThePerson(ActionEvent event) throws Exception {
        // servera dismiss data gönder
    }
}
