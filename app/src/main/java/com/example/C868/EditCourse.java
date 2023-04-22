package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.Adapter.AssessmentAdapter;
import com.example.c196.Entities.AssessmentModel;
import com.example.c196.Entities.CourseModel;
import com.example.c196.Entities.TermModel;
import com.example.c196.Repositories.AssessmentRepository;
import com.example.c196.Repositories.CourseRepository;
import com.example.c196.Repositories.TermRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EditCourse extends AppCompatActivity {
    Button editCourseBtn, deleteCourseBtn;
    TextInputLayout editCourseTitle, editCourseInstructorName, editCourseInstructorPhone, editCourseInstructorEmail, editCourseSelectStatus, editCourseSelectTerm, editCourseNote;
    RecyclerView assessmentRecyclerView;
    boolean editMode;
    TextView dateError, noDisplay;
    DatePicker editCourseStartDate, editCourseEndDate;
    TermRepository termRepo;
    CourseRepository courseRepo;
    AutoCompleteTextView autoCompleteStatus, autoCompleteTerm;
    ImageView backBtn;
    String[] dynamic_terms;
    List<AssessmentModel> assessmentList;
    ArrayAdapter<String> adapterStatusItems;
    ArrayAdapter<String> adapterTermItems;
    String[] status_items = {"Plan To Take", "In Progress", "Completed", "Dropped"};
    String username;
    int courseID, startYear, startMonth, startDayOfMonth, endYear, endMonth, endDayOfMonth;
    CourseModel course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        //Hooks
        editCourseTitle = (TextInputLayout) findViewById(R.id.edit_course_title_field);
        editCourseInstructorName = (TextInputLayout) findViewById(R.id.edit_course_instructor_name_field);
        editCourseInstructorPhone = (TextInputLayout) findViewById(R.id.edit_course_instructor_phone_number_field);
        editCourseInstructorEmail = (TextInputLayout) findViewById(R.id.edit_course_instructor_email_field);
        editCourseSelectStatus = (TextInputLayout) findViewById(R.id.edit_course_select_status);
        editCourseSelectTerm = (TextInputLayout) findViewById(R.id.edit_course_select_term);
        editCourseStartDate = (DatePicker) findViewById(R.id.edit_course_start_date);
        editCourseEndDate = (DatePicker) findViewById(R.id.edit_course_end_date);
        editCourseNote = (TextInputLayout) findViewById(R.id.edit_course_note);
        editCourseBtn = (Button) findViewById(R.id.edit_course_btn);
        deleteCourseBtn = (Button) findViewById(R.id.edit_course_delete_btn);
        assessmentRecyclerView = (RecyclerView) findViewById(R.id.edit_course_asessment_recyclerview);
        noDisplay = (TextView) findViewById(R.id.edit_course_no_display);
        dateError = (TextView) findViewById(R.id.edit_course_date_error);
        backBtn = (ImageView) findViewById(R.id.edit_course_back_button);
        backBtn.setClickable(true);

        //Setting edit mode to false because we start the activity in view mode
        editMode = false;

        setTransparent(dateError);

        //Reception of the values sent by the previous intent
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("username");
            courseID = extras.getInt("courseID");
        }

        //Initializing the course repository
        courseRepo = new CourseRepository(getApplication());

        //Initializing the term repository needed to populate the Select Term drop down list
        termRepo = new TermRepository(getApplication());


        //Building array to populate the dropdown list for the possible terms
        List<String> termTitleList = termRepo.getTermsByUsername(username).stream().map(TermModel::getTermTitle).collect(Collectors.toList());
        dynamic_terms =  termTitleList.stream().toArray(String[]::new);
        dynamic_terms = Utility.capitalizeArray(dynamic_terms);

        //Populate the dropdown list for the possible course status
        autoCompleteStatus = (AutoCompleteTextView) findViewById(R.id.edit_course_auto_complete_status);
        adapterStatusItems = new ArrayAdapter<String>(this, R.layout.list_item, status_items);
        autoCompleteStatus.setAdapter(adapterStatusItems);

        //Populate the dropdown list for the possible terms
        autoCompleteTerm = findViewById(R.id.edit_course_auto_complete_term);
        adapterTermItems = new ArrayAdapter<String>(this, R.layout.list_item, dynamic_terms);
        autoCompleteTerm.setAdapter(adapterTermItems);

        setFieldStatus(false);


        //On Item Click Listener for the status dropdown
        autoCompleteStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        //On Item Click Listener for the term dropdown
        autoCompleteTerm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        //Retrieving the currently selected course
        course = courseRepo.getCoursesByID(courseID);

        //Splitting the date into individual variables
        String[] startDateSplitter = course.getCourseStartDate().split("-");
        String[] endDateSplitter = course.getCourseEndDate().split("-");
        startYear = Integer.parseInt(startDateSplitter[0]);
        startMonth = Integer.parseInt(startDateSplitter[1]) - 1;
        startDayOfMonth = Integer.parseInt(startDateSplitter[2]);
        endYear = Integer.parseInt(endDateSplitter[0]);
        endMonth = Integer.parseInt(endDateSplitter[1]) - 1;
        endDayOfMonth = Integer.parseInt(endDateSplitter[2]);

        //Setting the fields with the selected course values
        editCourseTitle.getEditText().setText(Utility.capitalizeString(course.getCourseTitle()));
        editCourseInstructorName.getEditText().setText(Utility.capitalizeString(course.getCourseInstructorName()).trim());
        editCourseInstructorPhone.getEditText().setText(Utility.capitalizeString(course.getCourseInstructorPhoneNumber()));
        editCourseInstructorEmail.getEditText().setText(Utility.capitalizeString(course.getCourseInstructorEmail()));
        editCourseSelectStatus.getEditText().setText(Utility.toTitleCase(course.getCourseStatus()));
        editCourseSelectTerm.getEditText().setText(Utility.capitalizeString(termRepo.getTermByID(course.getTermId()).getTermTitle()));
        editCourseStartDate.updateDate(startYear, startMonth, startDayOfMonth);
        editCourseEndDate.updateDate(endYear, endMonth, endDayOfMonth);
        editCourseNote.getEditText().setText(course.getNotes().isEmpty()? " " : course.getNotes());
        editCourseNote.setHint(!course.getNotes().isEmpty()? "Notes" : "Notes(Optional)");

        //Populating recycler view with the assessments associated to the selected course
        getAssessments();
        assessmentRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), assessmentRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        AssessmentRepository assessmentRepo = new AssessmentRepository(getApplication());
                        int assessmentID = assessmentRepo.getAssessmentsByCourseID(courseID).get(position).getAssessmentID();
                        Intent intent = new Intent(getApplicationContext(), EditAssessment.class);
                        intent.putExtra("username", username);
                        intent.putExtra("assessmentID", assessmentID);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );

        //Creating notification channel
        notificationChannel();
    }

    private void scheduleNotification(int courseId, String date, String courseTitle, String notificationTitle, String strMsg) {
        long currentDate = Utility.getCurrentDate();
        long notificationDate = Utility.getTimeInMilliSeconds(date);
        if(currentDate <= Utility.getTimeInMilliSeconds(date)){
            NotificationReceiver.scheduleCourseNotification(getApplicationContext(), courseId, notificationDate, notificationTitle, Utility.capitalizeString(courseTitle)  + strMsg + date);
        }
    }


    private void notificationChannel(){
        //Verifying that android version is equals or higher than Android 8.0 before creating notification channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Course Reminder";
            String description = "Course Reminder Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("C196", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void createNotification(View view){
        //Creating the strings needed to build the notification
        String date = course.getCourseStartDate();
        String notificationTitle = "Course starts today!";
        String courseTitle = course.getCourseTitle();
        String strMsg = " starts on: ";
        //Schedule the notification for the start date
        scheduleNotification(course.getCourseId(), date, courseTitle, notificationTitle, strMsg);

        date = course.getCourseEndDate();
        notificationTitle = "Course ends today!";
        strMsg = " ends on: ";
        //Schedule the notification for the end date
        scheduleNotification(course.getCourseId(), date, courseTitle, notificationTitle, strMsg);
    }


    public void shareNoteEmail(View view){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, username + " would like to share a note with you.");
        intent.putExtra(Intent.EXTRA_TEXT, editCourseNote.getEditText().getText().toString());
        startActivity(intent);
    }

    public void shareNoteSms(View view){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:"));
        intent.putExtra(Intent.EXTRA_TEXT, editCourseNote.getEditText().getText().toString());
        startActivity(intent);
    }

    public void deleteCourse(View view){
        //Verifying if edit mode is currently false
        if(!editMode) {
            //If we are not in edit mode, this button is treated as the delete button
            // Checking if there are any assessments associated to this course before deleting it
            if (assessmentList.size() > 0) {
                //If there are assessment associated to the term, display error message and do not allow the delete operation
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()) {
                            new AlertDialog.Builder(EditCourse.this).setTitle("Cannot Delete Course")
                                    .setMessage("This course has assessments assigned to it. If you wish to delete this course, please delete the associated assessments.")
                                    .setCancelable(true).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    }).show();
                        }
                    }
                });
            } else {
                //If there are no assessments associated to the course, allow the user to carry the delete operation
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()) {
                            new AlertDialog.Builder(EditCourse.this).setTitle("Delete Course")
                                    .setMessage("Are you sure you would like to delete this Course")
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
            }
        } else {
            //If edit mode is true, this button is treated as a "Cancel" button for the term edit
            //Resetting the input fields to their original value
            editCourseStartDate.updateDate(startYear, startMonth, startDayOfMonth);
            editCourseEndDate.updateDate(endYear, endMonth, endDayOfMonth);
            editCourseTitle.getEditText().setText(Utility.capitalizeString(course.getCourseTitle()));
            editCourseTitle.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
            editCourseTitle.setError(null);
            editCourseTitle.setErrorEnabled(false);
            editCourseInstructorName.getEditText().setText(Utility.capitalizeString(course.getCourseInstructorName()));
            editCourseInstructorPhone.getEditText().setText(Utility.capitalizeString(course.getCourseInstructorPhoneNumber()));
            editCourseInstructorEmail.getEditText().setText(Utility.capitalizeString(course.getCourseInstructorEmail()));
            editCourseSelectStatus.getEditText().setText(Utility.capitalizeString(course.getCourseStatus()));
            editCourseSelectTerm.getEditText().setText(Utility.capitalizeString(termRepo.getTermByID(course.getTermId()).getTermTitle()));
            editCourseNote.getEditText().setText(course.getNotes());
            setTransparent(dateError);

            editMode = false;
            setFieldStatus(false);
            editCourseBtn.setText("Edit Term");
            deleteCourseBtn.setText("Delete Term");

        }
    }

    public void editCourse(View view){
        //Verifying is edit mode is currently false
        if(!editMode){
            //If edit mode is false, we set it to true and enable the fields to be edited
            editMode = true;
            setFieldStatus(true);

            List<String> termTitleList = termRepo.getTermsByUsername(username).stream().map(TermModel::getTermTitle).collect(Collectors.toList());
            dynamic_terms =  termTitleList.stream().toArray(String[]::new);
            dynamic_terms = Utility.capitalizeArray(dynamic_terms);

            autoCompleteStatus = (AutoCompleteTextView) findViewById(R.id.edit_course_auto_complete_status);
            adapterStatusItems = new ArrayAdapter<String>(this, R.layout.list_item, status_items);
            autoCompleteStatus.setAdapter(adapterStatusItems);

            autoCompleteTerm = findViewById(R.id.edit_course_auto_complete_term);
            adapterTermItems = new ArrayAdapter<String>(this, R.layout.list_item, dynamic_terms);
            autoCompleteTerm.setAdapter(adapterTermItems);

            autoCompleteTerm.setAdapter(adapterTermItems);
            editCourseBtn.setText("Save Changes");
            deleteCourseBtn.setText("Cancel");
        } else {
            //If edit mode is true and this button is clicked, we are treating this button as the "Save changes"
            // Grabbing the values from the edited fields
            String updatedCourseTitle = editCourseTitle.getEditText().getText().toString().toLowerCase();
            String updatedCourseInstructorName = editCourseInstructorName.getEditText().getText().toString().toLowerCase();
            String updatedCourseInstructorPhone = editCourseInstructorPhone.getEditText().getText().toString().toLowerCase();
            String updatedCourseInstructorEmail = editCourseInstructorEmail.getEditText().getText().toString().toLowerCase();
            String updatedCourseStatus = editCourseSelectStatus.getEditText().getText().toString().toLowerCase();
            String updatedCourseTerm = editCourseSelectTerm.getEditText().getText().toString().toLowerCase();
            String updatedCourseNote = editCourseNote.getEditText().getText().toString().toLowerCase();

            int updatedStartYear = editCourseStartDate.getYear();
            int updatedStartMonth = editCourseStartDate.getMonth() + 1;
            int updatedStartDayOfMonth = editCourseStartDate.getDayOfMonth();
            int updatedEndYear = editCourseEndDate.getYear();
            int updatedEndMonth = editCourseEndDate.getMonth() + 1;
            int updatedEndDayOfMonth = editCourseEndDate.getDayOfMonth();

            //Verifying that android version is equals or higher than Android 8.0 before using the LocalDate class
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate updatedStartDate = LocalDate.of(updatedStartYear, updatedStartMonth, updatedStartDayOfMonth);
                LocalDate updatedEndDate = LocalDate.of(updatedEndYear, updatedEndMonth, updatedEndDayOfMonth);

                //Text input and date validation through the Utility class
                if(!Utility.isValidTitle(editCourseTitle) | !Utility.isValidName(editCourseInstructorName) | !Utility.isValidPhone(editCourseInstructorPhone) |
                        !Utility.isValidEmail(editCourseInstructorEmail)| !Utility.isValidDropDownSelection(editCourseSelectStatus, status_items) |
                        !Utility.isValidDropDownSelection(editCourseSelectTerm, dynamic_terms) | !Utility.isValidDateCombination(editCourseStartDate, editCourseEndDate, dateError)){
                    // If one of the input field  is invalid, the operation will fail and display the appropriate error message
                    Toast.makeText(this, "Operation failed", Toast.LENGTH_LONG).show();
                    return;
                } else{

                    //If the input values are valid, we create a new CourseModel with the current Course ID, Term ID and username and insert it using the course repository.
                    CourseModel updatedCourse = new CourseModel(courseID, username, updatedCourseTitle,
                            updatedCourseInstructorName, updatedCourseInstructorPhone,
                            updatedCourseInstructorEmail, updatedCourseStatus, course.getTermId(),
                            updatedStartDate.toString(), updatedEndDate.toString(), updatedCourseNote);

                    //Validation to see if the user already has a course with the same name
                    if( (!courseRepo.isTaken(username, course.getTermId(), updatedCourseTitle)) || (updatedCourseTitle.equals(course.getCourseTitle()))){
                        // Confirming the changes to the course and updating the record in the database
                        courseRepo.insertCourse(updatedCourse);
                        Toast.makeText(this, "Course edited", Toast.LENGTH_LONG).show();
                        editMode = false;
                        setFieldStatus(false);
                        editCourseStartDate.updateDate(updatedStartYear, updatedStartMonth, updatedStartDayOfMonth);
                        editCourseEndDate.updateDate(updatedEndYear, updatedEndMonth, updatedEndDayOfMonth);
                        editCourseTitle.getEditText().setText(updatedCourseTitle);
                        editCourseInstructorName.getEditText().setText(updatedCourseInstructorName);
                        editCourseInstructorPhone.getEditText().setText(updatedCourseInstructorPhone);
                        editCourseInstructorEmail.getEditText().setText(updatedCourseInstructorEmail);
                        editCourseSelectStatus.getEditText().setText(updatedCourseStatus);
                        editCourseSelectTerm.getEditText().setText(updatedCourseTerm);
                        editCourseNote.getEditText().setText(updatedCourseNote);
                        Intent intent = new Intent(this, DisplayData.class);
                        intent.putExtra("username", username);
                        intent.putExtra("data type", "course");
                        startActivity(intent);
                        super.finish();
                    }else {
                        //If course title is taken for this username and term
                        Utility.setTitleTakenTextInputLayout(editCourseTitle, "Course");
                    }
                }
            }
        }
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

    private void setFieldStatus(boolean status) {
        editCourseTitle.setEnabled(status);
        editCourseInstructorName.setEnabled(status);
        editCourseInstructorPhone.setEnabled(status);
        editCourseInstructorEmail.setEnabled(status);
        editCourseSelectStatus.setEnabled(status);
        editCourseSelectTerm.setEnabled(status);
        editCourseStartDate.setEnabled(status);
        editCourseEndDate.setEnabled(status);
        editCourseNote.setEnabled(status);
        autoCompleteStatus.setAdapter(adapterStatusItems);
    }

    private void setTransparent(TextView textView){
        textView.setAlpha(0.0f);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);
    }

    private void deleteConfirmed(){
        //This method is called when a user confirms that they wish to delete the item
        CourseRepository courseRepo = new CourseRepository(getApplication());
        courseRepo.deleteCourse(courseID);
        Intent intent = new Intent(this, DisplayData.class);
        intent.putExtra("username", username);
        intent.putExtra("data type", "course");
        startActivity(intent);
        super.finish();
    }

    private void getAssessments() {
        AssessmentRepository assessmentRepo = new AssessmentRepository(getApplication());
        assessmentList = assessmentRepo.getAssessmentsByCourseID(courseID);
        if(assessmentList.size() > 0){
            noDisplay.setAlpha(0.0f);
            noDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);
        }
        assessmentRecyclerView.setAdapter(new AssessmentAdapter(getApplicationContext(), assessmentList));
    }
}