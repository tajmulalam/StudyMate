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

        TextView classTestNameTV, classTestDateTV, classTestStatusTV;
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
            Log.d("dateD","deadline "+String.valueOf(classTestModelArrayList.get(position).getTestDate()));
            Log.d("nowdate","now date "+String.valueOf(getDateTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.classTestDateTV.setText("Deadline: " + classTestModelArrayList.get(position).getTestDate());
        if (classTestModelArrayList.get(position).getClassTestStatus() == 1||classTestModelArrayList.get(position).getClassTestStatus() == 0 &&  deadlineDate.after(todayDate)) {
            holder.classTestStatusTV.setText("Status: " + "Pending");
            holder.classTestStatusTV.setTextColor(Color.MAGENTA);
        } if (deadlineDate.before(todayDate)){
            holder.classTestStatusTV.setText("Status: " + "Submitted");

        }

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
                myAlert.setMsg("This will deleted with its related date. Are You Confirmed To delete this");
                myAlert.show(fragmentManager, "classTesttListDialog");
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
                CustomToast.SuccessToast(context, "Assignment Delete Successful");
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
}

