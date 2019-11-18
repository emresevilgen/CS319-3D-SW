package uiComponents;

import javafx.application.Application;
import javafx.stage.Stage;
import models.Game;
import models.User;

import static uiComponents.SceneChanger.moveToCreateLobby;
import static uiComponents.SceneChanger.moveToSignIn;

public class Main extends Application {
    public static User user = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        moveToSignIn(primaryStage);
        //moveToCreateLobby(primaryStage);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
