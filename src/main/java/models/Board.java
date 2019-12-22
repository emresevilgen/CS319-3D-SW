package models;


import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Board {
    @SerializedName("cards")
    public Card[] cards;

    @SerializedName("id")
    public String id;

    @SerializedName("stage")
    public int stage;

    @SerializedName("name")
    public String name;


}
