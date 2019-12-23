package rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.Constants;

/**
 * This class generates a Retrofit Builder for convertin JSON to Java Objects
 */
public class ApiClient {
    private static Retrofit retrofit = null;


    /**
     * @return the Retrofit Api Service
     */
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
