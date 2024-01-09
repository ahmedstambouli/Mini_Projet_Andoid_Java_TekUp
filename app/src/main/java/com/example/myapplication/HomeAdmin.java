package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeAdmin extends AppCompatActivity {

    Button pageE,pageC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        // Initialisation des boutons en utilisant leurs identifiants

        pageE=(Button) findViewById(R.id.buttonE);
        pageC=(Button) findViewById(R.id.buttonC);


        // Redirection vers HomeEmploye
        pageE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeAdmin.this, HomeEmploye.class);
                startActivity(intent);
            }
        });

        // Redirection vers Mainactiviter
        pageC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeAdmin.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}