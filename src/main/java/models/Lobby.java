package models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Lobby {
    @SerializedName("id")
    public String lobbyId;

    @SerializedName("admin")
    public String lobbyAdmin;

    @SerializedName("name")
    public String lobbyName;

    @SerializedName("code")
    public String lobbyCode;

    @SerializedName("users")
    public LobbyUser[] lobbyUsers;

    @SerializedName("mode")
    public Mode mode;

    @Override
    public String toString() {
        return "Lobby{" +
                "lobbyId='" + lobbyId + '\'' +
                ", lobbyAdmin='" + lobbyAdmin + '\'' +
                ", lobbyName='" + lobbyName + '\'' +
                ", lobbyCode='" + lobbyCode + '\'' +
                ", lobbyUsers=" + Arrays.toString(lobbyUsers) +
                ", mode=" + mode +
                '}';
    }
}