package uiComponents;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.Board;
import models.Card;
import models.Game;
import models.Player;
import utils.Constants;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static uiComponents.Main.mediaPlayer;
import static uiComponents.Main.settings;
import static uiComponents.SceneChanger.moveToMainMenu;

public class GameController implements Initializable {
    @FXML
    public ImageView focus;

    @FXML
    ImageView audioDescriptionView;
    @FXML
    ImageView soundEffectsView;
    @FXML
    ImageView exitView;
    @FXML
    ImageView playerBoard;
    @FXML
    ImageView acrossBoard;
    @FXML
    ImageView rightBoard;
    @FXML
    ImageView leftBoard;

    @FXML
    ImageView deck1;
    @FXML
    ImageView deck2;
    @FXML
    ImageView deck3;
    @FXML
    ImageView deck4;
    @FXML
    ImageView deck5;
    @FXML
    ImageView deck6;
    @FXML
    ImageView deck7;

    @FXML
    ImageView card1;
    @FXML
    ImageView card2;
    @FXML
    ImageView card3;
    @FXML
    ImageView card4;
    @FXML
    ImageView card5;
    @FXML
    ImageView card6;
    @FXML
    ImageView card7;
    @FXML
    ImageView card8;
    @FXML
    ImageView card9;
    @FXML
    ImageView card10;
    @FXML
    ImageView card11;
    @FXML
    ImageView card12;
    @FXML
    ImageView card13;
    @FXML
    ImageView card14;
    @FXML
    ImageView card15;
    @FXML
    ImageView card16;
    @FXML
    ImageView card17;
    @FXML
    ImageView card18;
    @FXML
    ImageView card19;

