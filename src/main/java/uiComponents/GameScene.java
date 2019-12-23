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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
import javafx.util.Duration;
import models.*;
import utils.Constants;

import java.io.*;
import java.net.URL;
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
    int playerNumberOfCards;
    private int boardIndex;

    // Image views for board
    private ImageView[] cardViews;
    private ImageView[] deckCardViews;

    int ageNumber = 0;
    int turnNumber = 0;


    private String keyInput = "";

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

        // Setting the mouse entered and exited listeners for hover effect
        nextTurnButton.setOnMouseEntered(e -> { nextTurnButton.setStyle(HOVERED_BUTTON_STYLE); });
        nextTurnButton.setOnMouseExited(e -> nextTurnButton.setStyle(IDLE_BUTTON_STYLE));
        discardButton.setOnMouseEntered(e -> { discardButton.setStyle(HOVERED_BUTTON_STYLE); });
        discardButton.setOnMouseExited(e -> discardButton.setStyle(IDLE_BUTTON_STYLE));
        structureButton.setOnMouseEntered(e -> { structureButton.setStyle(HOVERED_BUTTON_STYLE); });
        structureButton.setOnMouseExited(e -> structureButton.setStyle(IDLE_BUTTON_STYLE));
        wonderButton.setOnMouseEntered(e -> { wonderButton.setStyle(HOVERED_BUTTON_STYLE); });
        wonderButton.setOnMouseExited(e -> wonderButton.setStyle(IDLE_BUTTON_STYLE));

        Game game = DataHandler.getInstance().getGame();
        if(game.players[0].board.stage == 3)
            wonderButton.setDisable(true);

        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        Settings settings = DataHandler.getInstance().getSettings();

        firstTime = true;
        nextTurnButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if(settings.isAudioDescription() && !firstTime)
                    tts.read(nextTurnButton.getText());
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

        //Change next turn button to next age
        if(game.turnNumber == 6)
            nextTurnButton.setText("Next Age");
        else
            nextTurnButton.setText("Next Turn ");

        //-------------------------------------
        selectedCardIndex = 0;
        focusedCardInBoardIndex = 0;

        Card[] cards = DataHandler.getInstance().getGame().players[0].board.cards;

        SceneHandler.getInstance().getStageMain().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {
               KeyCode keyCode = event.getCode();
               Game game = DataHandler.getInstance().getGame();

               //Cards in the deck
               if (keyCode.equals(KeyCode.C)){
                   keyInput = "deck";
                   if (settings.isAudioDescription())
                       tts.read("The cards in the deck." + game.players[0].cards[selectedCardIndex].name);
                   selectTheCard(selectedCardIndex);
               }
               //Cards in the board
               else if (keyCode.equals(KeyCode.S)){
                   keyInput = "boardCards";
                   if(settings.isAudioDescription())
                       tts.read("The cards in the board." + cards[focusedCardInBoardIndex].name);
               }
               //Cards in the left player's board
               else if (keyCode.equals(KeyCode.A)){
                   keyInput = "leftBoardCards";
                   if(settings.isAudioDescription())
                       tts.read("The cards in the left board.");

                   if (game.players.length  == 3) {
                       Card[] cards = game.players[2].board.cards;
                       String username = game.players[2].name;
                       showOtherPlayerCardsHelper(cards, username);
                   }
                   else if (game.players.length  == 4) {
                       Card[] cards = game.players[3].board.cards;
                       String username = game.players[3].name;
                       showOtherPlayerCardsHelper(cards, username);
                   }
               }
               //Cards in the across player's board
               else if (keyCode.equals(KeyCode.W)) {
                   if (game.players.length  > 3) {
                       keyInput = "acrossBoardCards";
                       if (settings.isAudioDescription())
                           tts.read("The cards in the across board.");
                       Card[] cards = game.players[2].board.cards;
                       String username = game.players[2].name;
                       showOtherPlayerCardsHelper(cards, username);
                   }
               }
               //Cards in the right player's board
               else if (keyCode.equals(KeyCode.D)) {
                   keyInput =  "rightBoardCards";
                   if(settings.isAudioDescription())
                       tts.read("The cards in the right board.");
                   Card[] cards = game.players[1].board.cards;
                   String username = game.players[1].name;
                   showOtherPlayerCardsHelper(cards, username);
               }
               else if (keyCode.equals(KeyCode.B)){
                   keyInput = "boards";
                   boardIndex = 0;
                   if(settings.isAudioDescription())
                       tts.read("The boards." + game.players[boardIndex].board.name);
               }
               else if(keyCode.equals(KeyCode.R)) {
                   if (keyInput.equals("boardCards")) {
                       if (focusedCardInBoardIndex == game.players[0].board.cards.length - 1)
                       {
                           focusedCardInBoardIndex = -1;
                       }
                       focusedCardInBoardIndex++;
                       if (settings.isAudioDescription())
                           tts.read(cards[focusedCardInBoardIndex].name);
                   }
                   else if (keyInput.equals("deck")){
                       if(selectedCardIndex == (game.players[0].cards.length - 1) )
                       {
                           selectedCardIndex = -1;
                       }
                       selectedCardIndex++;
                       if(settings.isAudioDescription())
                           tts.read(game.players[0].cards[selectedCardIndex].name);
                       selectTheCard(selectedCardIndex);
                   }
                   else if (keyInput.equals("boards")){
                       if(boardIndex == game.players.length - 1)
                       {
                           boardIndex = -1;
                       }
                       boardIndex++;
                       if(settings.isAudioDescription())
                           tts.read(game.players[boardIndex].board.name);
                   }
               }
               else if (keyCode.equals(KeyCode.E)) {
                   if (keyInput.equals("boardCards")){
                       if (focusedCardInBoardIndex == 0) {
                           focusedCardInBoardIndex = game.players[0].board.cards.length;
                       }
                       focusedCardInBoardIndex--;
                       if (settings.isAudioDescription())
                           tts.read(cards[focusedCardInBoardIndex].name);
                   }
                   else if (keyInput.equals("deck")){
                       if(selectedCardIndex == 0)
                       {
                           selectedCardIndex = game.players[0].cards.length;
                       }
                       selectedCardIndex--;
                       if(settings.isAudioDescription())
                           tts.read(game.players[0].cards[selectedCardIndex].name);
                       selectTheCard(selectedCardIndex);
                   }
                   else if (keyInput.equals("boards")){
                       if(boardIndex == 0)
                       {
                           boardIndex = game.players.length;
                       }
                       boardIndex--;
                       if(settings.isAudioDescription())
                           tts.read(game.players[boardIndex].board.name);
                   }
               }
               else if(keyCode.equals(KeyCode.I)){
                    if (settings.isAudioDescription()){
                        String text = "Age is " + game.ageNumber + " and turn is " + game.turnNumber+ " ";
                        for (int i = 0; i < game.players.length; i++) {
                            if (game.players[i] != null) {
                                text = text + game.players[i].name + " has ";
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

                                text = text + " and the wonder stage is " + player.board.stage;
                            }
                        }
                        tts.read(text);
                    }
               }
               else if(keyCode.equals(KeyCode.N))
               {
                   switchAudioDescriptionHelper();
               }
               else if(keyCode.equals(KeyCode.M))
               {
                   switchSoundEffectsHelper();
               }
               else if(keyCode.equals(KeyCode.ESCAPE))
               {
                   exitHelper();
               }
               else if(keyCode.equals(keyCode.H))
               {
                   //SceneHandler.getInstance().showHowToPlayScene();
                   //showLootScreen("user2");
                   showBabylonScreen();
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

        // Initialize the button images and their status
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

        if (players.length == 4) {
            playerBoard.setImage(getBoardImage(players[0].board, 0));
            rightBoard.setImage(getBoardImage(players[1].board, 1));
            acrossBoard.setImage(getBoardImage(players[2].board, 2));
            leftBoard.setImage(getBoardImage(players[3].board, 3));
        }
        else if (players.length == 4) {
            playerBoard.setImage(getBoardImage(players[0].board, 0));
            rightBoard.setImage(getBoardImage(players[1].board, 1));
            leftBoard.setImage(getBoardImage(players[2].board, 2));
        }
        // --------------------------------------
        // --------------------------------------

        // To display the cards at the hand of the player
        Card[] playerCards = players[0].cards;
        for (int i = 0; i < playerCards.length; i++){
            deckCardViews[i].setImage(getCardImage(playerCards[i]));
        }

        // To classify the cards at the users board
        Card[] cards = players[0].board.cards;

        for (int i = 0; i < cards.length; i++){
            cardViews[i].setImage(getCardImage(cards[i]));
        }

        //Update coins, tokens and wonder stage on the table, write turn and age number
        Game game = DataHandler.getInstance().getGame();

        ageLabel.setText("Age: " + game.ageNumber);
        turnLabel.setText("Turn: " + game.turnNumber);

        if((game.players != null) && (game.players[0]!= null))
        {
            username1.setText(game.players[0].name);
            coin1.setText(Integer.toString(game.players[0].coin));
            token1.setText(Integer.toString(game.players[0].victoryPoints));
            stage1.setText(Integer.toString(game.players[0].board.stage));
        }
        if((game.players != null) && (game.players.length > 1 )&& (game.players[1]!= null))
        {
            username2.setText(game.players[1].name);
            coin2.setText(Integer.toString(game.players[1].coin));
            token2.setText(Integer.toString(game.players[1].victoryPoints));
            stage2.setText(Integer.toString(game.players[1].board.stage));
        }
        if((game.players != null) &&  (game.players.length > 2) &&(game.players[2]!= null))
        {
            username3.setText(game.players[2].name);
            coin3.setText(Integer.toString(game.players[2].coin));
            token3.setText(Integer.toString(game.players[2].victoryPoints));
            stage3.setText(Integer.toString(game.players[2].board.stage));
        }
        if((game.players != null) && (game.players.length > 3) && (game.players[3]!= null))
        {
            username4.setText(game.players[3].name);
            coin4.setText(Integer.toString(game.players[3].coin));
            token4.setText(Integer.toString(game.players[3].victoryPoints));
            stage4.setText(Integer.toString(game.players[3].board.stage));
        }
        else {
            username4.setText("");
            coin4.setText("");
            token4.setText("");
            stage4.setText("");
        }
    }

    // Board listeners
    public void showOtherPlayersCards(MouseEvent mouseEvent) {

        String username;
        // Get the players cards
        ImageView boardView = (ImageView)mouseEvent.getSource();
        Game game = DataHandler.getInstance().getGame();
        Card[] cards = null;

        if (game.players.length == 4) {
            if (boardView.equals(rightBoard)) {
                cards = game.players[1].board.cards;
                username = game.players[1].name;
            } else if (boardView.equals(acrossBoard)) {
                cards = game.players[2].board.cards;
                username = game.players[2].name;
            } else if (boardView.equals(leftBoard)) {
                cards = game.players[3].board.cards;
                username = game.players[3].name;
            } else
                return;
        }
        else {
            if (boardView.equals(rightBoard)) {
                cards = game.players[1].board.cards;
                username = game.players[1].name;
            } else if (boardView.equals(leftBoard)) {
                cards = game.players[2].board.cards;
                username = game.players[2].name;
            } else
                return;
        }
        showOtherPlayerCardsHelper(cards, username);

    }

    public void showLootScreen(String username)
    {
        Card [] cards;
        // Get the players cards
        Game game = DataHandler.getInstance().getGame();
        if (username.equals(game.players[1].name)) {
            cards = game.players[1].board.cards;
        }
        else if (username.equals(game.players[2].name)) {
            cards = game.players[2].board.cards;
        }
        else if (username.equals(game.players[3].name)) {
            cards = game.players[3].board.cards;
        }
        else
            return;
        try {
            SceneHandler.getInstance().showLootScene();
            Stage stage = SceneHandler.getInstance().getStagePopup();
            AnchorPane root = (AnchorPane) stage.getScene().getRoot();
            Label usernameLabel = (Label) root.getChildren().get(1);
            usernameLabel.setText(username);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showBabylonScreen()
    {
        // Get the players cards
        Game game = DataHandler.getInstance().getGame();
        try {
            SceneHandler.getInstance().showBabylonScene();
            Stage stage = SceneHandler.getInstance().getStagePopup();
            AnchorPane root = (AnchorPane) stage.getScene().getRoot();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showOtherPlayerCardsHelper(Card[] cards, String username) {
        try {
            SceneHandler.getInstance().showOtherBoardsCardsScene();
            Stage stage = SceneHandler.getInstance().getStagePopup();
            AnchorPane root = (AnchorPane) stage.getScene().getRoot();
            Label usernameLabel = (Label) root.getChildren().get(1);
            usernameLabel.setText(username);

            for (int i = 0; i < cards.length; i++) {
                ((ImageView) root.getChildren().get(i + 2)).setImage(getCardImage(cards[i]));
            }

        }
        catch (Exception e){
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
            String cardName = card.name;
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
            String boardName = board.name;
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
        Card[] deckCards = DataHandler.getInstance().getGame().players[0].cards;
        for (; cardIndex < deckCards.length; cardIndex++){
            if (deckCards[cardIndex] != null && currentView.equals(deckCardViews[cardIndex])){
                break;
            }
        }
        selectTheCard(cardIndex);
        Settings settings = DataHandler.getInstance().getSettings();
        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        if (settings.isAudioDescription())
            tts.read(deckCards[cardIndex].name);
    }

    private void selectTheCard(int cardIndex){
        if (selectedCardView != null && selectedCardView.getImage() != null) {
            Glow glow = new Glow();
            glow.setLevel(0.0);
            selectedCardView.setEffect(glow);
            selectedCardView.setScaleX(1);
            selectedCardView.setScaleY(1);
        }
        Card[] deckCards = DataHandler.getInstance().getGame().players[0].cards;
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

        Card[] cards = DataHandler.getInstance().getGame().players[0].board.cards;

        for (; cardIndex < cards.length; cardIndex++){
            Image image = cardViews[cardIndex].getImage();
            if (image != null && image.equals(currentImage))
                break;
        }

        if (cardIndex != cards.length && DataHandler.getInstance().getSettings().isAudioDescription()) {
            Reader tts = AudioDescriptionHandler.getInstance().getReader();
            tts.read(cards[cardIndex].name);
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
