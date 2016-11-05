package com.sumon.studymate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Md Tajmul Alam Sumon on 11/5/2016.
 */

public class RoutineManager {
    ////data field
    private RoutineModel aRoutine;
    private ArrayList<RoutineModel> allRoutine;
    private Cursor cursor;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    //// constructor
    public RoutineManager(Context context) {
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


    public boolean addNewRoutine(RoutineModel aRoutine) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_DAY, aRoutine.getDay());
        values.put(DBHelper.KEY_START_TIME, aRoutine.getStartTime());
        values.put(DBHelper.KEY_END_TIME, aRoutine.getEndTime());
        values.put(DBHelper.KEY_PERIOD, aRoutine.getPeriod());
        values.put(DBHelper.KEY_SEMESTER_ID, aRoutine.getSemesterID());
        values.put(DBHelper.KEY_COURSE_ID, aRoutine.getCourseID());
        values.put(DBHelper.KEY_TEACHER_ID, aRoutine.getTeacherID());
        long isInserted = database.insert(DBHelper.TABLE_ROUTINE, null, values);
        this.close();
        if (isInserted == -1)
            return false;
        else
            return true;
    }

    public ArrayList<RoutineModel> getAllRoutineBySemesterID(int semesterID) {
        this.open();
        allRoutine = new ArrayList<>();
        cursor = database.query(DBHelper.TABLE_ROUTINE, new String[]{DBHelper.KEY_ROUTINE_ID, DBHelper.KEY_DAY, DBHelper.KEY_START_TIME, DBHelper.KEY_END_TIME, DBHelper.KEY_PERIOD, DBHelper.KEY_SEMESTER_ID, DBHelper.KEY_COURSE_ID, DBHelper.KEY_TEACHER_ID}, DBHelper.KEY_SEMESTER_ID + " = " + semesterID, null, null, null, DBHelper.KEY_ROUTINE_ID + " ASC");
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int routineID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ROUTINE_ID));
                semesterID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SEMESTER_ID));
                int courseID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COURSE_ID));
                int teacherID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEACHER_ID));

                String day = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DAY));
                String startTime = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_START_TIME));

                String endTime = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_END_TIME));

                String period = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PERIOD));

                aRoutine = new RoutineModel(routineID, day, startTime, endTime, period, semesterID, courseID,teacherID);
                allRoutine.add(aRoutine);
                cursor.moveToNext();
            }
        }
        this.close();
        return allRoutine;
    }

    public boolean deleteRoutineByID(int routineID) {
        this.open();
        int deleted = database.delete(DBHelper.TABLE_ROUTINE, DBHelper.KEY_ROUTINE_ID + " = " + routineID, null);

        this.close();

        if (deleted > 0) {
            return true;

        } else
            return false;
    }

    public ArrayList<RoutineModel> getAllRoutine() {
        this.open();
        allRoutine = new ArrayList<>();
        cursor = database.query(DBHelper.TABLE_ROUTINE, null, null, null, null, null, DBHelper.KEY_SEMESTER_ID + " ASC", null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int routineID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ROUTINE_ID));
                int semesterID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_SEMESTER_ID));
                int courseID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_COURSE_ID));
                int teacherID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEACHER_ID));

                String day = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DAY));
                String startTime = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_START_TIME));

                String endTime = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_END_TIME));

                String period = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PERIOD));

                aRoutine = new RoutineModel(routineID, day, startTime, endTime, period, semesterID, courseID,teacherID);
                allRoutine.add(aRoutine);
                cursor.moveToNext();
            }
        }
        this.close();
        return allRoutine;
    }
}
