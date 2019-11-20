package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static uiComponents.SceneChanger.moveToGame;
import static uiComponents.SceneChanger.moveToMainMenu;

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


    public void startGame(ActionEvent event) throws Exception {
       // change to game panel
        moveToGame((Stage)startGameButton.getScene().getWindow());
    }

    public void dismissThePerson(ActionEvent event) throws Exception {
        // servera dismiss data gönder
        //moveToMainMenu((Stage)dismissPersonButton.getScene().getWindow());
    }

    public void deleteLobby(ActionEvent event) throws Exception {
        // servera delete data gönder
        moveToMainMenu((Stage)deleteLobbyButton.getScene().getWindow());
    }
}
