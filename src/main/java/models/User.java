package models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    public String userName;

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("token")
    public String token;

    @SerializedName("ranking")
    public int ranking;

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", ranking=" + ranking +
                '}';
    }

}