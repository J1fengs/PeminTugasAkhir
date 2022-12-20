package com.example.tugaspemin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
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

import api.ApiConfig;
import api.ApiService;
import model.AddMahasiswaResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText emailLogin, passLogin;
    private Button loginBtn;
    private TextView registerUser;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    String cookieString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailLogin = findViewById(R.id.loginEmail);
        passLogin = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        registerUser = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();


//        progressDialog = new ProgressDialog(MainActivity.this);
//        progressDialog.setTitle("Loading");
//        progressDialog.setMessage("Please Wait");
//        progressDialog.setCancelable(false);
//
//        registerUser.setOnClickListener(v -> {
//            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
//        });
//        loginBtn.setOnClickListener(v -> {
//            if(emailLogin.getText().length()>0 && passLogin.getText().length()>0){
//                login(emailLogin.getText().toString(), passLogin.getText().toString());
//            }else{
//                Toast.makeText(getApplicationContext(), "Email atau Password Kosong", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void login(String email, String password){
//        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful() && task.getResult()!=null){
//                    if(task.getResult().getUser()!=null){
//                        reload();
//                    }else{
//                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
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
        loginBtn.setOnClickListener(view -> {
            addDataMahasiswa();
        });
        registerUser.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }

    private void addDataMahasiswa() {
        showLoading(true);
        String email = emailLogin.getText().toString();
        String pw = passLogin.getText().toString();
        if (email.isEmpty() || pw.isEmpty()) {
            Toast.makeText(MainActivity.this, "Silahkan lengkapi form terlebih dahulu", Toast.LENGTH_SHORT).show();
            showLoading(false);
        } else {
            Call<AddMahasiswaResponse> client = ApiConfig.getApiService().login(email, pw);
            client.enqueue(new Callback<AddMahasiswaResponse>() {
                @Override
                public void onResponse(Call<AddMahasiswaResponse> call, Response<AddMahasiswaResponse> response) {
                    showLoading(false);
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Berhasil login !", Toast.LENGTH_SHORT).show();
                        String token = response.body().getToken();
                        Intent intent = new Intent(MainActivity.this, BerandaActivity.class);
                        intent.putExtra("token", token);
                        cookieString = CookieManager.getInstance().getCookie("https://note-taking.my.id/public/auth/login");
                        startActivity(intent);
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