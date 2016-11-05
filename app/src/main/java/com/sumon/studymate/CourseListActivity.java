package com.sumon.studymate;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class CourseListActivity extends AppCompatActivity {
    private ListView coursesListView;
    private AdapterForCourseList adapterForCourseList;
    private CourseManager courseManager;
    private ArrayList<CourseModel> allCourses;
    private EditText inputSearchET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        setTitle("Course List");
        coursesListView = (ListView) findViewById(R.id.coursesListView);
        inputSearchET = (EditText) findViewById(R.id.inputSearchET);

        fillAdapter();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddCourse);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddCourse();
            }
        });
        inputSearchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = inputSearchET.getText().toString().toLowerCase(Locale.getDefault());
                adapterForCourseList.filter(text);

            }
        });
    }

    private void fillAdapter() {
        courseManager = new CourseManager(this);
        allCourses = new ArrayList<>();
        allCourses = courseManager.getAllCourses();
        adapterForCourseList = new AdapterForCourseList(this, allCourses);
        adapterForCourseList.setFragmentManager(getFragmentManager());
        coursesListView.setAdapter(adapterForCourseList);
        adapterForCourseList.notifyDataSetChanged();
    }

    private void goToAddCourse() {
        startActivity(new Intent(CourseListActivity.this, AddCoursesActivity.class));
    }
}
