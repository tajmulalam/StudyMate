package com.sumon.studymate;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Md Tajmul Alam Sumon on 10/28/2016.
 */

public class AdapterFoRoutineList extends ArrayAdapter<RoutineModel> {
    private Context context;
    private ArrayList<RoutineModel> routineList;
    private LayoutInflater inflter;
    private int routineID;
    private int teacherID;


    public AdapterFoRoutineList(Context context, ArrayList<RoutineModel> routineList) {
        super(context, R.layout.custom_row_for_routine_list);
        this.context = context;
        this.routineList = routineList;
        inflter = (LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
        return routineList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder {

        TextView dayNameTV, timeTV, periodTV, teacherTV;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflter.inflate(R.layout.custom_row_for_routine_list, null);
            holder = new ViewHolder();
            holder.dayNameTV = (TextView) convertView.findViewById(R.id.dayNameTV);
            holder.timeTV = (TextView) convertView.findViewById(R.id.timeTV);
            holder.periodTV = (TextView) convertView.findViewById(R.id.periodTV);
            holder.teacherTV = (TextView) convertView.findViewById(R.id.teacherTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.dayNameTV.setText(routineList.get(position).getDay());
        holder.timeTV.setText(routineList.get(position).getStartTime() +"\n"+ "to"+"\n" + routineList.get(position).getEndTime());
        holder.periodTV.setText(routineList.get(position).getPeriod());
        teacherID = routineList.get(position).getTeacherID();
        holder.teacherTV.setText(new CourseManager(context).getCourseByID(routineList.get(position).getCourseID()).getCourseTitle()+"\n"+"Credit: "+new CourseManager(context).getCourseByID(routineList.get(position).getCourseID()).getCourseCredit()+"\n"+new TeacherManager(context).getTeacherByID(teacherID).getTeacherName());

        return convertView;
    }


}

