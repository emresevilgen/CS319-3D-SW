package uiComponents;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utils.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommerceScene implements Initializable {

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #b80028; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #b80028; -fx-opacity: 0.85;";
    @FXML
    public ImageView clayImage;
    @FXML
    public ImageView oreImage;
    @FXML
    public ImageView stoneImage;
    @FXML
    public ImageView woodImage;
    @FXML
    public ImageView glassImage;
    @FXML
    public ImageView loomImage;
    @FXML
    public ImageView papyrusImage;
    @FXML
    public Spinner claySpin;
    @FXML
    public Spinner stoneSpin;
    @FXML
    public Spinner oreSpin;
    @FXML
    public Spinner woodSpin;
    @FXML
    public Spinner glassSpin;
    @FXML
    public Spinner loomSpin;
    @FXML
    public Spinner papyrusSpin;

    @FXML
    Button makeCommerceButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Setting the mouse entered and exited listeners for hover effect
        makeCommerceButton.setOnMouseEntered(e -> makeCommerceButton.setStyle(HOVERED_BUTTON_STYLE));
        makeCommerceButton.setOnMouseExited(e -> makeCommerceButton.setStyle(IDLE_BUTTON_STYLE));

        try {
            FileInputStream inputStream = new FileInputStream(Constants.CLAY_IMAGE);
            Image clay = new Image(inputStream);
            clayImage.setImage(clay);

            FileInputStream inputStream2 = new FileInputStream(Constants.ORE_IMAGE);
            Image ore = new Image(inputStream2);
            oreImage.setImage(ore);

            FileInputStream inputStream3 = new FileInputStream(Constants.STONE_IMAGE);
            Image stone = new Image(inputStream3);
            stoneImage.setImage(stone);

            FileInputStream inputStream4 = new FileInputStream(Constants.WOOD_IMAGE);
            Image wood = new Image(inputStream4);
            woodImage.setImage(wood);

            FileInputStream inputStream5 = new FileInputStream(Constants.LOOM_IMAGE);
            Image loom = new Image(inputStream5);
            loomImage.setImage(loom);

            FileInputStream inputStream6 = new FileInputStream(Constants.GLASS_IMAGE);
            Image glass = new Image(inputStream6);
            glassImage.setImage(glass);

            FileInputStream inputStream7 = new FileInputStream(Constants.PAPYRUS_IMAGE);
            Image papyrus = new Image(inputStream7);
            papyrusImage.setImage(papyrus);

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

    public void makeCommerce(ActionEvent actionEvent){
    }
}
