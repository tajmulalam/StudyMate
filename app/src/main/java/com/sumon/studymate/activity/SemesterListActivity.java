package com.sumon.studymate.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.sumon.studymate.util.MySharedPrefManager;
import com.sumon.studymate.R;
import com.sumon.studymate.manager.SemesterManager;
import com.sumon.studymate.model.SemesterModel;
import com.sumon.studymate.adapter.AdapterForSemesterList;

import java.util.ArrayList;

public class SemesterListActivity extends AppCompatActivity {
    private ListView semesterListView;
    private SemesterManager semesterManager;
    private ArrayList<SemesterModel> semesterModelArrayList;
    private AdapterForSemesterList adapterForSemesterList;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_list);
        setTitle("Semester List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         fab = (FloatingActionButton) findViewById(R.id.fab);

        semesterListView = (ListView) findViewById(R.id.semesterListView);
        semesterManager = new SemesterManager(this);

        fillAdapter();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddSemester();
            }
        });
        semesterListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    // End has been reached
                    fab.startAnimation(AnimationUtils.loadAnimation(SemesterListActivity.this,
                            android.R.anim.fade_out));
                    fab.setVisibility(View.GONE);
                } else {
                    fab.startAnimation(AnimationUtils.loadAnimation(SemesterListActivity.this,
                            android.R.anim.fade_in));
                    fab.setVisibility(View.VISIBLE);
                }
                if (visibleItemCount == totalItemCount)
                    fab.setVisibility(View.VISIBLE);
            }
        });
        semesterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToAddCourse(position);
                Toast.makeText(SemesterListActivity.this, "jgdfjghjfg", Toast.LENGTH_SHORT).show();
            }
        });
        semesterListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(SemesterListActivity.this, AddCoursesActivity.class));
                return false;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void fillAdapter() {

        semesterModelArrayList = semesterManager.getAllSemester();
        if (semesterModelArrayList.size() > 0) {
            adapterForSemesterList = new AdapterForSemesterList(this, semesterModelArrayList);
            adapterForSemesterList.setFragmentManager(getFragmentManager());
            semesterListView.setAdapter(adapterForSemesterList);
            adapterForSemesterList.notifyDataSetChanged();
        }
    }

    private void goToAddCourse(int position) {
//        String key="semesterID";
//        int semesterID = semesterModelArrayList.get(position).getSemesterID();
//        Intent todoListIntent = new Intent(SemesterListActivity.this, AddCoursesActivity.class);
//        mySharedPrefManager.insertIntoPreferenceInt(key,semesterID);
//        startActivity(todoListIntent);
        startActivity(new Intent(SemesterListActivity.this, TeacherListActivity.class));
        finish();
    }

    private void goToAddSemester() {
        startActivity(new Intent(SemesterListActivity.this, AddSemesterActivity.class));
        finish();

    }
}
