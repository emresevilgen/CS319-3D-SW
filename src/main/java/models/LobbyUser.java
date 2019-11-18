package models;

import com.google.gson.annotations.SerializedName;

public class LobbyUser extends User{

    @SerializedName("readyState")
    public boolean isReady;

    @SerializedName("creatorState")
    public boolean isCreator;

    @Override
    public String toString() {
        return "LobbyUser{" +
                "isReady=" + isReady +
                ", isCreator=" + isCreator +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", ranking=" + ranking +
                '}';
    }

}
