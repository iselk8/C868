package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.Entities.CourseModel;
import com.example.c196.Entities.TermModel;
import com.example.c196.Repositories.CourseRepository;
import com.example.c196.Repositories.TermRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AddCourse extends AppCompatActivity {
    String username;
    final String[] status_items = {"Plan To Take", "In Progress", "Completed", "Dropped"};
    String[] dynamic_terms;
    ArrayAdapter<String> adapterStatusItems;
    ArrayAdapter<String> adapterTermItems;
    AutoCompleteTextView autoCompleteStatus;
    AutoCompleteTextView autoCompleteTerm;
    ImageView backBtn;
    TextInputLayout addCourseTitle, addCourseInstructorName, addCourseInstructorPhone, addCourseInstructorEmail, addCourseSelectStatus, addCourseSelectTerm, addCourseNote;
    TextView dateError;
    DatePicker addCourseStartDate, addCourseEndDate;
    TermRepository termRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        //Hooks
        addCourseTitle = (TextInputLayout) findViewById(R.id.add_course_title_field);
        addCourseInstructorName = (TextInputLayout) findViewById(R.id.add_course_instructor_name_field);
        addCourseInstructorPhone = (TextInputLayout) findViewById(R.id.add_course_instructor_phone_number_field);
        addCourseInstructorEmail = (TextInputLayout) findViewById(R.id.add_course_instructor_email_field);
        addCourseSelectStatus = (TextInputLayout) findViewById(R.id.add_course_select_status);
        addCourseSelectTerm = (TextInputLayout) findViewById(R.id.add_course_select_term);
        addCourseStartDate = (DatePicker) findViewById(R.id.add_course_start_date);
        addCourseEndDate = (DatePicker) findViewById(R.id.add_course_end_date);
        addCourseNote = (TextInputLayout) findViewById(R.id.add_course_note);
        dateError = (TextView) findViewById(R.id.add_course_date_error);
        dateError.setAlpha(0.0f);
        dateError.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);
        backBtn = findViewById(R.id.add_course_back_button);
        backBtn.setClickable(true);

        //Reception of the values sent by the previous intent
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            username = extras.getString("username");

        //Initializing the term repository needed to populate the Select Term drop down list
        termRepo = new TermRepository(getApplication());

        //Building array to populate the dropdown list for the possible terms
        List<String> termTitleList = termRepo.getTermsByUsername(username).stream().map(TermModel::getTermTitle).collect(Collectors.toList());
        dynamic_terms = termTitleList.stream().toArray(String[]::new);
        dynamic_terms = Utility.capitalizeArray(dynamic_terms);

        //Populate the dropdown list for the possible course status
        autoCompleteStatus = (AutoCompleteTextView) findViewById(R.id.auto_complete_status);
        adapterStatusItems = new ArrayAdapter<String>(this, R.layout.list_item, status_items);
        autoCompleteStatus.setAdapter(adapterStatusItems);

        //On Item Click Listener for the status dropdown
        autoCompleteStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });


        //Populate the dropdown list for the possible terms
        autoCompleteTerm = findViewById(R.id.auto_complete_term);
        adapterTermItems = new ArrayAdapter<String>(this, R.layout.list_item, dynamic_terms);
        autoCompleteTerm.setAdapter(adapterTermItems);

        //On Item Click Listener for the term dropdown
        autoCompleteTerm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void backBtnPressed(View v){ back(); }

    @Override
    public void onBackPressed(){
        back();
    }

    private void back(){
        Intent intent = new Intent(this, DisplayData.class);
        intent.putExtra("username", username);
        intent.putExtra("data type", "course");
        startActivity(intent);
        super.finish();
    }

    public void addCourse(View view){

        //Verifying that android version is equals or higher than Android 8.0 before using the LocalDate class
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

           //Text input, drop down list and date validation through the Utility class
            if(!Utility.isValidTitle(addCourseTitle) | !Utility.isValidName(addCourseInstructorName) | !Utility.isValidPhone(addCourseInstructorPhone) |
                    !Utility.isValidEmail(addCourseInstructorEmail)| !Utility.isValidDropDownSelection(addCourseSelectStatus, status_items) |
                    !Utility.isValidDropDownSelection(addCourseSelectTerm, dynamic_terms) | !Utility.isValidDateCombination(addCourseStartDate, addCourseEndDate, dateError)){

                //If input fields are invalid
                Toast.makeText(this, "Operation failed", Toast.LENGTH_LONG).show();
                return;
            } else {

                //Reading values from the TextInputLayout, AutoCompleteTextView and DatePickers and storing them into variables
                String courseTitle = addCourseTitle.getEditText().getText().toString().toLowerCase();
                String courseInstructorName = addCourseInstructorName.getEditText().getText().toString().toLowerCase();
                String courseInstructorPhone = addCourseInstructorPhone.getEditText().getText().toString();
                String courseInstructorEmail = addCourseInstructorEmail.getEditText().getText().toString().toLowerCase();
                String courseStatus = addCourseSelectStatus.getEditText().getText().toString();
                String courseTerm = addCourseSelectTerm.getEditText().getText().toString();
                String courseNote = addCourseNote.getEditText().getText().toString();

                int startYear = addCourseStartDate.getYear();
                int startMonth = addCourseStartDate.getMonth()+1;
                int startDayOfMonth = addCourseStartDate.getDayOfMonth();
                int endYear = addCourseEndDate.getYear();
                int endMonth = addCourseEndDate.getMonth()+1;
                int endDayOfMonth = addCourseEndDate.getDayOfMonth();

                //Creating LocalDate objects with the input from the date picker inputs
                LocalDate courseStartDate = LocalDate.of(startYear, startMonth, startDayOfMonth);
                LocalDate courseEndDate = LocalDate.of(endYear, endMonth, endDayOfMonth);

                //Creating a course repository that will be used to insert a new course into the database
                CourseRepository courseRepo = new CourseRepository(getApplication());

                //Getting the term id associated with the term title selected in the drop down list
                int termID = termRepo.getTermIDByTitle(username, courseTerm.toLowerCase());

                //Creating a course model used to insert the data into a database row.
                //NOTE: We are using the constructor without the courseId because it will be auto generated by the database as a primary key
                CourseModel course = new CourseModel(username, courseTitle, courseInstructorName, courseInstructorPhone, courseInstructorEmail,courseStatus, termID, courseStartDate.toString(), courseEndDate.toString(), courseNote);

                //Verifies if the course title already exists for that username and term before inserting the course
                if(!courseRepo.isTaken(username, termID, courseTitle)) {
                    courseRepo.insertCourse(course);
                    Toast.makeText(this, "Course added", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, DisplayData.class);
                    intent.putExtra("username", username);
                    intent.putExtra("data type", "course");
                    startActivity(intent);
                    super.finish();
                }else{
                    //If course title is taken for this username and term
                    Utility.setTitleTakenTextInputLayout(addCourseTitle, "Course");
                }
            }
        }
    }
}