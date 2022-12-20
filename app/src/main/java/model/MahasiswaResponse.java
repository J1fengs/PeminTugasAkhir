package model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MahasiswaResponse {
    @SerializedName("user")
    private JsonObject user;
    @SerializedName("message")
    private String message;
    public JsonObject getData(){
        return user;
    }
    public String isStatus() {
        return message;
    }
}
