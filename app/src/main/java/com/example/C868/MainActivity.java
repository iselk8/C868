package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private final static int SPLASH_SCREEN = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting the activity to be fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Creating animation and setting their duration and off set for the fading out
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(2000);

        Animation fadeOut = new AlphaAnimation(1,0);
        fadeOut.setDuration(2000);
        fadeOut.setStartOffset(2000);

        // Creating the animation set that will hold our animations
        AnimationSet animations = new AnimationSet(true);
        animations.addAnimation(fadeIn);
        animations.addAnimation(fadeOut);

        // Hooks
        ImageView image = findViewById(R.id.wguLogo);

        // Setting animation on the ImageView
        image.setAnimation(animations);

        // Creating new intent and launch the next activity after the animation in done
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN);
    }
}