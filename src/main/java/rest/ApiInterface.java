package rest;

import rest.models.UserResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<UserResponse> login(@Field("username") String username, @Field("password") String password);
}
