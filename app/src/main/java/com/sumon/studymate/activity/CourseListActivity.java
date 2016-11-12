package com.sumon.studymate.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import com.sumon.studymate.manager.CourseManager;
import com.sumon.studymate.model.CourseModel;
import com.sumon.studymate.R;
import com.sumon.studymate.adapter.AdapterForCourseList;

import java.util.ArrayList;
import java.util.Locale;

public class CourseListActivity extends AppCompatActivity {
    private ListView coursesListView;
    private AdapterForCourseList adapterForCourseList;
    private CourseManager courseManager;
    private ArrayList<CourseModel> allCourses;
    private EditText inputSearchET;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        setTitle("Course List");
        coursesListView = (ListView) findViewById(R.id.coursesListView);
        inputSearchET = (EditText) findViewById(R.id.inputSearchET);

        fillAdapter();
         fab = (FloatingActionButton) findViewById(R.id.fabAddCourse);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddCourse();
            }
        });

        coursesListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    // End has been reached
                    fab.startAnimation(AnimationUtils.loadAnimation(CourseListActivity.this,
                            android.R.anim.fade_out));
                    fab.setVisibility(View.GONE);
                } else {
                    fab.startAnimation(AnimationUtils.loadAnimation(CourseListActivity.this,
                            android.R.anim.fade_in));
                    fab.setVisibility(View.VISIBLE);
                }
                if (visibleItemCount == totalItemCount)
                    fab.setVisibility(View.VISIBLE);
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
