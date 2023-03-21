package com.example.c196.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.c196.Entities.CourseModel;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseModel course);

    @Delete
    void deleteCourse(CourseModel course);

    @Query("SELECT * FROM course WHERE course_id = :courseID")
    CourseModel getCourseByID(int courseID);

    @Query("SELECT course_title FROM course WHERE course_id = :courseID")
    String getCourseNameByID(int courseID);

    @Query("SELECT * FROM course WHERE term_id = :termID")
    List<CourseModel> getCourseByTermID(int termID);

    @Query("SELECT * FROM course WHERE username = :username")
    List<CourseModel> getCourseByUsername(String username);

    @Query("SELECT * FROM course ORDER BY course_start_date DESC")
    List<CourseModel> getAllCourses();

    @Query("DELETE FROM course WHERE course_id = :courseID")
    int deleteCourseByID(int courseID);

    @Query("SELECT EXISTS(SELECT * FROM course WHERE username = :username AND term_id = :termID AND course_title = :courseTitle)")
    boolean isTaken(String username, int termID, String courseTitle);

    @Query("SELECT course_id FROM course WHERE username = :username AND course_title = :courseTitle")
    int getCourseIDByTitle(String username, String courseTitle);

}
