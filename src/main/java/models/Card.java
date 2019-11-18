package models;


import com.google.gson.annotations.SerializedName;

public class Card {
    @SerializedName("cardDescription")
    public String cardDescription;

    @SerializedName("cardColor")
    public String cardColor;

    @SerializedName("cardId")
    public String cardId;

    @SerializedName("cardName")
    public String cardName;

    @Override
    public String toString() {
        return "Card{" +
                "cardDescription='" + cardDescription + '\'' +
                ", cardColor='" + cardColor + '\'' +
                ", cardId='" + cardId + '\'' +
                ", cardName='" + cardName + '\'' +
                '}';
    }

}
