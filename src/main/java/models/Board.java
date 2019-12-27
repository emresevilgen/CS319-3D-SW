package models;


import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Board {
    @SerializedName("cards")
    public Card[] cards;

    @SerializedName("id")
    public String id;

    @SerializedName("description")
    public String description;

    @SerializedName("stage")
    public int stage;

    @SerializedName("name")
    public String name;

    @Override
    public String toString() {
        return "Board{" +
                "cards=" + Arrays.toString(cards) +
                ", id='" + id + '\'' +
                ", stage=" + stage +
                ", name='" + name + '\'' +
                '}';
    }
}
