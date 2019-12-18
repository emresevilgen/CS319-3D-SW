package uiComponents;

import audioDescription.DescriptionReader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.DataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class OtherBoardsCards implements Initializable {
    @FXML
    private ImageView focus;
    private int rightBoardCardsNo = 0;
    private DescriptionReader tts = new DescriptionReader();

    // Initializing function
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*GameScene.boardScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                if(keyCode.equals(KeyCode.RIGHT) )
                {
                    //if in içinde boarddaki  card sayısını compare etmek için bunu kullan Main.game.players[1].board.cardCount,bunu da oyun sırasında arttır
                    if(rightBoardCardsNo == 3)//değiştir
                        {
                            rightBoardCardsNo = -1;
                        }
                    rightBoardCardsNo++;
                    tts.read(DataHandler.getInstance().getGame().players[1].board.cards[0].cardName);
                }
                else if(keyCode.equals(KeyCode.LEFT))
                {
                    //if in içinde boarddaki  card sayısını compare etmek için bunu kullan Main.game.players[1].board.cardCount,bunu da oyun sırasında arttır
                    if(rightBoardCardsNo == 0)
                    {
                        rightBoardCardsNo = 4;//değiştir
                    }
                    rightBoardCardsNo--;
                    tts.read(DataHandler.getInstance().getGame().players[1].board.cards[0].cardName);
                }
                event.consume();
            }
        }
        );*/
    }

    // Increase scale of the card mouse entered
    public void setScaleOn(MouseEvent mouseEvent) {
        ImageView current = (ImageView) mouseEvent.getSource();
        if (current.getImage() != null){
            Glow glow = new Glow();
            glow.setLevel(0.4);
            current.setEffect(glow);
            current.setScaleX(1.60);
            current.setScaleY(1.60);
        }
    }

    // Decrease scale of the card mouse exited
    public void setScaleOff(MouseEvent mouseEvent) {
        ImageView current = (ImageView) mouseEvent.getSource();
        if (current.getImage() != null){
            Glow glow = new Glow();
            glow.setLevel(0.0);
            current.setEffect(glow);
            current.setScaleX(1);
            current.setScaleY(1);
        }
    }
}


