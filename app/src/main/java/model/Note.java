package model;

import com.google.gson.annotations.SerializedName;

public class Note {
    @SerializedName("id")
    private Integer id;
    @SerializedName("judul")
    private String judul;
    @SerializedName("content")
    private String content;

    public Note(String id){
        this.judul = getJudul();
        this.content = getContent();
    }

    public Integer getId(){return id;}
    public String getJudul(){
        return judul;
    }
    public String getContent(){
        return content;
    }
}
