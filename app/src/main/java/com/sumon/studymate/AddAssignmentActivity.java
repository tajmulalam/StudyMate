package com.sumon.studymate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddAssignmentActivity extends AppCompatActivity {
    private AdapterForSemesterSpinner adapterForSemesterList;
    private ArrayList<SemesterModel> semesterModelArrayList;
    private SemesterManager semesterManager;


    private ArrayList<CourseModel> courseModelArrayList;
    private CourseManager courseManager;
    private AdapterForCourseSpinner forCourseSpinnerAdapter;


    private Spinner semesterListSpinnerAssignment, courseListSpinnerAssignment;


    private EditText submitDateET, assignmentTopicET;
    private CheckBox remindCheckBox;
    private ImageButton datePicAssignmentIBtn;
    private Button btnAddAssignment, btnUpdateAssignment;
    private AssignmentManager assignmentManager;


    private int assignmentID;
    private AssignmentModel aAssignment;
    private int isStartSelect = 1;
    private SimpleDateFormat sdf;
    private Date selectedDate, nowDate;
    private Bundle args;
    private String storeSubmitDate;
    private Handler handler;
    // for inserting data
    private int semesterID = -1;
    private int courseID = -1;
    private int status_Active = -1;
    private MySharedPrefManager mySharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        init();
    }

    private void init() {
        mySharedPrefManager = new MySharedPrefManager(this);
        datePicAssignmentIBtn = (ImageButton) findViewById(R.id.datePicAssignmentIBtn);
        submitDateET = (EditText) findViewById(R.id.submitDateET);
        assignmentTopicET = (EditText) findViewById(R.id.assignmentTopicET);
        btnAddAssignment = (Button) findViewById(R.id.btnAddAssignment);
        btnUpdateAssignment = (Button) findViewById(R.id.btnUpdateAssignment);
        remindCheckBox = (CheckBox) findViewById(R.id.remindCheckBox);
        remindCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    status_Active = 1;
//                    Toast.makeText(AddAssignmentActivity.this, String.valueOf(status_Active), Toast.LENGTH_SHORT).show();
                } else {
                    status_Active = 0;
//                    Toast.makeText(AddAssignmentActivity.this, String.valueOf(status_Active), Toast.LENGTH_SHORT).show();

                }
            }
        });
        assignmentManager = new AssignmentManager(this);
        assignmentID = getIntent().getIntExtra("assignmentID", -1);
        if (assignmentID != -1) {
            setTitle("Update Assignment");
            btnAddAssignment.setVisibility(View.GONE);
            btnUpdateAssignment.setVisibility(View.VISIBLE);

            setAllDataToField();
            btnUpdateAssignment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAssignmentInfo();
                }
            });

        } else {
            setTitle("Add Assignment");
            btnUpdateAssignment.setVisibility(View.GONE);
            btnAddAssignment.setVisibility(View.VISIBLE);
            btnAddAssignment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (submitDateET.getText().length() > 0 && submitDateET.getText().toString() != "") {
                        btnAddAssignment.setEnabled(true);

                        insertData();
                    } else {
                        btnAddAssignment.setEnabled(false);
                        CustomToast.FailToast(AddAssignmentActivity.this, "Date can not be empty");
                    }
                }
            });
        }
        datePicAssignmentIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartSelect = 1000;
                pickDate();

            }
        });
        sdf = new SimpleDateFormat("dd-MM-yyyy");
        semesterListSpinnerAssignment = (Spinner) findViewById(R.id.semesterListSpinnerAssignment);
        semesterListSpinnerAssignment.setPrompt("Select Semester");

        courseListSpinnerAssignment = (Spinner) findViewById(R.id.courseListSpinnerAssignment);

        semesterManager = new SemesterManager(this);
        courseManager = new CourseManager(this);

        semesterModelArrayList = new ArrayList<>();
        courseModelArrayList = new ArrayList<>();

        semesterModelArrayList = semesterManager.getAllSemester();
        courseModelArrayList = courseManager.getAllCourses();


        forCourseSpinnerAdapter = new AdapterForCourseSpinner(this, courseModelArrayList);
        courseListSpinnerAssignment.setAdapter(forCourseSpinnerAdapter);
        if (assignmentID != -1)
            courseListSpinnerAssignment.setSelection(getCourseIndexByID(aAssignment.getCourseID()));

        adapterForSemesterList = new AdapterForSemesterSpinner(this, semesterModelArrayList);
        semesterListSpinnerAssignment.setAdapter(adapterForSemesterList);
        if (assignmentID != -1)
            semesterListSpinnerAssignment.setSelection(getSemesterIndexByID(aAssignment.getSemesterID()));
        semesterListSpinnerAssignment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semesterID = semesterModelArrayList.get(position).getSemesterID();
                courseModelArrayList = courseManager.getAllCoursesBySemesterID(semesterID);
                forCourseSpinnerAdapter = new AdapterForCourseSpinner(AddAssignmentActivity.this, courseModelArrayList);
                courseListSpinnerAssignment.setAdapter(forCourseSpinnerAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        courseListSpinnerAssignment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseID = courseModelArrayList.get(position).getCourseID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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


            if (isStartSelect == 1000) {
                try {
                    selectedDate = sdf.parse(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    submitDateET.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (submitDateET.getText().length() > 0) {
                    storeSubmitDate = submitDateET.getText().toString();
                    btnAddAssignment.setEnabled(true);

                }
                isStartSelect = 1;
            }

        }
    };

    private void insertData() {
        String assignmentTitle = assignmentTopicET.getText().toString();

        try {
            selectedDate = sdf.parse(storeSubmitDate);
            nowDate = sdf.parse(getDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (selectedDate.equals(nowDate) || selectedDate.after(nowDate) && !selectedDate.before(nowDate)) {
            if (assignmentTitle.length() > 2) {

                aAssignment = new AssignmentModel(semesterID, courseID, storeSubmitDate, assignmentTitle, status_Active);
                boolean isInserted = assignmentManager.addNewAssignment(aAssignment);
                if (isInserted)
                    CustomToast.SuccessToast(this, "created successfully");
                submitDateET.getText().clear();
                assignmentTopicET.setText("");
                btnAddAssignment.setEnabled(false);

                Calendar cal = Calendar.getInstance();
                if (status_Active == 1) {
                    cal.setTime(selectedDate);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 30);
                    boolean isAlarmSet = setAlarm(cal, aAssignment);
                    if (isAlarmSet)
                        CustomToast.SuccessToast(this, "We Will Notified you for this Assignment");
                    else
                        CustomToast.FailToast(this, "Failed to set alarm");
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(AddAssignmentActivity.this, AssignmentListActivity.class));
                        finish();
                    }
                }, 5000);
            } else {
                CustomToast.FailToast(this, "Please Fill Properly");
            }
        } else {
            CustomToast.FailToast(this, "1. Submit Date must be greater or equal to current date");
        }

    }

    private void setAllDataToField() {
        aAssignment = assignmentManager.getAssignmentByID(assignmentID);
        Log.d("from setall method", " getSemesterID  " + String.valueOf(aAssignment.getSemesterID()));
        Log.d("from setall method", " getCourseID  " + String.valueOf(aAssignment.getCourseID()));
        submitDateET.setText(aAssignment.getSubmitDate());
        assignmentTopicET.setText(aAssignment.getTopic());

        if (aAssignment.getAssignmentStatus() == 1) {
            remindCheckBox.setChecked(true);
        } else {
            remindCheckBox.setChecked(false);
        }

    }


    private void updateAssignmentInfo() {
        String assignmentTitle = assignmentTopicET.getText().toString();

        try {
            if (isStartSelect == 1) {
                selectedDate = sdf.parse(submitDateET.getText().toString());
                nowDate = sdf.parse(getDateTime());
                storeSubmitDate = submitDateET.getText().toString();
            } else {
                selectedDate = sdf.parse(storeSubmitDate);
                nowDate = sdf.parse(getDateTime());
            }

        } catch (
                ParseException e
                )

        {
            e.printStackTrace();
        }

        if (selectedDate.equals(nowDate) || selectedDate.after(nowDate) && !selectedDate.before(nowDate))

        {
            if (assignmentTitle.length() > 2 && storeSubmitDate.length() > 0) {

                aAssignment = new AssignmentModel(semesterID, courseID, storeSubmitDate, assignmentTitle, status_Active);
                boolean isInserted = assignmentManager.editAssignment(assignmentID, aAssignment);
                if (isInserted) {
                    CustomToast.SuccessToast(this, "Update successful");
                    btnUpdateAssignment.setEnabled(false);
                    Calendar cal = Calendar.getInstance();
                    if (status_Active == 1) {
                        cal.setTime(selectedDate);
                        cal.set(Calendar.HOUR_OF_DAY, 9);
                        cal.set(Calendar.MINUTE, 30);
                        boolean isAlarmSet = setAlarm(cal, aAssignment);
                        if (isAlarmSet)
                            CustomToast.SuccessToast(this, "We Will Notified you for this Assignment");
                        else
                            CustomToast.FailToast(this, "Failed to set alarm");
                    }
                }
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(AddAssignmentActivity.this, AssignmentListActivity.class));
                        finish();
                    }
                }, 5000);
            } else {
                CustomToast.FailToast(this, "Please Make some changes to update information");
            }
        } else

        {
            CustomToast.FailToast(this, "1. Start Date must be smaller then End Date \n2. End Date must be greater then Start Date");
        }
    }

    // reset button method
    public void resetData(View view) {
        assignmentTopicET.getText().clear();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    boolean isSet = false;

    private boolean setAlarm(Calendar targetCal, AssignmentModel aAssignment) {
        mySharedPrefManager.putString("assignmentDate", aAssignment.getSubmitDate());
        mySharedPrefManager.putString("assignmentTopic", aAssignment.getTopic());
        mySharedPrefManager.insertIntoPreferenceInt("assignmentStatus", aAssignment.getAssignmentStatus());
        mySharedPrefManager.insertIntoPreferenceInt("assignmentSemesterID", aAssignment.getSemesterID());
        mySharedPrefManager.insertIntoPreferenceInt("assignmentCourseID", aAssignment.getCourseID());
        mySharedPrefManager.insertIntoPreferenceInt("assignmentID", aAssignment.getAssignmentID());


        Intent intent = new Intent(getBaseContext(), AlarmReceiverForAssignments.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
        isSet = true;
        if (isSet)
            return true;
        else
            return false;
    }


    public int getSemesterIndexByID(int semesterID) {
        for (SemesterModel _item : semesterModelArrayList) {
            if (_item.getSemesterID() == semesterID)
                return semesterModelArrayList.indexOf(_item);
        }
        return -1;
    }

    public int getCourseIndexByID(int courseID) {
        for (CourseModel _item : courseModelArrayList) {
            if (_item.getCourseID() == courseID)
                return courseModelArrayList.indexOf(_item);
        }
        return -1;
    }
}
