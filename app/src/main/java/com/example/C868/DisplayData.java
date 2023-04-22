package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.Adapter.AssessmentAdapter;
import com.example.c196.Adapter.CourseAdapter;
import com.example.c196.Adapter.TermAdapter;
import com.example.c196.Adapter.UserAdapter;
import com.example.c196.DAO.AssessmentDAO;
import com.example.c196.DAO.CourseDAO;
import com.example.c196.DAO.UserDAO;
import com.example.c196.Entities.AssessmentModel;
import com.example.c196.Entities.CourseModel;
import com.example.c196.Entities.TermModel;
import com.example.c196.Entities.UserModel;
import com.example.c196.Repositories.AssessmentRepository;
import com.example.c196.Repositories.CourseRepository;
import com.example.c196.Repositories.TermRepository;

import java.util.List;

public class DisplayData extends AppCompatActivity {

    RecyclerView recyclerView;
    Database db;
    TextView dataType, noDisplay;
    ImageView backBtn, addBtn;
    String username, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        db = Database.getDatabase(getApplicationContext());

        //Hooks
        dataType = findViewById(R.id.data_type);
        noDisplay = findViewById(R.id.no_display);
        recyclerView = findViewById(R.id.data_recyclerview);
        backBtn = findViewById(R.id.display_data_back_btn);
        addBtn = findViewById(R.id.display_data_add_btn);
        backBtn.setClickable(true);
        addBtn.setClickable(true);





        //Recycler item click listener for the detail button on the card.
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Replace by switch
                        //Switch to determine which type of activity needs to be opened
                        if(data.equals("term")){
                            int termID = db.getTermDAO().getTermByUser(username).get(position).getTermId();
                            Intent intent = new Intent(getApplicationContext(), EditTerm.class);
                            intent.putExtra("username", username);
                            intent.putExtra("termID", termID);
                            startActivity(intent);
                            DisplayData.super.finish();
                        }else if (data.equals("course")) {
                            int courseID = db.getCourseDAO().getCourseByUsername(username).get(position).getCourseId();
                            Intent intent = new Intent(getApplicationContext(), EditCourse.class);
                            intent.putExtra("username", username);
                            intent.putExtra("courseID", courseID);
                            startActivity(intent);
                            DisplayData.super.finish();
                        } else if (data.equals("assessment")) {
                            int assessmentID = db.getAssessmentDAO().getAssessmentByUser(username).get(position).getAssessmentID();
                            Intent intent = new Intent(getApplicationContext(), EditAssessment.class);
                            intent.putExtra("username", username);
                            intent.putExtra("assessmentID", assessmentID);
                            startActivity(intent);
                            DisplayData.super.finish();
                        }
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {}
                })
        );

        //Reception of the values sent by the previous intent
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            username = extras.getString("username");
            data = extras.getString("data type");
        }

        //Replace with a switch
        if(data.equals("term")){
            dataType.setText("Terms");
            getTerms();
        }else if (data.equals("course")) {
            dataType.setText("Courses");
            getCourses();
        } else if (data.equals("assessment")) {
            dataType.setText("Assessment");
            getAssessments();
        } else if (data.equals("user")) {
            dataType.setText("user");
            getUsers();
        }
    }

    public void backBtnPressed(View v){
        super.finish();
    }

    public void addBtnPressed(View v){
        if(data.equals("term")){
            Intent intent = new Intent(getApplicationContext(), AddTerm.class);
            intent.putExtra("username", username);
            startActivity(intent);
            super.finish();
        } else if (data.equals("course")) {
            Intent intent = new Intent(getApplicationContext(), AddCourse.class);
            intent.putExtra("username", username);
            startActivity(intent);
            super.finish();
        } else if(data.equals("assessment")){
            Intent intent = new Intent(getApplicationContext(), AddAssessment.class);
            intent.putExtra("username", username);
            startActivity(intent);
            super.finish();
        }
    }

    private void getTerms() {
        TermRepository termRepo = new TermRepository(getApplication());
        List<TermModel> termList = termRepo.getTermsByUsername(username);
        if(termList.size() > 0){
            noDisplay.setAlpha(0.0f);
            noDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);
        }
        recyclerView.setAdapter(new TermAdapter(getApplicationContext(), termList));
    }

    private void getUsers(){
        UserDAO userDAO = db.getUserDAO();
        List<UserModel> userList = userDAO.getAllUsers();
        if(userList.size() > 0){
            noDisplay.setAlpha(0.0f);
            noDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);
        }
        recyclerView.setAdapter(new UserAdapter(getApplicationContext(), userList));
    }

    private void getCourses(){
        CourseRepository courseRepo = new CourseRepository(getApplication());
        List<CourseModel> courseList = courseRepo.getCoursesByUser(username);
        if(courseList.size() > 0){
            noDisplay.setAlpha(0.0f);
            noDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);
        }
        recyclerView.setAdapter(new CourseAdapter(getApplicationContext(), courseList));
    }

    private void getAssessments(){
        AssessmentRepository assessmentRepo = new AssessmentRepository(getApplication());
        List<AssessmentModel> assessmentList = assessmentRepo.getAssessmentByUser(username);
        if(assessmentList.size() > 0){
            noDisplay.setAlpha(0.0f);
            noDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 1);
        }
        recyclerView.setAdapter(new AssessmentAdapter(getApplicationContext(), assessmentList));
    }
}