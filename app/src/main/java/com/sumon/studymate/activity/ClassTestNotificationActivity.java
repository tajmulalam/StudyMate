package com.sumon.studymate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sumon.studymate.manager.CourseManager;
import com.sumon.studymate.util.MySharedPrefManager;
import com.sumon.studymate.R;
import com.sumon.studymate.manager.SemesterManager;

public class ClassTestNotificationActivity extends AppCompatActivity {
    private TextView todayDateTV, topicTV, semesterNameNotiTV, courseNameNotiTV;
    private MySharedPrefManager mySharedPrefManager;
    private int classTestID=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test_notification);
        setTitle("Class Test Notification");
        todayDateTV = (TextView) findViewById(R.id.todayDateTV);
        topicTV = (TextView) findViewById(R.id.topicTV);
        semesterNameNotiTV = (TextView) findViewById(R.id.semesterNameNotiTV);
        courseNameNotiTV = (TextView) findViewById(R.id.courseNameNotiTV);
        mySharedPrefManager = new MySharedPrefManager(this);


        String today =  mySharedPrefManager.getString("testDate");
        String topic =mySharedPrefManager.getString("testTopic");
        int classTestStatus = mySharedPrefManager.getIntFromPreference("classTestStatus");
        int semesterID = mySharedPrefManager.getIntFromPreference("semesterID");
        int courseID = mySharedPrefManager.getIntFromPreference("courseID");
         classTestID = mySharedPrefManager.getIntFromPreference("classTestID");

        todayDateTV.setText("Today: " + today);
        topicTV.setText("Topic: " + topic);
        semesterNameNotiTV.setText("Semester Name: " + new SemesterManager(this).getSemesterByID(semesterID).getSemesterTitle());
        courseNameNotiTV.setText("Course Name: " + new CourseManager(this).getCourseByID(courseID).getCourseTitle());

    }


    public void goToHomeFromTest(View view) {
        startActivity(new Intent(ClassTestNotificationActivity.this, LauncherActivity.class));
        finish();
    }


}
