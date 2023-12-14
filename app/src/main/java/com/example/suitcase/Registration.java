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

public class Registration extends AppCompatActivity {

    TextView login;
    Button register;
    EditText username, password, cpassword;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        username = findViewById(R.id.usernmae);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        progress = findViewById(R.id.progress);


        register.setOnClickListener(v-> createAccount());
        login.setOnClickListener(v-> loginpage());

    }

    void createAccount(){
        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String cpass = cpassword.getText().toString().trim();

        boolean isValid = valid(email, pass, cpass);
        if(!isValid){
            return;
        }
        createacc(email, pass);

    }
    void createacc(String email, String pass){
        progress(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress(false);
                if(task.isSuccessful()){
                    Utility.showToast(Registration.this, "Account created, check email to verify");
                   // Toast.makeText(Registration.this, "Account created, check email to verify", Toast.LENGTH_SHORT).show();
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    finish();
                }
                else{
                    Utility.showToast(Registration.this, task.getException().getLocalizedMessage());
                    //Toast.makeText(Registration.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    void progress(boolean pro){
        if(pro){
            progress.setVisibility(View.VISIBLE);
            register.setVisibility(View.GONE);
        }else{
            progress.setVisibility(View.GONE);
            register.setVisibility(View.VISIBLE);
        }
    }

    boolean valid(String email, String pass, String cpass){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username.setError("Email is invalid");
            return false;
        }
        if(pass.length() < 6){
            password.setError("Password length is less than 6");
            return false;
        }
        if(!pass.equals(cpass)){
            cpassword.setError("Passwords doesn't match");
            return false;
        }
        return true;
    }

    void loginpage(){
        Intent it = new Intent(Registration.this, Login.class);
        startActivity(it);
    }
}