package com.sumon.studymate;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Md Tajmul Alam Sumon on 10/28/2016.
 */

public class AdapterForCourseList extends ArrayAdapter<CourseModel> implements MyAlert.WhichBtnClicked, Filterable {
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


    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
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
                myAlert.setMsg("This will deleted with its related date. Are You Confirmed To delete this");
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
}

