package com.example.c196.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "term",
        foreignKeys =
            @ForeignKey(
                    entity = UserModel.class,
                    parentColumns = "username",
                    childColumns = "username",
                    onDelete = ForeignKey.CASCADE
            )
)
public class TermModel {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "term_id")
    private int termId;

    @ColumnInfo(name = "term_title")
    private String termTitle;

    @ColumnInfo(name = "term_start_date")
    private String termStartDate;

    @ColumnInfo(name = "term_end_date")
    private String termEndDate;

    @ColumnInfo(name = "username")
    private String username;

    public TermModel(String termTitle, String termStartDate, String termEndDate, String username) {
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
        this.username = username;
    }

    public TermModel(int termId, String termTitle, String termStartDate, String termEndDate, String username) {
        this.termId = termId;
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
        this.username = username;
    }

    public TermModel() {
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public String getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(String termStartDate) {
        this.termStartDate = termStartDate;
    }

    public String getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(String termEndDate) {
        this.termEndDate = termEndDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "TermModel{" +
                "termId=" + termId +
                ", termTitle='" + termTitle + '\'' +
                ", termStartDate='" + termStartDate + '\'' +
                ", termEndDate='" + termEndDate + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
