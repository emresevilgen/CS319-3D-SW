package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.DataHandler;
import models.Settings;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HowToPlay implements Initializable {

    @FXML
    TextArea howToPlayText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        Settings settings = DataHandler.getInstance().getSettings();

        // Read the initial speech
        if(settings.isAudioDescription())
            tts.read( "how to play. \n "+ howToPlayText.getText());

        // Add the audio descriptions of the buttons
        SceneHandler.getInstance().getStagePopup().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                if (keyCode.equals(keyCode.ESCAPE))
                    SceneHandler.getInstance().getStagePopup().close();
                event.consume();
            }
        });

    }

}
