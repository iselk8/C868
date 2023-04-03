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

    ImageView image;
    Animation fadeIn;
    Animation fadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(2000);

        fadeOut = new AlphaAnimation(1,0);
        fadeOut.setDuration(2000);
        fadeOut.setStartOffset(2000);

        AnimationSet animations = new AnimationSet(true);
        animations.addAnimation(fadeIn);
        animations.addAnimation(fadeOut);

        image = findViewById(R.id.wguLogo);
        image.setAnimation(animations);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }
}