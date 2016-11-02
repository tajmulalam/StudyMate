package com.sumon.studymate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Md Tajmul Alam Sumon on 10/29/2016.
 */

public class AdapterForCourseSpinner extends ArrayAdapter<CourseModel> {


    private ArrayList<CourseModel> courseModelArrayList;
    private Context context;

    public AdapterForCourseSpinner(Context context, ArrayList<CourseModel> courseModelArrayList) {
        super(context, R.layout.spinner_for_course, courseModelArrayList);
        this.courseModelArrayList = courseModelArrayList;
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
        TextView courseNameForSpinnerTV;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_for_course, null);
            viewHolder = new ViewHolder();
            viewHolder.courseNameForSpinnerTV = (TextView) convertView.findViewById(R.id.courseNameForSpinnerTV);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.courseNameForSpinnerTV.setText(courseModelArrayList.get(position).getCourseTitle());
        return convertView;
    }

}
