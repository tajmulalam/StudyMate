package com.sumon.studymate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Md Tajmul Alam Sumon on 10/28/2016.
 */

public class AdapterForCourseList extends ArrayAdapter<CourseModel> {
    private Context context;
    private ArrayList<CourseModel> courseList;
    private LayoutInflater inflter;
    private TeacherManager teacherManager;
    private TeacherModel aTeacher;

    public AdapterForCourseList(Context context, ArrayList<CourseModel>courseList) {
        super(context, R.layout.custom_row_for_course_list);
        this.context = context;
        this.courseList=courseList;
        inflter = (LayoutInflater.from(context));
        teacherManager=new TeacherManager(context);

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

        TextView courseTitleTV,courseCodeTV,courseCreditTV,courseTeacherTV;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflter.inflate(R.layout.custom_row_for_course_list, null);
            holder = new ViewHolder();
            holder.courseTitleTV = (TextView) convertView.findViewById(R.id.courseTitleTV);
            holder.courseCodeTV = (TextView) convertView.findViewById(R.id.courseCodeTV);
            holder.courseCreditTV = (TextView) convertView.findViewById(R.id.courseCreditTV);
            holder.courseTeacherTV = (TextView) convertView.findViewById(R.id.courseTeacherTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.courseTitleTV.setText(courseList.get(position).getCourseTitle());
        holder.courseCodeTV.setText("Course Code: "+courseList.get(position).getCourseCode());
        holder.courseCreditTV.setText("Credit: "+courseList.get(position).getCourseCredit());
        aTeacher=teacherManager.getTeacherByID(courseList.get(position).getCourseTeacherID());
        holder.courseTeacherTV.setText("Teacher: "+aTeacher.getTeacherName()!=""?aTeacher.getTeacherName():"Not Available");

        return convertView;
    }
}

