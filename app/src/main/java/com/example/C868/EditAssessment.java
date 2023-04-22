package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

public class EditAssessment extends AppCompatActivity {
    boolean editMode;
    TextView typeError;
    Button editAssessmentBtn, deleteAssessmentBtn;
    RadioButton editAssessmentTypeObjective, editAssessmentTypePerformance;
    TextInputLayout editAssessmentTitle, editAssessmentSelectCourse;
    RadioGroup editAssessmentType;
    DatePicker editAssessmentDueDate;
    CourseRepository courseRepo;
    AssessmentRepository assessmentRepo;
    AutoCompleteTextView autoCompleteCourse;
    ImageView backBtn;
    String[] dynamic_courses;
    ArrayAdapter<String> adapterCourseItems;
    String username;
    int assessmentID, dueYear, dueMonth, dueDayOfMonth;
    AssessmentModel assessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);

        //Hooks
        editAssessmentTitle = (TextInputLayout) findViewById(R.id.edit_assessment_title_field);
        editAssessmentSelectCourse = (TextInputLayout) findViewById(R.id.edit_assessment_select_course);
        editAssessmentType = (RadioGroup) findViewById(R.id.edit_assessment_type_radio_group);
        editAssessmentTypeObjective = (RadioButton) findViewById(R.id.edit_assessment_objective_radio);
        editAssessmentTypePerformance = (RadioButton) findViewById(R.id.edit_assessment_performance_radio);
        editAssessmentDueDate = (DatePicker) findViewById(R.id.edit_assessment_due_date);
        editAssessmentBtn = (Button) findViewById(R.id.edit_assessment_btn);
        deleteAssessmentBtn = (Button) findViewById(R.id.delete_assessment_btn);
        typeError = (TextView) findViewById(R.id.edit_assessment_type_error);
        backBtn = (ImageView) findViewById(R.id.edit_assessment_back_button);
        backBtn.setClickable(true);

        //Setting edit mode to false because we start the activity in view mode
        editMode = false;

        setTransparent(typeError);

        //Reception of the values sent by the previous intent
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("username");
            assessmentID = extras.getInt("assessmentID");
        }

        //Initializing the assessment repository
        assessmentRepo = new AssessmentRepository(getApplication());

        //Initializing the course repository needed to populate the Select Course drop down list
        courseRepo = new CourseRepository(getApplication());

        //Building array to populate the dropdown list for the possible courses
        List<String> courseTitleList = courseRepo.getCoursesByUser(username).stream().map(CourseModel::getCourseTitle).collect(Collectors.toList());
        dynamic_courses = courseTitleList.stream().toArray(String[]::new);
        dynamic_courses = Utility.capitalizeArray(dynamic_courses);

        //Populate the dropdown list for the possible courses
        autoCompleteCourse = (AutoCompleteTextView) findViewById(R.id.edit_auto_complete_course);
        adapterCourseItems = new ArrayAdapter<String>(this, R.layout.list_item, dynamic_courses);
        autoCompleteCourse.setAdapter(adapterCourseItems);

        setFieldStatus(false);

        //On Item Click Listener for the status dropdown
        autoCompleteCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_LONG).show();
            }
        });

        //Retrieving the currently selected assessment
        assessment = assessmentRepo.getAssessmentByID(assessmentID);

        //Splitting the date into individual variables
        String[] dueDateSplitter = assessment.getAssessmentDueDate().split("-");
        dueYear = Integer.parseInt(dueDateSplitter[0]);
        dueMonth = Integer.parseInt(dueDateSplitter[1]) - 1;
        dueDayOfMonth = Integer.parseInt(dueDateSplitter[2]);

        //Setting the fields with the selected assessment values
        editAssessmentTitle.getEditText().setText(Utility.capitalizeString(assessment.getAssessmentTitle()));
        editAssessmentSelectCourse.getEditText().setText(Utility.capitalizeString(courseRepo.getCoursesByID(assessment.getCourseID()).getCourseTitle()));
        editAssessmentDueDate.updateDate(dueYear, dueMonth, dueDayOfMonth);
        selectRadioType(assessment);

        //Creating notification channel
        notificationChannel();

    }

    private void notificationChannel(){
        //Verifying that android version is equals or higher than Android 8.0 before creating notification channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Assessment Reminder";
            String description = "Assessment Reminder Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("C196", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void createNotification(View view){
        //Creating the strings needed to build the notification
        String date = assessment.getAssessmentDueDate();
        String notificationTitle = "Assessment is due today!";
        String assessmentTitle = assessment.getAssessmentTitle();
        //Schedule the notification for the start date
        scheduleNotification(assessment.getAssessmentID(), date, assessmentTitle, notificationTitle);
    }


    private void scheduleNotification(int courseId, String date, String assessmentTitle, String notificationTitle) {
        long currentDate = Utility.getCurrentDate();
        long notificationDate = Utility.getTimeInMilliSeconds(date);
        if(currentDate <= Utility.getTimeInMilliSeconds(date)){
            NotificationReceiver.scheduleAssessmentNotification(getApplicationContext(), courseId, notificationDate, notificationTitle, Utility.capitalizeString(assessmentTitle) + " is due for " + date);
        }
    }

    public void deleteAssessment(View view){
        //Verifying if edit mode is currently false
        if(!editMode) {
            //If we are not in edit mode, this button is treated as the delete button
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isFinishing()) {
                        new AlertDialog.Builder(EditAssessment.this).setTitle("Delete Assessment")
                                .setMessage("Are you sure you would like to delete this assessment")
                                .setCancelable(true).setNegativeButton("No", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteConfirmed();
                                    }
                                }).show();
                    }
                }
            });
        }else {
            //If edit mode is true, this button is treated as a "Cancel" button for the term edit
            //Resetting the input fields to their original value
            editAssessmentDueDate.updateDate(dueYear, dueMonth, dueDayOfMonth);
            editAssessmentTitle.getEditText().setText(Utility.capitalizeString(assessment.getAssessmentTitle()));
            editAssessmentTitle.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
            editAssessmentTitle.setError(null);
            editAssessmentTitle.setErrorEnabled(false);
            editAssessmentSelectCourse.getEditText().setText(Utility.capitalizeString(courseRepo.getCoursesByID(assessment.getCourseID()).getCourseTitle()));
            selectRadioType(assessment);
            setTransparent(typeError);

            editMode = false;
            setFieldStatus(false);
            editAssessmentBtn.setText("Edit Assessment");
            deleteAssessmentBtn.setText("Delete Assessment");
        }

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

    private void setTransparent(TextView textView) {
        textView.setAlpha(0.0f);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);
    }

    private void deleteConfirmed() {
        //This method is called when a user confirms that they wish to delete the item
        AssessmentRepository assessmentRepo = new AssessmentRepository(getApplication());
        assessmentRepo.deleteAssessment(assessmentID);
        Intent intent = new Intent(this, DisplayData.class);
        intent.putExtra("username", username);
        intent.putExtra("data type", "assessment");
        startActivity(intent);
        super.finish();
    }

    public void editAssessment(View view){
        //Verifying is edit mode is currently false
        if(!editMode){
            //If edit mode is false, we set it to true and enable the fields to be edited
            editMode = true;
            setFieldStatus(true);

            List<String> courseTitleList = courseRepo.getCoursesByUser(username).stream().map(CourseModel::getCourseTitle).collect(Collectors.toList());
            dynamic_courses = courseTitleList.stream().toArray(String[]::new);
            dynamic_courses = Utility.capitalizeArray(dynamic_courses);

            autoCompleteCourse = (AutoCompleteTextView) findViewById(R.id.edit_auto_complete_course);
            adapterCourseItems = new ArrayAdapter<String>(this, R.layout.list_item, dynamic_courses);
            autoCompleteCourse.setAdapter(adapterCourseItems);

            editAssessmentBtn.setText("Save Changes");
            deleteAssessmentBtn.setText("Cancel");
        }else {
            //If edit mode is true and this button is clicked, we are treating this button as the "Save changes"
            // Grabbing the values from the edited fields
            String updatedAssessmentTitle = editAssessmentTitle.getEditText().getText().toString().toLowerCase();
            String updatedAssessmentCourse = editAssessmentSelectCourse.getEditText().getText().toString().toLowerCase();
            int courseID = assessment.getCourseID();
            int termID = assessment.getTermID();
            int selectedID = editAssessmentType.getCheckedRadioButtonId();
            RadioButton selection = (RadioButton) findViewById(selectedID);

            int updatedDueYear = editAssessmentDueDate.getYear();
            int updatedDueMonth = editAssessmentDueDate.getMonth() + 1;
            int updatedDueDayOfMonth = editAssessmentDueDate.getDayOfMonth();

            //Verifying that android version is equals or higher than Android 8.0 before using the LocalDate class
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate updatedDueDate = LocalDate.of(updatedDueYear, updatedDueMonth, updatedDueDayOfMonth);

                //Text input and date validation through the Utility class
                if(!Utility.isValidTitle(editAssessmentTitle) | !Utility.isValidDropDownSelection(editAssessmentSelectCourse, dynamic_courses) | !Utility.isValidRadioSelection(editAssessmentType, typeError)){
                    // If one of the input field  is invalid, the operation will fail and display the appropriate error message
                    Toast.makeText(this, "Operation failed", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    //If the input values are valid, we create a new AssessmentModel with the current Course ID, Term ID, Assessment ID and username and insert it using the assessment repository.
                    AssessmentModel updatedAssessment = new AssessmentModel(assessmentID, username, termID, updatedAssessmentTitle, courseID, selection.getText().toString(), updatedDueDate.toString());

                    //Validation to see if the user already has an assessment with the same name for the same course
                    if(!assessmentRepo.isTaken(username, courseID, updatedAssessmentTitle) || (updatedAssessmentTitle.equals(assessment.getAssessmentTitle()))){
                        // Confirming the changes to the assessment and updating the record in the database
                        assessmentRepo.insertAssessment(updatedAssessment);
                        Toast.makeText(this, "Assessment edited", Toast.LENGTH_LONG).show();
                        editMode = false;
                        setFieldStatus(false);
                        editAssessmentDueDate.updateDate(updatedDueYear, updatedDueMonth, updatedDueDayOfMonth);
                        editAssessmentTitle.getEditText().setText(updatedAssessmentTitle);
                        editAssessmentSelectCourse.getEditText().setText(updatedAssessmentCourse);
                        selectRadioType(updatedAssessment);
                        Intent intent = new Intent(this, DisplayData.class);
                        intent.putExtra("username", username);
                        intent.putExtra("data type", "assessment");
                        startActivity(intent);
                        super.finish();
                    }else{
                        //If assessment title is taken for this username and course
                        Utility.setTitleTakenTextInputLayout(editAssessmentTitle, "Assessment");
                    }
                }
            }

        }
    }

    private void setFieldStatus(boolean status){
        editAssessmentTitle.setEnabled(status);
        editAssessmentSelectCourse.setEnabled(status);
        editAssessmentTypeObjective.setEnabled(status);
        editAssessmentTypePerformance.setEnabled(status);
        editAssessmentDueDate.setEnabled(status);
    }

    private void selectRadioType(AssessmentModel assessment){
        if(assessment.getAssessmentType().equals("Objective"))
            editAssessmentTypeObjective.setChecked(true);
        if(assessment.getAssessmentType().equals("Performance"))
            editAssessmentTypePerformance.setChecked(true);

    }
}