package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

public class SignInController {

    public Button signUpButton;
    public Button signInButton;

   public void moveToSignUp(ActionEvent event) throws Exception {
       Stage stage;
       Parent root;

       stage = (Stage) signUpButton.getScene().getWindow();

       FXMLLoader loader = new FXMLLoader();
       FileInputStream fileInputStream = new FileInputStream(new File("src\\main\\resources\\fxml\\SignUp.fxml"));
       root = loader.load(fileInputStream);

       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
   }

    public void moveToMainMenu(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) signInButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File("src\\main\\resources\\fxml\\MainMenu.fxml"));
        root = loader.load(fileInputStream);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
