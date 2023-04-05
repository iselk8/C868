package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Dashboard extends AppCompatActivity {

    String username;
    ImageView logout, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Bundle extras = getIntent().getExtras();

        //Hooks
        logout = (ImageView) findViewById(R.id.dashboard_logout_btn);
        logout.setClickable(true);

        search = (ImageView) findViewById(R.id.dashboard_search_btn);
        search.setClickable(true);

        if(extras != null){
            username = extras.getString("username");
        }
    }

    public void launchSearch(View view){

        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);

    }

    public void logOutBtn(View view){
        logout();
    }

    @Override
    public void onBackPressed(){
        logout();
    }

    private void logout() {
        //Opens dialog box to confirm the user would like to log out before closing this activity
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    new AlertDialog.Builder(Dashboard.this).setTitle("Log out")
                            .setMessage("Are you sure you would like to log out?")
                            .setCancelable(true).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Dashboard.super.finish();
                                }
                            }).show();
                }
            }
        });
    }

    public void launchTerms(View view){
        Intent intent = new Intent(this, DisplayData.class);
        intent.putExtra("username", username);
        intent.putExtra("data type", "term");
        startActivity(intent);
    }

    public void launchCourses(View view){
        Intent intent = new Intent(this, DisplayData.class);
        intent.putExtra("username", username);
        intent.putExtra("data type", "course");
        startActivity(intent);
    }

    public void launchAssessments(View view){
        Intent intent = new Intent(this, DisplayData.class);
        intent.putExtra("username", username);
        intent.putExtra("data type", "assessment");
        startActivity(intent);
    }
}