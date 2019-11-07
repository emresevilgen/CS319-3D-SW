package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class SignUpController {
    public Button signUpButton;

    public void moveToMainMenu(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) signUpButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

