package com.sumon.studymate;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AssignmentListActivity extends AppCompatActivity {
    private ListView assignmentListView;
    private AssignmentManager assignmentManager;
    private ArrayList<AssignmentModel> assignmentModelArrayList;
    private AdapterForAssignmentList adapterForAssignmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_list);

        assignmentListView = (ListView) findViewById(R.id.assignmentListView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNewAssignment);

        assignmentManager = new AssignmentManager(this);
        fillAdapter();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddAssignment();
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
        assignmentModelArrayList=new ArrayList<>();
        assignmentModelArrayList = assignmentManager.getAllAssignment();

        adapterForAssignmentList = new AdapterForAssignmentList(this, assignmentModelArrayList);
        adapterForAssignmentList.setFragmentManager(getFragmentManager());
        assignmentListView.setAdapter(adapterForAssignmentList);
        adapterForAssignmentList.notifyDataSetChanged();

    }

}
