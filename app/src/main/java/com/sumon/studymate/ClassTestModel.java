package com.sumon.studymate;

/**
 * Created by Md Tajmul Alam Sumon on 11/2/2016.
 */

public class ClassTestModel {
    private int classTestID;
    private int semesterID;
    private int courseID;
    private String testDate;
    private String classTestTopic;
    private int classTestStatus;

    public ClassTestModel(int classTestID, int semesterID, int courseID, String testDate, String classTestTopic, int classTestStatus) {
        this.classTestID = classTestID;
        this.semesterID = semesterID;
        this.courseID = courseID;
        this.testDate = testDate;
        this.classTestTopic = classTestTopic;
        this.classTestStatus = classTestStatus;
    }


    public ClassTestModel(int semesterID, int courseID, String testDate, String classTestTopic, int classTestStatus) {
        this.semesterID = semesterID;
        this.courseID = courseID;
        this.testDate = testDate;
        this.classTestTopic = classTestTopic;
        this.classTestStatus = classTestStatus;
    }


    public int getClassTestID() {
        return classTestID;
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
}
