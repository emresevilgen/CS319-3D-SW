package models;


import com.google.gson.annotations.SerializedName;

public class Mode {
    @SerializedName("shufflePlaces")
    public boolean shufflePlaces;

    @SerializedName("secretSkills")
    public boolean secretSkills;

    @SerializedName("invalidMovePenalty")
    public boolean invalidMovePenalty;

    @SerializedName("loot")
    public boolean loot;

}
