package com.example.nitcmag_e.Login_and_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitcmag_e.MainActivityPages.MainActivity2;
import com.example.nitcmag_e.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity {

    EditText username,password;
    Button signin;

    TextView signUp, forgotPassword;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

    username = findViewById(R.id.editTextTextEmailAddress);
    password = findViewById(R.id.editTextTextPassword);
    signin = findViewById(R.id.button);
    signUp = findViewById(R.id.signUpButtonLogin);
    forgotPassword = findViewById(R.id.textViewForgotPassword);

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

    forgotPassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

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
                if(task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();

                    if(user.getUid().equals("UO9GbdDNhVPuWDvcofJRoWLeJH72"))
                    {
                        System.out.println("hii");
                        Toast.makeText(LoginPage.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginPage.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(auth.getCurrentUser().isEmailVerified())
                    {
                        Toast.makeText(LoginPage.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginPage.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginPage.this, "Please verify your email.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}


