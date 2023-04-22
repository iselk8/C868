package com.example.c196.Repositories;

import android.app.Application;

import com.example.c196.DAO.CourseDAO;
import com.example.c196.Database;
import com.example.c196.Entities.CourseModel;

import java.util.List;

public class CourseRepository {

    private CourseDAO courseDAO;

    public CourseRepository(Application application) {
        courseDAO = Database.getDatabase(application).getCourseDAO();
    }

    public void insertCourse(CourseModel course){
        courseDAO.insertCourse(course);
    }

    public void deleteCourse(int courseID){
        courseDAO.deleteCourseByID(courseID);
    }


    public List<CourseModel> getCoursesByUser(String username) {
        return courseDAO.getCourseByUsername(username);
    }

    public CourseModel getCoursesByID(int courseID) {
        return courseDAO.getCourseByID(courseID);
    }

    public List<CourseModel> getCoursesByTermID(int termID) {
        return courseDAO.getCourseByTermID(termID);
    }

    public List<CourseModel> getCourseByTitle(String username, String title){return courseDAO.getCourseByTitle(username, title);}

    public int getCourseIDByTitle(String username, String courseTitle){
        return courseDAO.getCourseIDByTitle(username, courseTitle);
    }

    public boolean isTaken(String username, int termID, String courseTitle){
        return courseDAO.isTaken(username, termID, courseTitle);
    }

}
