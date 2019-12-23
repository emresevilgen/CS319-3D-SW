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

/**
 * This interface is used for specifying the requests
 */
public interface ApiInterface {
    @FormUrlEncoded
    @POST("user/login/")
    Call<GeneralResponse<User>> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/signup/")
    Call<GeneralResponse<User>> signUp(@Field("name") String name, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("updateUser.php")
    Call<GeneralResponse<User>> updateUser(@Field("name") String name, @Field("password") String password, @Field("token") String token);

    @FormUrlEncoded
    @POST("lobby/join/")
    Call<GeneralResponse<Lobby>> enterLobby(@Field("username") String username, @Field("token") String token, @Field("code") String lobbyCode);

    @FormUrlEncoded
    @POST("lobby/create/")
    Call<GeneralResponse<Lobby>> createLobby(@Field("username") String username, @Field("name") String lobbyName, @Field("token") String token, @Field("mode") String mode);

    @FormUrlEncoded
    @POST("lobby/")
    Call<GeneralResponse<Lobby>> getLobby(@Field("username") String username, @Field("token") String token);

    @FormUrlEncoded
    @POST("lobby/exit/")
    Call<GeneralResponse<Lobby>> exitLobby(@Field("username") String username, @Field("token") String token);

    @FormUrlEncoded
    @POST("lobby/ready/")
    Call<GeneralResponse<Lobby>> getReady(@Field("username") String username, @Field("token") String token, @Field("ready") boolean ready);

    @FormUrlEncoded
    @POST("game/")
    Call<GeneralResponse<Game>> getGameData(@Field("username") String username, @Field("token") String token);

    @FormUrlEncoded
    @POST("game/commerce/")
    Call<GeneralResponse<Game>> commerce(@Field("username") String username, @Field("token") String token, @Field("isWithLeft") Boolean isWidthLeft, @Field("commerceMaterials") String commerceMaterials);

    @FormUrlEncoded
    @POST("card/use/")
    Call<GeneralResponse<Game>> useCard(@Field("username") String username, @Field("token") String token, @Field("cardId") String cardId, @Field("selectionType") int selectionType, @Field("freeBuilding") boolean freeBuilding);



}
