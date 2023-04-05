package com.example.c196.Repositories;

import android.app.Application;

import com.example.c196.DAO.CourseDAO;
import com.example.c196.Database;
import com.example.c196.Entities.CourseModel;

import java.util.List;

public class CourseRepository {

    private CourseDAO courseDAO;

    private List<CourseModel> courseList;

    public CourseRepository(Application application) {
        Database db = Database.getDatabase(application);
        courseDAO = db.getCourseDAO();
        courseList = courseDAO.getAllCourses();
    }

    public void insertCourse(CourseModel course){
        courseDAO.insertCourse(course);
    }

    public void deleteCourse(CourseModel course){
        courseDAO.deleteCourse(course);
    }

    public void deleteCourse(int courseID){
        courseDAO.deleteCourseByID(courseID);
    }

    public List<CourseModel> getAllCourseList() {
        return courseList;
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

    public String getCourseNameByID(int courseID){
        return courseDAO.getCourseNameByID(courseID);
    }

    public List<CourseModel> getCourseByTitle(String username, String title){return courseDAO.getCourseByTitle(username, title);}

    public int getCourseIDByTitle(String username, String courseTitle){
        return courseDAO.getCourseIDByTitle(username, courseTitle);
    }

    public boolean isTaken(String username, int termID, String courseTitle){
        return courseDAO.isTaken(username, termID, courseTitle);
    }

}
