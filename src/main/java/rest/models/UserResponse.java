package rest.models;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("success")
    public boolean success;

    @SerializedName("message")
    public String message;

    @SerializedName("payload")
    public Payload payload;


    public class Payload {
        @SerializedName("username")
        public String username;

        @SerializedName("token")
        public String token;

        @Override
        public String toString() {
            return "Payload{" +
                    "username='" + username + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", payload=" + payload.toString() +
                '}';
    }
}
