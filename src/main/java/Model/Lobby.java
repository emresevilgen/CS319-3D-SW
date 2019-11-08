package model;

public class Lobby {
    String lobbyName;
    String lobbyCode;
    LobbyUser[] lobbyUsers;
    Mode mode;
    int userCount; //yeni ekledim
    public Lobby()
    {
        //userCount = ;
        //lobbyName = ;
        //lobbyCode = ;
        lobbyUsers= new LobbyUser[userCount];
        //mode = new Mode(shufflePlaces, bargain, secretSkills, invalidMovePenalty, loot);
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public LobbyUser[] getLobbyUsers() {
        return lobbyUsers;
    }

    public void setLobbyUsers(LobbyUser[] lobbyUsers) {
        this.lobbyUsers = lobbyUsers;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
