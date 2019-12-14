package uiComponents;

import audioDescription.TextToSpeech;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

public class GameScene implements Initializable {
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

    // Image views for board
    ImageView[] cardViews;

    int ageNumber = 0;
    int turnNumber = 0;


    private String keyInput = "";
    private int deckNo;
    private int boardCardsNo;
    private int leftBoardCardsNo;
    private int acrossBoardCardsNo;
    private int rightBoardCardsNo;
    private int boardsNo;
    private Card[] cardsInColorOrder;

    private TextToSpeech tts = new TextToSpeech();

    // To send a request at every second
    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            // Server request and update the game
            update();
        }
    }));

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //-------------------------------------
        //-------------------------------------
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

        //-------------------------------------
       Main.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {
               KeyCode keyCode = event.getCode();
               switch (keyCode)
               {
                   // Cards in the player's deck
                   case C: keyInput = "deck";
                        deckNo = 0; boardCardsNo = 0; leftBoardCardsNo = 0; acrossBoardCardsNo= 0; rightBoardCardsNo = 0; boardsNo = 0;
                        tts.read("The cards in the deck");
                        tts.read(Main.game.players[0].playerCards[deckNo].cardName);
                        break;

                   //Cards in the player's board
                   case S: keyInput = "boardCards";
                        deckNo = 0; boardCardsNo = 0; leftBoardCardsNo = 0; acrossBoardCardsNo= 0; rightBoardCardsNo = 0; boardsNo = 0;
                        tts.read("The cards in the board");
                        tts.read(Main.game.players[0].board.cards[boardCardsNo].cardName);
                        break;

                   //Cards in the left player's board
                   case A: keyInput = "leftBoardCards"; deckNo = 0; boardCardsNo = 0; leftBoardCardsNo = 0; acrossBoardCardsNo= 0; rightBoardCardsNo = 0; boardsNo = 0; tts.read("The cards in the left board"); break;

                   //Cards in the across player's board
                   case W: keyInput = "acrossBoardCards"; deckNo = 0; boardCardsNo = 0; leftBoardCardsNo = 0; acrossBoardCardsNo= 0; rightBoardCardsNo = 0; boardsNo = 0; tts.read("The cards in the across board"); break;

                   //Cards in the right player's board
                   case D: keyInput =  "rightBoardCards";
                   deckNo = 0; boardCardsNo = 0; leftBoardCardsNo = 0; acrossBoardCardsNo= 0; rightBoardCardsNo = 0; boardsNo = 0;
                        tts.read("The cards in the right board");
                        break;

                   //Boards description
                   case B: keyInput = "boards";
                        deckNo = 0; boardCardsNo = 0; leftBoardCardsNo = 0; acrossBoardCardsNo= 0; rightBoardCardsNo = 0; boardsNo = 0;
                        tts.read("The boards");
                       tts.read(Main.game.players[boardsNo].board.wonderName);
                       break;

               }
               if(keyCode.equals(KeyCode.RIGHT) && keyInput.equals("boardCards"))
               {
                   //if in içinde boarddaki  card sayısını compare etmek için bunu kullan Main.game.players[0].board.cardCount,bunu da oyun sırasında arttır
                   if(boardCardsNo == 3)//değiştir
                   {
                       boardCardsNo = -1;
                   }
                   boardCardsNo++;
                   tts.read(cardsInColorOrder[boardCardsNo].cardName);
               }
               else if(keyCode.equals(KeyCode.LEFT) && keyInput.equals("boardCards"))
               {
                   //if in içinde boarddaki  card sayısını compare etmek için bunu kullan Main.game.players[0].board.cardCount,bunu da oyun sırasında arttır
                   if(boardCardsNo == 0)
                   {
                       boardCardsNo = 4;//değiştir
                   }
                   boardCardsNo--;
                   tts.read(cardsInColorOrder[boardCardsNo].cardName);
               }

               if(keyCode.equals(KeyCode.ENTER) && keyInput.equals("boardCards"))
               {

               }

               else if(keyCode.equals(KeyCode.RIGHT) && keyInput.equals("deck"))
               {
                   if(deckNo == (7-turnNumber) )
                   {
                       deckNo = -1;
                   }
                   deckNo++;
                   tts.read(Main.game.players[0].playerCards[deckNo].cardName);
               }
               else if(keyCode.equals(KeyCode.LEFT) && keyInput.equals("deck"))
               {
                   if(deckNo == 0)
                   {
                       deckNo = 6 - turnNumber;
                   }
                   tts.read(Main.game.players[0].playerCards[deckNo].cardName);
                   deckNo--;
               }
               else if(keyCode.equals(KeyCode.RIGHT) && keyInput.equals("boards"))
               {
                   if(boardsNo == 3)  // Player number ı serverdan çektiğin şeyi koy buraya
                   {
                       boardsNo = -1;
                   }
                   boardsNo++;
                   tts.read(Main.game.players[boardsNo].board.wonderName);
               }
               else if(keyCode.equals(KeyCode.LEFT) && keyInput.equals("boards"))
               {
                   if(boardsNo == 0)  // Player number ı serverdan çektiğin şeyi koy buraya
                   {
                       boardsNo = 4; // player number+1
                   }
                   boardsNo--;
                   tts.read(Main.game.players[boardsNo].board.wonderName);
               }

               event.consume();
           }
       });
        //-------------------------------------

        // Initialize the card view array
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

        // Start sending requests
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

        // Initialize the buttom images and their status
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

            // Update the data
            update();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Audio description button listener
    public void switchAudioDescription(MouseEvent actionEvent) {
        // Change the image size accorsing to its status
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

    // Sound effects button listener
    public void switchSoundEffects(MouseEvent actionEvent) {
        // CHange the image according to its status
        settings.switchSoundEffects();
        FileInputStream inputStream = null;
        try {
            if (settings.isSoundEffects()) {
                // Play music
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_ON_IMAGE);
                mediaPlayer.play();
            }
            else {
                // Stop music
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_OFF_IMAGE);
                mediaPlayer.stop();

            }

            Image soundImage = new Image(inputStream);
            soundEffectsView.setImage(soundImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Exit button listener
    public void exit(MouseEvent actionEvent){
        // Show confirmation pop up
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.setTitle("Exit Game");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Do you want to exit the current game?");
        // Add options
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonNo, buttonYes);

        Optional<ButtonType> result = alert.showAndWait();
        // Get the result
        if (result.get() == buttonYes){
            timeLine.stop(); // Stop requests
            mediaPlayer.stop(); // Stop game music

            // Play menu music
            Media sound = new Media(new File(Constants.MENU_SOUND).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
            mediaPlayer.play();

            moveToMainMenu((Stage)exitView.getScene().getWindow());

        } else {
            alert.close();
        }
    }

    // Focus into card when mouse entered
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

    // Focus out from card when mouse exited
    public void focusOut(MouseEvent mouseEvent) {
        focus.setLayoutX(0);
        focus.setLayoutY(0);
        focus.setImage(null);
    }

    // Focus on hand card when mouse exited
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

    // Focus out from hand card when mouse exited
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
        // Check the music of the age
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

        // --------------------------------------
        // --------------------------------------
        // Initialize a al
        // Set the board images
        Player[] players = Main.game.players;
        playerBoard.setImage(getBoardImage(players[0].board, 0));
        rightBoard.setImage(getBoardImage(players[1].board, 1));
        acrossBoard.setImage(getBoardImage(players[2].board, 2));
        leftBoard.setImage(getBoardImage(players[3].board, 3));
        // --------------------------------------
        // --------------------------------------

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


        // To classify the cards at the users board
        Card[] cards = players[0].board.cards;

        ArrayList<Card> brownCards = new ArrayList<>();
        ArrayList<Card> grayCards = new ArrayList<>();
        ArrayList<Card> redCards = new ArrayList<>();
        ArrayList<Card> blueCards = new ArrayList<>();
        ArrayList<Card> yellowCards = new ArrayList<>();
        ArrayList<Card> purpleCards = new ArrayList<>();
        ArrayList<Card> greenCards = new ArrayList<>();

        //To use for ordered list in audio description
        cardsInColorOrder = new Card[19]; // Main.game.players[0].board.cardCount


        for (int i = 0; i < cards.length; i++){
            if (cards[i] != null){
                Card card = cards[i];
                String color = card.cardColor;
                switch (color){
                    case "Brown": brownCards.add(card); cardsInColorOrder[i] = card; break;
                    case "Gray": grayCards.add(card); cardsInColorOrder[i] = card; break;
                    case "Red": redCards.add(card); cardsInColorOrder[i] = card; break;
                    case "Blue": blueCards.add(card); cardsInColorOrder[i] = card; break;
                    case "Yellow": yellowCards.add(card); cardsInColorOrder[i] = card; break;
                    case "Purple": purpleCards.add(card); cardsInColorOrder[i] = card; break;
                    case "Green": greenCards.add(card); cardsInColorOrder[i] = card; break;
                }
            }
        }

        // Display the cards in order
        int viewOrder = 0;

        for (int i = 0; i < brownCards.size(); i++){
            cardViews[viewOrder].setImage(getCardImage(brownCards.get(i)));
            viewOrder++;
        }

        for (int i = 0; i < grayCards.size(); i++){
            cardViews[viewOrder].setImage(getCardImage(grayCards.get(i)));
            viewOrder++;
        }

        for (int i = 0; i < greenCards.size(); i++){
            cardViews[viewOrder].setImage(getCardImage(greenCards.get(i)));
            viewOrder++;
        }

        for (int i = 0; i < redCards.size(); i++){
            cardViews[viewOrder].setImage(getCardImage(redCards.get(i)));
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

    }

    public static Stage boardStage;
    public static Scene boardScene;
    // Board listeners
    public void showOtherPlayersCards(MouseEvent mouseEvent) {
        Card [] cards;
        // Get the players cards
        ImageView boardView = (ImageView)mouseEvent.getSource();
        if (boardView.equals(rightBoard))
            cards = Main.game.players[1].board.cards;
        else if (boardView.equals(acrossBoard))
            cards = Main.game.players[2].board.cards;
        else if (boardView.equals(leftBoard))
            cards = Main.game.players[3].board.cards;
        else
            return;

        // Load FXML and show pop up window

        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = null;
        FileInputStream backgroundFile = null;

        AnchorPane root = null;

        try{
            fileInputStream = new FileInputStream(new File(Constants.OTHER_BOARDS_CARDS_FXML));
            backgroundFile = new FileInputStream(Constants.CREATE_LOBBY_BACK_IMAGE);
            root = (AnchorPane)loader.load(fileInputStream);

            // Set background image
            Image backgroundImage = new Image(backgroundFile);
            ImageView backgroundView = (ImageView) root.getChildren().get(0);
            backgroundView.setImage(backgroundImage);

            boardScene = new Scene(root);
            boardStage = new Stage();
            boardStage.setScene(boardScene);
            boardStage.initStyle(StageStyle.UTILITY);
            boardStage.show();

            // Classify the cards
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

            // Display the cards in order
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


    /**
     * Gets the Card object and returns its image
     * @param card Card object
     * @return the image of the card
     */
    public Image getCardImage(Card card){
        if (card != null) {
            String cardName = card.cardName;
            // Get the path of the image
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

    /**
     * Gets the Card object and returns its image
     * @param board Card object
     * @param index location of the player
     * @return the image of the card
     */
    public Image getBoardImage(Board board, int index){
        if (board != null) {
            String boardName = board.wonderName;
            // Get the path of the image according to the location of the player
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
