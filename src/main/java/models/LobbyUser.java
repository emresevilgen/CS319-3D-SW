package models;

public class LobbyUser extends User{

    boolean isReady;

    public LobbyUser()
    {
        isReady = false;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
