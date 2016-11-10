package com.sumon.studymate.model;

/**
 * Created by Md Tajmul Alam Sumon on 10/27/2016.
 */
///// semestemodel class use for semester data model,
    /// setter getter created
public class SemesterModel {
    private int semesterID;
    private String semesterTitle;
    private String semester_start_date;
    private String semester_end_date;

    public SemesterModel(String semesterTitle, String semester_start_date, String semester_end_date) {
        this.semesterTitle = semesterTitle;
        this.semester_start_date = semester_start_date;
        this.semester_end_date = semester_end_date;
    }

    public SemesterModel(int semesterID, String semesterTitle, String semester_start_date, String semester_end_date) {
        this.semesterID = semesterID;
        this.semesterTitle = semesterTitle;
        this.semester_start_date = semester_start_date;
        this.semester_end_date = semester_end_date;
    }

    public SemesterModel() {
    }

    public int getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(int semesterID) {
        this.semesterID = semesterID;
    }

    public String getSemesterTitle() {
        return semesterTitle;
    }

    public void setSemesterTitle(String semesterTitle) {
        this.semesterTitle = semesterTitle;
    }

    public String getSemester_start_date() {
        return semester_start_date;
    }

    public void setSemester_start_date(String semester_start_date) {
        this.semester_start_date = semester_start_date;
    }

    public String getSemester_end_date() {
        return semester_end_date;
    }

    public void setSemester_end_date(String semester_end_date) {
        this.semester_end_date = semester_end_date;
    }
}
