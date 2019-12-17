package uiComponents;
import audioDescription.TextToSpeech;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.*;
import rest.ServerConnectionHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SceneHandler sceneHandler = SceneHandler.getInstance();
        sceneHandler.start(primaryStage);
        DataHandler dataHandler = DataHandler.getInstance();
        ServerConnectionHandler serverConnectionHandler = ServerConnectionHandler.getInstance();


    }


    public static void main(String[] args) {
        launch(args);
    }





}
