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
