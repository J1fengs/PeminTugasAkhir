package com.example.tugaspemin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import api.ApiConfig;
import model.AddMahasiswaResponse;
import model.MahasiswaResponse;
import model.Note;
import model.NoteResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BerandaActivity extends AppCompatActivity {

    private Button btnProfile, btnNotes,btnLogout;
    private TextView juduls;
    private List<Note> noteList;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NoteAdapter adapter;
    private List<Note> notesList = new ArrayList<>();
    private SwipeRefreshLayout srl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        //Recycler View
        recyclerView = findViewById(R.id.recylerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(this, notesList);
        recyclerView.setAdapter(adapter);

//        fetchNotes();

        Intent intent = getIntent();
        String token = intent.getStringExtra("token");

        Intent prfl = new Intent(BerandaActivity.this, ProfileActivity.class);

        btnProfile = findViewById(R.id.profil);
        btnNotes = findViewById(R.id.btn_notes);
        btnLogout = findViewById(R.id.logout);
        juduls = findViewById(R.id.judulNote);
        progressBar = findViewById(R.id.progressBar);
        srl = findViewById(R.id.srl);


        btnProfile.setOnClickListener(view -> {
            prfl.putExtra("token", token);
            startActivity(prfl);
        });

        btnNotes.setOnClickListener(view -> {
            Intent baru = new Intent(BerandaActivity.this, PostActivity.class);
            baru.putExtra("token", token);
            startActivity(baru);
        });

        btnLogout.setOnClickListener(view -> {
            finish();
        });

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                retrieveData();
                srl.setRefreshing(false);
            }
        });

        Call<NoteResponse> client = ApiConfig.getApiService().getNote(token);
        client.enqueue(new Callback<NoteResponse>() {
            @Override
            public void onResponse(Call<NoteResponse> call, Response<NoteResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                        noteList = response.body().getData();
                        setData(noteList);
                        notesList.addAll(response.body().getData());
                        adapter.notifyDataSetChanged();
                } else {
                    if (response.body() != null) {
                        Log.e("", "onFailure: " + response.message());
                    }
                }
            }
            @Override
            public void onFailure(Call<NoteResponse> call, Throwable t) {
                Log.e("Error Retrofit","onFailure: " + t.getMessage());
            }
        });

    }

//    private void fetchNotes() {
//        ApiConfig.getApiService().getNote().enqueue(new Callback<NoteResponse>() {
//            @Override
//            public void onResponse(Call<NoteResponse> call, Response<NoteResponse> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<NoteResponse> call, Throwable t) {
//
//            }
//        });
//    }

    private void setData(List<Note> noteList) {
        if(noteList.size()>0){
            String tmp2 = "";
            for (int i=0;i<noteList.size();i++){
                String tmp = noteList.get(i).getJudul();
                juduls.setText(tmp2 + "\n" + tmp);
                tmp2 = juduls.getText().toString();
            }
        }
//        juduls.setText(noteList.get(0).getJudul());
    }

    public void retrieveData(){
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        Call<NoteResponse> client = ApiConfig.getApiService().getNote(token);
        client.enqueue(new Callback<NoteResponse>() {
            @Override
            public void onResponse(Call<NoteResponse> call, Response<NoteResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    notesList = response.body().getData();
                    adapter = new NoteAdapter(BerandaActivity.this, notesList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    if (response.body() != null) {
                        Log.e("", "onFailure: " + response.message());
                    }
                }
            }
            @Override
            public void onFailure(Call<NoteResponse> call, Throwable t) {
                Log.e("Error Retrofit","onFailure: " + t.getMessage());
            }
        });
    }

}
