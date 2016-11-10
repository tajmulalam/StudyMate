package com.sumon.studymate.model;

/**
 * Created by Md Tajmul Alam Sumon on 11/2/2016.
 */

public class AssignmentModel {
    private int assignmentID;
    private int semesterID;
    private int courseID;
    private String submitDate;
    private String topic;
    private int assignmentStatus;

    public AssignmentModel(int assignmentID, int semesterID, int courseID, String submitDate, String topic, int assignmentStatus) {
        this.assignmentID = assignmentID;
        this.semesterID = semesterID;
        this.courseID = courseID;
        this.submitDate = submitDate;
        this.topic = topic;
        this.assignmentStatus = assignmentStatus;
    }


    public AssignmentModel(int semesterID, int courseID, String submitDate, String topic, int assignmentStatus) {
        this.semesterID = semesterID;
        this.courseID = courseID;
        this.submitDate = submitDate;
        this.topic = topic;
        this.assignmentStatus = assignmentStatus;
    }

    public int getAssignmentID() {
        return assignmentID;
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

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(int assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }
}
