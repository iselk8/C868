package com.example.c196;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

//Utility class to avoid code repetition for repetitive tasks
public class Utility {

    static final int ERROR_HEX_COLOR = -43230;

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    //Validates a Name. Name must be 3 to 12 letters long
    public static boolean isValidName(TextInputLayout nameField) {
        String name = nameField.getEditText().getText().toString();
        String nameRegex = "^[a-zA-Z]{3,12}$";
        if (name.isEmpty()) {
            nameField.setError("Field cannot be empty");
            nameField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        } else if (!name.matches(nameRegex)) {
            nameField.setError("Name must be between 3 and 12 letters long");
            nameField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        } else {
            nameField.setError(null);
            nameField.setErrorEnabled(false);
            nameField.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
            return true;
        }
    }

    //Validates a username. username must be 4 to 12 characters long and cannot contain whitespaces
    public static boolean isValidUsername(TextInputLayout usernameField){
        String username = usernameField.getEditText().getText().toString();
        String noWhiteSpaceRegex = "\\A\\w{4,12}\\z";

        if (username.isEmpty()) {
            usernameField.setError("Field cannot be empty");
            usernameField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        } else if (!username.matches(noWhiteSpaceRegex)) {
            usernameField.setError("Username must be between 4 and 12 characters long and cannot contain whitespaces");
            usernameField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        }else {
            usernameField.setError(null);
            usernameField.setErrorEnabled(false);
            usernameField.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
            return true;
        }
    }

    //Validates a Password. Password must be 4 to 15 characters long and cannot contain whitespaces
    public static boolean isValidPassword(TextInputLayout passwordField){
        String password = passwordField.getEditText().getText().toString();
        String passwordRequirements = "^[^\\s]{4,}$";

        if (password.isEmpty()) {
            passwordField.setError("Field cannot be empty");
            passwordField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        } else if (!password.matches(passwordRequirements)) {
            passwordField.setError("Password must contain at least 4 characters and cannot contain whitespaces");
            passwordField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        } else {
            passwordField.setError(null);
            passwordField.setErrorEnabled(false);
            passwordField.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
            return true;
        }
    }

    //Validates if pair of passwords are matching
    public static boolean isMatchingPassword(TextInputLayout passwordField, TextInputLayout confirmPasswordField){
        if(isValidPassword(passwordField) | isValidPassword(confirmPasswordField)){
            if(!passwordField.getEditText().getText().toString().matches(confirmPasswordField.getEditText().getText().toString())){
                passwordField.setError("Passwords do not match");
                passwordField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
                confirmPasswordField.setError("Passwords do not match");
                confirmPasswordField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
                return false;
            }else {
                passwordField.setError(null);
                passwordField.setErrorEnabled(false);
                passwordField.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
                confirmPasswordField.setError(null);
                confirmPasswordField.setErrorEnabled(false);
                confirmPasswordField.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
                return true;
            }
        }else {
            return false;
        }
    }

    //Validates a Title. Title must be 3 to 12 characters long
    public static boolean isValidTitle(TextInputLayout titleField){
        String title = titleField.getEditText().getText().toString();
        String titleRegex = "^.{3,12}$";

        if(title.isEmpty()){
            titleField.setError("Field cannot be empty");
            titleField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        } else if (!title.matches(titleRegex)) {
            titleField.setError("Title must be between 3 and 12 characters long");
            titleField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        } else{
            titleField.setError(null);
            titleField.setErrorEnabled(false);
            titleField.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
            return true;
        }

    }

