package com.sumon.studymate;

/**
 * Created by Md Tajmul Alam Sumon on 10/31/2016.
 */

public class CourseModel {


    private int courseID;
    private int courseSemesterID;
    private int courseTeacherID;
    private String courseTitle;
    private String courseCode;
    private String courseCredit;

    public CourseModel(int courseID, int courseSemesterID, int courseTeacherID, String courseTitle, String courseCode, String courseCredit) {
        this.courseID = courseID;
        this.courseSemesterID = courseSemesterID;
        this.courseTeacherID = courseTeacherID;
        this.courseTitle = courseTitle;
        this.courseCode = courseCode;
        this.courseCredit = courseCredit;
    }

    public CourseModel(int courseSemesterID, int courseTeacherID, String courseTitle, String courseCode, String courseCredit) {
        this.courseSemesterID = courseSemesterID;
        this.courseTeacherID = courseTeacherID;
        this.courseTitle = courseTitle;
        this.courseCode = courseCode;
        this.courseCredit = courseCredit;
    }

    public int getCourseSemesterID() {
        return courseSemesterID;
    }

    public void setCourseSemesterID(int courseSemesterID) {
        this.courseSemesterID = courseSemesterID;
    }

    public int getCourseTeacherID() {
        return courseTeacherID;
    }

    public void setCourseTeacherID(int courseTeacherID) {
        this.courseTeacherID = courseTeacherID;
    }

    public int getCourseID() {
        return courseID;
    }


    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
