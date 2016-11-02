package com.sumon.studymate;

/**
 * Created by Md Tajmul Alam Sumon on 10/29/2016.
 */

public class TeacherModel {
    private int teacherID;
    private String teacherName;
    private String teacherDesignation;
    private String teacherMobile;
    private String teacherEmail;


    public TeacherModel(int teacherID, String teacherName, String teacherDesignation, String teacherMobile, String teacherEmail) {
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.teacherDesignation = teacherDesignation;
        this.teacherMobile = teacherMobile;
        this.teacherEmail = teacherEmail;
    }

    public TeacherModel(String teacherName, String teacherDesignation, String teacherMobile, String teacherEmail) {
        this.teacherName = teacherName;
        this.teacherDesignation = teacherDesignation;
        this.teacherMobile = teacherMobile;
        this.teacherEmail = teacherEmail;
    }

    public int getTeacherID() {
        return teacherID;
    }


    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherDesignation() {
        return teacherDesignation;
    }

    public void setTeacherDesignation(String teacherDesignation) {
        this.teacherDesignation = teacherDesignation;
    }

    public String getTeacherMobile() {
        return teacherMobile;
    }

    public void setTeacherMobile(String teacherMobile) {
        this.teacherMobile = teacherMobile;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }
}
