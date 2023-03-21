package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void launchLoginScreen(View view){
        Intent intent = new Intent(this, LogInScreenActivity.class);
        startActivity(intent);

    }

    public void launchSignUpScreen(View view){
        Intent intent = new Intent(this, SignUpScreenActivity.class);
        startActivity(intent);
    }
}