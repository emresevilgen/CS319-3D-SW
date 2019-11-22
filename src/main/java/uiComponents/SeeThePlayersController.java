package uiComponents;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static uiComponents.SceneChanger.moveToGame;
import static uiComponents.SceneChanger.moveToMainMenu;

public class SeeThePlayersController implements Initializable {
    @FXML
    public Button deleteLobbyButton;
    @FXML
    public Button startGameButton;
    @FXML
    public Button dismissPersonButton;

    @FXML
    public Label secondNameLabel;
    @FXML
    public Label secondStateLabel;
    @FXML
    public Label thirdNameLabel;
    @FXML
    public Label thirdStateLabel;
    @FXML
    public Label fourthNameLabel;
    @FXML
    public Label fourthStateLabel;
    @FXML
    public CheckBox secondPlayerCheckBox;
    @FXML
    public CheckBox thirdPlayerCheckBox;
    @FXML
    public CheckBox fourthPlayerCheckBox;

    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #817277; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #817277; -fx-opacity: 0.85;";

    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("server request");
        }
    }));

    public void startGame(ActionEvent event) throws Exception {
        // send request to server if get success move to game screen move to game screen
        moveToGame((Stage)startGameButton.getScene().getWindow());
        timeLine.stop();
    }

    public void dismissThePerson(ActionEvent event) throws Exception {
        boolean secondPlayer = secondPlayerCheckBox.isSelected();
        boolean thirdPlayer = thirdPlayerCheckBox.isSelected();
        boolean fourthPlayer = fourthPlayerCheckBox.isSelected();

        // servera dismiss data gönder
        //moveToMainMenu((Stage)dismissPersonButton.getScene().getWindow());
    }

    public void deleteLobby(ActionEvent event) throws Exception {
        // servera delete data gönder

        timeLine.stop();
        moveToMainMenu((Stage)deleteLobbyButton.getScene().getWindow());


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();
        deleteLobbyButton.setOnMouseEntered(e -> deleteLobbyButton.setStyle(HOVERED_BUTTON_STYLE));
        deleteLobbyButton.setOnMouseExited(e -> deleteLobbyButton.setStyle(IDLE_BUTTON_STYLE));
        startGameButton.setOnMouseEntered(e -> startGameButton.setStyle(HOVERED_BUTTON_STYLE));
        startGameButton.setOnMouseExited(e -> startGameButton.setStyle(IDLE_BUTTON_STYLE));
        dismissPersonButton.setOnMouseEntered(e -> dismissPersonButton.setStyle(HOVERED_BUTTON_STYLE));
        dismissPersonButton.setOnMouseExited(e -> dismissPersonButton.setStyle(IDLE_BUTTON_STYLE));


        // get the lobby data and initilize the people in the lobby
    }
}
