package uiComponents;

import javafx.application.Application;
import javafx.application.Platform;
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
    public static MediaPlayer mediaPlayer = null;


    @Override
    public void start(Stage primaryStage) throws Exception{
        playMusic();
        moveToSignIn(primaryStage);
        //moveToCreateLobby(primaryStage);
        settings = new Settings();
       // moveToGame(primaryStage);


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
