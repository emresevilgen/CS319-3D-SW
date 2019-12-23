package models;

public class DataHandler {
    private static DataHandler dataHandler = new DataHandler();

    // Data objects
    private User user = null;
    private Settings settings = null;
    private Game game = null;
    private Lobby lobby = null;

    private DataHandler(){
        settings = new Settings();

/*        //-------------------------------------
        //-------------------------------------
        // To delete
        Game game = new Game();
        game.ageNumber = 1;
        game.turnNumber = 1;
        game.players = new Player[4];
        game.players[0] = new Player();

        game.players[0].cards = new Card[7];
        game.players[0].cards[0] = new Card();
        game.players[0].cards[0].name = "Altar";
        game.players[0].cards[1] = new Card();
        game.players[0].cards[1].name = "Apothecary";
        game.players[0].cards[2] = new Card();
        game.players[0].cards[2].name = "Aqueduct";
        game.players[0].cards[3] = new Card();
        game.players[0].cards[3].name = "Archery Range";
        game.players[0].cards[4] = new Card();
        game.players[0].cards[4].name = "Arena";
        game.players[0].cards[5] = new Card();
        game.players[0].cards[5].name = "Arsenal";
        game.players[0].cards[6] = new Card();
        game.players[0].cards[6].name = "Forum";


        game.players[0].board = new Board();
        // System.out.println(DataHandler.getInstance().getGame().users.length);
        game.players[0].board.name = "Alexandria";
        game.players[0].board.cards = new Card[16];
        game.players[0].board.cards[0] = new Card();
        game.players[0].board.cards[0].name = "Tree Farm";
        game.players[0].board.cards[0].color = "Brown";
        game.players[0].board.cards[1] = new Card();
        game.players[0].board.cards[1].name = "Loom";
        game.players[0].board.cards[1].color = "Gray";
        game.players[0].board.cards[2] = new Card();
        game.players[0].board.cards[2].name = "Library";
        game.players[0].board.cards[2].color = "Green";
        game.players[0].board.cards[3] = new Card();
        game.players[0].board.cards[3].name = "Walls";
        game.players[0].board.cards[3].color = "Red";
        game.players[0].board.cards[4] = new Card();
        game.players[0].board.cards[4].name = "Tree Farm";
        game.players[0].board.cards[4].color = "Brown";
        game.players[0].board.cards[5] = new Card();
        game.players[0].board.cards[5].name = "Loom";
        game.players[0].board.cards[5].color = "Gray";
        game.players[0].board.cards[6] = new Card();
        game.players[0].board.cards[6].name = "Library";
        game.players[0].board.cards[6].color = "Green";
        game.players[0].board.cards[7] = new Card();
        game.players[0].board.cards[7].name = "Walls";
        game.players[0].board.cards[7].color = "Red";
        game.players[0].board.cards[8] = new Card();
        game.players[0].board.cards[8].name = "Tree Farm";
        game.players[0].board.cards[8].color = "Brown";
        game.players[0].board.cards[9] = new Card();
        game.players[0].board.cards[9].name = "Loom";
        game.players[0].board.cards[9].color = "Gray";
        game.players[0].board.cards[10] = new Card();
        game.players[0].board.cards[10].name = "Library";
        game.players[0].board.cards[10].color = "Green";
        game.players[0].board.cards[11] = new Card();
        game.players[0].board.cards[11].name = "Walls";
        game.players[0].board.cards[11].color = "Red";
        game.players[0].board.cards[12] = new Card();
        game.players[0].board.cards[12].name = "Tree Farm";
        game.players[0].board.cards[12].color = "Brown";
        game.players[0].board.cards[13] = new Card();
        game.players[0].board.cards[13].name = "Loom";
        game.players[0].board.cards[13].color = "Gray";
        game.players[0].board.cards[14] = new Card();
        game.players[0].board.cards[14].name = "Library";
        game.players[0].board.cards[14].color = "Green";
        game.players[0].board.cards[15] = new Card();
        game.players[0].board.cards[15].name = "Walls";
        game.players[0].board.cards[15].color = "Red";

        game.players[1] = new Player();
        game.players[1].board = new Board();
        game.players[1].board.name = "Ephesos";
        game.players[1].board.cards = new Card[15];
        game.players[1].board.cards[0] = new Card();
        game.players[1].board.cards[0].name = "Tree Farm";
        game.players[1].board.cards[0].color = "Brown";
        game.players[1].board.cards[1] = new Card();
        game.players[1].board.cards[1].name = "Loom";
        game.players[1].board.cards[1].color = "Gray";
        game.players[1].board.cards[2] = new Card();
        game.players[1].board.cards[2].name = "Library";
        game.players[1].board.cards[2].color = "Green";
        game.players[1].board.cards[3] = new Card();
        game.players[1].board.cards[3].name = "Walls";
        game.players[1].board.cards[3].color = "Red";
        game.players[1].board.cards[4] = new Card();
        game.players[1].board.cards[4].name = "Walls";
        game.players[1].board.cards[4].color = "Red";
        game.players[1].board.cards[5] = new Card();
        game.players[1].board.cards[5].name = "Walls";
        game.players[1].board.cards[5].color = "Red";
        game.players[1].board.cards[6] = new Card();
        game.players[1].board.cards[6].name = "Walls";
        game.players[1].board.cards[6].color = "Red";
        game.players[1].board.cards[7] = new Card();
        game.players[1].board.cards[7].name = "Walls";
        game.players[1].board.cards[7].color = "Red";
        game.players[1].board.cards[8] = new Card();
        game.players[1].board.cards[8].name = "Walls";
        game.players[1].board.cards[8].color = "Red";
        game.players[1].board.cards[9] = new Card();
        game.players[1].board.cards[9].name = "Walls";
        game.players[1].board.cards[9].color = "Red";
        game.players[1].board.cards[10] = new Card();
        game.players[1].board.cards[10].name = "Walls";
        game.players[1].board.cards[10].color = "Red";
        game.players[1].board.cards[11] = new Card();
        game.players[1].board.cards[11].name = "Walls";
        game.players[1].board.cards[11].color = "Red";
        game.players[1].board.cards[12] = new Card();
        game.players[1].board.cards[12].name = "Caravansery";
        game.players[1].board.cards[12].color = "Yellow";
        game.players[1].board.cards[13] = new Card();
        game.players[1].board.cards[13].name = "Walls";
        game.players[1].board.cards[13].color = "Red";
        game.players[1].board.cards[14] = new Card();
        game.players[1].board.cards[14].name = "Walls";
        game.players[1].board.cards[14].color = "Red";

        game.players[2] = new Player();
        game.players[2].board = new Board();
        game.players[2].board.name = "Gizah";
        game.players[2].board.cards = new Card[19];
        game.players[2].board.cards[1] = new Card();
        game.players[2].board.cards[1].name = "Library";
        game.players[2].board.cards[1].color = "Green";
        game.players[2].board.cards[1] = new Card();
        game.players[2].board.cards[1].name = "Walls";
        game.players[2].board.cards[1].color = "Red";
        game.players[2].board.cards[1] = new Card();
        game.players[2].board.cards[1].name = "Walls";
        game.players[2].board.cards[1].color = "Red";

        game.players[3] = new Player();
        game.players[3].board = new Board();
        game.players[3].board.name = "Babylon";
        game.players[3].board.cards = new Card[19];

        game.players[0].victoryPoints = 0;
        game.players[1].victoryPoints = 1;
        game.players[2].victoryPoints = 2;
        game.players[3].victoryPoints = 3;
        game.players[0].coin = 0;
        game.players[1].coin = 1;
        game.players[2].coin = 2;
        game.players[3].coin = 3;

        game.players[0].name = "user1";
        game.players[1].name = "user2";
        game.players[2].name = "user3";
        game.players[3].name = "user4";

        setGame(game);*/

        //-----------------------------------------------
    }

    //Get the only object available
    public static DataHandler getInstance(){
        if (dataHandler == null)
            dataHandler = new DataHandler();

        return dataHandler;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) { this.user = user; }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
}
