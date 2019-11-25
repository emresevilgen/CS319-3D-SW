package models;

import com.google.gson.annotations.SerializedName;

public class LobbyUser {

    @Override
    public String toString() {
        return "LobbyUser{" +
                "isReady=" + isReady +
                ", username='" + username + '\'' +
                '}';
    }

    @SerializedName("isReady")
    public boolean isReady;

    @SerializedName("isActive")
    public boolean isActive;

    @SerializedName("username")
    public String username;

}
