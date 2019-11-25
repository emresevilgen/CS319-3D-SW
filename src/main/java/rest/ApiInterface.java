package rest;

import models.Game;
import models.Lobby;
import models.Mode;
import models.User;
import rest.models.GeneralResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<GeneralResponse<User>> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("signup.php")
    Call<GeneralResponse<User>> signUp(@Field("name") String name, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("updateUser.php")
    Call<GeneralResponse<User>> updateUser(@Field("name") String name, @Field("password") String password);

    @FormUrlEncoded
    @POST("enterLobby.php")
    Call<GeneralResponse<Lobby>> enterLobby(@Field("username") String username, @Field("token") String token, @Field("lobbyCode") String lobbyCode);

    @FormUrlEncoded
    @POST("createLobby.php")
    Call<GeneralResponse<Lobby>> createLobby(@Field("username") String username, @Field("token") String token, @Field("mode") Mode mode);

    @FormUrlEncoded
    @POST("getLobby.php")
    Call<GeneralResponse<Lobby>> getLobby(@Field("username") String username, @Field("token") String token, @Field("lobbyId") String lobbyId);

    @FormUrlEncoded
    @POST("getReady.php")
    Call<GeneralResponse<Lobby>> getReady(@Field("username") String username, @Field("token") String token, @Field("ready") boolean ready);

    @FormUrlEncoded
    @POST("startGame.php")
    Call<GeneralResponse<Game>> startGame(@Field("username") String username, @Field("token") String token, @Field("lobbyCode") String lobbyCode);

    @FormUrlEncoded
    @POST("getGame.php")
    Call<GeneralResponse<Game>> getGameData(@Field("username") String username, @Field("token") String token, @Field("gameId") String gameId);

    @FormUrlEncoded
    @POST("commerce.php")
    Call<GeneralResponse<Game>> bargain(@Field("gameId") String gameId, @Field("username") String username, @Field("token") String token, @Field("sellerPlayerId") String sellerPlayerId, @Field("materialTypes") int[] materialTypes, @Field("materialAmounts") int[] materialAmounts);

    @FormUrlEncoded
    @POST("pickCard.php")
    Call<GeneralResponse<Game>> pickCard(@Field("gameId") String gameId, @Field("username") String username, @Field("token") String token, @Field("selectedCardId") String selectedCardId);

}
