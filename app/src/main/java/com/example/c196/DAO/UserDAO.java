package com.example.c196.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.c196.Entities.UserModel;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(UserModel user);

    @Query("Select * FROM user")
    List<UserModel> getAllUsers();

    @Query("SELECT name FROM user WHERE username = :username")
    String getUserFirstName(String username);

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username=:userName)")
    boolean isTaken(String userName);

    @Query("SELECT EXISTS (SELECT * FROM user WHERE username=:userName AND password=:password)")
    boolean login(String userName, String password);
}
