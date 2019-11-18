package uiComponents;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import utils.Constants;

import java.io.FileInputStream;

import java.io.File;

import static uiComponents.SceneChanger.moveToCreateLobby;
import static uiComponents.SceneChanger.moveToSignIn;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        moveToSignIn(primaryStage);
        //moveToCreateLobby(primaryStage);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
