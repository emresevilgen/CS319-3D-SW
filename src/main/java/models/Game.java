package models;


import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Game {
    @SerializedName("turnNumber")
    public int turnNumber;

    @SerializedName("ageNumber")
    public int ageNumber;

    @SerializedName("gameId")
    public String gameId;

    @SerializedName("users")
    public User[] users;

    @SerializedName("players")
    public Player[] players;

    @Override
    public String toString() {
        return "Game{" +
                "turnNumber=" + turnNumber +
                ", ageNumber=" + ageNumber +
                ", gameId='" + gameId + '\'' +
                ", users=" + Arrays.toString(users) +
                ", players=" + Arrays.toString(players) +
                '}';
    }

}
