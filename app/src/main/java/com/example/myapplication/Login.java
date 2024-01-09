package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {


    EditText email,password;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initializing EditText and Button by finding their ID from the layout XML
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email2);
        password=findViewById(R.id.password2);
        button=findViewById(R.id.button);


        // Setting a click listener on the button
        button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        // Checking if the entered email and password match the predefined values for admin

        if((email.getText().toString().equals("admin@mail.com")) && ( (password.getText().toString().equals("admin"))) )
        {

            // If it matches, create an intent to navigate to HomeAdmin activity
            Intent intent =new Intent(Login.this, HomeAdmin.class);
            startActivity(intent);

            // Clearing the email and password fields after login
            email.setText("");
            password.setText("");
        }

        // Checking if the entered email and password match the predefined values for user

       else  if((email.getText().toString().equals("ahmed@mail.com")) && ( (password.getText().toString().equals("ahmed12345*"))) )
        {


            Intent intent =new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            email.setText("");
            password.setText("");
        }
        else {
            // If email or password do not match, show a toast indicating incorrect credentials

            Toast.makeText(Login.this, "Email or Password incorrect", Toast.LENGTH_SHORT).show();
        }
    }
});


        }

}