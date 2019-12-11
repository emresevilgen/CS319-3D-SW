package uiComponents;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class OtherBoardsCards implements Initializable {
    @FXML
    public ImageView focus;

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // Increase scale of the card mouse entered
    public void setScaleOn(MouseEvent mouseEvent) {
        ImageView current = (ImageView) mouseEvent.getSource();
        if (current.getImage() != null){
            Glow glow = new Glow();
            glow.setLevel(0.4);
            current.setEffect(glow);
            current.setScaleX(1.60);
            current.setScaleY(1.60);
        }
    }

    // Decrease scale of the card mouse exited
    public void setScaleOff(MouseEvent mouseEvent) {
        ImageView current = (ImageView) mouseEvent.getSource();
        if (current.getImage() != null){
            Glow glow = new Glow();
            glow.setLevel(0.0);
            current.setEffect(glow);
            current.setScaleX(1);
            current.setScaleY(1);
        }
    }
}