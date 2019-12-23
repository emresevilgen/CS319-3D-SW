package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Card;
import models.DataHandler;
import models.Game;
import models.Settings;
import rest.Requester;
import rest.ServerConnectionHandler;
import rest.models.GeneralResponse;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class SceneHandler extends Application {

    private static SceneHandler sceneHandler = new SceneHandler();
    private Stage stageMain;

    private Stage stagePopup;

    // For the background music
    private MediaPlayer mediaPlayer;

    private SceneHandler(){}

    //Get the only object available
    public static SceneHandler getInstance(){
        if (sceneHandler == null)
            sceneHandler = new SceneHandler();
        return sceneHandler;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DataHandler dataHandler = DataHandler.getInstance();
        if (dataHandler.getSettings().isSoundEffects())
            playMusic(); // Starts the music
        stageMain = primaryStage;
        stagePopup = new Stage();
        stagePopup.initStyle(StageStyle.UTILITY);
        stagePopup.setAlwaysOnTop(true);
        stagePopup.initOwner(stageMain);

        AnchorPane root;
        Scene scene;

        // Initialize scene
        root = new AnchorPane();
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); // No exit with ESC
        primaryStage.setFullScreenExitHint(null); // Exit hint pop up disabled


        //Move to functions

       //moveToCreateLobby();
        //moveToCredits();
        moveToGame();
        //moveToMainMenu();
        //moveToRankings();
        //moveToSeeThePlayers(true);
        //moveToSeeThePlayers(false);
        //moveToSettings();
        //moveToSignIn();
        //moveToSignUp();

    }

    // To play the background music
    public void playMusic(){
        Media sound = new Media(new File(Constants.MENU_SOUND).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
        mediaPlayer.play();
    }

    // Move main menu scene
    public void moveToMainMenu() {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try {
            fileInputStream = new FileInputStream(new File(Constants.MAIN_MENU_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.MAIN_BACKGROUND_IMAGE);
            logoFile = new FileInputStream(Constants.LOGO_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image logoImage = new Image(logoFile);
            ImageView logoView = (ImageView) root.getChildren().get(1);
            logoView.setImage(logoImage);

            // Show scene
            stageMain.getScene().setRoot(root);
            stageMain.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Move sign up scene
    public void moveToSignUp() {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.SIGN_UP_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.MAIN_BACKGROUND_IMAGE);
            logoFile = new FileInputStream(Constants.LOGO_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image logoImage = new Image(logoFile);
            ImageView logoView = (ImageView) root.getChildren().get(1);
            logoView.setImage(logoImage);

            // Show scene
            stageMain.getScene().setRoot(root);
            stageMain.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Move sign in scene
    public void moveToSignIn() {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.SIGN_IN_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.MAIN_BACKGROUND_IMAGE);
            logoFile = new FileInputStream(Constants.LOGO_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image logoImage = new Image(logoFile);
            ImageView logoView = (ImageView) root.getChildren().get(1);
            logoView.setImage(logoImage);

            // Show scene
            stageMain.getScene().setRoot(root);
            stageMain.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Move create lobby scene
    public void moveToCreateLobby() {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream backFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.CREATE_LOBBY_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.CREATE_LOBBY_BACKGROUND_IMAGE);
            backFile = new FileInputStream(Constants.CREATE_LOBBY_BACK_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image backImage = new Image(backFile);
            ImageView backView = (ImageView) root.getChildren().get(1);
            backView.setImage(backImage);

            // Show scene
            stageMain.getScene().setRoot(root);
            stageMain.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Move rankings scene
    public void moveToRankings() {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream backFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.RANKINGS_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.RANKINGS_BACKGROUND_IMAGE);
            backFile = new FileInputStream(Constants.RANKINGS_BACK_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);



            // Show scene
            stageMain.getScene().setRoot(root);
            stageMain.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Move settings scene
    public void moveToSettings() {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.SETTINGS_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.SETTINGS_BACKGROUND_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            // Show scene
            stageMain.getScene().setRoot(root);
            stageMain.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Move credits scene
    public void moveToCredits() {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.CREDITS_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.CREDITS_BACKGROUND_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            // Show scene
            stageMain.getScene().setRoot(root);
            stageMain.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Move see the players scene
    public void moveToSeeThePlayers(boolean isCreator) {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream backFile = null;
        AnchorPane root = null;

        try{
            if(isCreator)
                fileInputStream = new FileInputStream(new File(Constants.SEE_PLAYERS_FXML));
            else
                fileInputStream = new FileInputStream(new File(Constants.SEE_PLAYERS_2_FXML));

            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.SEE_THE_PLAYERS_BACKGROUND_IMAGE);
            backFile = new FileInputStream(Constants.SEE_THE_PLAYERS_BACK_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image backImage = new Image(backFile);
            ImageView backView = (ImageView) root.getChildren().get(1);
            backView.setImage(backImage);

            // Show scene
            stageMain.getScene().setRoot(root);
            stageMain.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Move game scene
    public void moveToGame() {

        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;

        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.GAME_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.GAME_BACKGROUND_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            // Show scene
            stageMain.getScene().setRoot(root);
            stageMain.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Exit
    public void exit(){
        // Show confirmation pop up
        final boolean[] first = {true};
        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        Settings settings = DataHandler.getInstance().getSettings();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.setTitle("Exit Game");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.initOwner(stageMain);

        if(settings.isAudioDescription())
        {
            tts.read("Do you want to exit?");
        }

        alert.setContentText("Do you want to exit?");
        // Add options
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonNo, buttonYes);
        alert.getButtonTypes().forEach(buttonType -> {
            alert.getDialogPane().lookupButton(buttonType).focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue && settings.isAudioDescription() && !first[0])
                    AudioDescriptionHandler.getInstance().getReader().read(buttonType.getText());
                first[0] = false;
            });
        });

        // Get the result
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            System.exit(0); // Exit
        } else {
            alert.close(); // Cancel
        }
    }

    public void showOtherBoardsCardsScene(){
        stagePopup.setScene(new Scene(new AnchorPane()));

        // Load FXML and show pop up window
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;

        AnchorPane root = null;

        try {
            fileInputStream = new FileInputStream(new File(Constants.OTHER_BOARDS_CARDS_FXML));
            backgroundFile = new FileInputStream(Constants.CREATE_LOBBY_BACK_IMAGE);
            root = (AnchorPane) loader.load(fileInputStream);

            // Set background image
            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            stagePopup.getScene().setRoot(root);
            stagePopup.sizeToScene();
            stagePopup.centerOnScreen();
            stagePopup.show();

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showHowToPlayScene(){
        stagePopup.setScene(new Scene(new AnchorPane()));

        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;

        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.HOW_TO_PLAY_FXML));
            backgroundFile = new FileInputStream(Constants.CREATE_LOBBY_BACK_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            // Set background image
            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);

            backgroundView.setImage(backgroundImage);

            stagePopup.getScene().setRoot(root);
            stagePopup.sizeToScene();
            stagePopup.centerOnScreen();
            stagePopup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCommerceScene(){
        // Load FXML and show pop up window
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;

        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.COMMERCE_FXML));
            backgroundFile = new FileInputStream(Constants.CREATE_LOBBY_BACK_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            // Set background image
            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);

            backgroundView.setImage(backgroundImage);

            stagePopup.getScene().setRoot(root);
            stagePopup.sizeToScene();
            stagePopup.centerOnScreen();
            stagePopup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLootScene(){
        stagePopup.setScene(new Scene(new AnchorPane()));

        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;

        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.LOOT_FXML));
            backgroundFile = new FileInputStream(Constants.CREATE_LOBBY_BACK_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            // Set background image
            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);

            backgroundView.setImage(backgroundImage);

            stagePopup.getScene().setRoot(root);
            stagePopup.sizeToScene();
            stagePopup.centerOnScreen();
            stagePopup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showBabylonScene(){
        stagePopup.setScene(new Scene(new AnchorPane()));

        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;

        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.BABYLON_STAGE2_FXML));
            backgroundFile = new FileInputStream(Constants.CREATE_LOBBY_BACK_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            // Set background image
            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);

            backgroundView.setImage(backgroundImage);

            stagePopup.getScene().setRoot(root);
            stagePopup.sizeToScene();
            stagePopup.centerOnScreen();
            stagePopup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public Stage getStageMain() { return stageMain; }

    public void setStageMain(Stage stage) { this.stageMain = stage; }

    public Stage getStagePopup() {
        return stagePopup;
    }

    public void setStagePopup(Stage stagePopup) {
        this.stagePopup = stagePopup;
    }

}

