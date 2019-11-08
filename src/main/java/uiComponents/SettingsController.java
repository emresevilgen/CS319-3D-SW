package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

public class SettingsController {
    public Button cancelButton;
    public Button saveButton;

    public void moveToMainMenu(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) saveButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"fxml"+File.separator+"MainMenu.fxml"));
        root = loader.load(fileInputStream);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
