package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Using a Handler to delay the execution of code for 2000 milliseconds (2 seconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Creating an intent to switch from SplashScreen to the Login activity

                Intent intent =new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}