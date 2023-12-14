package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    Button register;
    TextView login;
    String useremail;
//    Timer timer;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //fulscreen mode in phone
        //hides the notification tab
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ConstraintLayout linearLayout = findViewById(R.id.main);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            useremail = currentUser.getEmail();
        }


        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, Registration.class);
                startActivity(it);
            }
        });


        if (currentUser == null) {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(MainActivity.this, Login.class);
                    startActivity(it);
                }
            });
        } else {
//            login.setText("Login as " + useremail);
//            login.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent it = new Intent(MainActivity.this, HomePage.class);
//                    startActivity(it);
//                    finish();
//                }
//            });

//            timer = new Timer();
            login.setVisibility(View.GONE);
            register.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
//            timer.schedule(new TimerTask(){
//                @Override
//                public void run() {
                    startActivity(new Intent(MainActivity.this, HomePage.class));
                    finish();
//                }
//            }, 2000);
        }

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                if (currentUser != null) {
                    handleSendText(intent);
                }
                else{
                    Utility.showToast(MainActivity.this, "Please login to use this feature");
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                }

            }
        }
    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            Intent it = new Intent(MainActivity.this, NewNote.class);
            it.putExtra("url", sharedText);
            startActivity(it);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
        }

        }
    }
