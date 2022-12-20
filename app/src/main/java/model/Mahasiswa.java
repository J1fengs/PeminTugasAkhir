package model;

import com.google.gson.annotations.SerializedName;

public class Mahasiswa {
    @SerializedName("nama")
    private String nama;
    @SerializedName("email")
    private String email;

    public Mahasiswa(String id){
        this.nama = getNama();
        this.email = getEmail();
    }

    public String getNama(){
        return nama;
    }
    public String getEmail(){
        return email;
    }
}
