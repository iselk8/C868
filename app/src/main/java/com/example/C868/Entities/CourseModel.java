package com.example.c196.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "course", foreignKeys = {
        @ForeignKey(
                entity = UserModel.class,
                parentColumns = "username",
                childColumns = "username",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = TermModel.class,
                parentColumns = "term_id",
                childColumns = "term_id"
        )

})
public class CourseModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    private int courseId;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "course_title")
    private String courseTitle;

    @ColumnInfo(name = "course_instructor_name")
    private String courseInstructorName;

    @ColumnInfo(name = "course_instructor_phone_number")
    private String courseInstructorPhoneNumber;

    @ColumnInfo(name = "course_instructor_email")
    private String courseInstructorEmail;

    @ColumnInfo(name = "course_status")
    private String courseStatus;

    @ColumnInfo(name = "term_id")
    private int termId;

    @ColumnInfo(name = "course_start_date")
    private String courseStartDate;

    @ColumnInfo(name = "course_end_date")
    private String courseEndDate;

    @ColumnInfo(name = "notes")
    private String notes;

    public CourseModel(String username, String courseTitle, String courseInstructorName, String courseInstructorPhoneNumber, String courseInstructorEmail, String courseStatus, int termId, String courseStartDate, String courseEndDate, String notes) {
        this.username = username;
        this.courseTitle = courseTitle;
        this.courseInstructorName = courseInstructorName;
        this.courseInstructorPhoneNumber = courseInstructorPhoneNumber;
        this.courseInstructorEmail = courseInstructorEmail;
        this.courseStatus = courseStatus;
        this.termId = termId;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.notes = notes;
    }

    //All args constructor needed to replace course via the onConflictStrategy established for the insertCourse method in the CourseDAO interface
    public CourseModel(int courseId, String username, String courseTitle, String courseInstructorName, String courseInstructorPhoneNumber, String courseInstructorEmail, String courseStatus, int termId, String courseStartDate, String courseEndDate, String notes) {
        this.courseId = courseId;
        this.username = username;
        this.courseTitle = courseTitle;
        this.courseInstructorName = courseInstructorName;
        this.courseInstructorPhoneNumber = courseInstructorPhoneNumber;
        this.courseInstructorEmail = courseInstructorEmail;
        this.courseStatus = courseStatus;
        this.termId = termId;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.notes = notes;
    }

    public CourseModel() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseInstructorName() {
        return courseInstructorName;
    }

    public void setCourseInstructorName(String courseInstructorName) {
        this.courseInstructorName = courseInstructorName;
    }

    public String getCourseInstructorPhoneNumber() {
        return courseInstructorPhoneNumber;
    }

    public void setCourseInstructorPhoneNumber(String courseInstructorPhoneNumber) {
        this.courseInstructorPhoneNumber = courseInstructorPhoneNumber;
    }

    public String getCourseInstructorEmail() {
        return courseInstructorEmail;
    }

    public void setCourseInstructorEmail(String courseInstructorEmail) {
        this.courseInstructorEmail = courseInstructorEmail;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "CourseModel{" +
                "courseId=" + courseId +
                ", username='" + username + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseInstructorName='" + courseInstructorName + '\'' +
                ", courseInstructorPhoneNumber='" + courseInstructorPhoneNumber + '\'' +
                ", courseInstructorEmail='" + courseInstructorEmail + '\'' +
                ", courseStatus='" + courseStatus + '\'' +
                ", termId=" + termId +
                ", courseStartDate='" + courseStartDate + '\'' +
                ", courseEndDate='" + courseEndDate + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
