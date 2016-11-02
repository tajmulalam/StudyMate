package com.sumon.studymate;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class SemesterListActivity extends AppCompatActivity {
    private GridView semesterListView;
    private SemesterManager semesterManager;
    private ArrayList<SemesterModel> semesterModelArrayList;
    private AdapterForSemesterList adapterForSemesterList;
    private MySharedPrefManager mySharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        semesterListView = (GridView) findViewById(R.id.semesterListView);
        semesterManager = new SemesterManager(this);
        fillAdapter();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddSemester();
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
        startActivity(new Intent(SemesterListActivity.this, MainActivity.class));
        finish();

    }
}
