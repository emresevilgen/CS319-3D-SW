package model;

public class Settings {

    boolean soundEffects;
    boolean audioDescription;

    public Settings() {
        soundEffects = true;
        audioDescription = true;
    }

    public boolean isSoundEffects() {
        return soundEffects;
    }

    public void setSoundEffects(boolean soundEffects) {
        this.soundEffects = soundEffects;
    }

    public boolean isAudioDescription() {
        return audioDescription;
    }

    public void setAudioDescription(boolean audioDescription) {
        this.audioDescription = audioDescription;
    }
}
