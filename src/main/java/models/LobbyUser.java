package models;

import com.google.gson.annotations.SerializedName;

public class LobbyUser {

    @SerializedName("username")
    public String username;

    @SerializedName("isReady")
    public boolean isReady;

    @SerializedName("isActive")
    public boolean isActive;

    @Override
    public String toString() {
        return "LobbyUser{" +
                "username='" + username + '\'' +
                ", isReady=" + isReady +
                ", isActive=" + isActive +
                '}';
    }



}
