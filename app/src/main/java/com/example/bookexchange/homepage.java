package com.example.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class homepage extends AppCompatActivity implements View.OnClickListener {


    private Button profileB, uploadB;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<bookInfo> bookList;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        profileB = findViewById(R.id.profileB);
        uploadB = findViewById(R.id.uploadB);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        bookList = new ArrayList<>();


        databaseReference = FirebaseDatabase.getInstance().getReference("bookInfo");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList.clear();

                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    bookInfo book = snapshot1.getValue(bookInfo.class);
                    bookList.add(book);

                }

                myAdapter = new MyAdapter(homepage.this, bookList);
                recyclerView.setAdapter(myAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        profileB.setOnClickListener(this);
        uploadB.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profileB:
                Intent intent = new Intent(getApplicationContext(), profile.class);
                startActivity(intent);
                break;

            case R.id.uploadB:
                Intent intent1 = new Intent(getApplicationContext(), bookUpload.class);
                startActivity(intent1);
                break;


        }
    }
}