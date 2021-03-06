package com.sumon.studymate.activity;

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

import com.sumon.studymate.receiver.AlarmReceiverForClassTest;
import com.sumon.studymate.manager.ClassTestManager;
import com.sumon.studymate.model.ClassTestModel;
import com.sumon.studymate.manager.CourseManager;
import com.sumon.studymate.model.CourseModel;
import com.sumon.studymate.util.CustomToast;
import com.sumon.studymate.util.DatePickerFragment;
import com.sumon.studymate.util.MySharedPrefManager;
import com.sumon.studymate.R;
import com.sumon.studymate.manager.SemesterManager;
import com.sumon.studymate.model.SemesterModel;
import com.sumon.studymate.adapter.AdapterForCourseSpinner;
import com.sumon.studymate.adapter.AdapterForSemesterSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddClassTestActivity extends AppCompatActivity {
    private AdapterForSemesterSpinner adapterForSemesterList;
    private ArrayList<SemesterModel> semesterModelArrayList;
    private SemesterManager semesterManager;


    private ArrayList<CourseModel> courseModelArrayList;
    private CourseManager courseManager;
    private AdapterForCourseSpinner forCourseSpinnerAdapter;


    private Spinner semesterListSpinnerClassTest, courseListSpinnerClassTest;


    private EditText classTestDateET, classTestTopicET;
    private CheckBox classTestremindCheckBox;
    private ImageButton datePicClassTestIBtn;
    private Button btnAddClassTest, btnUpdateClassTest;
    private ClassTestManager classTestManager;

    private MySharedPrefManager mySharedPrefManager;


    private int classTestID;
    private ClassTestModel aClassTest;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_test);
        init();
    }

    private void init() {
        mySharedPrefManager = new MySharedPrefManager(this);
        datePicClassTestIBtn = (ImageButton) findViewById(R.id.datePicClassTestIBtn);
        classTestDateET = (EditText) findViewById(R.id.classTestDateET);
        classTestTopicET = (EditText) findViewById(R.id.classTestTopicET);
        btnAddClassTest = (Button) findViewById(R.id.btnAddClassTest);
        btnUpdateClassTest = (Button) findViewById(R.id.btnUpdateClassTest);
        classTestremindCheckBox = (CheckBox) findViewById(R.id.classTestremindCheckBox);
        classTestremindCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    status_Active = 1;
//                    Toast.makeText(AddClassTestActivity.this, String.valueOf(status_Active), Toast.LENGTH_SHORT).show();
                } else {
                    status_Active = 0;
//                    Toast.makeText(AddClassTestActivity.this, String.valueOf(status_Active), Toast.LENGTH_SHORT).show();

                }
            }
        });
        classTestManager = new ClassTestManager(this);
        classTestID = getIntent().getIntExtra("classTestID", -1);
        if (classTestID != -1 && classTestID != 0) {
            setTitle("Update Class Test");
            btnAddClassTest.setVisibility(View.GONE);
            btnUpdateClassTest.setVisibility(View.VISIBLE);

            setAllDataToField();
            btnUpdateClassTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateClassTestInfo();
                }
            });

        } else {
            setTitle("Add Class Test");
            btnUpdateClassTest.setVisibility(View.GONE);
            btnAddClassTest.setVisibility(View.VISIBLE);
            btnAddClassTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (classTestDateET.getText().length() > 0 && classTestDateET.getText().toString() != "") {
                        btnAddClassTest.setEnabled(true);

                        insertData();
                    } else {
                        btnAddClassTest.setEnabled(false);
                        CustomToast.FailToast(AddClassTestActivity.this, "Date can not be empty");
                    }
                }
            });
        }
        datePicClassTestIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartSelect = 1000;
                pickDate();

            }
        });
        sdf = new SimpleDateFormat("dd-MM-yyyy");
        semesterListSpinnerClassTest = (Spinner) findViewById(R.id.semesterListSpinnerClassTest);
        semesterListSpinnerClassTest.setPrompt("Select Semester");

        courseListSpinnerClassTest = (Spinner) findViewById(R.id.courseListSpinnerClassTest);

        semesterManager = new SemesterManager(this);
        courseManager = new CourseManager(this);

        semesterModelArrayList = new ArrayList<>();
        courseModelArrayList = new ArrayList<>();

        semesterModelArrayList = semesterManager.getAllSemester();
        courseModelArrayList = courseManager.getAllCourses();


        forCourseSpinnerAdapter = new AdapterForCourseSpinner(this, courseModelArrayList);
        courseListSpinnerClassTest.setAdapter(forCourseSpinnerAdapter);
        if (classTestID != -1)
            courseListSpinnerClassTest.setSelection(getCourseIndexByID(aClassTest.getCourseID()));

        adapterForSemesterList = new AdapterForSemesterSpinner(this, semesterModelArrayList);
        semesterListSpinnerClassTest.setAdapter(adapterForSemesterList);
        if (classTestID != -1)
            semesterListSpinnerClassTest.setSelection(getSemesterIndexByID(aClassTest.getSemesterID()));

        semesterListSpinnerClassTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semesterID = semesterModelArrayList.get(position).getSemesterID();
                courseModelArrayList = courseManager.getAllCoursesBySemesterID(semesterID);
                if (courseModelArrayList.size() > 0) {
                    btnAddClassTest.setEnabled(true);
                    if (classTestID != -1 && classTestID != 0) {
                        btnUpdateClassTest.setEnabled(true);
                    }
                    forCourseSpinnerAdapter = new AdapterForCourseSpinner(AddClassTestActivity.this, courseModelArrayList);
                    courseListSpinnerClassTest.setAdapter(forCourseSpinnerAdapter);
                } else {
                    btnAddClassTest.setEnabled(false);
                    if (classTestID != -1 && classTestID != 0) {
                        btnUpdateClassTest.setEnabled(false);
                    }
                    CustomToast.FailToast(AddClassTestActivity.this, "No Course Available on this semester");
                    forCourseSpinnerAdapter = new AdapterForCourseSpinner(AddClassTestActivity.this, courseModelArrayList);
                    courseListSpinnerClassTest.setAdapter(forCourseSpinnerAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        courseListSpinnerClassTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    classTestDateET.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (classTestDateET.getText().length() > 0 && courseModelArrayList.size()>0) {
                    storeSubmitDate = classTestDateET.getText().toString();
                    btnAddClassTest.setEnabled(true);
                } else
                    storeSubmitDate = "";
                isStartSelect = 1;

            }

        }
    };

    private void insertData() {
        String classTestTitle = classTestTopicET.getText().toString();

        try {
            if (storeSubmitDate.length() > 0)
                selectedDate = sdf.parse(storeSubmitDate);
            else

                CustomToast.FailToast(this, "Date Can Not Be Empty!");
            nowDate = sdf.parse(getDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (selectedDate.equals(nowDate) || selectedDate.after(nowDate) && !selectedDate.before(nowDate)) {
            if (classTestTitle.length() > 2) {

                aClassTest = new ClassTestModel(storeSubmitDate, classTestTitle, status_Active, semesterID, courseID);
                boolean isInserted = classTestManager.addNewClassTest(aClassTest);
                if (isInserted)
                    CustomToast.SuccessToast(this, "Created successfully");
                classTestDateET.getText().clear();
                classTestTopicET.setText("");
                btnAddClassTest.setEnabled(false);
                Calendar cal = Calendar.getInstance();
                if (status_Active == 1 && isInserted) {
                    cal.setTime(selectedDate);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 30);
                    boolean isAlarmSet = setAlarm(cal, aClassTest);
                    if (isAlarmSet)
                        CustomToast.SuccessToast(this, "We Will Notified you for this  Class Test");
                    else
                        CustomToast.FailToast(this, "Failed to set alarm");
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(AddClassTestActivity.this, ClassTestListActivity.class));
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
        aClassTest = classTestManager.getClassTestByID(classTestID);
        Log.d("from setall method", " getSemesterID  " + String.valueOf(aClassTest.getSemesterID()));
        Log.d("from setall method", " getCourseID  " + String.valueOf(aClassTest.getCourseID()));
        classTestDateET.setText(aClassTest.getTestDate());
        classTestTopicET.setText(aClassTest.getClassTestTopic());

        if (aClassTest.getClassTestStatus() == 1) {
            classTestremindCheckBox.setChecked(true);
        } else {
            classTestremindCheckBox.setChecked(false);
        }

    }


    private void updateClassTestInfo() {
        String classTestTitle = classTestTopicET.getText().toString();

        try {
            if (isStartSelect == 1) {
                if (classTestDateET.getText().toString().length() > 0 && classTestDateET.getText().toString() != "") {
                    selectedDate = sdf.parse(classTestDateET.getText().toString());
                    nowDate = sdf.parse(getDateTime());
                    storeSubmitDate = classTestDateET.getText().toString();
                }
            } else {
                if (classTestDateET.getText().toString().length() > 0 && classTestDateET.getText().toString() != "") {
                    selectedDate = sdf.parse(storeSubmitDate);
                    nowDate = sdf.parse(getDateTime());
                } else
                    CustomToast.FailToast(this, "Date Can't be Empty");
            }

        } catch (
                ParseException e
                )

        {
            e.printStackTrace();
        }

        if (selectedDate.equals(nowDate) || selectedDate.after(nowDate) && !selectedDate.before(nowDate))

        {
            if (classTestTitle.length() > 2 && storeSubmitDate.length() > 0) {

                aClassTest = new ClassTestModel(storeSubmitDate, classTestTitle, status_Active, semesterID, courseID);
                boolean isInserted = classTestManager.editClassTest(classTestID, aClassTest);
                if (isInserted) {
                    CustomToast.SuccessToast(this, "Update successful");
                    btnUpdateClassTest.setEnabled(false);
                    Calendar cal = Calendar.getInstance();
                    if (status_Active == 1) {
                        cal.setTime(selectedDate);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 30);
                        boolean isAlarmSet = setAlarm(cal, aClassTest);
                        if (isAlarmSet)
                            CustomToast.SuccessToast(this, "We Will Notified you for this  Class Test");
                        else
                            CustomToast.FailToast(this, "Failed to set alarm");
                    }
                }
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(AddClassTestActivity.this, ClassTestListActivity.class));
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
        classTestDateET.getText().clear();
        classTestTopicET.getText().clear();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    boolean isSet = false;

    private boolean setAlarm(Calendar targetCal, ClassTestModel aClassTest) {


        Intent intent = new Intent(getBaseContext(), AlarmReceiverForClassTest.class);

        mySharedPrefManager.putString("testDate", aClassTest.getTestDate());
        mySharedPrefManager.putString("testTopic", aClassTest.getClassTestTopic());
        mySharedPrefManager.insertIntoPreferenceInt("classTestStatus", aClassTest.getClassTestStatus());
        mySharedPrefManager.insertIntoPreferenceInt("semesterID", aClassTest.getSemesterID());
        mySharedPrefManager.insertIntoPreferenceInt("courseID", aClassTest.getCourseID());
        mySharedPrefManager.insertIntoPreferenceInt("classTestID", aClassTest.getClassTestID());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
        isSet = true;
        if (isSet)
            return true;
        else
            return false;
    }

    public void resetDataClassTest(View view) {

        classTestTopicET.getText().clear();

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