    //Validates if pair of dates are valid. End date must be after the Start date
    public static boolean isValidDateCombination(DatePicker startDatePicker, DatePicker endDatePicker, TextView dateError){
        int startYear = startDatePicker.getYear();
        int startMonth = startDatePicker.getMonth()+1;
        int startDayOfMonth = startDatePicker.getDayOfMonth();
        int endYear = endDatePicker.getYear();
        int endMonth = endDatePicker.getMonth()+1;
        int endDayOfMonth = endDatePicker.getDayOfMonth();

        LocalDate startDate;
        LocalDate endDate;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startDate = LocalDate.of(startYear, startMonth, startDayOfMonth);
                endDate = LocalDate.of(endYear, endMonth, endDayOfMonth);
                if(startDate.isAfter(endDate) | startDate.isEqual(endDate)) {
                    dateError.setAlpha(1.0f);
                    dateError.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    return false;
                } else {
                    dateError.setAlpha(0.0f);
                    dateError.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);
                    return true;
                }
            }else
                return false;
    }

    //Validates a Phone number. Phone number must be between 3 and 20 digits long
    public static boolean isValidPhone(TextInputLayout phoneField){
        String phone = phoneField.getEditText().getText().toString();
        String phoneRegex = "^.{3,20}$";

        if(phone.isEmpty()){
            phoneField.setError("Field cannot be empty");
            phoneField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        } else if (!phone.matches(phoneRegex)) {
            phoneField.setError("Incorrect phone format");
            phoneField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        } else{
            phoneField.setError(null);
            phoneField.setErrorEnabled(false);
            phoneField.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
            return true;
        }
    }

    //Validates an email address. Email must match the email format
    public static boolean isValidEmail(TextInputLayout emailField){
        String email = emailField.getEditText().getText().toString();
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if(email.isEmpty()){
            emailField.setError("Field cannot be empty");
            emailField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        } else if (!email.matches(emailRegex)) {
            emailField.setError("Incorrect email format");
            emailField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        } else{
            emailField.setError(null);
            emailField.setErrorEnabled(false);
            emailField.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
            return true;
        }
    }

    //Validates a drop down selection
    public static boolean isValidDropDownSelection(TextInputLayout dropDownSelection, String[] items){
        String selection = dropDownSelection.getEditText().getText().toString();
        if(!Arrays.stream(items).anyMatch(str -> str.equals(selection))){
            dropDownSelection.setError("Invalid selection");
            dropDownSelection.setEndIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
            return false;
        }else{
            dropDownSelection.setError(null);
            dropDownSelection.setEndIconTintList(ColorStateList.valueOf(Color.WHITE));
            dropDownSelection.setErrorEnabled(false);
            return true;
        }
    }

    //Validates radio group selection
    public static boolean isValidRadioSelection(RadioGroup radioGroup, TextView selectionError){
        if(radioGroup.getCheckedRadioButtonId() == -1) {
            selectionError.setAlpha(1.0f);
            selectionError.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            return false;
        } else {
            selectionError.setAlpha(0.0f);
            selectionError.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);
            return true;
        }
    }

    //Capitalize a String
    public static String capitalizeString(String str){
        String capitalized = str.substring(0,1).toUpperCase() + str.substring(1);
        return capitalized;
    }

    //Capitalize first letter of every word in a String
    public static String toTitleCase(String str){
        String[] arr = str.split(" ");
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < arr.length; i++)
            output.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
        return output.toString().trim();
    }

    //Capitalize each String in a String[]
    public static String [] capitalizeArray(String [] array){
        String [] capitalized = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            capitalized[i] = capitalizeString(array[i]);
        }
        return capitalized;
    }

    //Clears Text input layout fields
    public static void clearTextInputLayoutText(TextInputLayout... textFields){
        for (TextInputLayout t : textFields) {
            t.getEditText().getText().clear();
        }
    }

    //Set username taken error
    public static void setUsernameTakenTextInputLayout(TextInputLayout usernameField){
        usernameField.setError("Username already taken, please try something different.");
        usernameField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
    }

    //Set incorrect password error
    public static void setIncorrectLoginTextInputLayout(TextInputLayout usernameField, TextInputLayout passwordField){
        usernameField.setError("Incorrect password or username");
        usernameField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
        passwordField.setError("Incorrect password or username");
        passwordField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
    }

    //Set title taken error
    public static void setTitleTakenTextInputLayout(TextInputLayout titleField, String type){
        titleField.setError(type + " title already exists");
        titleField.setStartIconTintList(ColorStateList.valueOf(ERROR_HEX_COLOR));
    }


    public static Long getTimeInMilliSeconds(String dateVal){
        try{
            Date date = dateFormat.parse(dateVal + TimeZone.getDefault().getDisplayName());
            return date.getTime();
        } catch (ParseException e) {
            return 0l;
        }
    }

    public static long getCurrentDate(){
        String currentDate = dateFormat.format(new Date());
        return getTimeInMilliSeconds(currentDate);
    }

}
