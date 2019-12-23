package models;


import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Game {
    @SerializedName("id")
    public String id;

    @SerializedName("ageNumber")
    public int ageNumber;

    @SerializedName("turnNumber")
    public int turnNumber;

    @SerializedName("players")
    public Player[] players;

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", ageNumber=" + ageNumber +
                ", turnNumber=" + turnNumber +
                ", players=" + Arrays.toString(players) +
                '}';
    }


}
