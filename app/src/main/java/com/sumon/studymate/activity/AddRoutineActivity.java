package com.sumon.studymate.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.sumon.studymate.manager.CourseManager;
import com.sumon.studymate.model.CourseModel;
import com.sumon.studymate.util.CustomToast;
import com.sumon.studymate.R;
import com.sumon.studymate.manager.RoutineManager;
import com.sumon.studymate.model.RoutineModel;
import com.sumon.studymate.adapter.AdapterForCourseSpinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddRoutineActivity extends AppCompatActivity {

    private Spinner courseListSpinnerRoutine;
    private EditText dayET, statTimeET, endTimeET, periodET;
    private ImageButton starTimePicRoutineIBtn, endTImePicRoutineIBtn;
    private Button btnAddRoutine;
    private int semesterID = -1;
    private ArrayList<CourseModel> allCourse;
    private CourseManager courseManager;
    private AdapterForCourseSpinner courseSpinnerAdapter;
    private int courseID = -1;
    private int teacherID = -1;
    private RoutineManager routineManager;
    private RoutineModel aRoutine;
    private DateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);
        semesterID = getIntent().getIntExtra("semesterID", 0);
        setTitle("Add New Routine");
        initViewComponents();
        starTimePicRoutineIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(true);
            }
        });
        endTImePicRoutineIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(false);
            }
        });
        btnAddRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insetRoutine();
            }
        });
        courseListSpinnerRoutine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseID = allCourse.get(position).getCourseID();
                teacherID = allCourse.get(position).getCourseTeacherID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initViewComponents() {
        courseListSpinnerRoutine = (Spinner) findViewById(R.id.courseListSpinnerRoutine);
        dayET = (EditText) findViewById(R.id.dayET);
        statTimeET = (EditText) findViewById(R.id.statTimeET);
        endTimeET = (EditText) findViewById(R.id.endTimeET);
        periodET = (EditText) findViewById(R.id.periodET);
        starTimePicRoutineIBtn = (ImageButton) findViewById(R.id.starTimePicRoutineIBtn);
        endTImePicRoutineIBtn = (ImageButton) findViewById(R.id.endTImePicRoutineIBtn);
        btnAddRoutine = (Button) findViewById(R.id.btnAddRoutine);
        courseManager = new CourseManager(this);
        allCourse = new ArrayList<>();
        allCourse = courseManager.getAllCoursesBySemesterID(semesterID);
        if (allCourse.size()<1){
            CustomToast.FailToast(this,"No Course Available ! Create one First to create Routine");
            btnAddRoutine.setEnabled(false);

        }else{
            btnAddRoutine.setEnabled(true);
        }
        courseSpinnerAdapter = new AdapterForCourseSpinner(this, allCourse);
        courseListSpinnerRoutine.setAdapter(courseSpinnerAdapter);
        routineManager = new RoutineManager(this);

    }

    private Date startDate;
    private Date endDate;

    private void insetRoutine() {

        String day = dayET.getText().toString();
        String startTime = statTimeET.getText().toString();
        String endTime = endTimeET.getText().toString();
        String period = periodET.getText().toString();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm"); // 12 hour format
        if (startTime.length() > 0 && endTime.length() > 0) {
            try {
                if (startTime.length() > 0 && endTime.length() > 0) {
                    startDate = format.parse(startTime);
                    endDate = format.parse(endTime);
                } else
                    CustomToast.FailToast(this, "Time Can't be Empty");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (startDate.before(endDate)) {
                if (day.length() > 0 && day!="" && startTime.length() > 0 && startTime!=""&& endTime.length() > 0 && endTime!="" && period.length() > 0 && period!=""&& semesterID != 0 || semesterID != -1 && courseID != -1 || courseID != 0 && teacherID != -1) {

                    aRoutine = new RoutineModel(day, startTime, endTime, period, semesterID, courseID, teacherID);

                    boolean isInserted = routineManager.addNewRoutine(aRoutine);
                    if (isInserted) {
                        CustomToast.SuccessToast(this, "Routine inserted");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent routinListIntent = new Intent(AddRoutineActivity.this, RoutineInfoListActivity.class);
                                routinListIntent.putExtra("semesterID", semesterID);
                                startActivity(routinListIntent);
                                finish();
                            }
                        }, 700);
                    } else
                        CustomToast.FailToast(this, "Internal Error");
                } else
                    CustomToast.FailToast(this, "Please Fill Properly");
            } else
                CustomToast.FailToast(this, "Start Time must be before End Time");
        } else
            CustomToast.FailToast(this, "Time Can't be Empty");


    }

    public void resetDataRoutine(View view) {

        dayET.getText().clear();
        statTimeET.getText().clear();
        endTimeET.getText().clear();
        periodET.getText().clear();

    }


    private void setTime(final boolean whoseTime) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (whoseTime)
                    statTimeET.setText(getFomatedTime(hourOfDay, minute));
                else
                    endTimeET.setText(getFomatedTime(hourOfDay, minute));

            }

        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private String getFomatedTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        return aTime;
    }

}
