package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void launchLoginScreen(View view){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);

    }

    public void launchSignUpScreen(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}