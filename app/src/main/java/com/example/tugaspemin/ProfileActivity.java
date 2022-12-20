package com.example.tugaspemin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import api.ApiConfig;
import model.Mahasiswa;
import model.MahasiswaResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView email, nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        email = findViewById(R.id.tvNrp);



            Call<MahasiswaResponse> client = ApiConfig.getApiService().getUser(token);
            client.enqueue(new Callback<MahasiswaResponse>() {
                @Override
                public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            email.setText(response.body().getData().toString());
                        }
                    } else {
                        if (response.body() != null) {
                            Log.e("", "onFailure: " + response.message());
                        }
                    }
                }
                @Override
                public void onFailure(Call<MahasiswaResponse> call, Throwable t) {
                    Log.e("Error Retrofit", "onFailure: " + t.getMessage());
                }
            });
    }
}

