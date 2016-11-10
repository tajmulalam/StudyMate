package com.sumon.studymate.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sumon.studymate.manager.CourseManager;
import com.sumon.studymate.util.MySharedPrefManager;
import com.sumon.studymate.R;
import com.sumon.studymate.manager.SemesterManager;

public class AssignmentsNotificationActivity extends AppCompatActivity {
    private TextView todayAssignmentDateTV, topicAssignmentTV, semesterNameNotiAssignmentTV, courseNameNotiAssignmentTV;
    private MySharedPrefManager mySharedPrefManager;
    private int assignmentID = -1;
    private int classTestStatus = -1;
    private String topic;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments_notification);
        setTitle("Assignment Notification");


        todayAssignmentDateTV = (TextView) findViewById(R.id.todayAssignmentDateTV);
        topicAssignmentTV = (TextView) findViewById(R.id.topicAssignmentTV);
        semesterNameNotiAssignmentTV = (TextView) findViewById(R.id.semesterNameNotiAssignmentTV);
        courseNameNotiAssignmentTV = (TextView) findViewById(R.id.courseNameNotiAssignmentTV);
        mySharedPrefManager = new MySharedPrefManager(this);


        String today = mySharedPrefManager.getString("assignmentDate");
         topic = mySharedPrefManager.getString("assignmentTopic");
        classTestStatus = mySharedPrefManager.getIntFromPreference("assignmentStatus");
        int semesterID = mySharedPrefManager.getIntFromPreference("assignmentSemesterID");
        int courseID = mySharedPrefManager.getIntFromPreference("assignmentCourseID");
        assignmentID = mySharedPrefManager.getIntFromPreference("assignmentID");

        todayAssignmentDateTV.setText("Today: " + today);
        topicAssignmentTV.setText("Topic: " + topic);
        semesterNameNotiAssignmentTV.setText("Semester Name: " + new SemesterManager(this).getSemesterByID(semesterID).getSemesterTitle());
        courseNameNotiAssignmentTV.setText("Course Name: " + new CourseManager(this).getCourseByID(courseID).getCourseTitle());
    }

    public void goToHome(View view) {
        startActivity(new Intent(AssignmentsNotificationActivity.this, LauncherActivity.class));
        finish();
    }

}
