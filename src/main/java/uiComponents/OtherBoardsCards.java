package uiComponents;

import audioDescription.DescriptionReader;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.*;
import utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OtherBoardsCards implements Initializable {
    @FXML
    public Button commerceButton;
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

    // To send a request at every second
    Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            // Server request and update the game
            update();

        }
    }));

    private ImageView[] cardViews;
    private DescriptionReader tts = new DescriptionReader();
    private Card[] cards;
    private Card[] cardsInColorOrder;

    private int focusedCardInBoardIndex;


    // Button colors for hovered and not
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: #b80028; -fx-opacity: 1;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: #b80028; -fx-opacity: 0.85;";

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        // Setting the mouse entered and exited listeners for hover effect
        commerceButton.setOnMouseEntered(e -> { commerceButton.setStyle(HOVERED_BUTTON_STYLE); });
        commerceButton.setOnMouseExited(e -> commerceButton.setStyle(IDLE_BUTTON_STYLE));

        // Start sending requests
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

        SceneHandler.getInstance().getStagePopup().setOnCloseRequest(e -> {
            timeLine.stop();
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
                User[] users = DataHandler.getInstance().getGame().users;
                int userIndex = -1;
                for (int i = 0; i < users.length; i++) {
                    if (users[i] != null && users[i].userName.equals(username)) {
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
                            tts.read(cards[focusedCardInBoardIndex].cardName);
                    }
                    else if(keyCode.equals(KeyCode.E))
                    {
                        if (focusedCardInBoardIndex <= 0) {
                            focusedCardInBoardIndex = game.players[userIndex].board.cards.length;
                        }
                        focusedCardInBoardIndex--;
                        if (settings.isAudioDescription())
                            tts.read(cards[focusedCardInBoardIndex].cardName);
                    }
                }
                event.consume();
            }

        });
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

    public void commerce(ActionEvent actionEvent) {
        timeLine.stop();
        SceneHandler.getInstance().showCommerceScene();
    }

    public void focusOutBoardCard(MouseEvent mouseEvent) {
        focus.setLayoutX(0);
        focus.setLayoutY(0);
        focus.setFitWidth(0);
        focus.setFitHeight(0);
        focus.setImage(null);
    }

    private void update() {
        DataHandler dataHandler = DataHandler.getInstance();
        String username = usernameLabel.getText();
        User[] users = DataHandler.getInstance().getGame().users;
        int userIndex = -1;
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].userName.equals(username)) {
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
}


