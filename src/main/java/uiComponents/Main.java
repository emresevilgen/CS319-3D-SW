package uiComponents;

import audioDescription.AudioDescriptionHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import models.*;
import rest.ServerConnectionHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SceneHandler sceneHandler = SceneHandler.getInstance();
        sceneHandler.start(primaryStage);
        DataHandler dataHandler = DataHandler.getInstance();
        ServerConnectionHandler serverConnectionHandler = ServerConnectionHandler.getInstance();
        AudioDescriptionHandler audioDescriptionHandler = AudioDescriptionHandler.getInstance();

    }


    public static void main(String[] args) {
        launch(args);
    }





}
