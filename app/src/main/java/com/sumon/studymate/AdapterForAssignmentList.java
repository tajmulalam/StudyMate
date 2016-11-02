package com.sumon.studymate;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

public class AdapterForAssignmentList extends ArrayAdapter<AssignmentModel> implements MyAlert.WhichBtnClicked {
    private Context context;
    private ArrayList<AssignmentModel> assignmentArrayList;
    private LayoutInflater inflter;
    private int assignmentID;
    private FragmentManager fragmentManager;
    private MyAlert.WhichBtnClicked whichBtnClicked;
    private SimpleDateFormat sdf;
    private Date todayDate, deadlineDate;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public AdapterForAssignmentList(Context context, ArrayList<AssignmentModel> assignmentArrayList) {
        super(context, R.layout.custom_row_for_assignment_list);
        this.context = context;
        this.assignmentArrayList = assignmentArrayList;
        inflter = (LayoutInflater.from(context));
        this.whichBtnClicked = this;
        sdf = new SimpleDateFormat("dd-MM-yyyy");


    }

    @Override
    public int getCount() {
        return assignmentArrayList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder {

        TextView assignmentNameTV, submitDateTV, statusTV;
        ImageButton assignmentEditBtn, assignmentDeleteBtn;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflter.inflate(R.layout.custom_row_for_assignment_list, null);
            holder = new ViewHolder();
            holder.assignmentNameTV = (TextView) convertView.findViewById(R.id.assignmentNameTV);
            holder.submitDateTV = (TextView) convertView.findViewById(R.id.submitDateTV);
            holder.statusTV = (TextView) convertView.findViewById(R.id.statusTV);
            holder.assignmentEditBtn = (ImageButton) convertView.findViewById(R.id.assignmentEditBtn);
            holder.assignmentDeleteBtn = (ImageButton) convertView.findViewById(R.id.assignmentDeleteBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.assignmentNameTV.setText(assignmentArrayList.get(position).getTopic());
        try {
            deadlineDate = sdf.parse(assignmentArrayList.get(position).getSubmitDate());
            todayDate = sdf.parse(getDateTime());
            Log.d("dateD","deadline "+String.valueOf(assignmentArrayList.get(position).getSubmitDate()));
            Log.d("nowdate","now date "+String.valueOf(getDateTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.submitDateTV.setText("Deadline: " + assignmentArrayList.get(position).getSubmitDate());
        if (assignmentArrayList.get(position).getAssignmentStatus() == 1||assignmentArrayList.get(position).getAssignmentStatus() == 0 &&  deadlineDate.after(todayDate)) {
            holder.statusTV.setText("Status: " + "Pending");
            holder.statusTV.setTextColor(Color.MAGENTA);
        } if (deadlineDate.before(todayDate)){
            holder.statusTV.setText("Status: " + "Submitted");
            holder.statusTV.setTextColor(Color.GREEN);
        }

        holder.assignmentEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentID = assignmentArrayList.get(position).getAssignmentID();
                Intent addAssignmentIntent = new Intent(context, AddAssignmentActivity.class);
                addAssignmentIntent.putExtra("assignmentID", assignmentID);
//                mySharedPrefManager.insertIntoPreferenceInt("semesterID", semesterListID);
                context.startActivity(addAssignmentIntent);
            }
        });
        holder.assignmentDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignmentID = assignmentArrayList.get(position).getAssignmentID();
                MyAlert myAlert = new MyAlert();
                myAlert.setWhichBtnClicked(whichBtnClicked);
                myAlert.setTitle("Confirm Dialog");
                myAlert.setMsg("This will deleted with its related date. Are You Confirmed To delete this");
                myAlert.show(fragmentManager, "AssignmentListDialog");
            }
        });
        return convertView;
    }

    private boolean isDeleted = false;

    @Override
    public void okBtnClicked(boolean isOk) {
        if (isOk) {

            isDeleted = new AssignmentManager(context).deleteAssignmentByID(assignmentID);
            if (isDeleted) {
                CustomToast.SuccessToast(context, "Assignment Delete Successful");
                assignmentArrayList = null;
                assignmentArrayList = new AssignmentManager(context).getAllAssignment();
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

