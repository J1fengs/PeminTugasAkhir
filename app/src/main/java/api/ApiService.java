package api;

import com.google.gson.JsonObject;

import java.util.List;

import model.AddMahasiswaResponse;
import model.Mahasiswa;
import model.MahasiswaResponse;
import model.Note;
import model.NoteResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("user")
    Call<MahasiswaResponse> getUser(@Header("token") String token);

    @GET("note")
    Call<NoteResponse> getNote(@Header("token") String token);

    @GET("user")
    Call<Mahasiswa> getinfo(@Header("token") String token);

    @POST("auth/register")
    @FormUrlEncoded
    Call<AddMahasiswaResponse> regis(
            @Field("email") String email,
            @Field("nama") String nama,
            @Field("password") String password
    );

    @POST("note/add")
    @FormUrlEncoded
    Call<AddMahasiswaResponse> add(
            @Header("token") String token,
            @Field("judul") String judul,
            @Field("content") String content
    );

    @POST("auth/login")
    @FormUrlEncoded
    Call<AddMahasiswaResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("note/delete")
    Call<AddMahasiswaResponse> delete(
        @Header("token") String token
    );

}
