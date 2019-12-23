package rest.models;

import com.google.gson.annotations.SerializedName;


/**
 * This class is used for the generalize the response
 * @param <T> The class for specific response
 */
public class GeneralResponse<T> {

    // Success check
    @SerializedName("success")
    public boolean success;

    // Fail messages
    @SerializedName("message")
    public String message;

    // Payload is the object itself
    @SerializedName("payload")
    public T payload;


    @Override
    public String toString() {
        return "UserResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", payload=" + (payload != null ? payload.toString() : " null")+
                '}';
    }
}
