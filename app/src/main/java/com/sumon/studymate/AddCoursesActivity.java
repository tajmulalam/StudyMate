package com.sumon.studymate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class AddCoursesActivity extends AppCompatActivity {
    private Spinner semesterListSpinner, teacherListSpinner;
    private AdapterForSemesterSpinner adapterForSemesterList;
    private ArrayList<SemesterModel> semesterModelArrayList;
    private SemesterManager semesterManager;
    private LinearLayout newTeacherLnLayout;
    private ArrayList<TeacherModel> teacherListActivityArrayList;
    private TeacherManager teacherManager;
    private AdapterForTeacherSpinner adapterForTeacherSpinner;

    private EditText courseTitleET, courseCodeET, courseCreditET;
    private Button btnResetTeacherFillData, addNewCourseBtn;
    private ImageButton addNewTeacherIBtn;
    private LinearLayout selectTeacherSpinnerLayoutLn;

    // for inserting data
    private int semesterID = -1;
    private int teacherID = -1;
    private CourseModel aCourse;
    private CourseManager courseManager;
    private int courseId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        courseId = getIntent().getIntExtra("courseID", 0);
            init();

    }

//    private void initForUpdate() {
//        selectTeacherSpinnerLayoutLn = (LinearLayout) findViewById(R.id.selectTeacherSpinnerLayoutLn);
//        courseTitleET = (EditText) findViewById(R.id.courseTitleET);
//        courseCodeET = (EditText) findViewById(R.id.courseCodeET);
//        courseCreditET = (EditText) findViewById(R.id.courseCreditET);
//        addNewTeacherIBtn = (ImageButton) findViewById(R.id.addNewTeacherIBtn);
//        btnResetTeacherFillData = (Button) findViewById(R.id.btnResetTeacherFillData);
//        addNewCourseBtn = (Button) findViewById(R.id.addNewCourseBtn);
//
//
//        newTeacherLnLayout = (LinearLayout) findViewById(R.id.newTeacherLnLayout);
//        semesterManager = new SemesterManager(this);
//        semesterModelArrayList = semesterManager.getAllSemester();
//        teacherManager = new TeacherManager(this);
//        courseManager = new CourseManager(this);
//        teacherListActivityArrayList = teacherManager.getAllTeacher();
//        if (teacherListActivityArrayList.size() > 0) {
//            newTeacherLnLayout.setVisibility(View.GONE);
//            selectTeacherSpinnerLayoutLn.setVisibility(View.VISIBLE);
//        } else {
//            newTeacherLnLayout.setVisibility(View.VISIBLE);
//            selectTeacherSpinnerLayoutLn.setVisibility(View.GONE);
//        }
//
//        semesterListSpinner = (Spinner) findViewById(R.id.semesterListSpinner);
//        teacherListSpinner = (Spinner) findViewById(R.id.teacherListSpinner);
//
//        aCourse = courseManager.getCourseByID(courseId);
//        courseTitleET.setText(aCourse.getCourseTitle());
//        courseCodeET.setText(aCourse.getCourseCode());
//        courseCreditET.setText(aCourse.getCourseCredit());
//
//
//        adapterForTeacherSpinner = new AdapterForTeacherSpinner(this, teacherListActivityArrayList);
//        teacherListSpinner.setAdapter(adapterForTeacherSpinner);
//        teacherListSpinner.setSelection(aCourse.getCourseTeacherID() - 1);
//
//        adapterForSemesterList = new AdapterForSemesterSpinner(this, semesterModelArrayList);
//        semesterListSpinner.setAdapter(adapterForSemesterList);
//        semesterListSpinner.setSelection(aCourse.getCourseSemesterID() - 1);
//
//
//        semesterListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                semesterID = semesterModelArrayList.get(position).getSemesterID();
//                teacherListActivityArrayList = teacherManager.getAllTeacherBySemesterID(semesterID);
//                adapterForTeacherSpinner = new AdapterForTeacherSpinner(AddCoursesActivity.this, teacherListActivityArrayList);
//                teacherListSpinner.setAdapter(adapterForTeacherSpinner);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        teacherListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                teacherID = teacherListActivityArrayList.get(position).getTeacherID();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        addNewTeacherIBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToAddNewTeacher();
//            }
//        });
//
//        addNewCourseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertCourseInformation();
//            }
//        });
//
//        btnResetTeacherFillData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resetFieldDate();
//            }
//        });
//
//    }

    private void init() {
        selectTeacherSpinnerLayoutLn = (LinearLayout) findViewById(R.id.selectTeacherSpinnerLayoutLn);
        courseTitleET = (EditText) findViewById(R.id.courseTitleET);
        courseCodeET = (EditText) findViewById(R.id.courseCodeET);
        courseCreditET = (EditText) findViewById(R.id.courseCreditET);
        addNewTeacherIBtn = (ImageButton) findViewById(R.id.addNewTeacherIBtn);
        btnResetTeacherFillData = (Button) findViewById(R.id.btnResetTeacherFillData);
        addNewCourseBtn = (Button) findViewById(R.id.addNewCourseBtn);


        newTeacherLnLayout = (LinearLayout) findViewById(R.id.newTeacherLnLayout);
        semesterManager = new SemesterManager(this);
        semesterModelArrayList = semesterManager.getAllSemester();
        teacherManager = new TeacherManager(this);
        courseManager = new CourseManager(this);
        teacherListActivityArrayList = teacherManager.getAllTeacher();
        if (teacherListActivityArrayList.size() > 0) {
            newTeacherLnLayout.setVisibility(View.GONE);
            selectTeacherSpinnerLayoutLn.setVisibility(View.VISIBLE);
        } else {
            newTeacherLnLayout.setVisibility(View.VISIBLE);
            selectTeacherSpinnerLayoutLn.setVisibility(View.GONE);
        }

            semesterListSpinner = (Spinner) findViewById(R.id.semesterListSpinner);
            teacherListSpinner = (Spinner) findViewById(R.id.teacherListSpinner);
        if (courseId == -1) {

            adapterForTeacherSpinner = new AdapterForTeacherSpinner(this, teacherListActivityArrayList);
            teacherListSpinner.setAdapter(adapterForTeacherSpinner);

            adapterForSemesterList = new AdapterForSemesterSpinner(this, semesterModelArrayList);
            semesterListSpinner.setAdapter(adapterForSemesterList);
        }else{

            aCourse = courseManager.getCourseByID(courseId);
            courseTitleET.setText(aCourse.getCourseTitle());
            courseCodeET.setText(aCourse.getCourseCode());
            courseCreditET.setText(aCourse.getCourseCredit());


            adapterForTeacherSpinner = new AdapterForTeacherSpinner(this, teacherListActivityArrayList);
            teacherListSpinner.setAdapter(adapterForTeacherSpinner);
            teacherListSpinner.setSelection(aCourse.getCourseTeacherID() - 1);

            adapterForSemesterList = new AdapterForSemesterSpinner(this, semesterModelArrayList);
            semesterListSpinner.setAdapter(adapterForSemesterList);
            semesterListSpinner.setSelection(aCourse.getCourseSemesterID() - 1);
        }


        semesterListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semesterID = semesterModelArrayList.get(position).getSemesterID();
                teacherListActivityArrayList = teacherManager.getAllTeacherBySemesterID(semesterID);
                adapterForTeacherSpinner = new AdapterForTeacherSpinner(AddCoursesActivity.this, teacherListActivityArrayList);
                teacherListSpinner.setAdapter(adapterForTeacherSpinner);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        teacherListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacherID = teacherListActivityArrayList.get(position).getTeacherID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addNewTeacherIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddNewTeacher();
            }
        });

        addNewCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCourseInformation();
            }
        });

        btnResetTeacherFillData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFieldDate();
            }
        });

    }

    private void resetFieldDate() {
        courseTitleET.getText().clear();
        courseCreditET.getText().clear();
        courseCodeET.getText().clear();
    }

    private void insertCourseInformation() {

        String title = courseTitleET.getText().toString();
        String credit = courseCreditET.getText().toString();
        String code = courseCodeET.getText().toString();
        if (title.length() > 0 && credit.length() > 0 && code.length() > 0 && teacherID != -1 && semesterID != -1) {
            aCourse = new CourseModel(semesterID, teacherID, title, code, credit);
            boolean isInserted = courseManager.insertCourse(aCourse);
            if (isInserted) {
                CustomToast.SuccessToast(AddCoursesActivity.this, "Course Inserted");
                android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(AddCoursesActivity.this, CourseListActivity.class));
                        finish();
                    }
                }, 1000);
            } else
                CustomToast.FailToast(AddCoursesActivity.this, "Failed To Insert");
        } else
            CustomToast.FailToast(AddCoursesActivity.this, "Please Fill The Information Properly");

    }

    private void goToAddNewTeacher() {
        startActivity(new Intent(AddCoursesActivity.this, AddTeacherActivity.class));

    }

    public void back(View view) {
        startActivity(new Intent(AddCoursesActivity.this, MainActivity.class));

    }
}
