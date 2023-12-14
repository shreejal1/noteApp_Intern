package com.example.suitcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextView register;
    Button login;
    EditText username, password;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        username = findViewById(R.id.usernmae);
        password = findViewById(R.id.password);
        progress = findViewById(R.id.progress);

        login.setOnClickListener(v-> userlogin());
        register.setOnClickListener(v-> startActivity(new Intent(Login.this, Registration.class)));




    }
    void userlogin(){
        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();


        boolean isValid = valid(email, pass);
        if(!isValid){
            return;
        }
        loginfirebase(email, pass);

    }
    void loginfirebase(String email, String pass){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        progress(true);
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress(false);
                if(task.isSuccessful()){
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        startActivity(new Intent(Login.this, HomePage.class));
                        finish();
                    }
                    else{
                        Utility.showToast(Login.this, "Email not verified yet, please check mail");
                    }
                }
                else{
                    Utility.showToast(Login.this, task.getException().getLocalizedMessage());
                }
            }
        });
    }
    void progress(boolean pro){
        if(pro){
            progress.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
        }else{
            progress.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        }
    }


    boolean valid(String email, String pass){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username.setError("Email is invalid");
            return false;
        }
        if(pass.length() < 6){
            password.setError("Password length is less than 6");
            return false;
        }
        return true;
    }
}