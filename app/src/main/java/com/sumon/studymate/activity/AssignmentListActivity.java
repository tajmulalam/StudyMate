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

import com.sumon.studymate.manager.AssignmentManager;
import com.sumon.studymate.model.AssignmentModel;
import com.sumon.studymate.R;
import com.sumon.studymate.adapter.AdapterForAssignmentList;

import java.util.ArrayList;
import java.util.Locale;

public class AssignmentListActivity extends AppCompatActivity {
    private ListView assignmentListView;
    private AssignmentManager assignmentManager;
    private ArrayList<AssignmentModel> assignmentModelArrayList;
    private AdapterForAssignmentList adapterForAssignmentList;
    private EditText inputSearchAssignmetnET;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_list);
        setTitle("Assignment List");

        assignmentListView = (ListView) findViewById(R.id.assignmentListView);
        inputSearchAssignmetnET = (EditText) findViewById(R.id.inputSearchAssignmetnET);

         fab = (FloatingActionButton) findViewById(R.id.fabNewAssignment);

        assignmentManager = new AssignmentManager(this);
        fillAdapter();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddAssignment();
            }
        });

        assignmentListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    // End has been reached
                    fab.startAnimation(AnimationUtils.loadAnimation(AssignmentListActivity.this,
                            android.R.anim.fade_out));
                    fab.setVisibility(View.GONE);
                } else {
                    fab.startAnimation(AnimationUtils.loadAnimation(AssignmentListActivity.this,
                            android.R.anim.fade_in));
                    fab.setVisibility(View.VISIBLE);
                }
                if (visibleItemCount == totalItemCount)
                    fab.setVisibility(View.VISIBLE);
            }
        });
        inputSearchAssignmetnET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = inputSearchAssignmetnET.getText().toString().toLowerCase(Locale.getDefault());
                adapterForAssignmentList.filter(text);
            }
        });

    }

    private void goToAddAssignment() {
        startActivity(new Intent(AssignmentListActivity.this, AddAssignmentActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void fillAdapter() {
        assignmentModelArrayList = new ArrayList<>();
        assignmentModelArrayList = assignmentManager.getAllAssignment();

        adapterForAssignmentList = new AdapterForAssignmentList(this, assignmentModelArrayList);
        adapterForAssignmentList.setFragmentManager(getFragmentManager());
        assignmentListView.setAdapter(adapterForAssignmentList);
        adapterForAssignmentList.notifyDataSetChanged();

    }

}
