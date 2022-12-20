package model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoteResponse {
    @SerializedName("user")
    private List<Note> user;
    @SerializedName("message")
    private String message;
    public List<Note> getData(){
        return user;
    }
    public String isStatus() {
        return message;
    }
}
