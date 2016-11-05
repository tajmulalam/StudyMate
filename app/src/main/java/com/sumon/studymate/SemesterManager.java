package com.sumon.studymate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Md Tajmul Alam Sumon on 10/27/2016.
 */

public class SemesterManager {
    ////data field
    private SemesterModel aSemester;
    private ArrayList<SemesterModel> allSemester;
    private Cursor cursor;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    //// constructor
    public SemesterManager(Context context) {
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

    ///// method for inserting semester
    public boolean addSemester(SemesterModel aSemester) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_SEMESTER_TITLE, aSemester.getSemesterTitle());
        values.put(DBHelper.KEY_START_DATE, aSemester.getSemester_start_date());
        values.put(DBHelper.KEY_END_DATE, aSemester.getSemester_end_date());
        long isInserted = database.insert(DBHelper.TABLE_SEMESTER, null, values);
        this.close();
        if (isInserted != -1)
            return true;
        else return false;
    }

    //// method for updating semester
    public boolean editSemester(int id, SemesterModel aSemester) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_SEMESTER_TITLE, aSemester.getSemesterTitle());
        values.put(DBHelper.KEY_START_DATE, aSemester.getSemester_start_date());
        values.put(DBHelper.KEY_END_DATE, aSemester.getSemester_end_date());
        int isUpdated = database.update(DBHelper.TABLE_SEMESTER, values, DBHelper.KEY_SEMESTER_ID + " = " + id, null);
        this.close();
        if (isUpdated > 0)
            return true;
        else
            return false;

    }

    /// method for getting all semester list
    public ArrayList<SemesterModel> getAllSemester() {
        this.open();
        allSemester = new ArrayList<>();
        cursor = database.query(DBHelper.TABLE_SEMESTER, null, null, null, null, null, DBHelper.KEY_SEMESTER_ID + " ASC", null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int semesterID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SEMESTER_ID));

                String semesterTitle = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SEMESTER_TITLE));

                String start_date = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_START_DATE));

                String end_date = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_END_DATE));

                aSemester = new SemesterModel(semesterID, semesterTitle, start_date, end_date);
                allSemester.add(aSemester);
                cursor.moveToNext();
            }
        }
        this.close();
        return allSemester;
    }

    public SemesterModel getSemesterByID(int id) {
        this.open();
        cursor = database.query(DBHelper.TABLE_SEMESTER, new String[]{DBHelper.KEY_SEMESTER_ID, DBHelper.KEY_SEMESTER_TITLE, DBHelper.KEY_START_DATE, DBHelper.KEY_END_DATE}, DBHelper.KEY_SEMESTER_ID + " = " + id, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int semesterID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SEMESTER_ID));
            String semesterTitle = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SEMESTER_TITLE));

            String start_date = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_START_DATE));

            String end_date = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_END_DATE));

            aSemester = new SemesterModel(semesterID, semesterTitle, start_date, end_date);
        }
        this.close();
        return aSemester;
    }

    public boolean deleteSemesterByID(int semesterListID) {

        this.open();
        int deleted = database.delete(DBHelper.TABLE_SEMESTER, DBHelper.KEY_SEMESTER_ID + " = " + semesterListID, null);

        this.close();

        if (deleted > 0) {
            return true;

        } else
            return false;
    }
}
