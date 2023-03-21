package com.example.c196;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196.DAO.AssessmentDAO;
import com.example.c196.DAO.CourseDAO;
import com.example.c196.DAO.TermDAO;
import com.example.c196.DAO.UserDAO;
import com.example.c196.Entities.AssessmentModel;
import com.example.c196.Entities.CourseModel;
import com.example.c196.Entities.TermModel;
import com.example.c196.Entities.UserModel;

@androidx.room.Database(entities = {UserModel.class, TermModel.class, CourseModel.class, AssessmentModel.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static final String DB_NAME = "C196";

    public abstract UserDAO getUserDAO();

    public abstract TermDAO getTermDAO();

    public abstract CourseDAO getCourseDAO();

    public abstract AssessmentDAO getAssessmentDAO();
    private static Database instance;

    public static Database getDatabase(final Context context){
        if(instance == null){
            synchronized (Database.class){
                instance = Room.databaseBuilder(context, Database.class, DB_NAME).allowMainThreadQueries().build();
            }
        }

        return instance;
    }
}
