package com.sumon.studymate.manager;

/**
 * Created by Md Tajmul Alam Sumon on 10/27/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by Sumon on 3/16/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "StudyMateDB";
    static final String TABLE_SEMESTER = "semesterTable";
    static final String TABLE_COURSES = "coursesTable";
    static final String TABLE_TEACHER = "teacherTable";
    static final String TABLE_ASSIGNMENT = "assignmentTable";
    static final String TABLE_CLASS_TEST = "classTestTable";
    static final String TABLE_ROUTINE= "routineTable";


    //SEMESTER TABLES COLUMNS
    static final String KEY_SEMESTER_ID = "semester_id";
    static final String KEY_SEMESTER_TITLE = "semester_title";
    static final String KEY_START_DATE = "start_date";
    static final String KEY_END_DATE = "end_date";

    //COURSES TABLE COLUMNS

    static final String KEY_COURSE_ID = "course_id";
    static final String KEY_COURSE_TITTLE = "course_title";
    static final String KEY_COURSE_CODE = "course_code";
    static final String KEY_COURSE_CREDIT = "course_credit";

    ////TEACHER  table columns
    static final String KEY_TEACHER_ID = "teacher_id";
    static final String KEY_TEACHER_NAME = "teacher_name";
    static final String KEY_TEACHER_DESIGNATION = "teacher_designation";
    static final String KEY_TEACHER_MOBILE = "teacher_mobile";
    static final String KEY_TEACHER_EMAIL = "teacher_email";



    ///// ASSIGNMENT TABLE COLUMNS
    static final String KEY_ASSIGNMENT_ID = "assignment_id";
    static final String KEY_SUBMIT_DATE = "submit_date";
    static final String KEY_TOPIC = "topic";
    static final String KEY_ASSIGNMENT_STATUS = "assignment_status";

    ///// CLASS TEST TABLE COLUMNS
    static final String KEY_CLASS_TEST_ID = "class_test_id";
    static final String KEY_TEST_DATE = "test_date";
    static final String KEY_TEST_TOPIC = "class_test_topic";
    static final String KEY_CLASS_TEST_STATUS = "class_test_status";

    ///// Routine TABLE COLUMNS
    static final String KEY_ROUTINE_ID = "routine_id";
    static final String KEY_DAY = "day";
    static final String KEY_START_TIME = "class_start_time";
    static final String KEY_END_TIME = "class_end_time";
    static final String KEY_PERIOD = "class_period";



    // SEMESTER table create statement
    private static final String CREATE_TABLE_SEMESTER = "CREATE TABLE " + TABLE_SEMESTER
            + "(" + KEY_SEMESTER_ID + " INTEGER PRIMARY KEY," + KEY_SEMESTER_TITLE + " TEXT,"
            + KEY_START_DATE + " DATETIME," + KEY_END_DATE + " DATETIME" + ")";


    // Table Create Statements
    // COURSES table create statement
    private static final String CREATE_TABLE_COURSES = "CREATE TABLE "
            + TABLE_COURSES + "(" + KEY_COURSE_ID + " INTEGER PRIMARY KEY," + KEY_COURSE_TITTLE
            + " TEXT," + KEY_COURSE_CODE + " TEXT," + KEY_COURSE_CREDIT + " TEXT," + KEY_SEMESTER_ID + " INTEGER," + KEY_TEACHER_ID + " INTEGER," + " FOREIGN KEY( " + KEY_SEMESTER_ID + " ) REFERENCES " + TABLE_SEMESTER + " ( " + KEY_SEMESTER_ID + " ) ON DELETE CASCADE," + " FOREIGN KEY( " + KEY_TEACHER_ID + " )REFERENCES " + TABLE_TEACHER + " ( " + KEY_TEACHER_ID + " ) ON DELETE CASCADE " + ")";


    // TEACHER table create statement
    private static final String CREATE_TABLE_TEACHER = "CREATE TABLE "
            + TABLE_TEACHER + "(" + KEY_TEACHER_ID + " INTEGER PRIMARY KEY," + KEY_TEACHER_NAME
            + " TEXT," + KEY_TEACHER_DESIGNATION + " TEXT," + KEY_TEACHER_MOBILE + " TEXT," + KEY_TEACHER_EMAIL + " TEXT," + KEY_SEMESTER_ID + " INTEGER," + " FOREIGN KEY( " + KEY_SEMESTER_ID + " ) REFERENCES " + TABLE_SEMESTER + " ( " + KEY_SEMESTER_ID + " ) ON DELETE CASCADE " + ")";


    // ASSIGNMENT table create statement
    private static final String CREATE_TABLE_ASSIGNMENT= "CREATE TABLE "
            + TABLE_ASSIGNMENT + "(" + KEY_ASSIGNMENT_ID + " INTEGER PRIMARY KEY," + KEY_SUBMIT_DATE
            + " DATETIME," + KEY_TOPIC + " TEXT," + KEY_ASSIGNMENT_STATUS + " INTEGER," + KEY_SEMESTER_ID + " INTEGER," + KEY_COURSE_ID + " INTEGER," + " FOREIGN KEY( " + KEY_SEMESTER_ID + " ) REFERENCES " + TABLE_SEMESTER + " ( " + KEY_SEMESTER_ID + " ) ON DELETE CASCADE," + " FOREIGN KEY( " + KEY_COURSE_ID + " )REFERENCES " + TABLE_COURSES + " ( " + KEY_COURSE_ID + " ) ON DELETE CASCADE " + ")";

    // ASSIGNMENT table create statement
    private static final String CREATE_TABLE_CLASS_TEST= "CREATE TABLE "
            + TABLE_CLASS_TEST + "(" + KEY_CLASS_TEST_ID + " INTEGER PRIMARY KEY," + KEY_TEST_DATE
            + " DATETIME," + KEY_TEST_TOPIC + " TEXT," + KEY_CLASS_TEST_STATUS + " INTEGER," + KEY_SEMESTER_ID + " INTEGER," + KEY_COURSE_ID + " INTEGER," + " FOREIGN KEY( " + KEY_SEMESTER_ID + " ) REFERENCES " + TABLE_SEMESTER + " ( " + KEY_SEMESTER_ID + " ) ON DELETE CASCADE," + " FOREIGN KEY( " + KEY_COURSE_ID + " )REFERENCES " + TABLE_COURSES + " ( " + KEY_COURSE_ID + " ) ON DELETE CASCADE " + ")";



    // ROUTINE table create statement
    private static final String CREATE_TABLE_CLASS_ROUTINE= "CREATE TABLE "
            + TABLE_ROUTINE + "(" + KEY_ROUTINE_ID + " INTEGER PRIMARY KEY," + KEY_DAY
            + " TEXT," + KEY_START_TIME + " DATETIME," + KEY_END_TIME + " DATETIME,"+ KEY_PERIOD + " TEXT," + KEY_SEMESTER_ID + " INTEGER," + KEY_COURSE_ID + " INTEGER," + KEY_TEACHER_ID + " INTEGER,"+ " FOREIGN KEY( " + KEY_SEMESTER_ID + " ) REFERENCES " + TABLE_SEMESTER + " ( " + KEY_SEMESTER_ID + " ) ON DELETE CASCADE," + " FOREIGN KEY( " + KEY_COURSE_ID + " )REFERENCES " + TABLE_COURSES + " ( " + KEY_COURSE_ID + " ) ON DELETE CASCADE, "+ " FOREIGN KEY( " + KEY_TEACHER_ID + " )REFERENCES " + TABLE_TEACHER + " ( " + KEY_TEACHER_ID + " ) ON DELETE CASCADE " + ")";




    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_SEMESTER);
        db.execSQL(CREATE_TABLE_COURSES);
        db.execSQL(CREATE_TABLE_TEACHER);
        db.execSQL(CREATE_TABLE_ASSIGNMENT);
        db.execSQL(CREATE_TABLE_CLASS_TEST);
        db.execSQL(CREATE_TABLE_CLASS_ROUTINE);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            }


        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEMESTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS_TEST);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_CLASS_ROUTINE);


        // create new tables
        onCreate(db);
    }
}