    ImageView[] cardViews;
    int ageNumber = 0;
    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            // Server request and update the game
            update();
            System.out.println("server request");
        }
    }));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // To delete
        Main.game = new Game();
        Main.game.ageNumber = 1;
        Main.game.players = new Player[4];
        Main.game.players[0] = new Player();
        Main.game.players[0].playerCards = new Card[7];
        Main.game.players[0].playerCards[0] = new Card();
        Main.game.players[0].playerCards[0].cardName = "Altar";
        Main.game.players[0].playerCards[1] = new Card();
        Main.game.players[0].playerCards[1].cardName = "Apothecary";
        Main.game.players[0].playerCards[2] = new Card();
        Main.game.players[0].playerCards[2].cardName = "Aqueduct";
        Main.game.players[0].playerCards[3] = new Card();
        Main.game.players[0].playerCards[3].cardName = "Archery Range";
        Main.game.players[0].playerCards[4] = new Card();
        Main.game.players[0].playerCards[4].cardName = "Arena";
        Main.game.players[0].playerCards[5] = new Card();
        Main.game.players[0].playerCards[5].cardName = "Arsenal";
        Main.game.players[0].playerCards[6] = new Card();
        Main.game.players[0].playerCards[6].cardName = "Forum";

        Main.game.players[0].board = new Board();
        Main.game.players[0].board.wonderName = "Alexandria A";
        Main.game.players[0].board.cards = new Card[19];
        Main.game.players[0].board.cards[0] = new Card();
        Main.game.players[0].board.cards[0].cardName = "Tree Farm";
        Main.game.players[0].board.cards[0].cardColor = "Brown";
        Main.game.players[0].board.cards[1] = new Card();
        Main.game.players[0].board.cards[1].cardName = "Loom";
        Main.game.players[0].board.cards[1].cardColor = "Gray";
        Main.game.players[0].board.cards[2] = new Card();
        Main.game.players[0].board.cards[2].cardName = "Library";
        Main.game.players[0].board.cards[2].cardColor = "Green";
        Main.game.players[0].board.cards[3] = new Card();
        Main.game.players[0].board.cards[3].cardName = "Walls";
        Main.game.players[0].board.cards[3].cardColor = "Red";

        Main.game.players[1] = new Player();
        Main.game.players[1].board = new Board();
        Main.game.players[1].board.wonderName = "Ephesos A";
        Main.game.players[1].board.cards = new Card[19];
        Main.game.players[1].board.cards[0] = new Card();
        Main.game.players[1].board.cards[0].cardName = "Tree Farm";
        Main.game.players[1].board.cards[0].cardColor = "Brown";
        Main.game.players[1].board.cards[1] = new Card();
        Main.game.players[1].board.cards[1].cardName = "Loom";
        Main.game.players[1].board.cards[1].cardColor = "Gray";
        Main.game.players[1].board.cards[2] = new Card();
        Main.game.players[1].board.cards[2].cardName = "Library";
        Main.game.players[1].board.cards[2].cardColor = "Green";
        Main.game.players[1].board.cards[3] = new Card();
        Main.game.players[1].board.cards[3].cardName = "Walls";
        Main.game.players[1].board.cards[3].cardColor = "Red";

        Main.game.players[2] = new Player();
        Main.game.players[2].board = new Board();
        Main.game.players[2].board.wonderName = "Gizah A";
        Main.game.players[2].board.cards = new Card[19];

        Main.game.players[3] = new Player();
        Main.game.players[3].board = new Board();
        Main.game.players[3].board.wonderName = "Babylon A";
        Main.game.players[3].board.cards = new Card[19];

       //
        
        cardViews = new ImageView[19];
        cardViews[0] = card1;
        cardViews[1] = card2;
        cardViews[2] = card3;
        cardViews[3] = card4;
        cardViews[4] = card5;
        cardViews[5] = card6;
        cardViews[6] = card7;
        cardViews[7] = card8;
        cardViews[8] = card9;
        cardViews[9] = card10;
        cardViews[10] = card11;
        cardViews[11] = card12;
        cardViews[12] = card13;
        cardViews[13] = card14;
        cardViews[14] = card15;
        cardViews[15] = card16;
        cardViews[16] = card17;
        cardViews[17] = card18;
        cardViews[18] = card19;

        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

        try {
            FileInputStream inputStream = new FileInputStream(Constants.AUDIO_DESCRIPTION_IMAGE);
            Image audioImage = new Image(inputStream);
            audioDescriptionView.setImage(audioImage);
            if (settings.isAudioDescription()){
                audioDescriptionView.setFitHeight(70);
                audioDescriptionView.setFitWidth(70);
                audioDescriptionView.setY(-13);
                audioDescriptionView.setX(-10);
            }
            else{
                audioDescriptionView.setFitWidth(45);
                audioDescriptionView.setFitHeight(45);
                audioDescriptionView.setY(0);
                audioDescriptionView.setX(0);
            }

            if (settings.isSoundEffects())
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_ON_IMAGE);
            else
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_OFF_IMAGE);

            Image soundImage = new Image(inputStream);

            soundEffectsView.setImage(soundImage);

            inputStream = new FileInputStream(Constants.EXIT_IMAGE);
            Image exitImage = new Image(inputStream);

            exitView.setImage(exitImage);

            update();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void switchAudioDescription(MouseEvent actionEvent) {
        settings.switchAudioDescription();
        if(settings.isAudioDescription()){
            audioDescriptionView.setFitHeight(70);
            audioDescriptionView.setFitWidth(70);
            audioDescriptionView.setY(-13);
            audioDescriptionView.setX(-10);
        }
        else {
            audioDescriptionView.setFitWidth(45);
            audioDescriptionView.setFitHeight(45);
            audioDescriptionView.setY(0);
            audioDescriptionView.setX(0);
        }


    }

    public void switchSoundEffects(MouseEvent actionEvent) {
        settings.switchSoundEffects();
        FileInputStream inputStream = null;
        try {
            if (settings.isSoundEffects()) {
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_ON_IMAGE);
                mediaPlayer.play();
            }
            else {
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_OFF_IMAGE);
                mediaPlayer.stop();

            }

            Image soundImage = new Image(inputStream);
            soundEffectsView.setImage(soundImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        timeLine.stop(); // To kill timeline
    }

    public void exit(MouseEvent actionEvent){
        System.out.println("xxx");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.setTitle("Exit Game");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Do you want to exit the current game?");
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonNo, buttonYes);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            moveToMainMenu((Stage)exitView.getScene().getWindow());
            timeLine.stop();
            mediaPlayer.stop();
            Media sound = new Media(new File(Constants.MENU_SOUND).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
            mediaPlayer.play();
        } else {
            alert.close();
        }
    }

    public void focusIntoCard(MouseEvent mouseEvent) {
        ImageView current = (ImageView) mouseEvent.getSource();
        if (current.getImage() != null){
            Glow glow = new Glow();
            glow.setLevel(0.4);
            focus.setEffect(glow);
            focus.setX(current.getX());
            focus.setY(current.getY());
            focus.setLayoutX(current.getLayoutX());
            focus.setLayoutY(current.getLayoutY());
            focus.setImage(current.getImage());
            focus.setScaleX(1.30);
            focus.setScaleY(1.30);
        }
    }

    public void focusOut(MouseEvent mouseEvent) {
        focus.setLayoutX(0);
        focus.setLayoutY(0);
        focus.setImage(null);
    }

    public void effectOn(MouseEvent mouseEvent) {
        ImageView current = (ImageView) mouseEvent.getSource();
        if (current.getImage() != null){
           Glow glow = new Glow();
           glow.setLevel(0.4);
           current.setEffect(glow);
           current.setScaleX(1.30);
           current.setScaleY(1.30);
        }
    }

    public void effectOff(MouseEvent mouseEvent) {
        ImageView current = (ImageView) mouseEvent.getSource();
        if (current.getImage() != null){
            Glow glow = new Glow();
            glow.setLevel(0.0);
            current.setEffect(glow);
            current.setScaleX(1);
            current.setScaleY(1);
        }
    }

    public void update(){

        if (ageNumber != Main.game.ageNumber) {
            ageNumber = Main.game.ageNumber;
            Media sound;
            if (ageNumber == 1)
                sound = new Media(new File(Constants.AGE_ONE_SOUND).toURI().toString());
            else if (ageNumber == 2)
                sound = new Media(new File(Constants.AGE_TWO_SOUND).toURI().toString());
            else
                sound = new Media(new File(Constants.AGE_THREE_SOUND).toURI().toString());

            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
            mediaPlayer.play();
        }


        Player[] players = Main.game.players;
        playerBoard.setImage(getBoardImage(players[0].board, 0));
        rightBoard.setImage(getBoardImage(players[1].board, 1));
        acrossBoard.setImage(getBoardImage(players[2].board, 2));
        leftBoard.setImage(getBoardImage(players[3].board, 3));
        
        // To display the cards at the hand of the player
        Card[] playerCards = players[0].playerCards;
        if (playerCards[0] != null)
            deck1.setImage(getCardImage(playerCards[0]));
        if (playerCards[1] != null)
            deck2.setImage(getCardImage(playerCards[1]));
        if (playerCards[2] != null)
            deck3.setImage(getCardImage(playerCards[2]));
        if (playerCards[3] != null)
            deck4.setImage(getCardImage(playerCards[3]));
        if (playerCards[4] != null)
            deck5.setImage(getCardImage(playerCards[4]));
        if (playerCards[5] != null)
            deck6.setImage(getCardImage(playerCards[5]));
        if (playerCards[6] != null)
            deck7.setImage(getCardImage(playerCards[6]));


        
        Card[] cards = players[0].board.cards;
        ArrayList<Card> brownCards = new ArrayList<>();
        ArrayList<Card> grayCards = new ArrayList<>();
        ArrayList<Card> redCards = new ArrayList<>();
        ArrayList<Card> blueCards = new ArrayList<>();
        ArrayList<Card> yellowCards = new ArrayList<>();
        ArrayList<Card> purpleCards = new ArrayList<>();
        ArrayList<Card> greenCards = new ArrayList<>();

        for (int i = 0; i < cards.length; i++){
            if (cards[i] != null){
                Card card = cards[i];
                String color = card.cardColor;
                switch (color){
                    case "Brown": brownCards.add(card); break;
                    case "Gray": grayCards.add(card); break;
                    case "Red": redCards.add(card); break;
                    case "Blue": blueCards.add(card); break;
                    case "Yellow": yellowCards.add(card); break;
                    case "Purple": purpleCards.add(card); break;
                    case "Green": greenCards.add(card); break;
                }
            }
        }

        int viewOrder = 0;

        for (int i = 0; i < redCards.size(); i++){
            cardViews[viewOrder].setImage(getCardImage(redCards.get(i)));
            viewOrder++;
        }

        for (int i = 0; i < brownCards.size(); i++){
            cardViews[viewOrder].setImage(getCardImage(brownCards.get(i)));
            viewOrder++;
        }

        for (int i = 0; i < grayCards.size(); i++){
            cardViews[viewOrder].setImage(getCardImage(grayCards.get(i)));
            viewOrder++;
        }

        for (int i = 0; i < blueCards.size(); i++){
            cardViews[viewOrder].setImage(getCardImage(blueCards.get(i)));
            viewOrder++;
        }

        for (int i = 0; i < yellowCards.size(); i++){
            cardViews[viewOrder].setImage(getCardImage(yellowCards.get(i)));
            viewOrder++;
        }

        for (int i = 0; i < purpleCards.size(); i++){
            cardViews[viewOrder].setImage(getCardImage(purpleCards.get(i)));
            viewOrder++;
        }

        for (int i = 0; i < greenCards.size(); i++){
            cardViews[viewOrder].setImage(getCardImage(greenCards.get(i)));
            viewOrder++;
        }

    }

    public void showOtherPlayersCards(MouseEvent mouseEvent) {
        Card [] cards;
        ImageView boardView = (ImageView)mouseEvent.getSource();
        if (boardView.equals(rightBoard))
            cards = Main.game.players[1].board.cards;
        else if (boardView.equals(acrossBoard))
            cards = Main.game.players[2].board.cards;
        else if (boardView.equals(leftBoard))
            cards = Main.game.players[3].board.cards;
        else
            return;


        Stage boardStage;
        Scene boardScene;
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;

        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.OTHER_BOARDS_CARDS_FXML));
            backgroundFile = new FileInputStream(Constants.CREATE_LOBBY_BACK_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            boardScene = new Scene(root);
            boardStage = new Stage();
            boardStage.setScene(boardScene);
            boardStage.initStyle(StageStyle.UTILITY);
            boardStage.show();

            ArrayList<Card> brownCards = new ArrayList<>();
            ArrayList<Card> grayCards = new ArrayList<>();
            ArrayList<Card> redCards = new ArrayList<>();
            ArrayList<Card> blueCards = new ArrayList<>();
            ArrayList<Card> yellowCards = new ArrayList<>();
            ArrayList<Card> purpleCards = new ArrayList<>();
            ArrayList<Card> greenCards = new ArrayList<>();

            for (int i = 0; i < cards.length; i++){
                if (cards[i] != null){
                    Card card = cards[i];
                    String color = card.cardColor;
                    switch (color){
                        case "Brown": brownCards.add(card); break;
                        case "Gray": grayCards.add(card); break;
                        case "Red": redCards.add(card); break;
                        case "Blue": blueCards.add(card); break;
                        case "Yellow": yellowCards.add(card); break;
                        case "Purple": purpleCards.add(card); break;
                        case "Green": greenCards.add(card); break;
                    }
                }
            }

            int viewOrder = 0;

            for (int i = 0; i < redCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+1)).setImage(getCardImage(redCards.get(i)));
                viewOrder++;
            }

            for (int i = 0; i < brownCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+1)).setImage(getCardImage(brownCards.get(i)));
                viewOrder++;
            }

            for (int i = 0; i < grayCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+1)).setImage(getCardImage(grayCards.get(i)));
                viewOrder++;
            }

            for (int i = 0; i < blueCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+1)).setImage(getCardImage(blueCards.get(i)));
                viewOrder++;
            }

            for (int i = 0; i < yellowCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+1)).setImage(getCardImage(yellowCards.get(i)));
                viewOrder++;
            }

            for (int i = 0; i < purpleCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+1)).setImage(getCardImage(purpleCards.get(i)));
                viewOrder++;
            }

            for (int i = 0; i < greenCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+1)).setImage(getCardImage(greenCards.get(i)));
                viewOrder++;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getCardImage(Card card){
        if (card != null) {
            String cardName = card.cardName;
            String cardPath = Constants.CARD_IMAGE + File.separator + cardName.replaceAll(" ", "").toLowerCase() + ".png";
            try {
                FileInputStream inputStream = new FileInputStream(cardPath);
                return new Image(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Image getBoardImage(Board board, int index){
        if (board != null) {
            String boardName = board.wonderName;
            String boardPath;
            if (index == 0)
                boardPath = Constants.BOARD_IMAGE + File.separator + boardName.replaceAll(" ", "").toLowerCase() + "bottom" + ".png";
            else if (index == 1)
                boardPath = Constants.BOARD_IMAGE + File.separator + boardName.replaceAll(" ", "").toLowerCase() + "right" + ".png";
            else if (index == 2)
                boardPath = Constants.BOARD_IMAGE + File.separator + boardName.replaceAll(" ", "").toLowerCase() + "top" + ".png";
            else if (index == 3)
                boardPath = Constants.BOARD_IMAGE + File.separator + boardName.replaceAll(" ", "").toLowerCase() + "left" + ".png";
            else
                return null;

            try {
                FileInputStream inputStream = new FileInputStream(boardPath);
                return new Image(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
