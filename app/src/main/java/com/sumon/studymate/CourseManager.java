package com.sumon.studymate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Md Tajmul Alam Sumon on 10/31/2016.
 */

public class CourseManager {

    ////data field
    private CourseModel aCourse;
    private ArrayList<CourseModel> allCourses;
    private Cursor cursor;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    //// constructor
    public CourseManager(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    ///// open database  connection
    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    /// close database connection
    private void close() {
        dbHelper.close();
    }


    public boolean insertCourse(CourseModel aCourse) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_COURSE_TITTLE, aCourse.getCourseTitle());
        values.put(DBHelper.KEY_COURSE_CODE, aCourse.getCourseCode());
        values.put(DBHelper.KEY_COURSE_CREDIT, aCourse.getCourseCredit());
        values.put(DBHelper.KEY_SEMESTER_ID, aCourse.getCourseSemesterID());
        values.put(DBHelper.KEY_TEACHER_ID, aCourse.getCourseTeacherID());
        long isInserted = database.insert(DBHelper.TABLE_COURSES, null, values);
        this.close();
        if (isInserted != -1)
            return true;
        else return false;
    }

    public ArrayList<CourseModel> getAllCourses() {
        this.open();
        allCourses = new ArrayList<>();
        cursor = database.query(DBHelper.TABLE_COURSES, null, null, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int courseID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COURSE_ID));

                int courseSemesterID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SEMESTER_ID));

                int courseTeacherID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEACHER_ID));
                String courseTitle = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_COURSE_TITTLE));

                String courseCode = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_COURSE_CODE));

                String courseCredit = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_COURSE_CREDIT));

                aCourse = new CourseModel(courseSemesterID, courseTeacherID, courseID, courseTitle, courseCode, courseCredit);
                allCourses.add(aCourse);
                cursor.moveToNext();
            }
        }
        this.close();
        return allCourses;
    }

    public void deleteCourseBySemesterID(int semesterID) {
        this.open();
        database.delete(DBHelper.TABLE_COURSES, DBHelper.KEY_SEMESTER_ID + " = " + semesterID, null);
        this.close();
    }

    public ArrayList<CourseModel> getAllCoursesBySemesterID(int semesterID) {
        this.open();
        allCourses = new ArrayList<>();
        cursor = database.query(DBHelper.TABLE_COURSES, new String[]{DBHelper.KEY_COURSE_ID, DBHelper.KEY_COURSE_TITTLE, DBHelper.KEY_COURSE_CODE, DBHelper.KEY_COURSE_CREDIT,DBHelper.KEY_SEMESTER_ID,DBHelper.KEY_TEACHER_ID}, DBHelper.KEY_SEMESTER_ID + " = " + semesterID, null, null, null, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int courseID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COURSE_ID));

                int courseSemesterID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SEMESTER_ID));

                int courseTeacherID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEACHER_ID));
                String courseTitle = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_COURSE_TITTLE));

                String courseCode = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_COURSE_CODE));

                String courseCredit = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_COURSE_CREDIT));

                aCourse = new CourseModel(courseSemesterID, courseTeacherID, courseID, courseTitle, courseCode, courseCredit);
                allCourses.add(aCourse);
                cursor.moveToNext();
            }
        }
        this.close();
        return allCourses;
    }

    public boolean deleteCourseByID(int courseID) {
        this.open();
        int deleted =  database.delete(DBHelper.TABLE_COURSES, DBHelper.KEY_COURSE_ID + " = " + courseID, null);
        this.close();
        if (deleted > 0) {
            return true;

        } else
            return false;
    }
}
