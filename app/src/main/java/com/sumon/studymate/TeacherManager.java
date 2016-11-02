package com.sumon.studymate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Md Tajmul Alam Sumon on 10/29/2016.
 */

public class TeacherManager {
    ////data field
    private TeacherModel aTeacher;
    private ArrayList<TeacherModel> allTeacher;
    private Cursor cursor;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    //// constructor
    public TeacherManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    ///// open database  connection
    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    /// close database connection
    private void close() {
        dbHelper.close();
    }

    public boolean addNewTeacher(int semesterID,TeacherModel aTeacher){
        this.open();
        ContentValues  values=new ContentValues();
        values.put(DBHelper.KEY_TEACHER_NAME,aTeacher.getTeacherName());
        values.put(DBHelper.KEY_TEACHER_DESIGNATION,aTeacher.getTeacherDesignation());
        values.put(DBHelper.KEY_TEACHER_MOBILE,aTeacher.getTeacherMobile());
        values.put(DBHelper.KEY_TEACHER_EMAIL,aTeacher.getTeacherEmail());
        values.put(DBHelper.KEY_SEMESTER_ID,semesterID);
        long isInserted=database.insert(DBHelper.TABLE_TEACHER,null,values);
        this.close();
        if (isInserted==-1)
            return  false;
        else
            return true;

    }

    public ArrayList<TeacherModel> getAllTeacher(){
        this.open();
        allTeacher=new ArrayList<>();
        cursor = database.query(DBHelper.TABLE_TEACHER, null, null, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int teacherID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEACHER_ID));

                String teacherName = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEACHER_NAME));

                String designation = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEACHER_DESIGNATION));

                String mob = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEACHER_MOBILE));
                String email = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEACHER_EMAIL));

                aTeacher = new TeacherModel(teacherID, teacherName, designation,mob,email);
                allTeacher.add(aTeacher);
                cursor.moveToNext();
            }
        }
        this.close();
        return allTeacher;
    }
    public ArrayList<TeacherModel> getAllTeacherBySemesterID(int id){
        this.open();
        allTeacher=new ArrayList<>();
        cursor = database.query(DBHelper.TABLE_TEACHER, new String[]{DBHelper.KEY_TEACHER_ID, DBHelper.KEY_TEACHER_NAME, DBHelper.KEY_TEACHER_DESIGNATION, DBHelper.KEY_TEACHER_MOBILE, DBHelper.KEY_TEACHER_EMAIL}, DBHelper.KEY_SEMESTER_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int teacherID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEACHER_ID));

                String teacherName = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEACHER_NAME));

                String designation = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEACHER_DESIGNATION));

                String mob = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEACHER_MOBILE));
                String email = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEACHER_EMAIL));

                aTeacher = new TeacherModel(teacherID, teacherName, designation,mob,email);
                allTeacher.add(aTeacher);
                cursor.moveToNext();
            }
        }
        this.close();
        return allTeacher;
    }
    public boolean editTeacher(int teacherId, TeacherModel aTeacher,int semesterID) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_TEACHER_NAME,aTeacher.getTeacherName());
        values.put(DBHelper.KEY_TEACHER_DESIGNATION,aTeacher.getTeacherDesignation());
        values.put(DBHelper.KEY_TEACHER_MOBILE,aTeacher.getTeacherMobile());
        values.put(DBHelper.KEY_TEACHER_EMAIL,aTeacher.getTeacherEmail());
        values.put(DBHelper.KEY_SEMESTER_ID,semesterID);
        int isUpdated = database.update(DBHelper.TABLE_TEACHER, values, DBHelper.KEY_TEACHER_ID + " = " + teacherId, null);
        this.close();
        if (isUpdated > 0)
            return true;
        else
            return false;

    }

    public TeacherModel getTeacherByID(int id) {
        this.open();
        cursor = database.query(DBHelper.TABLE_TEACHER, new String[]{DBHelper.KEY_TEACHER_ID, DBHelper.KEY_TEACHER_NAME, DBHelper.KEY_TEACHER_DESIGNATION, DBHelper.KEY_TEACHER_MOBILE, DBHelper.KEY_TEACHER_EMAIL}, DBHelper.KEY_TEACHER_ID + " = " + id, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int teacherID=cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TEACHER_ID));
            String teacherName = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEACHER_NAME));

            String teacherDesig = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEACHER_DESIGNATION));

            String teacherMob = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEACHER_MOBILE));
            String teacherEmail = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEACHER_EMAIL));

            aTeacher = new TeacherModel(teacherID, teacherName, teacherDesig, teacherMob,teacherEmail);
        }
        this.close();
        return aTeacher;
    }

    public void deleteTeacherBySemesterID(int semesterListID) {
        this.open();
        database.delete(DBHelper.TABLE_TEACHER, DBHelper.KEY_SEMESTER_ID + " = " + semesterListID, null);
        this.close();

    }

}
