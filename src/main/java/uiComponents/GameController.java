package uiComponents;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static uiComponents.Main.mediaPlayer;
import static uiComponents.Main.settings;
import static uiComponents.SceneChanger.moveToMainMenu;

public class GameController implements Initializable {
    @FXML
    ImageView audioDescriptionView;

    @FXML
    ImageView soundEffectsView;

    @FXML
    ImageView exitView;

    @FXML
    ImageView playerBoard;

    @FXML
    ImageView acrossBoard;

    @FXML
    ImageView rightBoard;

    @FXML
    ImageView leftBoard;

    @FXML
    ImageView deck1;
    @FXML
    ImageView deck2;
    @FXML
    ImageView deck3;
    @FXML
    ImageView deck4;
    @FXML
    ImageView deck5;
    @FXML
    ImageView deck6;
    @FXML
    ImageView deck7;

    @FXML
    ImageView card1;
    @FXML
    ImageView card2;
    @FXML
    ImageView card9;
    @FXML
    ImageView card12;


    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            System.out.println("server request");
        }
    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FileInputStream inputStream = new FileInputStream(Constants.AUDIO_DESCRIPTION_IMAGE);
            Image audioImage = new Image(inputStream);
            audioDescriptionView.setImage(audioImage);
            if (settings.isAudioDescription()){
                audioDescriptionView.setFitHeight(70);
                audioDescriptionView.setFitWidth(70);
                audioDescriptionView.setY(-13);
                audioDescriptionView.setX(-10);
            }
            else{
                audioDescriptionView.setFitWidth(45);
                audioDescriptionView.setFitHeight(45);
                audioDescriptionView.setY(0);
                audioDescriptionView.setX(0);
            }

            if (settings.isSoundEffects())
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_ON_IMAGE);
            else
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_OFF_IMAGE);

            Image soundImage = new Image(inputStream);

            soundEffectsView.setImage(soundImage);

            inputStream = new FileInputStream(Constants.EXIT_IMAGE);
            Image exitImage = new Image(inputStream);

            exitView.setImage(exitImage);

            inputStream = new FileInputStream(Constants.ALEXANDRIA_A_IMAGE);
            Image board1 = new Image(inputStream);
            playerBoard.setImage(board1);
            inputStream = new FileInputStream(Constants.EPHESOS_A_IMAGE);
            Image board2 = new Image(inputStream);
            acrossBoard.setImage(board2);
            inputStream = new FileInputStream(Constants.GIZAH_A_IMAGE);
            Image board3 = new Image(inputStream);
            rightBoard.setImage(board3);
            inputStream = new FileInputStream(Constants.BABYLON_A_IMAGE);
            Image board4 = new Image(inputStream);
            leftBoard.setImage(board4);

            inputStream = new FileInputStream(Constants.CARD_ALTAR_IMAGE);
            Image deckCard1 = new Image(inputStream);
            deck1.setImage(deckCard1);
            inputStream = new FileInputStream(Constants.CARD_APOTHECARY_IMAGE);
            Image deckCard2 = new Image(inputStream);
            deck2.setImage(deckCard2);
            inputStream = new FileInputStream(Constants.CARD_AQUEDUCT_IMAGE);
            Image deckCard3 = new Image(inputStream);
            deck3.setImage(deckCard3);
            inputStream = new FileInputStream(Constants.CARD_ARCHERYRANGE_IMAGE);
            Image deckCard4 = new Image(inputStream);
            deck4.setImage(deckCard4);
            inputStream = new FileInputStream(Constants.CARD_ARENA_IMAGE);
            Image deckCard5 = new Image(inputStream);
            deck5.setImage(deckCard5);
            inputStream = new FileInputStream(Constants.CARD_ARSENAL_IMAGE);
            Image deckCard6 = new Image(inputStream);
            deck6.setImage(deckCard6);
            inputStream = new FileInputStream(Constants.CARD_FORUM_IMAGE);
            Image deckCard7 = new Image(inputStream);
            deck7.setImage(deckCard7);

            inputStream = new FileInputStream(Constants.CARD_TREEFARM_IMAGE);
            Image boardCard1 = new Image(inputStream);
            card1.setImage(boardCard1);
            inputStream = new FileInputStream(Constants.CARD_LOOM_IMAGE);
            Image boardCard2 = new Image(inputStream);
            card2.setImage(boardCard2);
            inputStream = new FileInputStream(Constants.CARD_LIBRARY_IMAGE);
            Image boardCard9 = new Image(inputStream);
            card9.setImage(boardCard9);
            inputStream = new FileInputStream(Constants.CARD_WALLS_IMAGE);
            Image boardCard12 = new Image(inputStream);
            card12.setImage(boardCard12);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

    }

    public void switchAudioDescription(MouseEvent actionEvent) {
        settings.switchAudioDescription();
        if(settings.isAudioDescription()){
            audioDescriptionView.setFitHeight(70);
            audioDescriptionView.setFitWidth(70);
            audioDescriptionView.setY(-13);
            audioDescriptionView.setX(-10);
        }
        else {
            audioDescriptionView.setFitWidth(45);
            audioDescriptionView.setFitHeight(45);
            audioDescriptionView.setY(0);
            audioDescriptionView.setX(0);
        }


    }

    public void switchSoundEffects(MouseEvent actionEvent) {
        settings.switchSoundEffects();
        FileInputStream inputStream = null;
        try {
            if (settings.isSoundEffects()) {
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_ON_IMAGE);
                mediaPlayer.play();
            }
            else {
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_OFF_IMAGE);
                mediaPlayer.stop();

            }

            Image soundImage = new Image(inputStream);
            soundEffectsView.setImage(soundImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        timeLine.stop(); // To kill timeline
    }

    public void exit(MouseEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Do you want to exit the current game?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            moveToMainMenu((Stage)exitView.getScene().getWindow());
        } else {
            alert.close();
        }
    }
}
