package models;

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import uiComponents.SceneHandler;

public class DataHandler {
    private static DataHandler dataHandler = new DataHandler();

    // Data objects
    private User user = null;
    private Settings settings = null;
    private Game game = null;
    private Lobby lobby = null;

    private DataHandler(){
        settings = new Settings();

        //-------------------------------------
        //-------------------------------------
        // To delete
        Game game = new Game();
        game.ageNumber = 1;
        game.turnNumber = 1;
        game.players = new Player[4];
        game.players[0] = new Player();
        game.users = new User[4];
        game.users[0] = new User();
        game.users[1] = new User();
        game.users[2] = new User();
        game.users[3] = new User();
        game.users[0].userName = "user1";
        game.users[1].userName = "user2";
        game.users[2].userName = "user3";
        game.users[3].userName = "user4";
        game.players[0].playerCards = new Card[7];
        game.players[0].playerCards[0] = new Card();
        game.players[0].playerCards[0].cardName = "Altar";
        game.players[0].playerCards[1] = new Card();
        game.players[0].playerCards[1].cardName = "Apothecary";
        game.players[0].playerCards[2] = new Card();
        game.players[0].playerCards[2].cardName = "Aqueduct";
        game.players[0].playerCards[3] = new Card();
        game.players[0].playerCards[3].cardName = "Archery Range";
        game.players[0].playerCards[4] = new Card();
        game.players[0].playerCards[4].cardName = "Arena";
        game.players[0].playerCards[5] = new Card();
        game.players[0].playerCards[5].cardName = "Arsenal";
        game.players[0].playerCards[6] = new Card();
        game.players[0].playerCards[6].cardName = "Forum";

        game.players[0].board = new Board();
        // System.out.println(DataHandler.getInstance().getGame().users.length);
        game.players[0].board.wonderName = "Alexandria A";
        game.players[0].board.cards = new Card[19];
        game.players[0].board.cards[0] = new Card();
        game.players[0].board.cards[0].cardName = "Tree Farm";
        game.players[0].board.cards[0].cardColor = "Brown";
        game.players[0].board.cards[1] = new Card();
        game.players[0].board.cards[1].cardName = "Loom";
        game.players[0].board.cards[1].cardColor = "Gray";
        game.players[0].board.cards[2] = new Card();
        game.players[0].board.cards[2].cardName = "Library";
        game.players[0].board.cards[2].cardColor = "Green";
        game.players[0].board.cards[3] = new Card();
        game.players[0].board.cards[3].cardName = "Walls";
        game.players[0].board.cards[3].cardColor = "Red";

        game.players[1] = new Player();
        game.players[1].board = new Board();
        game.players[1].board.wonderName = "Ephesos A";
        game.players[1].board.cards = new Card[19];
        game.players[1].board.cards[0] = new Card();
        game.players[1].board.cards[0].cardName = "Tree Farm";
        game.players[1].board.cards[0].cardColor = "Brown";
        game.players[1].board.cards[1] = new Card();
        game.players[1].board.cards[1].cardName = "Loom";
        game.players[1].board.cards[1].cardColor = "Gray";
        game.players[1].board.cards[2] = new Card();
        game.players[1].board.cards[2].cardName = "Library";
        game.players[1].board.cards[2].cardColor = "Green";
        game.players[1].board.cards[3] = new Card();
        game.players[1].board.cards[3].cardName = "Walls";
        game.players[1].board.cards[3].cardColor = "Red";

        game.players[2] = new Player();
        game.players[2].board = new Board();
        game.players[2].board.wonderName = "Gizah A";
        game.players[2].board.cards = new Card[19];

        game.players[3] = new Player();
        game.players[3].board = new Board();
        game.players[3].board.wonderName = "Babylon A";
        game.players[3].board.cards = new Card[19];

        game.players[0].victoryPoints = 0;
        game.players[1].victoryPoints = 1;
        game.players[2].victoryPoints = 2;
        game.players[3].victoryPoints = 3;
        game.players[0].coin = 0;
        game.players[1].coin = 1;
        game.players[2].coin = 2;
        game.players[3].coin = 3;

        setGame(game);

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
