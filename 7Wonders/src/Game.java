public class Game {
    int turnNumber;
    int ageNumber;
    String gameId;
    User[] users;
    Player[] players;
    int playerCount; //yeni ekledim
    public Game()
    {
        //playerCount= ;
        turnNumber = 1;
        ageNumber = 1;
        //gameId = ;
        users = new User[playerCount];
        players = new Player[playerCount];
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public int getAgeNumber() {
        return ageNumber;
    }

    public void setAgeNumber(int ageNumber) {
        this.ageNumber = ageNumber;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
