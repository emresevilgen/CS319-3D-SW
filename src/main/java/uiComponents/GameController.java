package uiComponents;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import utils.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import static uiComponents.Main.settings;

public class GameController implements Initializable {
    @FXML
    ImageView audioDescriptionView;

    @FXML
    ImageView soundEffectsView;
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
            if (settings.isSoundEffects())
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_ON_IMAGE);
            else
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_OFF_IMAGE);

            Image soundImage = new Image(inputStream);
            soundEffectsView.setImage(soundImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        timeLine.stop(); // To kill timeline
    }
}
