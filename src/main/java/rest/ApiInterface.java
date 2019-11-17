package rest;

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
    @POST("signUp.php")
    Call<GeneralResponse<User>> signUp(@Field("name") String name, @Field("username") String username, @Field("password") String password);

}
