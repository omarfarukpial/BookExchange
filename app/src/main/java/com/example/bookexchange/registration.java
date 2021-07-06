package com.example.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity implements View.OnClickListener {

    private Button RegistrationCreateAccount;
    private EditText RegistrationEmail, RegistrationPass, RegistrationConformPass, RegistrationName, RegistrationAge, RegistrationPhone;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        mDbase = FirebaseDatabase.getInstance();

        RegistrationCreateAccount = findViewById(R.id.RegistrationCreateAccount);
        RegistrationEmail = findViewById(R.id.RegistrationEmail);
        RegistrationPass = findViewById(R.id.RegistrationPass);
        RegistrationConformPass = findViewById(R.id.RegistrationConformPass);
        RegistrationName = findViewById(R.id.RegistrationName);
        RegistrationAge = findViewById(R.id.RegistrationAge);
        RegistrationPhone = findViewById(R.id.RegistrationPhone);


        RegistrationCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RegistrationCreateAccount:
                registrationMethod ();
                break;

        }
    }

    private void registrationMethod() {
        String email = RegistrationEmail.getText().toString().trim();
        String password = RegistrationPass.getText().toString().trim();
        String confirmPassword = RegistrationConformPass.getText().toString().trim();
        String name = RegistrationName.getText().toString();
        String age = RegistrationAge.getText().toString();
        String phone = RegistrationPhone.getText().toString();

        //checking the validity of the email
        if(email.isEmpty())
        {
            RegistrationEmail.setError("Enter an email address");
            RegistrationEmail.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            RegistrationEmail.setError("Enter a valid email address");
            RegistrationEmail.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            RegistrationPass.setError("Enter a password");
            RegistrationPass.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    user user = new user(name,age,phone,email);
                    FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Account created Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),homepage.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
                else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                        Toast.makeText(getApplicationContext(), "This Email is already registered!", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
}