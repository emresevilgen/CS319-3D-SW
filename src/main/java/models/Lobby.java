package models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Lobby {
    @SerializedName("lobbyId")
    public String lobbyId;

    @SerializedName("lobbyName")
    public String lobbyName;

    @SerializedName("lobbyCode")
    public String lobbyCode;

    @SerializedName("lobbyUsers")
    public LobbyUser[] lobbyUsers;

    @SerializedName("mode")
    public Mode mode;

    @Override
    public String toString() {
        return "Lobby{" +
                "lobbyId='" + lobbyId + '\'' +
                ", lobbyName='" + lobbyName + '\'' +
                ", lobbyCode='" + lobbyCode + '\'' +
                ", lobbyUsers=" + Arrays.toString(lobbyUsers) +
                ", mode=" + mode +
                '}';
    }
}

