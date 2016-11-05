package com.sumon.studymate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddTeacherActivity extends AppCompatActivity {
    private TeacherManager teacherManager;
    private TeacherModel aTeacher;
    private EditText teacherNameET, teacherDesignationET, teacherMobileET, teacherEmailET;
    private Button btnAddTeacher, btnUpdateTeacher;
    private Button resetBtn;
    private AdapterForSemesterSpinner adapterForSemesterSpinner;
    private ArrayList<SemesterModel> semesterList;
    private SemesterManager semesterManager;
    private Spinner semesterListSpinnerForTeacher;
    private int semesterID;
    private int teacherID = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        teacherID = getIntent().getIntExtra("teacherID", 0);
        init();

    }

    private void init() {
        teacherNameET = (EditText) findViewById(R.id.teacherNameET);
        teacherDesignationET = (EditText) findViewById(R.id.teacherDesignationET);
        teacherMobileET = (EditText) findViewById(R.id.teacherMobileET);
        teacherEmailET = (EditText) findViewById(R.id.teacherEmailET);
        teacherManager = new TeacherManager(this);
        btnAddTeacher = (Button) findViewById(R.id.btnAddTeacher);
        btnUpdateTeacher = (Button) findViewById(R.id.btnUpdateTeacher);
        resetBtn = (Button) findViewById(R.id.resetBtn);
        semesterListSpinnerForTeacher = (Spinner) findViewById(R.id.semesterListSpinnerForTeacher);

        if (teacherID != -1 && teacherID != 0) {
            setTitle("Update Teacher");
            btnUpdateTeacher.setVisibility(View.VISIBLE);
            btnAddTeacher.setVisibility(View.GONE);
            teacherManager = new TeacherManager(this);
            aTeacher = teacherManager.getTeacherByID(teacherID);

            semesterManager = new SemesterManager(this);
            semesterList = semesterManager.getAllSemester();
            adapterForSemesterSpinner = new AdapterForSemesterSpinner(this, semesterList);
            semesterListSpinnerForTeacher.setAdapter(adapterForSemesterSpinner);
            semesterListSpinnerForTeacher.setSelection(getSemesterIndexByID(aTeacher.getSemesterID()));
            fillData();
        } else {
            setTitle("Add New Teacher");
            btnUpdateTeacher.setVisibility(View.GONE);
            btnAddTeacher.setVisibility(View.VISIBLE);
            semesterManager = new SemesterManager(this);
            semesterList = semesterManager.getAllSemester();
            adapterForSemesterSpinner = new AdapterForSemesterSpinner(this, semesterList);
            semesterListSpinnerForTeacher.setAdapter(adapterForSemesterSpinner);
        }


        semesterListSpinnerForTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semesterID = semesterList.get(position).getSemesterID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }


        });


        btnUpdateTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFieldData();
            }
        });
    }

    private void updateDate() {

        String name = teacherNameET.getText().toString();
        String designation = teacherDesignationET.getText().toString();
        String mobile = teacherMobileET.getText().toString();
        String email = teacherEmailET.getText().toString();
        if (name.length() > 0 && designation.length() > 0 && mobile.length() > 0 && email.length() > 0) {
            aTeacher = new TeacherModel(name, designation, mobile, email, semesterID);
            boolean isUpdated = teacherManager.editTeacher(teacherID, aTeacher);
            if (isUpdated) {
                CustomToast.SuccessToast(this, "Teacher Information successfully Updated");
                startActivity(new Intent(AddTeacherActivity.this, TeacherListActivity.class));
            } else
                CustomToast.FailToast(this, "Please Input Valid Information");

        }


    }

    private void fillData() {
        teacherNameET.setText(aTeacher.getTeacherName());
        teacherDesignationET.setText(aTeacher.getTeacherDesignation());
        teacherMobileET.setText(aTeacher.getTeacherMobile());
        teacherEmailET.setText(aTeacher.getTeacherEmail());
    }


    private void insertData() {

        String name = teacherNameET.getText().toString();
        String designation = teacherDesignationET.getText().toString();
        String mobile = teacherMobileET.getText().toString();
        String email = teacherEmailET.getText().toString();
        if (name.length() > 0 && designation.length() > 0 && mobile.length() > 0 && email.length() > 0) {
            aTeacher = new TeacherModel(name, designation, mobile, email, semesterID);
            boolean isInserted = teacherManager.addNewTeacher(aTeacher);
            if (isInserted) {
                CustomToast.SuccessToast(this, "Teacher Information successfully added");
                startActivity(new Intent(AddTeacherActivity.this, TeacherListActivity.class));
            } else
                CustomToast.FailToast(this, "Please Input Valid Information");

        }


    }

    private void resetFieldData() {
        teacherNameET.getText().clear();
        teacherDesignationET.getText().clear();
        teacherMobileET.getText().clear();
        teacherEmailET.getText().clear();
    }

    public int getSemesterIndexByID(int semesterID) {
        for (SemesterModel _item : semesterList) {
            if (_item.getSemesterID() == semesterID)
                return semesterList.indexOf(_item);
        }
        return -1;
    }

}
