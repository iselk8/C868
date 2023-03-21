package com.example.c196.Repositories;

import android.app.Application;

import com.example.c196.DAO.AssessmentDAO;
import com.example.c196.Database;
import com.example.c196.Entities.AssessmentModel;

import java.util.List;

public class AssessmentRepository {

    private AssessmentDAO assessmentDAO;

    private List<AssessmentModel> assessmentList;

    public AssessmentRepository(Application application) {
        Database db = Database.getDatabase(application);
        assessmentDAO = db.getAssessmentDAO();
        assessmentList = assessmentDAO.getAllAssessment();
    }

    public void insertAssessment(AssessmentModel assessment){
        assessmentDAO.insertAssessment(assessment);
    }

    public void deleteAssessment(AssessmentModel assessment){
        assessmentDAO.deleteAssessment(assessment);
    }

    public void deleteAssessment(int assessmentID){
        assessmentDAO.deleteAssessmentByID(assessmentID);
    }

    public List<AssessmentModel> getAllAssessmentList(){
        return assessmentList;
    }

    public List<AssessmentModel> getAssessmentsByCourseID(int courseID){
        return assessmentDAO.getAssessmentByCourseID(courseID);
    }

    public AssessmentModel getAssessmentByID(int assessmentID){return assessmentDAO.getAssessmentByID(assessmentID);}

    public List<AssessmentModel> getAssessmentByUser(String username){
        return assessmentDAO.getAssessmentByUser(username);
    }

    public boolean isTaken(String username, int courseID, String assessmentTitle){
        return assessmentDAO.isTaken(username, courseID, assessmentTitle);
    }
}
