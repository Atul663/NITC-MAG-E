package com.example.nitcmag_e.Login_and_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nitcmag_e.MainActivity;
import com.example.nitcmag_e.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    EditText username,password;
    Button signin, signUp;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

    username = findViewById(R.id.editTextTextEmailAddress);
    password = findViewById(R.id.editTextTextPassword);
    signin = findViewById(R.id.button);
    signUp = findViewById(R.id.signUpButtonLogin);

    signin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String userEmailId = username.getText().toString();
            String userPassword = password.getText().toString();

            if (userEmailId.isEmpty() && userPassword.isEmpty()) {
                Toast.makeText(LoginPage.this, "Please enter the Email id and Password", Toast.LENGTH_SHORT).show();
            } else if (userEmailId.isEmpty()) {
                Toast.makeText(LoginPage.this, "Please enter a Email id", Toast.LENGTH_SHORT).show();

            } else if (userPassword.isEmpty()) {
                Toast.makeText(LoginPage.this, "Please enter a password", Toast.LENGTH_SHORT).show();
            } else if (Patterns.EMAIL_ADDRESS.matcher(userEmailId).matches()) {
                signInWithFirebase(userEmailId, userPassword);
            } else {
                Toast.makeText(LoginPage.this, "Please enter a valid email id", Toast.LENGTH_SHORT).show();
            }
        }
    });

    signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginPage.this, SignUpPage.class);
            startActivity(intent);
        }
    });
    }

    private void signInWithFirebase(String userEmailId, String userPassword) {
        auth.signInWithEmailAndPassword(userEmailId,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    if(auth.getCurrentUser().isEmailVerified())
                    {
                        Toast.makeText(LoginPage.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginPage.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(LoginPage.this, "Please verify your email. ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}


