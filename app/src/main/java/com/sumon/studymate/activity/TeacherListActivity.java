package com.sumon.studymate.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.sumon.studymate.R;
import com.sumon.studymate.manager.TeacherManager;
import com.sumon.studymate.model.TeacherModel;
import com.sumon.studymate.adapter.AdapterForTeacherList;

import java.util.ArrayList;
import java.util.Locale;

public class TeacherListActivity extends AppCompatActivity {

    private TeacherManager teacherManager;
    private ArrayList<TeacherModel> teacherlist;
    private ListView teacherListView;
    private AdapterForTeacherList adapterForTeacherList;
    private EditText inputSearchForTeacherET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);
        setTitle("Teacher List");
        teacherListView = (ListView) findViewById(R.id.teacherListView);
        inputSearchForTeacherET = (EditText) findViewById(R.id.inputSearchForTeacherET);
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
        inputSearchForTeacherET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = inputSearchForTeacherET.getText().toString().toLowerCase(Locale.getDefault());
                adapterForTeacherList.filter(text);
            }
        });
    }

    private void goToAddTeacher() {
        startActivity(new Intent(TeacherListActivity.this, AddTeacherActivity.class));
        finish();
    }


}
