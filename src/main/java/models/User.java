package models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    public String userName;

    @SerializedName("userId")
    public String userId;

    @SerializedName("name")
    public String name;

    @SerializedName("token")
    public String token;

    @SerializedName("createdAt")
    public String createdAt;

    @SerializedName("ranking")
    public int ranking;

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", ranking=" + ranking +
                '}';
    }
}