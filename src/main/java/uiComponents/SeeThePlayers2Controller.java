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

    public void moveToMainMenu(ActionEvent event) throws Exception {
        //servera data
        Stage stage;
        Parent root;

        stage = (Stage) leaveButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File("src\\main\\resources\\fxml\\MainMenu.fxml"));
        root = loader.load(fileInputStream);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void beReady(ActionEvent event) throws Exception {
        // change to game panel
    }
}
