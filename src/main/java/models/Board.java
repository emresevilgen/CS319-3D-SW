package models;


public class Board {
    Card[] cards;
    String wonderId;
    int wonderStage;
    String wonderName;

    public Board()
    {
        cards = new Card[19]; // loottan dolayı 19 da olabilir
        //wonderId = ;
        //wonderStage = ;
        //wonderName = ;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public String getWonderId() {
        return wonderId;
    }

    public void setWonderId(String wonderId) {
        this.wonderId = wonderId;
    }

    public int getWonderStage() {
        return wonderStage;
    }

    public void setWonderStage(int wonderStage) {
        this.wonderStage = wonderStage;
    }

    public String getWonderName() {
        return wonderName;
    }

    public void setWonderName(String wonderName) {
        this.wonderName = wonderName;
    }
}
