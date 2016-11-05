package com.sumon.studymate;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class AssignmentListActivity extends AppCompatActivity {
    private ListView assignmentListView;
    private AssignmentManager assignmentManager;
    private ArrayList<AssignmentModel> assignmentModelArrayList;
    private AdapterForAssignmentList adapterForAssignmentList;
    private EditText inputSearchAssignmetnET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_list);
        setTitle("Assignment List");

        assignmentListView = (ListView) findViewById(R.id.assignmentListView);
        inputSearchAssignmetnET = (EditText) findViewById(R.id.inputSearchAssignmetnET);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNewAssignment);

        assignmentManager = new AssignmentManager(this);
        fillAdapter();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddAssignment();
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
