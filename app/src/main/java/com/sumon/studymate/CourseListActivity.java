package com.sumon.studymate;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class CourseListActivity extends AppCompatActivity {
    private ListView coursesListView;
    private AdapterForCourseList adapterForCourseList;
    private CourseManager courseManager;
    private ArrayList<CourseModel> allCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        coursesListView = (ListView) findViewById(R.id.coursesListView);
        courseManager = new CourseManager(this);
        allCourses = courseManager.getAllCourses();
        adapterForCourseList = new AdapterForCourseList(this, allCourses);
        coursesListView.setAdapter(adapterForCourseList);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddCourse);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddCourse();
            }
        });
    }

    private void goToAddCourse() {
        startActivity(new Intent(CourseListActivity.this, AddCoursesActivity.class));
    }
}
