package models;

public class Settings {

    boolean soundEffects;
    boolean audioDescription;

    public Settings() {
        soundEffects = false;
        audioDescription = false;
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

    public void switchAudioDescription(){ audioDescription = !audioDescription; }
    public void switchSoundEffects(){ soundEffects = !soundEffects; }



}
