package models;


import javafx.scene.paint.Color;

public class Card {
    String cardDescription;
    Color cardColor;
    String cardId;
    String cardName;

    public Card()
    {
        //cardDescription = ;
        //cardColor = ;
        //cardId = ;
        //cardName = ;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public Color getCardColor() {
        return cardColor;
    }

    public void setCardColor(Color cardColor) {
        this.cardColor = cardColor;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
