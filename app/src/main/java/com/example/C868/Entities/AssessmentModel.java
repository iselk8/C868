package com.example.c196.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment", foreignKeys = {
        @ForeignKey(entity = UserModel.class,
        parentColumns = "username",
        childColumns = "username",
        onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = TermModel.class,
        parentColumns = "term_id",
        childColumns = "term_id"),
        @ForeignKey(entity = CourseModel.class,
        parentColumns = "course_id",
        childColumns = "course_id")
})
public class AssessmentModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessment_id")
    private int assessmentID;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "term_id")
    private int termID;

    @ColumnInfo(name = "assessment_title")
    private String assessmentTitle;

    @ColumnInfo(name = "course_id")
    private int courseID;

    @ColumnInfo(name = "assessment_type")
    private String assessmentType;

    @ColumnInfo(name = "assessment_due_date")
    private String assessmentDueDate;

    public AssessmentModel(String username, int termID, String assessmentTitle, int courseID, String assessmentType, String assessmentDueDate) {
        this.username = username;
        this.termID = termID;
        this.assessmentTitle = assessmentTitle;
        this.courseID = courseID;
        this.assessmentType = assessmentType;
        this.assessmentDueDate = assessmentDueDate;
    }

    public AssessmentModel(int assessmentID, String username, int termID, String assessmentTitle, int courseID, String assessmentType, String assessmentDueDate) {
        this.assessmentID = assessmentID;
        this.username = username;
        this.termID = termID;
        this.assessmentTitle = assessmentTitle;
        this.courseID = courseID;
        this.assessmentType = assessmentType;
        this.assessmentDueDate = assessmentDueDate;
    }

    public AssessmentModel() {
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentDueDate() {
        return assessmentDueDate;
    }

    public void setAssessmentDueDate(String assessmentDueDate) {
        this.assessmentDueDate = assessmentDueDate;
    }

    @Override
    public String toString() {
        return "AssessmentModel{" +
                "assessmentId=" + assessmentID +
                ", username='" + username + '\'' +
                ", termId='" + termID + '\'' +
                ", assessmentTitle='" + assessmentTitle + '\'' +
                ", courseId='" + courseID + '\'' +
                ", assessmentType='" + assessmentType + '\'' +
                ", assessmentDueDate='" + assessmentDueDate + '\'' +
                '}';
    }
}
