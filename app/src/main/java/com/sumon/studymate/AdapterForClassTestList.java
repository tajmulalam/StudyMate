package com.sumon.studymate;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Md Tajmul Alam Sumon on 10/28/2016.
 */

public class AdapterForClassTestList extends ArrayAdapter<ClassTestModel> implements MyAlert.WhichBtnClicked {
    private Context context;
    private ArrayList<ClassTestModel> classTestModelArrayList;
    private LayoutInflater inflter;
    private int classTestID;
    private FragmentManager fragmentManager;
    private MyAlert.WhichBtnClicked whichBtnClicked;
    private SimpleDateFormat sdf;
    private Date todayDate, deadlineDate;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public AdapterForClassTestList(Context context, ArrayList<ClassTestModel> classTestModelArrayList) {
        super(context, R.layout.custom_row_for_class_test_list);
        this.context = context;
        this.classTestModelArrayList = classTestModelArrayList;
        inflter = (LayoutInflater.from(context));
        this.whichBtnClicked = this;
        sdf = new SimpleDateFormat("dd-MM-yyyy");


    }

    @Override
    public int getCount() {
        return classTestModelArrayList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder {

        TextView classTestNameTV, classTestDateTV, classTestStatusTV,semesterClassTestTV,courseClassTestTV,teacherClassTestTV;
        ImageButton classTestEditBtn, classTestDeleteBtn;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflter.inflate(R.layout.custom_row_for_class_test_list, null);
            holder = new ViewHolder();
            holder.classTestNameTV = (TextView) convertView.findViewById(R.id.classTestNameTV);
            holder.classTestDateTV = (TextView) convertView.findViewById(R.id.classTestDateTV);
            holder.classTestStatusTV = (TextView) convertView.findViewById(R.id.classTestStatusTV);
            holder.semesterClassTestTV = (TextView) convertView.findViewById(R.id.semesterClassTestTV);
            holder.courseClassTestTV = (TextView) convertView.findViewById(R.id.courseClassTestTV);
            holder.teacherClassTestTV = (TextView) convertView.findViewById(R.id.teacherClassTestTV);
            holder.classTestEditBtn = (ImageButton) convertView.findViewById(R.id.classTestEditBtn);
            holder.classTestDeleteBtn = (ImageButton) convertView.findViewById(R.id.classTestDeleteBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.classTestNameTV.setText(classTestModelArrayList.get(position).getClassTestTopic());
        try {
            deadlineDate = sdf.parse(classTestModelArrayList.get(position).getTestDate());
            todayDate = sdf.parse(getDateTime());
            Log.d("dateD", "deadline " + String.valueOf(classTestModelArrayList.get(position).getTestDate()));
            Log.d("nowdate", "now date " + String.valueOf(getDateTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.classTestDateTV.setText("Deadline: " + classTestModelArrayList.get(position).getTestDate());
        if (classTestModelArrayList.get(position).getClassTestStatus() == 1 || classTestModelArrayList.get(position).getClassTestStatus() == 0) {
            holder.classTestStatusTV.setText("Pending");
            holder.classTestStatusTV.setTextColor(Color.RED);
        }
        if (classTestModelArrayList.get(position).getClassTestStatus() == 3|| deadlineDate.before(todayDate)) {
            holder.classTestStatusTV.setText("Class Test Done");
            holder.classTestStatusTV.setTextColor(context.getResources().getColor(R.color.colorPrimary));


        }
        holder.semesterClassTestTV.setText(new SemesterManager(context).getSemesterByID(classTestModelArrayList.get(position).getSemesterID()).getSemesterTitle());
        holder.courseClassTestTV.setText(new CourseManager(context).getCourseByID(classTestModelArrayList.get(position).getCourseID()).getCourseTitle());
        int teacherID=new CourseManager(context).getCourseByID(classTestModelArrayList.get(position).getCourseID()).getCourseTeacherID();
        holder.teacherClassTestTV.setText(new TeacherManager(context).getTeacherByID(teacherID).getTeacherName());



        holder.classTestEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classTestID = classTestModelArrayList.get(position).getClassTestID();
                Intent addAssignmentIntent = new Intent(context, AddClassTestActivity.class);
                addAssignmentIntent.putExtra("classTestID", classTestID);
//                mySharedPrefManager.insertIntoPreferenceInt("semesterID", semesterListID);
                context.startActivity(addAssignmentIntent);
            }
        });
        holder.classTestDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classTestID = classTestModelArrayList.get(position).getClassTestID();
                MyAlert myAlert = new MyAlert();
                myAlert.setWhichBtnClicked(whichBtnClicked);
                myAlert.setTitle("Confirm Dialog");
                myAlert.setMsg(classTestModelArrayList.get(position).getClassTestTopic()+"\n \n"+"This will deleted with its related date. Are You Confirmed To delete this");
                myAlert.show(fragmentManager, "classTestListDialog");
            }
        });
        return convertView;
    }

    private boolean isDeleted = false;

    @Override
    public void okBtnClicked(boolean isOk) {
        if (isOk) {
            isDeleted = new ClassTestManager(context).deleteClassTestByID(classTestID);
            if (isDeleted) {
                CustomToast.SuccessToast(context, "Class Test Delete Successful");
                classTestModelArrayList = null;
                classTestModelArrayList = new ClassTestManager(context).getAllClassTest();
                notifyDataSetChanged();
            } else
                CustomToast.FailToast(context, "failed");
        }
    }

    @Override
    public void cancelkBtnClicked(boolean isCancel) {

    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        classTestModelArrayList.clear();
        if (charText.length() == 0) {

            classTestModelArrayList.addAll(new ClassTestManager(context).getAllClassTest());
        }
        else
        {

            for (ClassTestModel aClassTest : new ClassTestManager(context).getAllClassTest())
            {
                if (aClassTest.getClassTestTopic().toLowerCase(Locale.getDefault()).contains(charText)||aClassTest.getTestDate().toLowerCase(Locale.getDefault()).contains(charText)||new SemesterManager(context).getSemesterByID(aClassTest.getSemesterID()).getSemesterTitle().toLowerCase(Locale.getDefault()).contains(charText)||new CourseManager(context).getCourseByID(aClassTest.getCourseID()).getCourseTitle().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    classTestModelArrayList.add(aClassTest);
                }
            }
        }
        notifyDataSetChanged();
    }

}

