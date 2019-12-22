package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.Reader;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.*;
import utils.Constants;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameScene implements Initializable {
    @FXML
    public ImageView focus;

    @FXML
    Button nextTurnButton;
    @FXML
    Button structureButton;
    @FXML
    Button wonderButton;
    @FXML
    Button discardButton;

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

    //Table elements
    @FXML
    ImageView coins;
    @FXML
    ImageView militaryTokens;
    @FXML
    ImageView table;
    @FXML
    Label username1;
    @FXML
    Label username2;
    @FXML
    Label username3;
    @FXML
    Label username4;
    @FXML
    Label coin1;
    @FXML
    Label coin2;
    @FXML
    Label coin3;
    @FXML
    Label coin4;
    @FXML
    Label token1;
    @FXML
    Label token2;
    @FXML
    Label token3;
    @FXML
    Label token4;
    @FXML
    ImageView wonderStage;
    @FXML
    Label stage1;
    @FXML
    Label stage2;
    @FXML
    Label stage3;
    @FXML
    Label stage4;
    @FXML
    Label ageLabel;
    @FXML
    Label turnLabel;


    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #b80028; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #b80028; -fx-opacity: 0.85;";

    private Card selectedCard;
    private ImageView selectedCardView;
    private int selectedCardIndex;
    private Card focusedCardInBoard;
    private ImageView focusedCardInBoardView;
    private int focusedCardInBoardIndex;


    private int boardIndex;

    // Image views for board
    private ImageView[] cardViews;
    private ImageView[] deckCardViews;

    int ageNumber = 0;
    int turnNumber = 0;


    private String keyInput = "";
    private int leftBoardCardsNo;
    private int acrossBoardCardsNo;
    private int rightBoardCardsNo;
    private Card[] cardsInColorOrder;
    public Stage boardStage;
    public Scene boardScene;
    public Stage howToPlayStage;
    public Scene howToPlayScene;
    public boolean firstTime;

    public boolean showError;

    // To send a request at every second
    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            // Server request and update the game


        }
    }));

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            FileInputStream inputStream = new FileInputStream(Constants.COIN_IMAGE);
            Image coinImage = new Image(inputStream);
            coins.setImage(coinImage);
            FileInputStream inputStream2 = new FileInputStream(Constants.TOKEN_IMAGE);
            Image tokenImage = new Image(inputStream2);
            militaryTokens.setImage(tokenImage);
            FileInputStream inputStream3 = new FileInputStream(Constants.TABLE_IMAGE);
            Image tableImage = new Image(inputStream3);
            table.setImage(tableImage);
            FileInputStream inputStream4 = new FileInputStream(Constants.WONDER_IMAGE);
            Image wonderImage = new Image(inputStream4);
            wonderStage.setImage(wonderImage);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //---------------------------------------------------------------------------
        //Pop for how to play
       /* Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How To Play");
        alert.setHeaderText("Please enter H key for How To Play");
        alert.initOwner(nextTurnButton.getScene().getWindow());
        alert.setGraphic(null);

        alert.showAndWait();*/

        //---------------------------------------------------------------------------

        // Setting the mouse entered and exited listeners for hover effect
        nextTurnButton.setOnMouseEntered(e -> { nextTurnButton.setStyle(HOVERED_BUTTON_STYLE); });
        nextTurnButton.setOnMouseExited(e -> nextTurnButton.setStyle(IDLE_BUTTON_STYLE));
        discardButton.setOnMouseEntered(e -> { discardButton.setStyle(HOVERED_BUTTON_STYLE); });
        discardButton.setOnMouseExited(e -> discardButton.setStyle(IDLE_BUTTON_STYLE));
        structureButton.setOnMouseEntered(e -> { structureButton.setStyle(HOVERED_BUTTON_STYLE); });
        structureButton.setOnMouseExited(e -> structureButton.setStyle(IDLE_BUTTON_STYLE));
        wonderButton.setOnMouseEntered(e -> { wonderButton.setStyle(HOVERED_BUTTON_STYLE); });
        wonderButton.setOnMouseExited(e -> wonderButton.setStyle(IDLE_BUTTON_STYLE));

        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        Settings settings = DataHandler.getInstance().getSettings();

        firstTime = true;
        nextTurnButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if(settings.isAudioDescription() && !firstTime)
                    tts.read("Next turn");
                nextTurnButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                nextTurnButton.setStyle(IDLE_BUTTON_STYLE);
            firstTime = false;
        });

        discardButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if(settings.isAudioDescription())
                    tts.read("Discard the card");
                discardButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                discardButton.setStyle(IDLE_BUTTON_STYLE);
        });

        structureButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if(settings.isAudioDescription())
                    tts.read("Build a structure");
                structureButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                structureButton.setStyle(IDLE_BUTTON_STYLE);
        });

        wonderButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue) {
                if(settings.isAudioDescription())
                    tts.read("Build a wonder stage");
                wonderButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                wonderButton.setStyle(IDLE_BUTTON_STYLE);
        });
        //-------------------------------------
        //Change next turn button to next age
        Game game = DataHandler.getInstance().getGame();
        if(game.turnNumber == 6)
            nextTurnButton.setText("Next Age");
        else
            nextTurnButton.setText("Next Turn ");

        //-------------------------------------
        selectedCardIndex = 0;
        focusedCardInBoardIndex = 0;

        SceneHandler.getInstance().getStage().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {
               KeyCode keyCode = event.getCode();
               Game game = DataHandler.getInstance().getGame();

               switch (keyCode)
               {
                   // Cards in the player's deck
                   case C:
                       keyInput = "deck";
                       leftBoardCardsNo = 0; acrossBoardCardsNo= 0; rightBoardCardsNo = 0; boardIndex = 0;
                       if(settings.isAudioDescription()) {
                           tts.read("The cards in the deck." + game.players[0].playerCards[selectedCardIndex].cardName);
                           selectTheCard(selectedCardIndex);
                       }
                       break;

                   //Cards in the player's board
                   case S:
                       keyInput = "boardCards";
                       leftBoardCardsNo = 0; acrossBoardCardsNo= 0; rightBoardCardsNo = 0; boardIndex = 0;
                       if(settings.isAudioDescription())
                           tts.read("The cards in the board." + cardsInColorOrder[focusedCardInBoardIndex].cardName);
                       break;

                   //Cards in the left player's board
                   case A:
                       keyInput = "leftBoardCards";
                       leftBoardCardsNo = 0; acrossBoardCardsNo= 0; rightBoardCardsNo = 0; boardIndex = 0;
                       if(settings.isAudioDescription())
                           tts.read("The cards in the left board.");
                       break;

                   //Cards in the across player's board
                   case W:
                       keyInput = "acrossBoardCards";
                       leftBoardCardsNo = 0; acrossBoardCardsNo= 0; rightBoardCardsNo = 0; boardIndex = 0;
                       if(settings.isAudioDescription())
                           tts.read("The cards in the across board.");
                       break;

                   //Cards in the right player's board
                   case D:
                       keyInput =  "rightBoardCards";
                       leftBoardCardsNo = 0; acrossBoardCardsNo= 0; rightBoardCardsNo = 0; boardIndex = 0;
                       if(settings.isAudioDescription())
                           tts.read("The cards in the right board.");
                       break;

                   //Boards description
                   case B: keyInput = "boards";
                       leftBoardCardsNo = 0; acrossBoardCardsNo= 0; rightBoardCardsNo = 0; boardIndex = 0;
                       if(settings.isAudioDescription())
                           tts.read("The boards." + game.players[boardIndex].board.wonderName);
                       break;

               }

               if(keyCode.equals(KeyCode.R) && keyInput.equals("boardCards"))
               {
                   //if in içinde boarddaki  card sayısını compare etmek için bunu kullan game.players[0].board.cardCount,bunu da oyun sırasında arttır
                   if(focusedCardInBoardIndex == 3)//değiştir
                   {
                       focusedCardInBoardIndex = -1;
                   }
                   focusedCardInBoardIndex++;
                   if(settings.isAudioDescription())
                       tts.read(cardsInColorOrder[focusedCardInBoardIndex].cardName);
               }
               else if(keyCode.equals(KeyCode.E) && keyInput.equals("boardCards"))
               {
                   //if in içinde boarddaki  card sayısını compare etmek için bunu kullan game.players[0].board.cardCount,bunu da oyun sırasında arttır
                   if(focusedCardInBoardIndex == 0)
                   {
                       focusedCardInBoardIndex = 4;//değiştir
                   }
                   focusedCardInBoardIndex--;
                   if(settings.isAudioDescription())
                       tts.read(cardsInColorOrder[focusedCardInBoardIndex].cardName);
               }

               if(keyCode.equals(KeyCode.ENTER) && keyInput.equals("boardCards"))
               {

               }

               else if(keyCode.equals(KeyCode.R) && keyInput.equals("deck"))
               {
                   if(selectedCardIndex == (6-turnNumber) )
                   {
                       selectedCardIndex = -1;
                   }
                   selectedCardIndex++;
                   if(settings.isAudioDescription()) {
                       tts.read(game.players[0].playerCards[selectedCardIndex].cardName);
                       selectTheCard(selectedCardIndex);
                   }
               }
               else if(keyCode.equals(KeyCode.E) && keyInput.equals("deck"))
               {
                   if(selectedCardIndex == 0)
                   {
                       selectedCardIndex = 7 - turnNumber;
                   }
                   selectedCardIndex--;
                   if(settings.isAudioDescription()) {
                       tts.read(game.players[0].playerCards[selectedCardIndex].cardName);
                       selectTheCard(selectedCardIndex);
                   }
               }
               else if(keyCode.equals(KeyCode.R) && keyInput.equals("boards"))
               {
                   if(boardIndex == 3)  // Player number ı serverdan çektiğin şeyi koy buraya
                   {
                       boardIndex = -1;
                   }
                   boardIndex++;
                   if(settings.isAudioDescription())
                       tts.read(game.players[boardIndex].board.wonderName);
               }
               else if(keyCode.equals(KeyCode.E) && keyInput.equals("boards"))
               {
                   if(boardIndex == 0)  // Player number ı serverdan çektiğin şeyi koy buraya
                   {
                       boardIndex = 4; // player number+1
                   }
                   boardIndex--;
                   if(settings.isAudioDescription())
                       tts.read(game.players[boardIndex].board.wonderName);
               }

               if(keyCode.equals(KeyCode.I)){
                    if (settings.isAudioDescription()){
                        String text = "Age is " + game.ageNumber + " and turn is " + game.turnNumber+ " ";
                        for (int i = 0; i < game.users.length; i++) {
                            if (game.users[i] != null) {
                                text = text + game.users[i].userName + " has ";
                                Player player = game.players[i];
                                if (player.coin > 1)
                                    text = text + player.coin + " coins ";
                                else
                                    text = text + player.coin + " coin ";

                                text = text + " and ";

                                if (player.victoryPoints > 1)
                                    text = text + player.victoryPoints + " victory tokens. \n";
                                else
                                    text = text + player.victoryPoints + " victory token. \n";

                                text = text + " and the wonder stage is " + player.board.wonderStage ;
                            }
                        }
                        tts.read(text);
                    }
               }

               if(keyCode.equals(KeyCode.N))
               {
                   switchAudioDescriptionHelper();
               }

               if(keyCode.equals(keyCode.M))
               {
                   switchSoundEffectsHelper();
               }
               if(keyCode.equals(KeyCode.ESCAPE))
               {
                   exitHelper();
               }

               if(keyCode.equals(keyCode.H))
               {
                   FXMLLoader loader = new FXMLLoader();
                   FileInputStream fileInputStream = null;
                   FileInputStream backgroundFile = null;

                   AnchorPane root = null;

                   try{
                       fileInputStream = new FileInputStream(new File(Constants.HOW_TO_PLAY_FXML));
                       backgroundFile = new FileInputStream(Constants.CREATE_LOBBY_BACK_IMAGE);
                       root = (AnchorPane)loader.load(fileInputStream);

                       // Set background image
                       Image backgroundImage = new Image(backgroundFile);
                       ImageView backgroundView = (ImageView) root.getChildren().get(0);

                       backgroundView.setImage(backgroundImage);

                       howToPlayScene = new Scene(root);
                       howToPlayStage = new Stage();
                       howToPlayStage.setScene(howToPlayScene);
                       howToPlayStage.initStyle(StageStyle.UTILITY);
                       howToPlayStage.setAlwaysOnTop(true);
                       howToPlayStage.initOwner(focus.getScene().getWindow());
                       howToPlayStage.show();

                   } catch (IOException e) {
                       e.printStackTrace();
                   }
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

        deckCardViews = new ImageView[7];
        deckCardViews[0] = deck1;
        deckCardViews[1] = deck2;
        deckCardViews[2] = deck3;
        deckCardViews[3] = deck4;
        deckCardViews[4] = deck5;
        deckCardViews[5] = deck6;
        deckCardViews[6] = deck7;

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
        switchAudioDescriptionHelper();
    }

    public void switchAudioDescriptionHelper()
    {
        // Change the image size according to its status
        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        Settings settings = DataHandler.getInstance().getSettings();
        settings.switchAudioDescription();
        if(settings.isAudioDescription()){
            tts.read("Enable audio description");
            audioDescriptionView.setFitHeight(70);
            audioDescriptionView.setFitWidth(70);
            audioDescriptionView.setY(-13);
            audioDescriptionView.setX(-10);
        }
        else {
            tts.read("Disable audio description");
            audioDescriptionView.setFitWidth(45);
            audioDescriptionView.setFitHeight(45);
            audioDescriptionView.setY(0);
            audioDescriptionView.setX(0);
        }
    }
    // Sound effects button listener
    public void switchSoundEffects(MouseEvent actionEvent) {
        switchSoundEffectsHelper();
    }

    public void switchSoundEffectsHelper()
    {
        Settings settings = DataHandler.getInstance().getSettings();

        // Change the image according to its status
        MediaPlayer mediaPlayer = SceneHandler.getInstance().getMediaPlayer();
        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        FileInputStream inputStream = null;
        settings.switchSoundEffects();
        try {
            if (settings.isSoundEffects()) {
                // Play music
                if(settings.isAudioDescription())
                    tts.read("Enable the sound effect");
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_ON_IMAGE);
                DataHandler dataHandler = DataHandler.getInstance();
                int ageNumber = dataHandler.getGame().ageNumber;

                Media sound;
                if (ageNumber == 1)
                    sound = new Media(new File(Constants.AGE_ONE_SOUND).toURI().toString());
                else if (ageNumber == 2)
                    sound = new Media(new File(Constants.AGE_TWO_SOUND).toURI().toString());
                else
                    sound = new Media(new File(Constants.AGE_THREE_SOUND).toURI().toString());

                mediaPlayer = new MediaPlayer(sound);
                SceneHandler.getInstance().setMediaPlayer(mediaPlayer);
                mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
                mediaPlayer.play();

            }
            else {
                // Stop music
                if(settings.isAudioDescription())
                    tts.read("Disable the sound effect");
                inputStream = new FileInputStream(Constants.SOUND_EFFECTS_OFF_IMAGE);
                if (mediaPlayer != null)
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
        exitHelper();
    }

    public void exitHelper()
    {
        disableItems();
        final boolean[] first = {true};
        Settings settings = DataHandler.getInstance().getSettings();
        Reader tts = AudioDescriptionHandler.getInstance().getReader();

        // Show confirmation pop up
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.setTitle("Exit Game");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.initOwner(exitView.getScene().getWindow());
        alert.setContentText("Do you want to exit the current game?");

        if(settings.isAudioDescription())
        {
            tts.read("Do you want to exit the current game? Press enter to say yes or no. No");
        }
        // Add options
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonNo, buttonYes);
        alert.getButtonTypes().forEach(buttonType -> {
            alert.getDialogPane().lookupButton(buttonType).focusedProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue && settings.isAudioDescription() && !first[0])
                    AudioDescriptionHandler.getInstance().getReader().read(buttonType.getText());
                first[0] = false;
            });
        });

        Optional<ButtonType> result = alert.showAndWait();
        enableItems();

        // Get the result
        if (result.get() == buttonYes){
            timeLine.stop(); // Stop requests
            DataHandler dataHandler = DataHandler.getInstance();

            if (dataHandler.getSettings().isSoundEffects()) {
                // Play menu music
                SceneHandler.getInstance().getMediaPlayer().stop();

                Media sound = new Media(new File(Constants.MENU_SOUND).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                SceneHandler.getInstance().setMediaPlayer(mediaPlayer);
                mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
                mediaPlayer.play();
            }
            dataHandler.setLobby(null);
            dataHandler.setGame(null);
            SceneHandler.getInstance().moveToMainMenu();

        } else {
            alert.close();
        }
    }
    // Focus into card when mouse entered
    public void focusIntoBoardCard(MouseEvent mouseEvent) {
        ImageView current = (ImageView) mouseEvent.getSource();
        if (current.getImage() != null){
            Glow glow = new Glow();
            glow.setLevel(0.4);
            focus.setEffect(glow);
            focus.setX(current.getX());
            focus.setY(current.getY());
            focus.setFitHeight(current.getFitHeight());
            focus.setFitWidth(current.getFitWidth());
            focus.setLayoutX(current.getLayoutX());
            focus.setLayoutY(current.getLayoutY());
            focus.setImage(current.getImage());
            focus.setScaleX(1.20);
            focus.setScaleY(1.20);
        }
    }

    // Focus out from card when mouse exited
    public void focusOutBoardCard(MouseEvent mouseEvent) {
        focus.setLayoutX(0);
        focus.setLayoutY(0);
        focus.setFitWidth(0);
        focus.setFitHeight(0);
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
        if (current.getImage() != null && !current.equals(selectedCardView)){
            Glow glow = new Glow();
            glow.setLevel(0.0);
            current.setEffect(glow);
            current.setScaleX(1);
            current.setScaleY(1);
        }
    }

    public void update(){
        // Check the music of the age
        if (ageNumber != DataHandler.getInstance().getGame().ageNumber) {
            ageNumber = DataHandler.getInstance().getGame().ageNumber;
            DataHandler dataHandler = DataHandler.getInstance();

            if (dataHandler.getSettings().isSoundEffects()) {
                Media sound;
                if (ageNumber == 1)
                    sound = new Media(new File(Constants.AGE_ONE_SOUND).toURI().toString());
                else if (ageNumber == 2)
                    sound = new Media(new File(Constants.AGE_TWO_SOUND).toURI().toString());
                else
                    sound = new Media(new File(Constants.AGE_THREE_SOUND).toURI().toString());

                SceneHandler.getInstance().getMediaPlayer().stop();
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                SceneHandler.getInstance().setMediaPlayer(mediaPlayer);
                mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
                mediaPlayer.play();
            }
        }

        // --------------------------------------
        // --------------------------------------
        // Set the board images
        Player[] players = DataHandler.getInstance().getGame().players;
        playerBoard.setImage(getBoardImage(players[0].board, 0));
        rightBoard.setImage(getBoardImage(players[1].board, 1));
        acrossBoard.setImage(getBoardImage(players[2].board, 2));
        leftBoard.setImage(getBoardImage(players[3].board, 3));
        // --------------------------------------
        // --------------------------------------

        // To display the cards at the hand of the player
        Card[] playerCards = players[0].playerCards;
        for (int i = 0; i < playerCards.length; i++){
            deckCardViews[i].setImage(getCardImage(playerCards[i]));
        }

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
        int length = cards.length;
        cardsInColorOrder = new Card[length]; // Main.game.players[0].board.cardCount

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

        for (int i = 0; i < brownCards.size(); i++){
            Card card = brownCards.get(i);
            cardViews[viewOrder].setImage(getCardImage(card));
            cardsInColorOrder[viewOrder] = card;
            viewOrder++;
        }

        for (int i = 0; i < grayCards.size(); i++){
            Card card = grayCards.get(i);
            cardViews[viewOrder].setImage(getCardImage(card));
            cardsInColorOrder[viewOrder] = card;
            viewOrder++;
        }

        for (int i = 0; i < greenCards.size(); i++){
            Card card = greenCards.get(i);
            cardViews[viewOrder].setImage(getCardImage(card));
            cardsInColorOrder[viewOrder] = card;
            viewOrder++;
        }

        for (int i = 0; i < redCards.size(); i++){
            Card card = redCards.get(i);
            cardViews[viewOrder].setImage(getCardImage(card));
            cardsInColorOrder[viewOrder] = card;
            viewOrder++;
        }

        for (int i = 0; i < blueCards.size(); i++){
            Card card = blueCards.get(i);
            cardViews[viewOrder].setImage(getCardImage(card));
            cardsInColorOrder[viewOrder] = card;
            viewOrder++;
        }

        for (int i = 0; i < yellowCards.size(); i++){
            Card card = yellowCards.get(i);
            cardViews[viewOrder].setImage(getCardImage(card));
            cardsInColorOrder[viewOrder] = card;
            viewOrder++;
        }

        for (int i = 0; i < purpleCards.size(); i++){
            Card card = purpleCards.get(i);
            cardViews[viewOrder].setImage(getCardImage(card));
            cardsInColorOrder[viewOrder] = card;
            viewOrder++;
        }

        //Update coins, tokens and wonder stage on the table, write turn and age number
        Game game = DataHandler.getInstance().getGame();

        ageLabel.setText("Age: " + game.ageNumber);
        turnLabel.setText("Turn: " + game.turnNumber);

        if((game.users != null) && (game.users[0]!= null))
        {
            username1.setText(game.users[0].userName);
            coin1.setText(Integer.toString(game.players[0].coin));
            token1.setText(Integer.toString(game.players[0].victoryPoints));
            stage1.setText(Integer.toString(game.players[0].board.wonderStage));
        }
        if((game.users != null) && (game.users.length > 1 )&& (game.users[1]!= null))
        {
            username2.setText(game.users[1].userName);
            coin2.setText(Integer.toString(game.players[1].coin));
            token2.setText(Integer.toString(game.players[1].victoryPoints));
            stage2.setText(Integer.toString(game.players[1].board.wonderStage));
        }
        if((game.users != null) &&  (game.users.length > 2) &&(game.users[2]!= null))
        {
            username3.setText(game.users[2].userName);
            coin3.setText(Integer.toString(game.players[2].coin));
            token3.setText(Integer.toString(game.players[2].victoryPoints));
            stage3.setText(Integer.toString(game.players[2].board.wonderStage));
        }
        if((game.users != null) && (game.users.length > 3) && (game.users[3]!= null))
        {
            username4.setText(game.users[3].userName);
            coin4.setText(Integer.toString(game.players[3].coin));
            token4.setText(Integer.toString(game.players[3].victoryPoints));
            stage4.setText(Integer.toString(game.players[3].board.wonderStage));
        }
    }

    // Board listeners
    public void showOtherPlayersCards(MouseEvent mouseEvent) {
        Card [] cards;
        String username;
        // Get the players cards
        ImageView boardView = (ImageView)mouseEvent.getSource();
        Game game = DataHandler.getInstance().getGame();
        if (boardView.equals(rightBoard)) {
            cards = game.players[1].board.cards;
            username = game.users[1].userName;
        }
        else if (boardView.equals(acrossBoard)) {
            cards = game.players[2].board.cards;
            username = game.users[2].userName;
        }
        else if (boardView.equals(leftBoard)) {
            cards = game.players[3].board.cards;
            username = game.users[3].userName;
        }
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
            Label usernameLabel = (Label) root.getChildren().get(1);

            usernameLabel.setText(username);
            backgroundView.setImage(backgroundImage);

            boardScene = new Scene(root);
            boardStage = new Stage();
            boardStage.setScene(boardScene);
            boardStage.initStyle(StageStyle.UTILITY);
            boardStage.setAlwaysOnTop(true);
            boardStage.initOwner(focus.getScene().getWindow());
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
                ((ImageView)root.getChildren().get(viewOrder+2)).setImage(getCardImage(redCards.get(i)));
                viewOrder++;
            }

            for (int i = 0; i < brownCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+2)).setImage(getCardImage(brownCards.get(i)));
                viewOrder++;
            }

            for (int i = 0; i < grayCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+2)).setImage(getCardImage(grayCards.get(i)));
                viewOrder++;
            }

            for (int i = 0; i < blueCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+2)).setImage(getCardImage(blueCards.get(i)));
                viewOrder++;
            }

            for (int i = 0; i < yellowCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+2)).setImage(getCardImage(yellowCards.get(i)));
                viewOrder++;
            }

            for (int i = 0; i < purpleCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+2)).setImage(getCardImage(purpleCards.get(i)));
                viewOrder++;
            }

            for (int i = 0; i < greenCards.size(); i++){
                ((ImageView)root.getChildren().get(viewOrder+2)).setImage(getCardImage(greenCards.get(i)));
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

    public void nextTurnAction(ActionEvent event)
    {
        update();
    }

    public void wonderAction(ActionEvent event)
    {
        DataHandler.getInstance().getGame().ageNumber = 3;

        update();
    }

    public void structureAction(ActionEvent event)
    {
        DataHandler.getInstance().getGame().ageNumber = 2;

        update();
    }

    public void discardAction(ActionEvent event)
    {
        DataHandler.getInstance().getGame().ageNumber = 1;

        update();
    }

    public void clickToCard(MouseEvent mouseEvent) {
        ImageView currentView = (ImageView) mouseEvent.getSource();
        int cardIndex = 0;
        Card[] deckCards = DataHandler.getInstance().getGame().players[0].playerCards;
        for (; cardIndex < deckCards.length; cardIndex++){
            if (deckCards[cardIndex] != null && currentView.equals(deckCardViews[cardIndex])){
                break;
            }
        }
        selectTheCard(cardIndex);

        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        tts.read(deckCards[cardIndex].cardName);
    }

    private void selectTheCard(int cardIndex){
        if (selectedCardView != null && selectedCardView.getImage() != null) {
            Glow glow = new Glow();
            glow.setLevel(0.0);
            selectedCardView.setEffect(glow);
            selectedCardView.setScaleX(1);
            selectedCardView.setScaleY(1);
        }
        Card[] deckCards = DataHandler.getInstance().getGame().players[0].playerCards;
        selectedCardView = deckCardViews[cardIndex];
        Glow glow = new Glow();
        glow.setLevel(0.4);
        selectedCardView.setEffect(glow);
        selectedCardView.setScaleX(1.30);
        selectedCardView.setScaleY(1.30);
        selectedCard = deckCards[cardIndex];
        selectedCardIndex = cardIndex;
    }

    public void clickBoardCard(MouseEvent mouseEvent) {
        int cardIndex = 0;
        Image currentImage = ((ImageView)mouseEvent.getSource()).getImage();

        for (; cardIndex < cardsInColorOrder.length; cardIndex++){
            Image image = cardViews[cardIndex].getImage();
            if (image != null && image.equals(currentImage))
                break;
        }

        if (cardIndex != cardsInColorOrder.length) {
            Reader tts = AudioDescriptionHandler.getInstance().getReader();
            tts.read(cardsInColorOrder[cardIndex].cardName);
        }
    }

    // Error message
    private void showErrorMessage(String errorMsg){
        if (showError) {
            disableItems();

            final boolean[] first = {true};
            DataHandler dataHandler = DataHandler.getInstance();

            if (dataHandler.getSettings().isAudioDescription()) {
                AudioDescriptionHandler.getInstance().getReader().read("Error. " + errorMsg + " Press enter to say OK");
            }

            showError = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(errorMsg);
            alert.initOwner(nextTurnButton.getScene().getWindow());
            alert.getButtonTypes().forEach(buttonType -> {
                alert.getDialogPane().lookupButton(buttonType).focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue && dataHandler.getSettings().isAudioDescription() && !first[0])
                        AudioDescriptionHandler.getInstance().getReader().read(buttonType.getText());
                    first[0] = false;
                });
            });

            Optional<ButtonType> result = alert.showAndWait();
            showError = true;
            enableItems();
        }
    }

    private void disableItems(){
        nextTurnButton.setDisable(true);
        discardButton.setDisable(true);
        structureButton.setDisable(true);
        wonderButton.setDisable(true);
    }

    private void enableItems(){
        nextTurnButton.setDisable(false);
        discardButton.setDisable(false);
        structureButton.setDisable(false);
        wonderButton.setDisable(false);
    }



}
