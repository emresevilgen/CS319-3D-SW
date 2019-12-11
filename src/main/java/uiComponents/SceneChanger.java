package uiComponents;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SceneChanger {

    // Move main menu scene
    public static void moveToMainMenu(Stage stage) {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try {
            fileInputStream = new FileInputStream(new File(Constants.MAIN_MENU_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.MAIN_BACKGROUND_IMAGE);
            logoFile = new FileInputStream(Constants.LOGO_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image logoImage = new Image(logoFile);
            ImageView logoView = (ImageView) root.getChildren().get(1);
            logoView.setImage(logoImage);

            // Show scene
            stage.getScene().setRoot(root);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Move sign up scene
    public static void moveToSignUp(Stage stage) {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.SIGN_UP_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.MAIN_BACKGROUND_IMAGE);
            logoFile = new FileInputStream(Constants.LOGO_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image logoImage = new Image(logoFile);
            ImageView logoView = (ImageView) root.getChildren().get(1);
            logoView.setImage(logoImage);

            // Show scene
            stage.getScene().setRoot(root);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Move sign in scene
    public static void moveToSignIn(Stage stage) {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.SIGN_IN_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.MAIN_BACKGROUND_IMAGE);
            logoFile = new FileInputStream(Constants.LOGO_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image logoImage = new Image(logoFile);
            ImageView logoView = (ImageView) root.getChildren().get(1);
            logoView.setImage(logoImage);

            // Show scene
            stage.getScene().setRoot(root);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Move create lobby scene
    public static void moveToCreateLobby(Stage stage) {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream backFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.CREATE_LOBBY_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.CREATE_LOBBY_BACKGROUND_IMAGE);
            backFile = new FileInputStream(Constants.CREATE_LOBBY_BACK_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image backImage = new Image(backFile);
            ImageView backView = (ImageView) root.getChildren().get(1);
            backView.setImage(backImage);

            // Show scene
            stage.getScene().setRoot(root);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Move rankings scene
    public static void moveToRankings(Stage stage) {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream backFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.RANKINGS_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.RANKINGS_BACKGROUND_IMAGE);
            backFile = new FileInputStream(Constants.RANKINGS_BACK_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image backImage = new Image(backFile);
            ImageView backView = (ImageView) root.getChildren().get(1);
            backView.setImage(backImage);

            // Show scene
            stage.getScene().setRoot(root);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Move settings scene
    public static void moveToSettings(Stage stage) {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.SETTINGS_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.SETTINGS_BACKGROUND_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            // Show scene
            stage.getScene().setRoot(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Move credits scene
    public static void moveToCredits(Stage stage) {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream logoFile = null;
        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.CREDITS_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.CREDITS_BACKGROUND_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            // Show scene
            stage.getScene().setRoot(root);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Move see the players scene
    public static void moveToSeeThePlayers(Stage stage, boolean isCreator) {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;
        FileInputStream backFile = null;
        AnchorPane root = null;

        try{
            if(isCreator)
                fileInputStream = new FileInputStream(new File(Constants.SEE_PLAYERS_FXML));
            else
                fileInputStream = new FileInputStream(new File(Constants.SEE_PLAYERS_2_FXML));

            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.SEE_THE_PLAYERS_BACKGROUND_IMAGE);
            backFile = new FileInputStream(Constants.SEE_THE_PLAYERS_BACK_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            Image backImage = new Image(backFile);
            ImageView backView = (ImageView) root.getChildren().get(1);
            backView.setImage(backImage);

            // Show scene
            stage.getScene().setRoot(root);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Move game scene
    public static void moveToGame(Stage stage) {
        // Load the fxml
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;

        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.GAME_FXML));
            // Get the images from file and add to the image views
            backgroundFile = new FileInputStream(Constants.GAME_BACKGROUND_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            // Show scene
            stage.getScene().setRoot(root);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Exit
    public static void exit(Stage stage){
        System.exit(0);
    }
}

