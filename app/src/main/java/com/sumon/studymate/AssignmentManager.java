package com.sumon.studymate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Md Tajmul Alam Sumon on 11/2/2016.
 */

public class AssignmentManager {
    ////data field
    private AssignmentModel aAssignment;
    private ArrayList<AssignmentModel> allAssignment;
    private Cursor cursor;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    //// constructor
    public AssignmentManager(Context context) {
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


    ///// method for inserting assignment
    public boolean addNewAssignment(AssignmentModel aAssignment) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_SUBMIT_DATE, aAssignment.getSubmitDate());
        values.put(DBHelper.KEY_TOPIC, aAssignment.getTopic());
        values.put(DBHelper.KEY_ASSIGNMENT_STATUS, aAssignment.getAssignmentStatus());
        values.put(DBHelper.KEY_SEMESTER_ID, aAssignment.getSemesterID());
        values.put(DBHelper.KEY_COURSE_ID, aAssignment.getCourseID());
        long isInserted = database.insert(DBHelper.TABLE_ASSIGNMENT, null, values);
        this.close();
        if (isInserted != -1)
            return true;
        else return false;
    }


    //// method for updating assignment
    public boolean editAssignment(int assignmentID, AssignmentModel aAssignment) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_SUBMIT_DATE, aAssignment.getSubmitDate());
        values.put(DBHelper.KEY_TOPIC, aAssignment.getTopic());
        values.put(DBHelper.KEY_ASSIGNMENT_STATUS, aAssignment.getAssignmentStatus());
        values.put(DBHelper.KEY_SEMESTER_ID, aAssignment.getSemesterID());
        values.put(DBHelper.KEY_COURSE_ID, aAssignment.getCourseID());
        int isUpdated = database.update(DBHelper.TABLE_ASSIGNMENT, values, DBHelper.KEY_ASSIGNMENT_ID + " = " + assignmentID, null);
        this.close();
        if (isUpdated > 0)
            return true;
        else
            return false;

    }


    /// method for getting all Assignment list
    public ArrayList<AssignmentModel> getAllAssignment() {
        this.open();
        allAssignment = new ArrayList<>();
        cursor = database.query(DBHelper.TABLE_ASSIGNMENT, null, null, null, null, null,  DBHelper.KEY_ASSIGNMENT_ID + " DESC", null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int assignemntID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ASSIGNMENT_ID));
                int semesterID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SEMESTER_ID));
                int courseID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COURSE_ID));

                String submitDate = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SUBMIT_DATE));

                String topic = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TOPIC));

                int status = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ASSIGNMENT_STATUS));

                aAssignment = new AssignmentModel(assignemntID, semesterID, courseID, submitDate, topic, status);
                allAssignment.add(aAssignment);
                cursor.moveToNext();
            }
        }
        this.close();
        return allAssignment;
    }

    public AssignmentModel getAssignmentByID(int assignmentID) {

        this.open();
        cursor = database.query(DBHelper.TABLE_ASSIGNMENT, new String[]{DBHelper.KEY_ASSIGNMENT_ID, DBHelper.KEY_SUBMIT_DATE, DBHelper.KEY_TOPIC, DBHelper.KEY_ASSIGNMENT_STATUS, DBHelper.KEY_SEMESTER_ID, DBHelper.KEY_COURSE_ID}, DBHelper.KEY_ASSIGNMENT_ID + " = " + assignmentID, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int assignemntID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ASSIGNMENT_ID));
            int semesterID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SEMESTER_ID));
            int courseID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COURSE_ID));

            String submitDate = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SUBMIT_DATE));

            String topic = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TOPIC));

            int status = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ASSIGNMENT_STATUS));

            aAssignment = new AssignmentModel(assignemntID, semesterID, courseID, submitDate, topic, status);
        }
        this.close();
        return aAssignment;
    }


    public boolean deleteAssignmentByID(int assignmentID) {

        this.open();
        int deleted = database.delete(DBHelper.TABLE_ASSIGNMENT, DBHelper.KEY_ASSIGNMENT_ID + " = " + assignmentID, null);
        this.close();

        if (deleted > 0) {
            return true;

        } else
            return false;
    }

    public boolean markAsDoneAssignmentByID(String topic, int status) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_ASSIGNMENT_STATUS, status);
        int updated = database.update(DBHelper.TABLE_ASSIGNMENT, contentValues, DBHelper.KEY_TOPIC + " = " + topic, null);
        this.close();
        if (updated > 0) {
            return true;
        } else return false;

    }


}
