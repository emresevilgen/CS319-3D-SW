package rest.models;

import com.google.gson.annotations.SerializedName;

public class GeneralResponse<T> {

    @SerializedName("success")
    public boolean success;

    @SerializedName("message")
    public String message;

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
