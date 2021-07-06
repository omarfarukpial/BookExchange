package com.example.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity implements View.OnClickListener {


    private Button signOutButton;
    private  TextView nameProfileDB, ageProfileDB, emailProfileDB, phoneProfileDB;



    private FirebaseAuth mAuth;
    private FirebaseDatabase dbase;
    private DatabaseReference dbRef;
    private FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        signOutButton = findViewById(R.id.signOutButton);


        nameProfileDB = findViewById(R.id.nameProfileDB);
        ageProfileDB = findViewById(R.id.ageProfileDB);
        emailProfileDB = findViewById(R.id.emailProfileDB);
        phoneProfileDB = findViewById(R.id.phoneProfileDB);




        mAuth = FirebaseAuth.getInstance();
        dbase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("user");




        dbRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user profile = snapshot.getValue(user.class);
                String name = profile.name;
                String age = profile.age;
                String email = profile.email;
                String phone = profile.phone;

                nameProfileDB.setText("Name: "+name);
                ageProfileDB.setText("Age: "+age);
                emailProfileDB.setText("Email: "+email);
                phoneProfileDB.setText("Phone: "+phone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        signOutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signOutButton:
                signOut();
                break;
        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(getApplicationContext(),LogIn.class);
        startActivity(intent);
    }
}