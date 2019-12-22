package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
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
        if(settings.isAudioDescription())
            tts.read(howToPlayText.getText());
    }

}
