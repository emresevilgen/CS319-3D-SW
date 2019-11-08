package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;

import java.io.File;

public class Main extends Application {




    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File("src\\main\\resources\\fxml\\SignIn.fxml"));
        Parent root = loader.load(fileInputStream);

        //Parent root = FXMLLoader.load(getClass().getResource("C:\\Users\\esrad\\Desktop\\CS319-3D-SW\\src\\main\\resources\\fxml\\SignIn.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
