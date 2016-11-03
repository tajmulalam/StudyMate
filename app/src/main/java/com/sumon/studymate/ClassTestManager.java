package com.sumon.studymate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Md Tajmul Alam Sumon on 11/2/2016.
 */

public class ClassTestManager {
    ////data field
    private ClassTestModel aClassTest;
    private ArrayList<ClassTestModel> allClassTest;
    private Cursor cursor;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    //// constructor
    public ClassTestManager(Context context) {
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
    public boolean addNewClassTest(ClassTestModel aClassTest) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_TEST_DATE, aClassTest.getTestDate());
        values.put(DBHelper.KEY_TEST_TOPIC, aClassTest.getClassTestTopic());
        values.put(DBHelper.KEY_CLASS_TEST_STATUS, aClassTest.getClassTestStatus());
        values.put(DBHelper.KEY_SEMESTER_ID, aClassTest.getSemesterID());
        values.put(DBHelper.KEY_COURSE_ID, aClassTest.getCourseID());
        long isInserted = database.insert(DBHelper.TABLE_CLASS_TEST, null, values);
        this.close();
        if (isInserted != -1)
            return true;
        else return false;
    }


    //// method for updating assignment
    public boolean editClassTest(int classTestID, ClassTestModel aClassTest) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_TEST_DATE, aClassTest.getTestDate());
        values.put(DBHelper.KEY_TEST_TOPIC, aClassTest.getClassTestTopic());
        values.put(DBHelper.KEY_CLASS_TEST_STATUS, aClassTest.getClassTestStatus());
        values.put(DBHelper.KEY_SEMESTER_ID, aClassTest.getSemesterID());
        values.put(DBHelper.KEY_COURSE_ID, aClassTest.getCourseID());
        int isUpdated = database.update(DBHelper.TABLE_CLASS_TEST, values, DBHelper.KEY_CLASS_TEST_ID + " = " + classTestID, null);
        this.close();
        if (isUpdated > 0)
            return true;
        else
            return false;

    }


    /// method for getting all Assignment list
    public ArrayList<ClassTestModel> getAllClassTest() {
        this.open();
        allClassTest = new ArrayList<>();
        cursor = database.query(DBHelper.TABLE_CLASS_TEST, null, null, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {

                int classTestID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_CLASS_TEST_ID));
                int semesterID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SEMESTER_ID));
                int courseID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COURSE_ID));

                String testDate = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEST_DATE));

                String testTopic = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEST_TOPIC));

                int status = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_CLASS_TEST_STATUS));

                aClassTest = new ClassTestModel(classTestID, semesterID, courseID, testDate, testTopic, status);
                allClassTest.add(aClassTest);
                cursor.moveToNext();
            }
        }
        this.close();
        return allClassTest;
    }

    public ClassTestModel getClassTestByID(int classTestID) {

        this.open();
        cursor = database.query(DBHelper.TABLE_CLASS_TEST, new String[]{DBHelper.KEY_CLASS_TEST_ID, DBHelper.KEY_TEST_DATE, DBHelper.KEY_TEST_TOPIC, DBHelper.KEY_CLASS_TEST_STATUS, DBHelper.KEY_SEMESTER_ID, DBHelper.KEY_COURSE_ID}, DBHelper.KEY_CLASS_TEST_ID + " = " + classTestID, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {


            int classTestId = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_CLASS_TEST_ID));
            int semesterID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SEMESTER_ID));
            int courseID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COURSE_ID));

            String testDate = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEST_DATE));

            String testTopic = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEST_TOPIC));

            int status = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_CLASS_TEST_STATUS));

            aClassTest = new ClassTestModel(classTestId, semesterID, courseID, testDate, testTopic, status);

        }
        this.close();
        return aClassTest;
    }


    public boolean deleteClassTestByID(int classTestID) {

        this.open();
        int deleted = database.delete(DBHelper.TABLE_CLASS_TEST, DBHelper.KEY_CLASS_TEST_ID + " = " + classTestID, null);
        this.close();

        if (deleted > 0) {
            return true;

        } else
            return false;
    }
}
