package com.sumon.studymate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class RoutineWizerdActivity extends AppCompatActivity {

    private ArrayList<SemesterModel> allSemester;
    private SemesterManager semesterManager;
    private Spinner semesterListForRoutineSpinner;
    private AdapterForSemesterSpinner adapterForSemesterSpinner;
    private Button routineNextBtn;
    private int semesterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_routine_wizerd);
        setTitle("Semester Routine List");
        semesterListForRoutineSpinner = (Spinner) findViewById(R.id.semesterListForRoutineSpinner);
        routineNextBtn = (Button) findViewById(R.id.routineNextBtn);
        semesterManager = new SemesterManager(this);
        allSemester = new ArrayList<>();
        allSemester = semesterManager.getAllSemester();
        adapterForSemesterSpinner = new AdapterForSemesterSpinner(this, allSemester);
        semesterListForRoutineSpinner.setAdapter(adapterForSemesterSpinner);
        routineNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddRoutineDetails();
            }
        });

        semesterListForRoutineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semesterID = allSemester.get(position).getSemesterID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void goToAddRoutineDetails() {
        Intent addRoutineIntent = new Intent(RoutineWizerdActivity.this, RoutineInfoListActivity.class);
        addRoutineIntent.putExtra("semesterID", semesterID);
        startActivity(addRoutineIntent);
        finish();
    }
}
