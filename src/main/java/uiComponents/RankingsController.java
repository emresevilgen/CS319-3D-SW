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

    public void moveToMainMenu(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"fxml"+File.separator+"MainMenu.fxml"));
        root = loader.load(fileInputStream);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
