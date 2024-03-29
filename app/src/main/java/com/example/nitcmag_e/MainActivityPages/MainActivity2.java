package com.example.nitcmag_e.MainActivityPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.nitcmag_e.PostArticle.AddPostFragement;
import com.example.nitcmag_e.Login_and_signup.LoginPage;
import com.example.nitcmag_e.Login_and_signup.SignUpPage;
import com.example.nitcmag_e.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity2 extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ImageView navigationDrawerIcon;
    ActionBarDrawerToggle toggle;

    TextView emailId,role;
    CircleImageView profileProfilePicture;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        navigationDrawerIcon = findViewById(R.id.navigationDrawerIcon);

        View view = navigationView.getHeaderView(0);

        emailId = view.findViewById(R.id.textViewEmailNavDrawer);
        role = view.findViewById(R.id.textViewRoleNavDrawer);
        profileProfilePicture = view.findViewById(R.id.profilePictureNavDraver);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content, new MainFragment());
        ft.commit();


        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        headerDetails();

        navigationDrawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

//        System.out.println(user.getUid());


        findRole();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.signInNavDrawer)
                {
                    Intent intent = new Intent(MainActivity2.this, LoginPage.class);
                    startActivity(intent);
                } else if (id == R.id.signUpNavDrawer) {
                    Intent intent = new Intent(MainActivity2.this, SignUpPage.class);
                    startActivity(intent);
                } else if (id == R.id.addPostNavDrawer) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content, new AddPostFragement());
                    ft.commit();
                }else {
                    auth.signOut();
                    Intent intent = new Intent(MainActivity2.this,LoginPage.class);
                    startActivity(intent);
                    finishAffinity();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }// END OF ONCREATE METHOD


    //USER DEFINE FUNCTION START

    void headerDetails()
    {
        if(user != null) {
            reference.child("User").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    emailId.setText(snapshot.child("email").getValue().toString());
                    role.setText(snapshot.child("role").getValue().toString());
                    String profilePicture = snapshot.child("profilePictures").getValue().toString();
                    System.out.println(profilePicture);
                    if(profilePicture == null)
                    {
                        profileProfilePicture.setImageResource(R.drawable.ic_launcher_background);
                    }
                    else
                    {
                        Picasso.get().load(profilePicture).into(profileProfilePicture);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
//            auth.signOut();
        }

        else {
            profileProfilePicture.setImageResource(R.drawable.ic_launcher_background);
            emailId.setText("Guest");
            role.setText("Guest");
        }
    }



    private void inflateMenu(char userRole) {
        if(userRole == 's')
        {
            navigationView.inflateMenu(R.menu.navigation_item_user);
        }
        else if(userRole == 'A')
        {
            navigationView.inflateMenu(R.menu.navigation_item_admin);
        }
    }

    void findRole()
    {

        if(user != null) {
             char[] userRole = new char[1];
            reference.child("User").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userRole[0] = snapshot.child("role").getValue().toString().charAt(0);
                    inflateMenu(userRole[0]);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else
            inflateMenu('s');
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}