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
    private FloatingActionButton fab;

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

        fab = (FloatingActionButton) findViewById(R.id.fabAddTeacher);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddTeacher();
            }
        });
        teacherListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    // End has been reached
                    fab.startAnimation(AnimationUtils.loadAnimation(TeacherListActivity.this,
                            android.R.anim.fade_out));
                    fab.setVisibility(View.GONE);
                } else {
                    fab.startAnimation(AnimationUtils.loadAnimation(TeacherListActivity.this,
                            android.R.anim.fade_in));
                    fab.setVisibility(View.VISIBLE);
                }
                if (visibleItemCount == totalItemCount)
                    fab.setVisibility(View.VISIBLE);
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
