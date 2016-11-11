package com.sumon.studymate.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sumon.studymate.activity.AddSemesterActivity;
import com.sumon.studymate.util.CustomToast;
import com.sumon.studymate.util.MyAlert;
import com.sumon.studymate.util.MySharedPrefManager;
import com.sumon.studymate.R;
import com.sumon.studymate.activity.SemesterListActivity;
import com.sumon.studymate.manager.SemesterManager;
import com.sumon.studymate.model.SemesterModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Md Tajmul Alam Sumon on 10/28/2016.
 */

public class AdapterForSemesterList extends ArrayAdapter<SemesterModel> implements MyAlert.WhichBtnClicked {
    private Context context;
    private SemesterListActivity semesterListActivity;
    private ArrayList<SemesterModel> semesterList;
    private LayoutInflater inflter;
    private MySharedPrefManager mySharedPrefManager;
    private int semesterListID;
    private FragmentManager fragmentManager;
    private MyAlert.WhichBtnClicked whichBtnClicked;
    private SimpleDateFormat sdf;
    private Date todayDate, semesterEndDate;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public AdapterForSemesterList(Context context, ArrayList<SemesterModel> semesterList) {
        super(context, R.layout.custom_row_for_semester_list);
        this.context = context;
        this.semesterList = semesterList;
        inflter = (LayoutInflater.from(context));
        mySharedPrefManager = new MySharedPrefManager(context);
        this.whichBtnClicked = this;
        sdf = new SimpleDateFormat("dd-MM-yyyy");

    }

    @Override
    public int getCount() {
        return semesterList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder {

        TextView semesterNameTV, semesterStartDateTV, semesterEndDateTV,semesterStatusTV;
        ImageButton semesterEditBtn, semesterDeleteBtn;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflter.inflate(R.layout.custom_row_for_semester_list, null);
            holder = new ViewHolder();
            holder.semesterNameTV = (TextView) convertView.findViewById(R.id.semesterNameTV);
            holder.semesterStartDateTV = (TextView) convertView.findViewById(R.id.semesterStartDateTV);
            holder.semesterEndDateTV = (TextView) convertView.findViewById(R.id.semesterEndDateTV);
            holder.semesterStatusTV = (TextView) convertView.findViewById(R.id.semesterStatusTV);
            holder.semesterEditBtn = (ImageButton) convertView.findViewById(R.id.semesterEditBtn);
            holder.semesterDeleteBtn = (ImageButton) convertView.findViewById(R.id.semesterDeleteBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.semesterNameTV.setText(semesterList.get(position).getSemesterTitle());
        holder.semesterStartDateTV.setText("Start: " + semesterList.get(position).getSemester_start_date());
        holder.semesterEndDateTV.setText("End: " + semesterList.get(position).getSemester_end_date());

        try {
            todayDate = sdf.parse(getDateTime());
            semesterEndDate = sdf.parse(semesterList.get(position).getSemester_end_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (todayDate.equals(semesterEndDate)){
            holder.semesterStatusTV.setText("Status: Will Complete Today");
            holder.semesterStatusTV.setTextColor(context.getResources().getColor(R.color.mdtp_accent_color));

        }
        if (semesterEndDate.before(todayDate)){
            holder.semesterStatusTV.setText("Status: Completed");
            holder.semesterStatusTV.setTextColor(Color.RED);


        }
        if (semesterEndDate.after(todayDate)){
            holder.semesterStatusTV.setText("Status: Running");
            holder.semesterStatusTV.setTextColor(context.getResources().getColor(R.color.colorPrimary));



        }

        holder.semesterEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semesterListID = semesterList.get(position).getSemesterID();
                Intent addSemesterIntent = new Intent(context, AddSemesterActivity.class);
                addSemesterIntent.putExtra("semesterID", semesterListID);
//                mySharedPrefManager.insertIntoPreferenceInt("semesterID", semesterListID);
                context.startActivity(addSemesterIntent);
            }
        });
        holder.semesterDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semesterListID = semesterList.get(position).getSemesterID();
                MyAlert myAlert = new MyAlert();
                myAlert.setWhichBtnClicked(whichBtnClicked);
                myAlert.setTitle("Confirm Dialog");
                myAlert.setMsg("This will deleted with its related data. Are You sure? To delete this");
                myAlert.show(fragmentManager, "SemesterListDialog");
            }
        });
        return convertView;
    }

    private boolean isDeleted = false;

    @Override
    public void okBtnClicked(boolean isOk) {
        if (isOk) {

            isDeleted = new SemesterManager(context).deleteSemesterByID(semesterListID);
            if (isDeleted) {
                CustomToast.SuccessToast(context, "Semester Delete Successful");
                semesterList = null;
                semesterList = new SemesterManager(context).getAllSemester();
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

