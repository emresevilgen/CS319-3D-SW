package UIComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CreateLobbyController {
    boolean isCreator = true;
    public Button cancelButton;
    public Button createButton;

    public void moveToMainMenu(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) cancelButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveToSeeThePlayers(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) createButton.getScene().getWindow();
        if(isCreator)
            root = FXMLLoader.load(getClass().getResource("SeeThePlayers.fxml"));
        else
            root = FXMLLoader.load(getClass().getResource("SeeThePlayers2.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
