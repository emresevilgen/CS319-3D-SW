package rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.Constants;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
