package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import models.*;
import rest.Requester;
import rest.ServerConnectionHandler;
import rest.models.GeneralResponse;
import utils.Constants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
    public Spinner<Integer> stoneSpin;
    @FXML
    public Spinner<Integer> oreSpin;
    @FXML
    public Spinner<Integer> woodSpin;
    @FXML
    public Spinner<Integer> glassSpin;
    @FXML
    public Spinner<Integer> loomSpin;
    @FXML
    public Spinner<Integer> papyrusSpin;
    @FXML
    public Label usernameLabel;
    @FXML
    private Spinner<Integer> claySpin ;

    public boolean showError = true;

    @FXML
    Button makeCommerceButton;
    boolean first = true;

    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            // Server request and update the game
            update();

        }
    }));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Setting the mouse entered and exited listeners for hover effect
        makeCommerceButton.setOnMouseEntered(e -> makeCommerceButton.setStyle(HOVERED_BUTTON_STYLE));
        makeCommerceButton.setOnMouseExited(e -> makeCommerceButton.setStyle(IDLE_BUTTON_STYLE));

        // Start sending requests
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

        SceneHandler.getInstance().getStagePopup().setOnCloseRequest(e -> {
            timeLine.stop();
        });

        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        Settings settings = DataHandler.getInstance().getSettings();
        if(settings.isAudioDescription())
            tts.read( "Commerce. \n Clay is  " +claySpin.getValue());

        makeCommerceButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(makeCommerceButton.getText());
                makeCommerceButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                makeCommerceButton.setStyle(IDLE_BUTTON_STYLE);
            first = false;
        });

        claySpin.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(" Clay is " +claySpin.getValue());
            }
            first = false;
        });
        oreSpin.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(" Ore is " +  oreSpin.getValue());
            }
            first = false;
        });
        stoneSpin.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(" Stone is " +  stoneSpin.getValue());
            }
            first = false;
        });
        woodSpin.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(" Wood is " +  woodSpin.getValue());
            }
            first = false;
        });
        glassSpin.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(" Glass is " +  glassSpin.getValue());
            }
            first = false;
        });
        papyrusSpin.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(" Papyrus is " +  papyrusSpin.getValue());
            }
            first = false;
        });
        loomSpin.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(" Loom is " +  loomSpin.getValue());
            }
            first = false;
        });

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

        makeCommerceButton.setDisable(true);

        SceneHandler.getInstance().getStagePopup().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                if (keyCode.equals(keyCode.ESCAPE))
                    SceneHandler.getInstance().getStagePopup().close();
                else if (keyCode.equals(KeyCode.C)){
                    if (settings.isAudioDescription())
                        tts.read("Commerce");
                    commerceHelper();
                }
                event.consume();
            }
        });


       /* if(((((Integer)(claySpin.getValue())) == 0)) && ((((Integer)(stoneSpin.getValue())) == 0)) &&((((Integer)(oreSpin.getValue())) == 0)) &&((((Integer)(woodSpin.getValue())) == 0)) &&((((Integer)(glassSpin.getValue())) == 0)) &&((((Integer)(loomSpin.getValue())) == 0)) &&((((Integer)(papyrusSpin.getValue())) == 0)))
        {
            makeCommerceButton.setDisable(true);
        }*/
    }

    public void update() {
        String username = usernameLabel.getText();
        Game game = DataHandler.getInstance().getGame();
        if (game.players.length == 4){
            if (game.players[2].name.equals(username)){
                makeCommerceButton.setVisible(false);
            }
            else
                makeCommerceButton.setVisible(true);
        }

        if((claySpin.getValue() == 0) && (woodSpin.getValue() == 0) &&(stoneSpin.getValue() == 0) &&(oreSpin.getValue() == 0) &&(glassSpin.getValue() == 0) &&(loomSpin.getValue() == 0) &&(papyrusSpin.getValue() == 0) )
        {
            makeCommerceButton.setDisable(true);
        }
        else
            makeCommerceButton.setDisable(false);
    }

    public void makeCommerce(ActionEvent actionEvent){
        commerceHelper();
    }

    public void commerceHelper() {
        int clay = claySpin.getValue();
        int ore = oreSpin.getValue();
        int stone = stoneSpin.getValue();
        int wood = woodSpin.getValue();
        int loom = loomSpin.getValue();
        int glass = glassSpin.getValue();
        int papyrus = papyrusSpin.getValue();

        Materials materials = new Materials();
        materials.clay = clay;
        materials.coin = 0;
        materials.glassworks = glass;
        materials.loom = loom;
        materials.ore = ore;
        materials.stone = stone;
        materials.lumber = wood;
        materials.press = papyrus;

        String username = usernameLabel.getText();

        boolean isWithLeft = !DataHandler.getInstance().getGame().players[1].name.equals(username);

        DataHandler dataHandler = DataHandler.getInstance();
        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Requester requester = ServerConnectionHandler.getInstance().getRequester();
                GeneralResponse<Game> gameResponse = requester.commerce(dataHandler.getUser().userName, dataHandler.getUser().token, isWithLeft,materials);
                if (gameResponse != null) {
                    if (gameResponse.success) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                DataHandler.getInstance().setGame(gameResponse.payload);
                            }
                        });
                    }
                    else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                showErrorMessage(gameResponse.message);
                            }
                        });
                    }
                }
                else {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showErrorMessage("There is something wrong with the connection");
                        }
                    });
                }
            }
        });
        requestThread.start();
    }

    // Error message
    private void showErrorMessage(String errorMsg){
        if (showError) {
            disableItems();

            final boolean[] first = {true};
            DataHandler dataHandler = DataHandler.getInstance();

            if (dataHandler.getSettings().isAudioDescription()) {
                AudioDescriptionHandler.getInstance().getReader().read("Error. " + errorMsg + " Press enter to say OK");
            }

            showError = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(errorMsg);
            alert.initOwner(makeCommerceButton.getScene().getWindow());
            alert.getButtonTypes().forEach(buttonType -> {
                alert.getDialogPane().lookupButton(buttonType).focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue && dataHandler.getSettings().isAudioDescription() && !first[0])
                        AudioDescriptionHandler.getInstance().getReader().read(buttonType.getText());
                    first[0] = false;
                });
            });

            Optional<ButtonType> result = alert.showAndWait();
            showError = true;
            enableItems();
        }
    }

    private void disableItems(){
        makeCommerceButton.setDisable(true);
    }

    private void enableItems(){
        makeCommerceButton.setDisable(false);
    }
}
