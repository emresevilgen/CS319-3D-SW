package uiComponents;

import javafx.application.Application;
import javafx.stage.Stage;
import models.*;


import static uiComponents.SceneChanger.*;

public class Main extends Application {
    public static User user = null;
    public static Settings settings = null;
    public static Game game = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        moveToSignIn(primaryStage);
        //moveToCreateLobby(primaryStage);
        settings = new Settings();
       // moveToGame(primaryStage);

    }


    public static void main(String[] args) {
        launch(args);
    }

}
