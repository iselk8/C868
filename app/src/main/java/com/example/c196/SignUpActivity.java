package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.c196.DAO.UserDAO;
import com.example.c196.Entities.UserModel;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
    TextInputLayout signupName, signupUsername, signupPassword, signupConfirmPassword;
    ImageView backBtn;
    Database db;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Hooks
        signupName = (TextInputLayout) findViewById(R.id.signup_name_field);
        signupUsername = (TextInputLayout) findViewById(R.id.signup_username_field);
        signupPassword = (TextInputLayout) findViewById(R.id.signup_password_field);
        signupConfirmPassword = (TextInputLayout) findViewById(R.id.signup_confirm_password_field);
        backBtn = (ImageView) findViewById(R.id.signup_back_button);
        backBtn.setClickable(true);

        //Initialization of the database and the userDAO
        db = Database.getDatabase(getApplicationContext());
        userDAO = db.getUserDAO();

    }

    public void backBtnPressed(View v){
        super.finish();
    }

    public void signUp(View v) {
        //Text input validation for the name, username, password and password confirmation through the Utility class
        if (!Utility.isValidName(signupName) | !Utility.isValidUsername(signupUsername) | !Utility.isMatchingPassword(signupPassword, signupConfirmPassword))
            //If one of the field input is invalid
            return;
         else {
            //Get all the values from the TextInputLayout into Strings
            String name = signupName.getEditText().getText().toString().toLowerCase();
            String username = signupUsername.getEditText().getText().toString().toLowerCase();
            String password = signupPassword.getEditText().getText().toString();

            //Creating userModel to insert into the database
            UserModel userModel = new UserModel();
            userModel.setName(name);
            userModel.setUsername(username);
            userModel.setPassword(password);

            //Verifies if the username is already taken by another user to avoid conflicting primary keys
            if(!userDAO.isTaken(userModel.getUsername())){
                userDAO.insertUser(userModel);
                Utility.clearTextInputLayoutText(signupName, signupUsername, signupPassword, signupConfirmPassword);
                Toast.makeText(this, "Data Successfully saved", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
                super.finish();
            }else {
                //If the username is taken
                Utility.setUsernameTakenTextInputLayout(signupUsername);
                Toast.makeText(this, "Data Not saved!", Toast.LENGTH_LONG).show();
            }

        }

    }


    public void launchSignInScreen(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        super.finish();
    }

}