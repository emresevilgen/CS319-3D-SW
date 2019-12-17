package rest;

import models.Game;
import models.Lobby;
import models.Mode;
import models.User;

public interface Requester {

    User login(String username, String password);
    User signUp(String name, String username, String password);
    User updateUser(String name, String password, String token);
    Lobby enterLobby(String username, String token, String lobbyCode);
    Lobby createLobby(String username, String lobbyName, String token, Mode mode);
    Lobby getLobby(String username, String token, String lobbyId);
    Lobby exitLobby(String username, String token, String lobbyId);
    Lobby getReady(String username, String token, boolean ready, String lobbyId);
    Game startGame(String username, String token, String lobbyCode);
    Game getGameData(String username, String token, String gameId, String playerId);
    Game getReadyGame(String username, String token, String gameId, boolean ready);
    Game commerce(String gameId, String username, String token, String sellerPlayerId, int[] materialTypes, int[] materialAmounts);
    Game pickCard(String gameId, String username, String token, String selectedCardId);

}
