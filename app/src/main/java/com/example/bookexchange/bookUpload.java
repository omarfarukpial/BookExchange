package com.example.bookexchange;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class bookUpload extends AppCompatActivity implements View.OnClickListener {

    private Button chooseImageB, bookUploadB;
    private EditText bookNameUpload, authorNameUpload, genreNameUpload, isbnUpload;
    private ImageView bookImageUP;
    private Uri imageUri;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userNameHome;
    private static final int IMAGE_REQUEST = 1;

    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_upload);

        chooseImageB =findViewById(R.id.chooseImageB);
        bookUploadB = findViewById(R.id.bookUploadB);
        bookNameUpload = findViewById(R.id.bookNameUpload);
        authorNameUpload = findViewById(R.id.authorNameUpload);
        genreNameUpload = findViewById(R.id.genreNameUpload);
        isbnUpload = findViewById(R.id.isbnUpload);
        bookImageUP = findViewById(R.id.bookImageUP);

        databaseReference = FirebaseDatabase.getInstance().getReference("bookInfo");
        storageReference = FirebaseStorage.getInstance().getReference("bookInfo");






        chooseImageB.setOnClickListener(this);
        bookUploadB.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseImageB:
                    bookImageChooser();
                break;
            case R.id.bookUploadB:

                    uploadBookInfo();
                    finish();
                    Intent intent = new Intent(bookUpload.this, homepage.class);
                    startActivity(intent);

                break;
        }
    }



    private void bookImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData()!= null) {

            imageUri = data.getData();
            Picasso.get().load(imageUri).into(bookImageUP);
        }
    }

    public String getFileExtension (Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }


    private void uploadBookInfo() {
        String bookName = bookNameUpload.getText().toString();
        String authorName = authorNameUpload.getText().toString();
        String genreName = genreNameUpload.getText().toString();
        String isbn = isbnUpload.getText().toString();

        DatabaseReference userName = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("name");
        userName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 userNameHome = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        StorageReference ref = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Image stored Successfully!", Toast.LENGTH_LONG).show();

                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();

                        bookInfo bookData = new bookInfo(downloadUrl.toString(), bookName, authorName,genreName,isbn, userNameHome);

                        String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(bookData);
                        //databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).setValue(bookData);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Image stored Unsuccessfully!", Toast.LENGTH_LONG).show();

                    }
                });



    }


}