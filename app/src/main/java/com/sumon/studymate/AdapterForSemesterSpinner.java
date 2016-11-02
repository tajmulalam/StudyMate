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

public class AdapterForSemesterSpinner extends ArrayAdapter<SemesterModel> {


    private ArrayList<SemesterModel> semesterModelArrayList;
    private Context context;

    public AdapterForSemesterSpinner(Context context, ArrayList<SemesterModel> semesterModelArrayList) {
        super(context, R.layout.spinner_for_semester, semesterModelArrayList);
        this.semesterModelArrayList = semesterModelArrayList;
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
        TextView SemesterNameForSpinnerTV;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_for_semester, null);
            viewHolder = new ViewHolder();
            viewHolder.SemesterNameForSpinnerTV = (TextView) convertView.findViewById(R.id.SemesterNameForSpinnerTV);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.SemesterNameForSpinnerTV.setText(semesterModelArrayList.get(position).getSemesterTitle());
        return convertView;
    }

}
