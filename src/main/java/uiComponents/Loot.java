package uiComponents;

import audioDescription.AudioDescriptionHandler;
import audioDescription.DescriptionReader;
import audioDescription.Reader;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import models.*;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Loot implements Initializable {
    @FXML
    public Button coinButton;
    @FXML
    public Button tokenButton;
    @FXML
    public Button cardButton;
    @FXML
    public Label usernameLabel;
    @FXML
    public ImageView card1;
    @FXML
    public ImageView card2;
    @FXML
    public ImageView card3;
    @FXML
    public ImageView card4;
    @FXML
    public ImageView card5;
    @FXML
    public ImageView card6;
    @FXML
    public ImageView card7;
    @FXML
    public ImageView card8;
    @FXML
    public ImageView card9;
    @FXML
    public ImageView card10;
    @FXML
    public ImageView card11;
    @FXML
    public ImageView card12;
    @FXML
    public ImageView card13;
    @FXML
    public ImageView card14;
    @FXML
    public ImageView card15;
    @FXML
    public ImageView card16;
    @FXML
    public ImageView card17;
    @FXML
    public ImageView card18;
    @FXML
    public ImageView card19;
    @FXML
    public ImageView card20;
    @FXML
    public ImageView card21;
    @FXML
    private ImageView focus;
    @FXML
    public ImageView coinLoot;
    @FXML
    public  ImageView militaryTokenLoot;
    boolean first = true;
    Image currentImage; // seçilen kartı bunda tut

    private Card[] cards;
    private int focusedCardInBoardIndex;



    private ImageView[] cardViews;
    private DescriptionReader tts = new DescriptionReader();

    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #b80028; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #b80028; -fx-opacity: 0.85;";

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
        Reader tts = AudioDescriptionHandler.getInstance().getReader();
        Settings settings = DataHandler.getInstance().getSettings();
        if(settings.isAudioDescription())
        {
            tts.read( "Take a loot. \n take the card ");
        }

        cardViews = new ImageView[21];
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
        cardViews[19] = card20;
        cardViews[20] = card21;

        // Start sending requests
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

        SceneHandler.getInstance().getStagePopup().setOnCloseRequest(e -> {
            timeLine.stop();
        });

        focusedCardInBoardIndex = -1;


        try {
            Game game = DataHandler.getInstance().getGame();
            FileInputStream inputStream = new FileInputStream(Constants.COIN3_IMAGE);
            Image coin = new Image(inputStream);
            coinLoot.setImage(coin);
            String username = usernameLabel.getText();
            int index = 0;
            for(int i = 0; i < game.players.length; i++)
            {
                if(username.equals(game.players[i].name))
                    index = i;
            }

            if(game.ageNumber ==1)
            {
                FileInputStream inputStream2 = new FileInputStream(Constants.TOKEN_IMAGE);
                Image token = new Image(inputStream2);
                militaryTokenLoot.setImage(token);
                tokenButton.setText("Take 1 Military Token ");
                if(game.players[index].coin == 0)
                    coinButton.setDisable(true);
                else if(game.players[index].coin < 3)
                {
                    coinButton.setDisable(false);
                    coinButton.setText("Take " +  game.players[index].coin + "s");
                }
                else
                {
                    coinButton.setDisable(false);
                    coinButton.setText("Take 3 coins");
                }
            }
            else if(game.ageNumber ==2)
            {
                FileInputStream inputStream2 = new FileInputStream(Constants.TOKEN3_IMAGE);
                Image token = new Image(inputStream2);
                militaryTokenLoot.setImage(token);
                tokenButton.setText("Take 3 Military Tokens ");
                if(game.players[index].coin == 0)
                    coinButton.setDisable(true);
                else if(game.players[index].coin < 4)
                {
                    coinButton.setDisable(false);
                    coinButton.setText("Take " +  game.players[index].coin + "coins");
                }
                else
                {
                    coinButton.setDisable(false);
                    coinButton.setText("Take 4 coins");
                }
            }
            else
            {
                FileInputStream inputStream2 = new FileInputStream(Constants.TOKEN5_IMAGE);
                Image token = new Image(inputStream2);
                militaryTokenLoot.setImage(token);
                tokenButton.setText("Take 5 Military Tokens ");
                if(game.players[index].coin == 0)
                    coinButton.setDisable(true);
                else if(game.players[index].coin < 5)
                {
                    coinButton.setDisable(false);
                    coinButton.setText("Take " +  game.players[index].coin + "coins");
                }
                else
                {
                    coinButton.setDisable(false);
                    coinButton.setText("Take 5 coins");
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Setting the mouse entered and exited listeners for hover effect
        coinButton.setOnMouseEntered(e -> { coinButton.setStyle(HOVERED_BUTTON_STYLE); });
        coinButton.setOnMouseExited(e -> coinButton.setStyle(IDLE_BUTTON_STYLE));
        cardButton.setOnMouseEntered(e -> { cardButton.setStyle(HOVERED_BUTTON_STYLE); });
        cardButton.setOnMouseExited(e -> cardButton.setStyle(IDLE_BUTTON_STYLE));
        tokenButton.setOnMouseEntered(e -> { tokenButton.setStyle(HOVERED_BUTTON_STYLE); });
        tokenButton.setOnMouseExited(e -> tokenButton.setStyle(IDLE_BUTTON_STYLE));


        coinButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(coinButton.getText());
                coinButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                coinButton.setStyle(IDLE_BUTTON_STYLE);
            first = false;
        });
        cardButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(cardButton.getText());
                cardButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                cardButton.setStyle(IDLE_BUTTON_STYLE);
            first = false;
        });
        tokenButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue){
                if (settings.isAudioDescription() && !first)
                    tts.read(tokenButton.getText());
                tokenButton.setStyle(HOVERED_BUTTON_STYLE);
            }
            else
                tokenButton.setStyle(IDLE_BUTTON_STYLE);
            first = false;
        });



        focusedCardInBoardIndex = -1;

        SceneHandler.getInstance().getStagePopup().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                if (keyCode.equals(KeyCode.ESCAPE))
                    SceneHandler.getInstance().getStagePopup().close();

                DataHandler dataHandler = DataHandler.getInstance();
                Settings settings = dataHandler.getSettings();
                Game game = DataHandler.getInstance().getGame();
                String username = usernameLabel.getText();
                Player[] players = DataHandler.getInstance().getGame().players;
                int userIndex = -1;
                for (int i = 0; i < players.length; i++) {
                    if (players[i] != null && players[i].name.equals(username)) {
                        userIndex = i;
                    }
                }
                if (dataHandler.getSettings().isAudioDescription() && userIndex != -1) {
                    if(keyCode.equals(KeyCode.R))
                    {
                        if (focusedCardInBoardIndex == game.players[userIndex].board.cards.length - 1)
                        {
                            focusedCardInBoardIndex = -1;
                        }
                        focusedCardInBoardIndex++;
                        if (settings.isAudioDescription())
                            tts.read(cards[focusedCardInBoardIndex].name);
                    }
                    else if(keyCode.equals(KeyCode.E))
                    {
                        if (focusedCardInBoardIndex <= 0) {
                            focusedCardInBoardIndex = game.players[userIndex].board.cards.length;
                        }
                        focusedCardInBoardIndex--;
                        if (settings.isAudioDescription())
                            tts.read(cards[focusedCardInBoardIndex].name);
                    }
                }
                event.consume();
            }

        });
    }

    private void update() {
        DataHandler dataHandler = DataHandler.getInstance();
        String username = usernameLabel.getText();
        Player[] players = DataHandler.getInstance().getGame().players;
        int userIndex = -1;
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null && players[i].name.equals(username)) {
                userIndex = i;
            }
        }
        if (userIndex != -1) {
            cards = dataHandler.getGame().players[userIndex].board.cards;
            for(int i= 0; i < cards.length; i++ )
            {
                cardViews[i].setImage(getCardImage(cards[i]));
            }
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

    public void focusIntoBoardCard(MouseEvent mouseEvent) {
        ImageView current = (ImageView) mouseEvent.getSource();
        if (current.getImage() != null){
            Glow glow = new Glow();
            glow.setLevel(0.4);
            focus.setEffect(glow);
            focus.setFitHeight(current.getFitHeight());
            focus.setFitWidth(current.getFitWidth());
            focus.setLayoutX(current.getLayoutX());
            focus.setLayoutY(current.getLayoutY());
            focus.setImage(current.getImage());
            focus.setScaleX(1.40);
            focus.setScaleY(1.40);

        }
    }

    public void clickBoardCard(MouseEvent mouseEvent) {
        int cardIndex = 0;
        currentImage = ((ImageView)mouseEvent.getSource()).getImage();
        String username = usernameLabel.getText();
        Game game = DataHandler.getInstance().getGame();
        int index = 0;
        for(int i = 0; i < game.players.length; i++)
        {
            if(username.equals(game.players[i].name))
               index = i;
        }

        for (; cardIndex < game.players[index].cards.length; cardIndex++){
            Image image = cardViews[cardIndex].getImage();
            if (image != null && image.equals(currentImage))
                break;
        }

        if (cardIndex != game.players[index].cards.length && DataHandler.getInstance().getSettings().isAudioDescription()) {
            Reader tts = AudioDescriptionHandler.getInstance().getReader();
            tts.read(game.players[index].cards[cardIndex].name);
        }
    }

    public void cardLoot(ActionEvent actionEvent) {
       //current image ı burda kullan
    }
    public void coinLoot(ActionEvent actionEvent) {
    }
    public void tokenLoot(ActionEvent actionEvent) {
    }

    public void focusOutBoardCard(MouseEvent mouseEvent) {
        focus.setLayoutX(0);
        focus.setLayoutY(0);
        focus.setFitWidth(0);
        focus.setFitHeight(0);
        focus.setImage(null);
    }
}
