package com.example.nitcmag_e.getStarted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nitcmag_e.R;

public class splashScreen extends AppCompatActivity {

    TextView title;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        title = findViewById(R.id.textViewSplash);
        image = findViewById(R.id.imageViewSplash);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
        //image.startAnimation(animation);
        title.startAnimation(animation);

        new Handler().postDelayed(() -> {
            Intent i = new Intent(splashScreen.this,getStartedPage.class);
            startActivity(i);
            finish();

        },5000);

    }
}