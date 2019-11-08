package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CreditsController {
    public Button backButton;

    public void moveToMainMenu(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) backButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("src\\main\\resources\\fxml\\MainMenu.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}