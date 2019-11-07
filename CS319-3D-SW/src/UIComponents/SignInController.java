package UIComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SignInController {

    public Button signUpButton;
    public Button signInButton;

   public void moveToSignUp(ActionEvent event) throws Exception {
       Stage stage;
       Parent root;

       stage = (Stage) signUpButton.getScene().getWindow();
       root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));

       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
   }

    public void moveToMainMenu(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        stage = (Stage) signInButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
