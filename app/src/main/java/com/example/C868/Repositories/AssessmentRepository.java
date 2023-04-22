package com.example.c196.Repositories;

import android.app.Application;

import com.example.c196.DAO.AssessmentDAO;
import com.example.c196.Database;
import com.example.c196.Entities.AssessmentModel;

import java.util.List;

public class AssessmentRepository {

    private AssessmentDAO assessmentDAO;

    public AssessmentRepository(Application application) {
        assessmentDAO = Database.getDatabase(application).getAssessmentDAO();
    }

    public void insertAssessment(AssessmentModel assessment){
        assessmentDAO.insertAssessment(assessment);
    }

    public void deleteAssessment(int assessmentID){
        assessmentDAO.deleteAssessmentByID(assessmentID);
    }

    public List<AssessmentModel> getAssessmentByTitle(String username, String title){return assessmentDAO.getAssessmentByTitle(username, title);}

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
