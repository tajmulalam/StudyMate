package com.sumon.studymate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.sumon.studymate.R;
import com.sumon.studymate.manager.SemesterManager;
import com.sumon.studymate.model.SemesterModel;
import com.sumon.studymate.adapter.AdapterForSemesterSpinner;
import com.sumon.studymate.util.CustomToast;

import java.util.ArrayList;

public class RoutineWizerdActivity extends AppCompatActivity {

    private ArrayList<SemesterModel> allSemester;
    private SemesterManager semesterManager;
    private Spinner semesterListForRoutineSpinner;
    private AdapterForSemesterSpinner adapterForSemesterSpinner;
    private Button routineNextBtn;
    private int semesterID = -1;

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
        if (semesterID != -1 && semesterID != 0) {
            routineNextBtn.setEnabled(true);
            Intent addRoutineIntent = new Intent(RoutineWizerdActivity.this, RoutineInfoListActivity.class);
            addRoutineIntent.putExtra("semesterID", semesterID);
            startActivity(addRoutineIntent);
            finish();
        }else {
            CustomToast.FailToast(this,"No Semester Available. Create one first.");
            routineNextBtn.setEnabled(false);
        }
    }
}
