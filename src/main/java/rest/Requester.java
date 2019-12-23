package rest;

import models.Game;
import models.Lobby;
import models.Mode;
import models.User;
import rest.models.GeneralResponse;

public interface Requester {

    GeneralResponse<User> login(String username, String password);
    GeneralResponse<User> signUp(String name, String username, String password);
    GeneralResponse<User>  updateUser(String name, String password, String token);
    GeneralResponse<Lobby> enterLobby(String username, String token, String lobbyCode);
    GeneralResponse<Lobby> createLobby(String username, String lobbyName, String token, Mode mode);
    GeneralResponse<Lobby> getLobby(String username, String token);
    GeneralResponse<Lobby> exitLobby(String username, String token);
    GeneralResponse<Lobby> getReady(String username, String token, boolean ready);
    GeneralResponse<Game> startGame(String username, String token, String lobbyCode);
    GeneralResponse<Game>  getGameData(String username, String token);
    GeneralResponse<Game>  getReadyGame(String username, String token, String gameId, boolean ready);
    GeneralResponse<Game>  commerce(String gameId, String username, String token, String sellerPlayerId, int[] materialTypes, int[] materialAmounts);
    GeneralResponse<Game>  pickCard(String gameId, String username, String token, String selectedCardId);

}
