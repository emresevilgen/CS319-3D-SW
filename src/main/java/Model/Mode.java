package model;


public class Mode {
    boolean shufflePlaces;
    boolean bargain;
    boolean secretSkills;
    boolean invalidMovePenalty;
    boolean loot;

    public Mode(boolean shufflePlaces, boolean bargain, boolean secretSkills, boolean invalidMovePenalty, boolean loot)
    {
        this.shufflePlaces = shufflePlaces;
        this.bargain = bargain;
        this.secretSkills = secretSkills;
        this.invalidMovePenalty = invalidMovePenalty;
        this.loot = loot;
    }

    public boolean isShufflePlaces() {
        return shufflePlaces;
    }

    public void setShufflePlaces(boolean shufflePlaces) {
        this.shufflePlaces = shufflePlaces;
    }

    public boolean isBargain() {
        return bargain;
    }

    public void setBargain(boolean bargain) {
        this.bargain = bargain;
    }

    public boolean isSecretSkills() {
        return secretSkills;
    }

    public void setSecretSkills(boolean secretSkills) {
        this.secretSkills = secretSkills;
    }

    public boolean isInvalidMovePenalty() {
        return invalidMovePenalty;
    }

    public void setInvalidMovePenalty(boolean invalidMovePenalty) {
        this.invalidMovePenalty = invalidMovePenalty;
    }

    public boolean isLoot() {
        return loot;
    }

    public void setLoot(boolean loot) {
        this.loot = loot;
    }
}
