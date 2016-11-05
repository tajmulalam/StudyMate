package com.sumon.studymate;

/**
 * Created by Md Tajmul Alam Sumon on 11/5/2016.
 */

public class RoutineModel {

    private int routineID;
    private String day;
    private String startTime;
    private String endTime;
    private String period;
    private int semesterID;
    private int courseID;
    private int teacherID;

    public RoutineModel(int routineID, String day, String startTime, String endTime, String period, int semesterID, int courseID, int teacherID) {
        this.routineID = routineID;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.period = period;
        this.semesterID = semesterID;
        this.courseID = courseID;
        this.teacherID = teacherID;
    }

    public RoutineModel(String day, String startTime, String endTime, String period, int semesterID, int courseID, int teacherID) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.period = period;
        this.semesterID = semesterID;
        this.courseID = courseID;
        this.teacherID = teacherID;
    }

    public int getRoutineID() {
        return routineID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }
}
