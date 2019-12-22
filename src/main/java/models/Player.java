package models;


import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Player {

    @SerializedName("id")
    public String id;

    @SerializedName("gameId")
    public String gameId;

    @SerializedName("name")
    public String name;
    
    @SerializedName("secretSkill")
    public String secretSkill;

    @SerializedName("cards")
    public Card[] cards;

    @SerializedName("board")
    public Board board;
    
    @SerializedName("leftPlayerId")
    public String leftPlayerId;

    @SerializedName("rightPlayerId")
    public String rightPlayerId;

    @SerializedName("victoryPoints")
    public int victoryPoints;

    @SerializedName("victoryTokens")
    public int victoryTokens;

    @SerializedName("defeatTokens")
    public int defeatTokens;

    @SerializedName("coin")
    public int coin;


}
