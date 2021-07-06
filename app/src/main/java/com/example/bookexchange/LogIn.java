package com.example.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private Button logInButton;
    private TextView createAccount2;
    private EditText logInUsername, logInPass;
    private Object Intent;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();



        logInButton = findViewById(R.id.logInButton);
        createAccount2 = findViewById(R.id.createAccount2);
        logInUsername = findViewById(R.id.logInUsername);
        logInPass = findViewById(R.id.logInPass);

        logInButton.setOnClickListener(this);
        createAccount2.setOnClickListener(this);
        logInUsername.setOnClickListener(this);
        logInPass.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logInButton:
                userLogin();
                break;
            case R.id.createAccount2:
                Intent intent = new Intent(getApplicationContext(), registration.class);
                startActivity(intent);
                break;
        }

    }

    private void userLogin() {

        String email = logInUsername.getText().toString().trim();
        String password = logInPass.getText().toString().trim();

        //checking the validity of the email
        if(email.isEmpty())
        {
            logInUsername.setError("Enter an email address");
            logInUsername.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            logInUsername.setError("Enter a valid email address");
            logInUsername.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            logInPass.setError("Enter a password");
            logInPass.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), homepage.class);
                    intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong Email or Password!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}