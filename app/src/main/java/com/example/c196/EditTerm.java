package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.Adapter.CourseAdapter;
import com.example.c196.Entities.CourseModel;
import com.example.c196.Entities.TermModel;
import com.example.c196.Repositories.CourseRepository;
import com.example.c196.Repositories.TermRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.List;

public class EditTerm extends AppCompatActivity {
    boolean editMode;
    int termID, startYear, startMonth, startDayOfMonth, endYear, endMonth, endDayOfMonth;
    String termTitle, username;
    TermModel term;
    TermRepository termRepo;
    TextInputLayout editTermTitle;
    DatePicker startDate, endDate;
    TextView dateError, noDisplay;
    Button editTermBtn, deleteTermBtn;
    RecyclerView courseRecyclerView;
    List<CourseModel> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);

        //Hooks
        noDisplay = (TextView) findViewById(R.id.edit_term_no_display);
        dateError = (TextView) findViewById(R.id.add_term_date_error);
        editTermTitle = (TextInputLayout) findViewById(R.id.edit_term_title);
        startDate = (DatePicker) findViewById(R.id.edit_term_start_date);
        endDate = (DatePicker) findViewById(R.id.edit_term_end_date);
        editTermBtn = (Button) findViewById(R.id.edit_term_btn);
        deleteTermBtn = (Button) findViewById(R.id.delete_term_btn);
        courseRecyclerView = (RecyclerView) findViewById(R.id.edit_term_course_recyclerview);

        //Setting edit mode to false because we start the activity in view mode
        editMode = false;

        setFieldStatus(false);
        setTransparent(dateError);

        //Reception of the values sent by the previous intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            termID = extras.getInt("termID");
        }


        //Initializing the term repository
        termRepo = new TermRepository(getApplication());

        //Retrieving the currently selected term
        term = termRepo.getTermByID(termID);

        //Splitting the date into individual variables
        String[] startDateSplitter = term.getTermStartDate().split("-");
        String[] endDateSplitter = term.getTermEndDate().split("-");
        startYear = Integer.parseInt(startDateSplitter[0]);
        startMonth = Integer.parseInt(startDateSplitter[1]) - 1;
        startDayOfMonth = Integer.parseInt(startDateSplitter[2]);
        endYear = Integer.parseInt(endDateSplitter[0]);
        endMonth = Integer.parseInt(endDateSplitter[1]) - 1;
        endDayOfMonth = Integer.parseInt(endDateSplitter[2]);

        //Retrieving the title from the currently selected term
        termTitle = term.getTermTitle();

        //Setting the fields with the selected term values
        editTermTitle.getEditText().setText(Utility.capitalizeString(termTitle));
        startDate.updateDate(startYear, startMonth, startDayOfMonth);
        endDate.updateDate(endYear, endMonth, endDayOfMonth);

        //Populating recycler view with the courses associated to the selected term
        getCourses();
        courseRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), courseRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        CourseRepository courseRepo = new CourseRepository(getApplication());
                        int courseID = courseRepo.getCoursesByTermID(termID).get(position).getCourseId();
                        Intent intent = new Intent(getApplicationContext(), EditCourse.class);
                        intent.putExtra("username", username);
                        intent.putExtra("courseID", courseID);
                        startActivity(intent);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    public void editTerm(View view) {
        //Verifying is edit mode is currently false
        if (!editMode) {
            //If edit mode is false, we set it to true and enable the fields to be edited
            editMode = true;
            setFieldStatus(true);
            editTermBtn.setText("Save Changes");
            deleteTermBtn.setText("Cancel");
        } else {
            //If edit mode is true and this button is clicked, we are treating this button as the "Save changes"
            // Grabbing the values from the edited fields
            String updatedTermTitle = editTermTitle.getEditText().getText().toString().toLowerCase();
            int updatedStartYear = startDate.getYear();
            int updatedStartMonth = startDate.getMonth() + 1;
            int updatedStartDayOfMonth = startDate.getDayOfMonth();
            int updatedEndYear = endDate.getYear();
            int updatedEndMonth = endDate.getMonth() + 1;
            int updatedEndDayOfMonth = endDate.getDayOfMonth();

            //Verifying that android version is equals or higher than Android 8.0 before using the LocalDate class
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate updatedStartDate = LocalDate.of(updatedStartYear, updatedStartMonth, updatedStartDayOfMonth);
                LocalDate updatedEndDate = LocalDate.of(updatedEndYear, updatedEndMonth, updatedEndDayOfMonth);

                //Text input and date validation through the Utility class
                if (!Utility.isValidTitle(editTermTitle) | !Utility.isValidDateCombination(startDate, endDate, dateError)){
                    // If one of the input field  is invalid, the operation will fail and display the appropriate error message
                    Toast.makeText(this, "Operation failed", Toast.LENGTH_LONG).show();
                    return;
                }else {

                    //If the input values are valid, we create a new TermModel with the current Term ID and username and insert it using the term repository.
                    TermModel updatedTerm = new TermModel(termID, updatedTermTitle, updatedStartDate.toString(), updatedEndDate.toString(), username);
                    //Validation to see if the user already has a term with the same name
                    if(!termRepo.isTaken(username, updatedTermTitle) || (updatedTermTitle.equals(term.getTermTitle()))){
                        // Confirming the changes to the term and updating the record in the database
                        termRepo.insertTerm(updatedTerm);
                        Toast.makeText(this, "Term edited", Toast.LENGTH_LONG).show();
                        editMode = false;
                        setFieldStatus(false);
                        startDate.updateDate(updatedStartYear, updatedStartMonth -1, updatedStartDayOfMonth);
                        endDate.updateDate(updatedEndYear, updatedEndMonth - 1, updatedEndDayOfMonth);
                        editTermTitle.getEditText().setText(updatedTermTitle);
                        editTermBtn.setText("Edit Term");
                        deleteTermBtn.setText("Delete Term");

                        Intent intent = new Intent(this, DisplayData.class);
                        intent.putExtra("username", username);
                        intent.putExtra("data type", "term");
                        startActivity(intent);
                        super.finish();

                    }else{
                        //If term title is taken for this username
                        Utility.setTitleTakenTextInputLayout(editTermTitle, "Term");
                    }
                }
            }
        }
    }

    public void deleteTerm(View view) {
        //Verifying if edit mode is currently false
        if (!editMode) {
            //If we are not in edit mode, this button is treated as the delete button
            // Checking if there are any courses associated to this term before deleting it
            if (courseList.size() > 0) {
                //If there are courses associated to the term, display error message and do not allow the delete operation
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()) {
                            new AlertDialog.Builder(EditTerm.this).setTitle("Cannot Delete Term")
                                    .setMessage("This term has courses assigned to it. If you wish to delete this course, please delete the associated courses.")
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
                //If there are no courses associated to the term, allow the user to carry the delete operation
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()) {
                            new AlertDialog.Builder(EditTerm.this).setTitle("Delete Term")
                                    .setMessage("Are you sure you would like to delete this Term")
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
        }else{
            //If edit mode is true, this button is treated as a "Cancel" button for the term edit
            //Resetting the input fields to their original value
            startDate.updateDate(startYear, startMonth -1, startDayOfMonth);
            endDate.updateDate(endYear, endMonth - 1, endDayOfMonth);
            editTermTitle.getEditText().setText(Utility.capitalizeString(termTitle));
            editTermTitle.setStartIconTintList(ColorStateList.valueOf(Color.WHITE));
            editTermTitle.setError(null);
            editTermTitle.setErrorEnabled(false);
            setTransparent(dateError);

            editMode = false;
            setFieldStatus(false);
            editTermBtn.setText("Edit Term");
            deleteTermBtn.setText("Delete Term");
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
        intent.putExtra("data type", "term");
        startActivity(intent);
        super.finish();
    }

    private void setTransparent(TextView textView) {
        textView.setAlpha(0.0f);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);

    }

    private void setFieldStatus(boolean status){
        editTermTitle.setEnabled(status);
        startDate.setEnabled(status);
        endDate.setEnabled(status);
    }

    private void deleteConfirmed() {
        //This method is called when a user confirms that they wish to delete the item
        termRepo.deleteTerm(termID);
        Intent intent = new Intent(this, DisplayData.class);
        intent.putExtra("username", username);
        intent.putExtra("data type", "term");
        startActivity(intent);
        super.finish();
    }

    private void getCourses() {
        CourseRepository courseRepo = new CourseRepository(getApplication());
        courseList = courseRepo.getCoursesByTermID(termID);
        if (courseList.size() > 0) {
            setTransparent(noDisplay);
        }
        courseRecyclerView.setAdapter(new CourseAdapter(getApplicationContext(), courseList));
    }
}