package rest;

import models.*;
import rest.models.GeneralResponse;
import retrofit2.http.Field;

public interface Requester {

    GeneralResponse<User> login(String username, String password);
    GeneralResponse<User> signUp(String name, String username, String password);
    GeneralResponse<User>  updateUser(String name, String password, String token);
    GeneralResponse<Lobby> enterLobby(String username, String token, String lobbyCode);
    GeneralResponse<Lobby> createLobby(String username, String lobbyName, String token, Mode mode);
    GeneralResponse<Lobby> getLobby(String username, String token);
    GeneralResponse<Lobby> exitLobby(String username, String token);
    GeneralResponse<Lobby> getReady(String username, String token, boolean ready);
    GeneralResponse<Game>  getGameData(String username, String token);
    GeneralResponse<Game>  commerce(String username, String token, Boolean isWidthLeft, Materials commerceMaterials);
    GeneralResponse<Game>  useCard(String username, String token, String cardId, int selectionType, boolean freeBuilding);

}
