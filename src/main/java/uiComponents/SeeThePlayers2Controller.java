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

import static uiComponents.SceneChanger.moveToMainMenu;

public class SeeThePlayers2Controller implements Initializable {
    @FXML
    public Button leaveButton;
    @FXML
    public Button readyButton;
    @FXML
    public Label seePlayersLabel;
    @FXML
    public Label firstNameLabel;
    @FXML
    public Label firstStateLabel;
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

    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #817277; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #817277; -fx-opacity: 0.85;";


    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("server request");
            // get the lobby data every seconds and when the game starts move to the game screen
            // if game starts then
            //         timeLine.stop();
        }
    }));

    public void beReady(ActionEvent event) throws Exception {
        //send request to the server
    }

    public void leave(ActionEvent event) throws Exception {
        // send a request to the server and move to the main menu
        timeLine.stop();
        moveToMainMenu((Stage)leaveButton.getScene().getWindow());

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();
        leaveButton.setOnMouseEntered(e -> leaveButton.setStyle(HOVERED_BUTTON_STYLE));
        leaveButton.setOnMouseExited(e -> leaveButton.setStyle(IDLE_BUTTON_STYLE));
        readyButton.setOnMouseEntered(e -> readyButton.setStyle(HOVERED_BUTTON_STYLE));
        readyButton.setOnMouseExited(e -> readyButton.setStyle(IDLE_BUTTON_STYLE));

        // Get the lobby data and initilize the people
    }
}
