package models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Lobby {
    @SerializedName("id")
    public String id;

    @SerializedName("gameId")
    public String gameId;

    @SerializedName("admin")
    public String admin;

    @SerializedName("name")
    public String name;

    @SerializedName("code")
    public String code;

    @SerializedName("users")
    public LobbyUser[] users;

    @SerializedName("mode")
    public Mode mode;

    @Override
    public String toString() {
        return "Lobby{" +
                "lobbyId='" + id + '\'' +
                ", lobbyAdmin='" + admin + '\'' +
                ", lobbyName='" + name + '\'' +
                ", lobbyCode='" + code + '\'' +
                ", lobbyUsers=" + Arrays.toString(users) +
                ", mode=" + mode +
                '}';
    }
}