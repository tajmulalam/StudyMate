package com.sumon.studymate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class LauncherActivity extends AppCompatActivity {

    private CardView semesterDashboardCardView, courseDashboardCardView, assignmentDashboardCardView,
            classTestDashboardCardView, teacherDashboardCardView, routineDashboardCardView;
    private TextView semesterCountTV, courseCountTV, classTestCountTV, assignmentCountTV, teacherCountTV, routineCountTV;

    //    all managers
    private SemesterManager semesterManager;
    private ArrayList<SemesterModel> allSemester;
    private CourseManager courseManager;
    private ArrayList<CourseModel> allCourses;
    private TeacherManager teacherManager;
    private ArrayList<TeacherModel> teacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        init();
    }


    private void init() {
        semesterDashboardCardView = (CardView) findViewById(R.id.semesterDashboardCardView);
        courseDashboardCardView = (CardView) findViewById(R.id.courseDashboardCardView);
        assignmentDashboardCardView = (CardView) findViewById(R.id.assignmentDashboardCardView);
        classTestDashboardCardView = (CardView) findViewById(R.id.classTestDashboardCardView);
        teacherDashboardCardView = (CardView) findViewById(R.id.teacherDashboardCardView);
        routineDashboardCardView = (CardView) findViewById(R.id.routineDashboardCardView);
        semesterCountTV = (TextView) findViewById(R.id.semesterCountTV);
        courseCountTV = (TextView) findViewById(R.id.courseCountTV);
        classTestCountTV = (TextView) findViewById(R.id.classTestCountTV);
        assignmentCountTV = (TextView) findViewById(R.id.assignmentCountTV);
        teacherCountTV = (TextView) findViewById(R.id.teacherCountTV);
        routineCountTV = (TextView) findViewById(R.id.routineCountTV);
        initManagers();

        semesterDashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSemesterList();
            }
        });
        courseDashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCoursesList();
            }
        });

        assignmentDashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAssignmentList();
            }
        });
        classTestDashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToClassTestList();
            }
        });

        teacherDashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTeacherList();
            }
        });

        routineDashboardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRoutine();
            }
        });
    }

    private void initManagers() {

        semesterManager = new SemesterManager(this);
        courseManager = new CourseManager(this);
        teacherManager = new TeacherManager(this);

        //fill arraylist
        allSemester = new ArrayList<>();
        allSemester = semesterManager.getAllSemester();

        allCourses = new ArrayList<>();
        allCourses = courseManager.getAllCourses();

        teacherList = new ArrayList<>();
        teacherList = teacherManager.getAllTeacher();


        semesterCountTV.setText(String.valueOf(allSemester.size()));
        courseCountTV.setText(String.valueOf(allCourses.size()));
        teacherCountTV.setText(String.valueOf(teacherList.size()));
        assignmentCountTV.setText(String.valueOf(new AssignmentManager(this).getAllAssignment().size()));
    }

    private void goToRoutine() {

    }

    private void goToTeacherList() {
        startActivity(new Intent(LauncherActivity.this, TeacherListActivity.class));

    }

    private void goToClassTestList() {

    }

    private void goToAssignmentList() {
        startActivity(new Intent(LauncherActivity.this, AssignmentListActivity.class));

    }

    private void goToCoursesList() {
        startActivity(new Intent(LauncherActivity.this, CourseListActivity.class));

    }

    private void goToSemesterList() {
        startActivity(new Intent(LauncherActivity.this, SemesterListActivity.class));
    }
}
