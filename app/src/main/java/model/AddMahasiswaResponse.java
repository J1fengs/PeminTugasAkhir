package model;

import com.google.gson.annotations.SerializedName;

public class AddMahasiswaResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("token")
    private String token;
    public String getMessage(){
        return message;
    }
    public String getToken(){
        return token;
    }
    public boolean isStatus(){
        return status;
    }
}
