package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.DataHandler;
import models.Settings;
import utils.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class BabylonStage2 implements Initializable {

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #b80028; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #b80028; -fx-opacity: 0.85;";

    @FXML
    public Button compassButton;
    @FXML
    public Button wheelButton;
    @FXML
    public Button scriptButton;
    @FXML
    public ImageView compassView;
    @FXML
    public ImageView scriptView;
    @FXML
    public ImageView wheelView;



    boolean first = true;


    public void wheelAction(ActionEvent actionEvent){
    }
    public void compassAction(ActionEvent actionEvent){
    }
    public void scriptAction(ActionEvent actionEvent){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Setting the mouse entered and exited listeners for hover effect
        compassButton.setOnMouseEntered(e -> compassButton.setStyle(HOVERED_BUTTON_STYLE));
        compassButton.setOnMouseExited(e -> compassButton.setStyle(IDLE_BUTTON_STYLE));
        wheelButton.setOnMouseEntered(e -> wheelButton.setStyle(HOVERED_BUTTON_STYLE));
        wheelButton.setOnMouseExited(e -> wheelButton.setStyle(IDLE_BUTTON_STYLE));
        scriptButton.setOnMouseEntered(e -> scriptButton.setStyle(HOVERED_BUTTON_STYLE));
        scriptButton.setOnMouseExited(e -> scriptButton.setStyle(IDLE_BUTTON_STYLE));


        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        Settings settings = DataHandler.getInstance().getSettings();
        if(settings.isAudioDescription())
            tts.read( "Pick a scientific symbol. \n  ");

        compassButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(compassButton.getText());
                compassButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                compassButton.setStyle(IDLE_BUTTON_STYLE);
            first = false;
        });

        wheelButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(wheelButton.getText());
                wheelButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                wheelButton.setStyle(IDLE_BUTTON_STYLE);
            first = false;
        });
        scriptButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(scriptButton.getText());
                scriptButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                scriptButton.setStyle(IDLE_BUTTON_STYLE);
            first = false;
        });


        try {
            FileInputStream inputStream = new FileInputStream(Constants.COMPASS_IMAGE);
            Image c = new Image(inputStream);
            compassView.setImage(c);

            FileInputStream inputStream2 = new FileInputStream(Constants.WHEEL_IMAGE);
            Image w = new Image(inputStream2);
            wheelView.setImage(w);

            FileInputStream inputStream3 = new FileInputStream(Constants.SCRIPT_IMAGE);
            Image s = new Image(inputStream3);
            scriptView.setImage(s);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
