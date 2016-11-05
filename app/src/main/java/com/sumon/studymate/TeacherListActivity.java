package com.sumon.studymate;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TeacherListActivity extends AppCompatActivity {

    private TeacherManager teacherManager;
    private ArrayList<TeacherModel> teacherlist;
    private ListView teacherListView;
    private AdapterForTeacherList adapterForTeacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);
        setTitle("Teacher List");
        teacherListView = (ListView) findViewById(R.id.teacherListView);
        teacherManager = new TeacherManager(this);
        teacherlist = new ArrayList<>();
        teacherlist = teacherManager.getAllTeacher();
        adapterForTeacherList = new AdapterForTeacherList(this, teacherlist);
        adapterForTeacherList.setFragmentManager(getFragmentManager());

        teacherListView.setAdapter(adapterForTeacherList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddTeacher);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddTeacher();
            }
        });
    }

    private void goToAddTeacher() {
        startActivity(new Intent(TeacherListActivity.this, AddTeacherActivity.class));
        finish();
    }
}
