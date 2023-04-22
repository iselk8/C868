package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.Adapter.AssessmentAdapter;
import com.example.c196.Adapter.CourseAdapter;
import com.example.c196.Adapter.TermAdapter;
import com.example.c196.Entities.AssessmentModel;
import com.example.c196.Entities.CourseModel;
import com.example.c196.Entities.TermModel;
import com.example.c196.Repositories.AssessmentRepository;
import com.example.c196.Repositories.CourseRepository;
import com.example.c196.Repositories.TermRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    String username, type;
    RadioGroup searchTypeRadioGroup;
    TextInputLayout searchTitleField;
    Button searchBtn;
    ImageView backBtn;
    TextView searchResultTitle, searchResultNoDisplay;
    RecyclerView resultsRecyclerView;

    TermRepository termRepo;
    CourseRepository courseRepo;
    AssessmentRepository assessmentRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Hooks
        searchTypeRadioGroup = findViewById(R.id.search_type_radio_group);
        searchTitleField =  findViewById(R.id.search_title_field);
        searchBtn = findViewById(R.id.search_submit_btn);
        searchResultTitle = findViewById(R.id.search_result_title);
        searchResultNoDisplay = findViewById(R.id.search_result_no_display);
        resultsRecyclerView = findViewById(R.id.search_result_recyclerview);
        backBtn = findViewById(R.id.search_result_back_button);
        backBtn.setClickable(true);

        // Instantiating the needed repositories for the search feature
        termRepo = new TermRepository(getApplication());
        courseRepo = new CourseRepository(getApplication());
        assessmentRepo = new AssessmentRepository(getApplication());

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            username = extras.getString("username");
        }

        resultsRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), resultsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if(type.equals("terms")){
                                int termID = termRepo.getTermsByUsername(username).get(position).getTermId();
                                Intent intent = new Intent(getApplicationContext(), EditTerm.class);
                                intent.putExtra("username", username);
                                intent.putExtra("termID", termID);
                                startActivity(intent);
                                SearchActivity.super.finish();
                        }else if (type.equals("courses")) {
                                int courseID = courseRepo.getCoursesByUser(username).get(position).getCourseId();
                                Intent intent = new Intent(getApplicationContext(), EditCourse.class);
                                intent.putExtra("username", username);
                                intent.putExtra("courseID", courseID);
                                startActivity(intent);
                                SearchActivity.super.finish();
                        } else if (type.equals("assessments")) {
                            int assessmentID = assessmentRepo.getAssessmentByUser(username).get(position).getAssessmentID();
                            Intent intent = new Intent(getApplicationContext(), EditAssessment.class);
                            intent.putExtra("username", username);
                            intent.putExtra("courseID", assessmentID);
                            startActivity(intent);
                            SearchActivity.super.finish();
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    public void backBtnPressed(View view){
        back();
    }

    @Override
    public void onBackPressed(){
        back();
    }

    private void back() {
        super.finish();
    }

    public void search(View view){
        // Saving the string input from the TextInputLayout
        String searchStr = searchTitleField.getEditText().getText().toString().toLowerCase();

        // Grabbing the selection from the RadioGroup
        int selectedID = searchTypeRadioGroup.getCheckedRadioButtonId();
        RadioButton selection = findViewById(selectedID);
        type = selection.getText().toString().toLowerCase();

        // Switch case to call the appropriate method depending on the selection from the RadioGroup
        switch (type){
            case "terms":
                executeSearchTerm(searchStr);
                break;
            case "courses":
                executeSearchCourse(searchStr);
                break;
            case "assessments":
                executeSearchAssessment(searchStr);
                break;
        }


    }

    private void executeSearchAssessment(String searchStr) {
        // Setting the search result TextView visible
        searchResultTitle.setVisibility(View.VISIBLE);

        // Retrieving the assessments that match the input
        List<AssessmentModel> assessments = assessmentRepo.getAssessmentByTitle(username, searchStr);

        if(assessments.size() < 1){
            searchResultNoDisplay.setVisibility(View.VISIBLE);
            resultsRecyclerView.setVisibility(View.INVISIBLE);
        }else {
            searchResultNoDisplay.setVisibility(View.INVISIBLE);
            resultsRecyclerView.setVisibility(View.VISIBLE);
            resultsRecyclerView.setAdapter(new AssessmentAdapter(getApplicationContext(), assessments));
        }
    }

    private void executeSearchCourse(String searchStr) {
        // Setting the search result TextView visible
        searchResultTitle.setVisibility(View.VISIBLE);

        // Retrieving the courses that match the input
        List<CourseModel> courses = courseRepo.getCourseByTitle(username, searchStr);

        if(courses.size() < 1){
            searchResultNoDisplay.setVisibility(View.VISIBLE);
            resultsRecyclerView.setVisibility(View.INVISIBLE);
        }else {
            searchResultNoDisplay.setVisibility(View.INVISIBLE);
            resultsRecyclerView.setVisibility(View.VISIBLE);
            resultsRecyclerView.setAdapter(new CourseAdapter(getApplicationContext(), courses));
        }

    }

    private void executeSearchTerm(String searchStr) {
        // Setting the search result TextView visible
        searchResultTitle.setVisibility(View.VISIBLE);

        // Retrieving the terms that match the input
        List<TermModel> terms = termRepo.getTermByTitle(username, searchStr);

        if(terms.size() < 1){
            searchResultNoDisplay.setVisibility(View.VISIBLE);
            resultsRecyclerView.setVisibility(View.INVISIBLE);
        }else {
            searchResultNoDisplay.setVisibility(View.INVISIBLE);
            resultsRecyclerView.setVisibility(View.VISIBLE);
            resultsRecyclerView.setAdapter(new TermAdapter(getApplicationContext(), terms));
        }
    }
}