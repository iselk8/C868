package com.example.c196.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserModel {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "password")
    private String password;


    public UserModel(@NonNull String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public UserModel() {
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
