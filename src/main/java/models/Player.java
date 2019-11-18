package models;


import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Player {

    @SerializedName("secretSkill")
    public String secretSkill;

    @SerializedName("playerCards")
    public Card[] playerCards;

    @SerializedName("board")
    public Board board;

    @SerializedName("playerId")
    public String playerId;

    @SerializedName("leftPlayerId")
    public String leftPlayerId;

    @SerializedName("rightPlayerId")
    public String rightPlayerId;

    @SerializedName("victoryPoints")
    public int victoryPoints;

    @SerializedName("militaryPoints")
    public int militaryPoints;

    @SerializedName("coin")
    public int coin;

    @Override
    public String toString() {
        return "Player{" +
                "secretSkill='" + secretSkill + '\'' +
                ", playerCards=" + Arrays.toString(playerCards) +
                ", board=" + board +
                ", playerId='" + playerId + '\'' +
                ", leftPlayerId='" + leftPlayerId + '\'' +
                ", rightPlayerId='" + rightPlayerId + '\'' +
                ", victoryPoints=" + victoryPoints +
                ", militaryPoints=" + militaryPoints +
                ", coin=" + coin +
                '}';
    }
}
