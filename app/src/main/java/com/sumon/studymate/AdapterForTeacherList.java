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

public class AdapterForTeacherList extends ArrayAdapter<TeacherModel> {
    private Context context;
    private ArrayList<TeacherModel> teacherList;
    private LayoutInflater inflter;

    public AdapterForTeacherList(Context context, ArrayList<TeacherModel>teacherList) {
        super(context, R.layout.custom_row_for_teacher_list);
        this.context = context;
        this.teacherList=teacherList;
        inflter = (LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
        return teacherList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {

        TextView teacherNameTV,teacherDesignationTV,teacherMobileTV,teacherEmailTV;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflter.inflate(R.layout.custom_row_for_teacher_list, null);
            holder = new ViewHolder();
            holder.teacherNameTV = (TextView) convertView.findViewById(R.id.teacherNameTV);
            holder.teacherDesignationTV = (TextView) convertView.findViewById(R.id.teacherDesignationTV);
            holder.teacherMobileTV = (TextView) convertView.findViewById(R.id.teacherMobileTV);
            holder.teacherEmailTV = (TextView) convertView.findViewById(R.id.teacherEmailTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.teacherNameTV.setText(teacherList.get(position).getTeacherName());
        holder.teacherDesignationTV.setText("Position: "+teacherList.get(position).getTeacherDesignation());
        holder.teacherMobileTV.setText("Mob: "+teacherList.get(position).getTeacherMobile());
        holder.teacherEmailTV.setText("Email: "+teacherList.get(position).getTeacherEmail()!=""?teacherList.get(position).getTeacherEmail():"Not Available");

        return convertView;
    }
}

