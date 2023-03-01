package com.example.nitcmag_e;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nitcmag_e.Login_and_signup.LoginPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    AlertDialog dialog;
    TabLayout tabLayout;
    TabItem home,sport,educational,fest,technical;
    PagerAdapter pagerAdapter;
    ViewPager viewPager;
    Toolbar toolbar;
    FloatingActionButton addPost;

    EditText username,password;
    Button signin;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tabLayout);
        home = findViewById(R.id.home);
        sport = findViewById(R.id.sport);
        educational = findViewById(R.id.educational);
        fest = findViewById(R.id.fest);
        technical = findViewById(R.id.technical);

        addPost = findViewById(R.id.floatingActionButtonAddPost);

        viewPager = findViewById(R.id.fragment);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 5);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2 || tab.getPosition() == 3 || tab.getPosition() == 4 || tab.getPosition() == 5)
                {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null)
                {
                    Intent intent = new Intent(MainActivity.this, AddPost.class);
                    startActivity(intent);
                }

                else
                {
                    AlertDialog.Builder dialogLogin = new AlertDialog.Builder(MainActivity.this);
                    View loginView = getLayoutInflater().inflate(R.layout.dialog_login,null);

                    username = loginView.findViewById(R.id.editTextDialogUsername);
                    password = loginView.findViewById(R.id.editTextDialogPassword);
                    signin = loginView.findViewById(R.id.buttonDialogLogin);
                    dialogLogin.setView(loginView);
                    dialog = dialogLogin.create();
                    dialog.show();
                    signin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String userEmailId = username.getText().toString();
                            String userPassword = password.getText().toString();

                            if (userEmailId.isEmpty() && userPassword.isEmpty()) {
                                Toast.makeText(MainActivity.this, "Please enter the Email id and Password", Toast.LENGTH_SHORT).show();
                            } else if (userEmailId.isEmpty()) {
                                Toast.makeText(MainActivity.this, "Please enter a Email id", Toast.LENGTH_SHORT).show();

                            } else if (userPassword.isEmpty()) {
                                Toast.makeText(MainActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                            } else if (Patterns.EMAIL_ADDRESS.matcher(userEmailId).matches()) {
                                signInWithFirebase(userEmailId, userPassword);
                            } else {
                                Toast.makeText(MainActivity.this, "Please enter a valid email id", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
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
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, AddPost.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this, "Please verify your email.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

}