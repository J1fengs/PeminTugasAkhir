package com.example.tugaspemin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import api.ApiConfig;
import model.AddMahasiswaResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {

    private Button upload;
    private EditText juduls, kontens;
    private ProgressBar progressBar;
    String tokens;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        upload = findViewById(R.id.upload);
        juduls = findViewById(R.id.judul);
        kontens = findViewById(R.id.konten);
        progressBar = findViewById(R.id.progressBar);
        Intent intent = getIntent();
        tokens = intent.getStringExtra("token");



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadNote();
            }
        });

    }
    private void uploadNote() {
        showLoading(true);
        String judul = juduls.getText().toString();
        String konten = kontens.getText().toString();
        String token = tokens;
        if (judul.isEmpty() || konten.isEmpty()) {
            Toast.makeText(PostActivity.this, "Silahkan lengkapi form terlebih dahulu", Toast.LENGTH_SHORT).show();
            showLoading(false);
        } else {
            Call<AddMahasiswaResponse> client = ApiConfig.getApiService().add(token, judul, konten);
            client.enqueue(new Callback<AddMahasiswaResponse>() {
                @Override
                public void onResponse(Call<AddMahasiswaResponse> call, Response<AddMahasiswaResponse> response) {
                    showLoading(false);
                    if (response.isSuccessful()) {
                        if (response.body() == null) {
                            Toast.makeText(PostActivity.this, "Berhasil upload !", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(PostActivity.this, "Berhasil upload !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (response.body() == null) {
                            Log.e("", "onFailure: " + response.body().getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddMahasiswaResponse> call, Throwable t) {
                    showLoading(false);
                    Log.e("Error Retrofit", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
