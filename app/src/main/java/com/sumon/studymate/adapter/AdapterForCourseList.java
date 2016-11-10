package com.sumon.studymate.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sumon.studymate.activity.AddCoursesActivity;
import com.sumon.studymate.manager.CourseManager;
import com.sumon.studymate.model.CourseModel;
import com.sumon.studymate.util.CustomToast;
import com.sumon.studymate.util.MyAlert;
import com.sumon.studymate.R;
import com.sumon.studymate.manager.SemesterManager;
import com.sumon.studymate.model.SemesterModel;
import com.sumon.studymate.manager.TeacherManager;
import com.sumon.studymate.model.TeacherModel;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Md Tajmul Alam Sumon on 10/28/2016.
 */

public class AdapterForCourseList extends ArrayAdapter<CourseModel> implements MyAlert.WhichBtnClicked {
    private Context context;
    private ArrayList<CourseModel> courseList;
    private LayoutInflater inflter;
    private TeacherManager teacherManager;
    private TeacherModel aTeacher;
    private int courseID;
    private MyAlert.WhichBtnClicked whichBtnClicked;
    private FragmentManager fragmentManager;
    private SemesterModel semester;


    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public AdapterForCourseList(Context context, ArrayList<CourseModel> courseList) {
        super(context, R.layout.custom_row_for_course_list);
        this.context = context;
        this.courseList = courseList;
        inflter = (LayoutInflater.from(context));
        teacherManager = new TeacherManager(context);
        this.whichBtnClicked = this;

    }

    @Override
    public int getCount() {
        return courseList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }



    static class ViewHolder {

        TextView courseTitleTV, courseCodeTV, courseCreditTV, courseTeacherTV, courseSemesterTV;
        ImageButton courseEditBtn, courseDeleteBtn;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflter.inflate(R.layout.custom_row_for_course_list, null);
            holder = new ViewHolder();
            holder.courseTitleTV = (TextView) convertView.findViewById(R.id.courseTitleTV);
            holder.courseCodeTV = (TextView) convertView.findViewById(R.id.courseCodeTV);
            holder.courseCreditTV = (TextView) convertView.findViewById(R.id.courseCreditTV);
            holder.courseTeacherTV = (TextView) convertView.findViewById(R.id.courseTeacherTV);
            holder.courseSemesterTV = (TextView) convertView.findViewById(R.id.courseSemesterTV);
            holder.courseEditBtn = (ImageButton) convertView.findViewById(R.id.courseEditBtn);
            holder.courseDeleteBtn = (ImageButton) convertView.findViewById(R.id.courseDeleteBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.courseTitleTV.setText(courseList.get(position).getCourseTitle());
        holder.courseCodeTV.setText("Course Code: " + courseList.get(position).getCourseCode());
        holder.courseCreditTV.setText("Credit: " + courseList.get(position).getCourseCredit());


        aTeacher = teacherManager.getTeacherByID(courseList.get(position).getCourseTeacherID());
        holder.courseTeacherTV.setText("Teacher: " + aTeacher.getTeacherName());

        semester = new SemesterManager(context).getSemesterByID(courseList.get(position).getCourseSemesterID());
        holder.courseSemesterTV.setText(semester.getSemesterTitle() == null ? "" : "Semester: " + semester.getSemesterTitle());

        holder.courseEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseID = courseList.get(position).getCourseID();
                Intent addCourseIntent = new Intent(context, AddCoursesActivity.class);
                addCourseIntent.putExtra("courseID", courseID);
//                mySharedPrefManager.insertIntoPreferenceInt("semesterID", semesterListID);
                context.startActivity(addCourseIntent);
            }
        });
        holder.courseDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseID = courseList.get(position).getCourseID();
                MyAlert myAlert = new MyAlert();
                myAlert.setWhichBtnClicked(whichBtnClicked);
                myAlert.setTitle("Confirm Dialog");
                myAlert.setMsg("This will deleted with its related data. Are You sure? To delete this");
                myAlert.show(fragmentManager, "CourseListDialog");
            }
        });
        return convertView;
    }

    private boolean isDeleted = false;

    @Override
    public void okBtnClicked(boolean isOk) {
        if (isOk) {

            isDeleted = new CourseManager(context).deleteCourseByID(courseID);
            if (isDeleted) {
                CustomToast.SuccessToast(context, "Course Delete Successful");
                courseList = null;
                courseList = new CourseManager(context).getAllCourses();
                notifyDataSetChanged();
            } else
                CustomToast.FailToast(context, "failed");
        }
    }

    @Override
    public void cancelkBtnClicked(boolean isCancel) {

    }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        courseList.clear();
        if (charText.length() == 0) {

            courseList.addAll(new CourseManager(context).getAllCourses());
        }
        else
        {

            for (CourseModel course : new CourseManager(context).getAllCourses())
            {
                if (course.getCourseTitle().toLowerCase(Locale.getDefault()).contains(charText)||course.getCourseCode().toLowerCase(Locale.getDefault()).contains(charText)||course.getCourseCredit().toLowerCase(Locale.getDefault()).contains(charText)||new SemesterManager(context).getSemesterByID(course.getCourseSemesterID()).getSemesterTitle().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    courseList.add(course);
                }
            }
        }
        notifyDataSetChanged();
    }
}

