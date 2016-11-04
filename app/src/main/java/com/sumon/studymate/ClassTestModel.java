package com.sumon.studymate;

/**
 * Created by Md Tajmul Alam Sumon on 11/2/2016.
 */

public class ClassTestModel {
    private int classTestID;
    private String testDate;
    private String classTestTopic;
    private int classTestStatus;
    private int semesterID;
    private int courseID;

    public ClassTestModel(int classTestID, String testDate, String classTestTopic, int classTestStatus, int semesterID, int courseID) {
        this.classTestID = classTestID;
        this.testDate = testDate;
        this.classTestTopic = classTestTopic;
        this.classTestStatus = classTestStatus;
        this.semesterID = semesterID;
        this.courseID = courseID;
    }

    public ClassTestModel(String testDate, String classTestTopic, int classTestStatus, int semesterID, int courseID) {
        this.testDate = testDate;
        this.classTestTopic = classTestTopic;
        this.classTestStatus = classTestStatus;
        this.semesterID = semesterID;
        this.courseID = courseID;
    }

    public int getClassTestID() {
        return classTestID;
    }


    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getClassTestTopic() {
        return classTestTopic;
    }

    public void setClassTestTopic(String classTestTopic) {
        this.classTestTopic = classTestTopic;
    }

    public int getClassTestStatus() {
        return classTestStatus;
    }

    public void setClassTestStatus(int classTestStatus) {
        this.classTestStatus = classTestStatus;
    }

    public int getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(int semesterID) {
        this.semesterID = semesterID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}