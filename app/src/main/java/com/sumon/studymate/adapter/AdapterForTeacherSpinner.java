package com.sumon.studymate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sumon.studymate.R;
import com.sumon.studymate.model.TeacherModel;

import java.util.ArrayList;

/**
 * Created by Md Tajmul Alam Sumon on 10/29/2016.
 */

public class AdapterForTeacherSpinner extends ArrayAdapter<TeacherModel> {


    private ArrayList<TeacherModel> teacherModelArrayList;
    private Context context;

    public AdapterForTeacherSpinner(Context context, ArrayList<TeacherModel> teacherModelArrayList) {
        super(context, R.layout.spinner_for_teacher, teacherModelArrayList);
        this.teacherModelArrayList = teacherModelArrayList;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    static class ViewHolder {
        TextView TeacherNameForSpinnerTV,teacherDesignationForSpinnerTV;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_for_teacher, null);
            viewHolder = new ViewHolder();
            viewHolder.TeacherNameForSpinnerTV = (TextView) convertView.findViewById(R.id.TeacherNameForSpinnerTV);
            viewHolder.teacherDesignationForSpinnerTV = (TextView) convertView.findViewById(R.id.teacherDesignationForSpinnerTV);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.TeacherNameForSpinnerTV.setText(teacherModelArrayList.get(position).getTeacherName());
        viewHolder.teacherDesignationForSpinnerTV.setText(teacherModelArrayList.get(position).getTeacherDesignation());
        return convertView;
    }

}
