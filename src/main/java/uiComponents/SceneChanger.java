package uiComponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SceneChanger {
    public static void moveToMainMenu(Stage stage) {
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try {
            fileInputStream = new FileInputStream(new File(Constants.MAIN_MENU_FXML));
            backgroundFile = new FileInputStream(Constants.MAIN_BACKGROUND_IMAGE);
            logoFile = new FileInputStream(Constants.LOGO_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image logoImage = new Image(logoFile);
            ImageView logoView = (ImageView) root.getChildren().get(1);
            logoView.setImage(logoImage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void moveToSignUp(Stage stage) {

        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.SIGN_UP_FXML));
            backgroundFile = new FileInputStream(Constants.MAIN_BACKGROUND_IMAGE);
            logoFile = new FileInputStream(Constants.LOGO_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image logoImage = new Image(logoFile);
            ImageView logoView = (ImageView) root.getChildren().get(1);
            logoView.setImage(logoImage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void moveToSignIn(Stage stage) {

        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.SIGN_IN_FXML));
            backgroundFile = new FileInputStream(Constants.MAIN_BACKGROUND_IMAGE);
            logoFile = new FileInputStream(Constants.LOGO_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image logoImage = new Image(logoFile);
            ImageView logoView = (ImageView) root.getChildren().get(1);
            logoView.setImage(logoImage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void moveToSeeThePlayers(Stage stage, boolean isCreator) {
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try{
            if(isCreator)
                fileInputStream = new FileInputStream(new File(Constants.SEE_PLAYERS_FXML));
            else
                fileInputStream = new FileInputStream(new File(Constants.SEE_PLAYERS_2_FXML));

            backgroundFile = new FileInputStream(Constants.MAIN_BACKGROUND_IMAGE);
            logoFile = new FileInputStream(Constants.LOGO_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image logoImage = new Image(logoFile);
            ImageView logoView = (ImageView) root.getChildren().get(1);
            logoView.setImage(logoImage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

