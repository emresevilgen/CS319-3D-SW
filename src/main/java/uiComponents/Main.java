package uiComponents;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import models.*;
import utils.Constants;


import java.io.File;
import java.nio.file.Paths;

import static uiComponents.SceneChanger.*;

public class Main extends Application {
    public static User user = null;
    public static Settings settings = null;
    public static Game game = null;
    public static Lobby lobby = null;
    public static MediaPlayer mediaPlayer = null;


    @Override
    public void start(Stage primaryStage) throws Exception{
        playMusic();
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreenExitHint(null);
        settings = new Settings();


        //moveToCreateLobby(primaryStage);
        //moveToCredits(primaryStage);
        moveToGame(primaryStage);
        //moveToMainMenu(primaryStage);
        //moveToRankings(primaryStage);
        //moveToSeeThePlayers(primaryStage, true);
        //moveToSeeThePlayers(primaryStage, false);
        //moveToSettings(primaryStage);
        //moveToSignIn(primaryStage);
        //moveToSignUp(primaryStage);

    }
    public void playMusic(){
        Media sound = new Media(new File(Constants.MENU_SOUND).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
