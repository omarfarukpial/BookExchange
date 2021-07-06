package com.example.bookexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();



        if (mAuth.getCurrentUser() != null) {
            finish();

            Intent intent = new Intent(getApplicationContext(), homepage.class);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
        else {
            finish();
            Intent in = new Intent(getApplicationContext(), LogIn.class);
            in.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);

        }




    }
}