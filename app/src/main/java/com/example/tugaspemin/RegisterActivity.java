package com.example.tugaspemin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import api.ApiConfig;
import model.AddMahasiswaResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText regisName, regisEmail, regisPass;
    private Button regisBtn;
    private TextView loginUser;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regisName = findViewById(R.id.registerName);
        regisEmail = findViewById(R.id.registerEmail);
        regisPass = findViewById(R.id.registerPassword);
        regisBtn = findViewById(R.id.registerBtn);
        loginUser = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);

        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        loginUser.setOnClickListener(v ->{
//            finish();
//        });
//        regisBtn.setOnClickListener(v -> {
//            if(regisName.getText().length()>0 && regisEmail.getText().length()>0 && regisPass.getText().length()>0){
//                register(regisName.getText().toString(),regisEmail.getText().toString(),regisPass.getText().toString());
//            }else{
//                Toast.makeText(getApplicationContext(), "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    private void register(String name, String email, String password){
//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful() && task.getResult()!=null){
//                    FirebaseUser firebaseUser = task.getResult().getUser();
//                    if(firebaseUser!=null) {
//                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
//                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                reload();
//                            }
//                        });
//                    }else{
//                        Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//    private void reload(){
//        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
//    }
        regisBtn.setOnClickListener(view -> {
            addDataMahasiswa();
        });
        loginUser.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void addDataMahasiswa() {
        showLoading(true);
        String nama = regisName.getText().toString();
        String email = regisEmail.getText().toString();
        String pw = regisPass.getText().toString();
        if (nama.isEmpty() || email.isEmpty() || pw.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Silahkan lengkapi form terlebih dahulu", Toast.LENGTH_SHORT).show();
            showLoading(false);
        } else {
            Call<AddMahasiswaResponse> client = ApiConfig.getApiService().regis(email, nama, pw);
            client.enqueue(new Callback<AddMahasiswaResponse>() {
                @Override
                public void onResponse(Call<AddMahasiswaResponse> call, Response<AddMahasiswaResponse> response) {
                    showLoading(false);
                    if (response.isSuccessful()) {
                        if (response.body() == null) {
                            Toast.makeText(RegisterActivity.this, "Berhasil register !", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(RegisterActivity.this, "Berhasil register !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

