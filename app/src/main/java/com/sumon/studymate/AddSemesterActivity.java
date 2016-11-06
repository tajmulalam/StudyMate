package com.sumon.studymate;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddSemesterActivity extends AppCompatActivity {

    private ImageButton datePicImageBtn;
    private ImageButton datePicImageBtn2;
    private EditText startDateET, endDateET;
    private int isStartDate = 1, isEndDate = 2;
    private Button btnAddSemester, btnUpdateSemester;
    private SemesterModel aSemester;
    private EditText semesterTitleET;
    private String storeSatrtDate, storeEndDate;
    private SemesterManager semesterManager;
    private Bundle args;
    private Date fromDate, toDate;
    private SimpleDateFormat sdf;
    private int semesterID = -1;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semester);

        init();
    }

    //  initialization view components
    private void init() {

        datePicImageBtn = (ImageButton) findViewById(R.id.datePicImageBtn);
        datePicImageBtn2 = (ImageButton) findViewById(R.id.datePicImageBtn2);
        startDateET = (EditText) findViewById(R.id.startDateET);
        endDateET = (EditText) findViewById(R.id.endDateET);
        btnAddSemester = (Button) findViewById(R.id.btnAddSemester);
        btnUpdateSemester = (Button) findViewById(R.id.btnUpdateSemester);
        semesterTitleET = (EditText) findViewById(R.id.semesterTitleET);
        semesterManager = new SemesterManager(this);
        semesterID = getIntent().getIntExtra("semesterID", -1);
        if (semesterID != -1) {
            setTitle("Update Semester");

            btnAddSemester.setVisibility(View.GONE);
            btnUpdateSemester.setVisibility(View.VISIBLE);
            aSemester = semesterManager.getSemesterByID(semesterID);
            setAllDataToField();
            btnUpdateSemester.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateSemesterInfo();
                }
            });

        } else {
            setTitle("Add Semester");
            btnUpdateSemester.setVisibility(View.GONE);
            btnAddSemester.setVisibility(View.VISIBLE);
            btnAddSemester.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (startDateET.getText().length() > 0 && startDateET.getText().toString() != "" && endDateET.getText().length() > 0 && endDateET.getText().toString() != "") {
                        btnAddSemester.setEnabled(true);

                        insertData();
                    } else {
                        btnAddSemester.setEnabled(false);
                        CustomToast.FailToast(AddSemesterActivity.this, "Date can not be empty");
                    }
                }


            });
        }


        datePicImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = 1000;
                pickDate();

            }
        });
        datePicImageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEndDate = 2000;
                pickDate();
            }
        });

        sdf = new SimpleDateFormat("dd-MM-yyyy");
    }


    // used to initialization prev data to edit text for update method
    private void setAllDataToField() {
        semesterTitleET.setText(aSemester.getSemesterTitle());
        startDateET.setText(aSemester.getSemester_start_date());
        endDateET.setText(aSemester.getSemester_end_date());

    }

    // update data method
    private void updateSemesterInfo() {
        String semesterTitle = semesterTitleET.getText().toString();


        try {
            if (isStartDate == 1 && isEndDate == 2) {
                fromDate = sdf.parse(startDateET.getText().toString());
                toDate = sdf.parse(endDateET.getText().toString());
            } else {
                fromDate = sdf.parse(storeSatrtDate);
                toDate = sdf.parse(storeEndDate);
            }

        } catch (
                ParseException e
                )

        {
            e.printStackTrace();
        }

        if (fromDate.before(toDate) && toDate.after(fromDate))

        {
            storeSatrtDate = startDateET.getText().toString();
            storeEndDate = endDateET.getText().toString();
            if (semesterTitle.length() > 2 && semesterTitle.length() > 0 && storeEndDate.length() > 0 && storeSatrtDate.length() > 0) {

                aSemester = new SemesterModel(semesterTitle, storeSatrtDate, storeEndDate);
                boolean isInserted = semesterManager.editSemester(semesterID, aSemester);
                if (isInserted)
                    CustomToast.SuccessToast(this, "Update successful");
                btnUpdateSemester.setEnabled(false);
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(AddSemesterActivity.this, SemesterListActivity.class));
                    }
                }, 600);
            } else {
                CustomToast.FailToast(this, "Please Make some changes to update information");
            }
        } else

        {
            CustomToast.FailToast(this, "1. Start Date must be smaller then End Date \n2. End Date must be greater then Start Date");
        }

    }


    /// insert data method
    private void insertData() {
        String semesterTitle = semesterTitleET.getText().toString();
        try {
            fromDate = sdf.parse(storeSatrtDate);
            toDate = sdf.parse(storeEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (fromDate.before(toDate) && toDate.after(fromDate)) {
            if (semesterTitle.length() > 2) {

                aSemester = new SemesterModel(semesterTitle, storeSatrtDate, storeEndDate);
                boolean isInserted = semesterManager.addSemester(aSemester);
                if (isInserted)
                    CustomToast.SuccessToast(this, "created successfully");
                semesterTitleET.getText().clear();
                startDateET.setText("");
                endDateET.setText("");
                btnAddSemester.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(AddSemesterActivity.this, SemesterListActivity.class));
                        finish();
                    }
                }, 400);
            } else {
                CustomToast.FailToast(this, "Input validation error");
            }
        } else {
            CustomToast.FailToast(this, "1. Start Date must be smaller then End Date \n2. End Date must be greater then Start Date");
        }
    }


    // display current date
    private void pickDate() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(), "Date Picker");
    }


    // date set setListener to changes the date selected state
    android.app.DatePickerDialog.OnDateSetListener ondate = new android.app.DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {


            if (isStartDate == 1000) {
                try {
                    fromDate = sdf.parse(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    startDateET.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (startDateET.getText().length() > 0) {
                    storeSatrtDate = startDateET.getText().toString();
                    btnAddSemester.setEnabled(true);
                    isStartDate = 1;
                }
            }
            if (isEndDate == 2000) {
                try {
                    toDate = sdf.parse(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    endDateET.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (endDateET.getText().length() > 0) {
                    storeEndDate = endDateET.getText().toString();
                    isEndDate = 2;
                }
            }
        }
    };

    // reset button method
    public void resetData(View view) {
        semesterTitleET.getText().clear();

    }


}

