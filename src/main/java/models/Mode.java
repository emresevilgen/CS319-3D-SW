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

    @Override
    public String toString() {
        return "Mode{" +
                "shufflePlaces=" + shufflePlaces +
                ", secretSkills=" + secretSkills +
                ", invalidMovePenalty=" + invalidMovePenalty +
                ", loot=" + loot +
                '}';
    }

}
