package models;


import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Board {
    @SerializedName("cards")
    public Card[] cards;

    @SerializedName("wonderId")
    public String wonderId;

    @SerializedName("wonderStage")
    public int wonderStage;

    @SerializedName("wonderName")
    public String wonderName;

    @Override
    public String toString() {
        return "Board{" +
                "cards=" + Arrays.toString(cards) +
                ", wonderId='" + wonderId + '\'' +
                ", wonderStage=" + wonderStage +
                ", wonderName='" + wonderName + '\'' +
                '}';
    }

}
