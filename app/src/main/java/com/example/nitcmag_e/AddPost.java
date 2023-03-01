package com.example.nitcmag_e;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AddPost extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        signout = findViewById(R.id.button2);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(AddPost.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}