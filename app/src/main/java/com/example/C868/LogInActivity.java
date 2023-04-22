package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.c196.DAO.UserDAO;
import com.google.android.material.textfield.TextInputLayout;

public class LogInActivity extends AppCompatActivity {

    TextInputLayout loginUsername, loginPassword;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Hooks
        loginUsername = (TextInputLayout) findViewById(R.id.login_username_field);
        loginPassword = (TextInputLayout) findViewById(R.id.login_password_field);
        ImageView backBtn = (ImageView) findViewById(R.id.login_back_button);
        backBtn.setClickable(true);

        //Initialization of the userDAO
        userDAO = Database.getDatabase(getApplicationContext()).getUserDAO();

    }

    public void backBtnPressed(View v){
        super.finish();
    }

    public void authenticateUser(View view){
        //Text input validation for the username and password through the Utility class
        if(!Utility.isValidUsername(loginUsername) | !Utility.isValidPassword(loginPassword)){
            //If username or password inputs are invalid
            return;
        }else {
            //Grabbing the values from the text input fields
            String username = loginUsername.getEditText().getText().toString().toLowerCase();
            String password = loginPassword.getEditText().getText().toString();

            //attempt to login through the userDAO
            if(userDAO.login(username, password)){
                Utility.clearTextInputLayoutText(loginUsername, loginPassword);
                Toast.makeText(this, "Logged in as " + username, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Dashboard.class);
                intent.putExtra("username", username);
                intent.putExtra("name", userDAO.getUserFirstName(username));
                startActivity(intent);
            }else {
                //If the login attempt is not successful
                Utility.setIncorrectLoginTextInputLayout(loginUsername, loginPassword);
                Toast.makeText(this, "Login Failed!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void launchSignUp(View view){
        // Creating new intent and launching the sign up activity
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        super.finish();
    }
}