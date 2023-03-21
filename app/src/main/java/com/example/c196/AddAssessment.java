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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.Entities.AssessmentModel;
import com.example.c196.Entities.CourseModel;
import com.example.c196.Repositories.AssessmentRepository;
import com.example.c196.Repositories.CourseRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AddAssessment extends AppCompatActivity {
    String username;
    String[] dynamic_courses;
    ArrayAdapter<String> adapterCourseItems;
    AutoCompleteTextView autoCompleteCourse;
    ImageView backBtn;
    TextInputLayout addAssessmentTitle, addAssessmentSelectCourse;
    RadioGroup addAssessmentRadioGroup;
    TextView selectionError;
    DatePicker addAssessmentDueDate;
    CourseRepository courseRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        //Hooks
        addAssessmentTitle = (TextInputLayout) findViewById(R.id.add_assessment_title_field);
        addAssessmentSelectCourse = (TextInputLayout) findViewById(R.id.add_assessment_select_course);
        addAssessmentDueDate = (DatePicker) findViewById(R.id.add_assessment_due_date);
        addAssessmentRadioGroup = (RadioGroup) findViewById(R.id.add_assessment_type_radio_group);
        selectionError = (TextView) findViewById(R.id.add_assessment_type_error);
        selectionError.setAlpha(0.0f);
        selectionError.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);
        backBtn = findViewById(R.id.add_assessment_back_button);
        backBtn.setClickable(true);

        //Reception of the values sent by the previous intent
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            username = extras.getString("username");

        //Initializing the course repository needed to populate the Select Course drop down list
        courseRepo = new CourseRepository(getApplication());

        //Populate the dropdown list for the possible courses
        List<String> courseTitleList = courseRepo.getCoursesByUser(username).stream().map(CourseModel::getCourseTitle).collect(Collectors.toList());
        dynamic_courses = courseTitleList.stream().toArray(String[]::new);
        dynamic_courses = Utility.capitalizeArray(dynamic_courses);

        //Populate the drop down list for the possible course options
        autoCompleteCourse = (AutoCompleteTextView) findViewById(R.id.auto_complete_course);
        adapterCourseItems = new ArrayAdapter<String>(this, R.layout.list_item, dynamic_courses);
        autoCompleteCourse.setAdapter(adapterCourseItems);

        //On Item Click Listener for the course dropdown
        autoCompleteCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private void back() {
        Intent intent = new Intent(this, DisplayData.class);
        intent.putExtra("username", username);
        intent.putExtra("data type", "assessment");
        startActivity(intent);
        super.finish();
    }

    public void addAssessment(View view){

        //Verifying that android version is equals or higher than Android 8.0 before using the LocalDate class
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            //Text input, drop down list and radio group validation through the Utility class
            if(!Utility.isValidTitle(addAssessmentTitle) | !Utility.isValidDropDownSelection(addAssessmentSelectCourse, dynamic_courses) | !Utility.isValidRadioSelection(addAssessmentRadioGroup, selectionError)){

                //If input fields are invalid
                Toast.makeText(this, "Operation Failed", Toast.LENGTH_LONG).show();
                return;
            }else {

                //Reading values from the TextInputLayout, AutoCompleteTextView and DatePickers and storing them into variables
                String assessmentTitle = addAssessmentTitle.getEditText().getText().toString().toLowerCase();
                String assessmentCourse = addAssessmentSelectCourse.getEditText().getText().toString().toLowerCase();

                int dueDateYear = addAssessmentDueDate.getYear();
                int dueDateMonth = addAssessmentDueDate.getMonth()+1;
                int dueDateDayOfMonth = addAssessmentDueDate.getDayOfMonth();

                //Creating LocalDate objects with the input from the date picker input
                LocalDate dueDate = LocalDate.of(dueDateYear, dueDateMonth, dueDateDayOfMonth);

                //Creating an assessment repository that will be used to insert a new assessment into the database
                AssessmentRepository assessmentRepo = new AssessmentRepository(getApplication());

                //Getting the course id associated with the course title selected in the drop down list
                int courseID = courseRepo.getCourseIDByTitle(username, assessmentCourse);

                //Getting the term id associated with the course title selected in the drop down list
                int termID = courseRepo.getCoursesByID(courseID).getTermId();

                //Getting the selection in the radio group
                int selectedId = addAssessmentRadioGroup.getCheckedRadioButtonId();
                RadioButton selection = (RadioButton) findViewById(selectedId);

                //Creating an assessment model used to insert the data into a database row.
                //NOTE: We are using the constructor without the assessmentId because it will be auto generated by the database as a primary key
                AssessmentModel assessment = new AssessmentModel(username, termID, assessmentTitle, courseID, selection.getText().toString(), dueDate.toString());

                //Verifies if the assessment title already exists for that username and course before inserting the course
                if(!assessmentRepo.isTaken(username, courseID, assessmentTitle)){
                    assessmentRepo.insertAssessment(assessment);
                    Toast.makeText(this, "Assessment added", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, DisplayData.class);
                    intent.putExtra("username", username);
                    intent.putExtra("data type", "assessment");
                    startActivity(intent);
                    super.finish();
                }else{
                    //If assessment title is taken for this username and course
                    Utility.setTitleTakenTextInputLayout(addAssessmentTitle, "Assessment");
                }
            }
        }
    }
}