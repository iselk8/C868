package com.example.c196.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.c196.Entities.AssessmentModel;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(AssessmentModel assessment);

    @Delete
    void deleteAssessment(AssessmentModel assessment);

    @Query("DELETE FROM assessment WHERE assessment_id = :assessmentID")
    int deleteAssessmentByID(int assessmentID);

    @Query("SELECT * FROM assessment WHERE assessment_id = :assessmentID")
    AssessmentModel getAssessmentByID(int assessmentID);

    @Query("SELECT * FROM assessment WHERE course_id = :courseID")
    List<AssessmentModel> getAssessmentByCourseID(int courseID);

    @Query("SELECT * FROM assessment WHERE term_id = :termID")
    List<AssessmentModel> getAssessmentByTermID(int termID);

    @Query("SELECT * FROM assessment WHERE username = :username")
    List<AssessmentModel> getAssessmentByUser(String username);

    @Query("SELECT * FROM assessment ORDER BY assessment_due_date DESC")
    List<AssessmentModel> getAllAssessment();

    @Query("SELECT * FROM assessment WHERE username = :username AND assessment_title LIKE '%' || :title || '%'")
    List<AssessmentModel> getAssessmentByTitle(String username, String title);

    @Query("SELECT EXISTS(SELECT * FROM assessment WHERE username = :username AND course_id = :courseID AND assessment_title = :assessmentTitle)")
    boolean isTaken(String username, int courseID, String assessmentTitle);
}
