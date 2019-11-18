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

public class Main extends Application {




    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File(Constants.SIGN_IN_FXML));
        AnchorPane root = (AnchorPane)loader.load(fileInputStream);
        FileInputStream inputStream = new FileInputStream(Constants.MAIN_BACKGROUND_IMAGE);
        Image image = new Image(inputStream);

        ImageView imageView = (ImageView) root.getChildren().get(0);
        imageView.setImage(image);
        Scene scene = new Scene(root);

        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
