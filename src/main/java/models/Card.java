package models;


import com.google.gson.annotations.SerializedName;

public class Card {
    @SerializedName("id")
    public String id;

    @SerializedName("description")
    public String description;

    @SerializedName("color")
    public String color;

    @SerializedName("name")
    public String name;

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", color='" + color + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
