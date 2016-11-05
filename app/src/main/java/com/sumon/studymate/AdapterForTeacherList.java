package com.sumon.studymate;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Md Tajmul Alam Sumon on 10/28/2016.
 */

public class AdapterForTeacherList extends ArrayAdapter<TeacherModel> implements MyAlert.WhichBtnClicked {
    private Context context;
    private ArrayList<TeacherModel> teacherList;
    private LayoutInflater inflter;
    private MyAlert.WhichBtnClicked whichBtnClicked;
    private FragmentManager fragmentManager;
    private int teacherID = -1;

    public AdapterForTeacherList(Context context, ArrayList<TeacherModel> teacherList) {
        super(context, R.layout.custom_row_for_teacher_list);
        this.context = context;
        this.teacherList = teacherList;
        inflter = (LayoutInflater.from(context));
        this.whichBtnClicked = this;

    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
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

        TextView teacherNameTV, teacherDesignationTV, teacherMobileTV, teacherEmailTV, teacherSemesterTV;
        ImageButton teacherEditBtn, teacherDeleteBtn, callTeacherBtn, msgTeacherBtn;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflter.inflate(R.layout.custom_row_for_teacher_list, null);
            holder = new ViewHolder();
            holder.teacherNameTV = (TextView) convertView.findViewById(R.id.teacherNameTV);
            holder.teacherDesignationTV = (TextView) convertView.findViewById(R.id.teacherDesignationTV);
            holder.teacherMobileTV = (TextView) convertView.findViewById(R.id.teacherMobileTV);
            holder.teacherEmailTV = (TextView) convertView.findViewById(R.id.teacherEmailTV);
            holder.teacherSemesterTV = (TextView) convertView.findViewById(R.id.teacherSemesterTV);
            holder.teacherEditBtn = (ImageButton) convertView.findViewById(R.id.teacherEditBtn);
            holder.teacherDeleteBtn = (ImageButton) convertView.findViewById(R.id.teacherDeleteBtn);
            holder.callTeacherBtn = (ImageButton) convertView.findViewById(R.id.callTeacherBtn);
            holder.msgTeacherBtn = (ImageButton) convertView.findViewById(R.id.msgTeacherBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.teacherNameTV.setText(teacherList.get(position).getTeacherName());
        holder.teacherDesignationTV.setText("Position: " + teacherList.get(position).getTeacherDesignation());
        holder.teacherMobileTV.setText("Mob: " + teacherList.get(position).getTeacherMobile());
        holder.teacherEmailTV.setText("Email: " + teacherList.get(position).getTeacherEmail() != "" ? teacherList.get(position).getTeacherEmail() : "Not Available");
        holder.teacherSemesterTV.setText("Semester: " + new SemesterManager(context).getSemesterByID(teacherList.get(position).getSemesterID()).getSemesterTitle());
        holder.teacherEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherID = teacherList.get(position).getTeacherID();
                Intent updateTeacherIntent = new Intent(context, AddTeacherActivity.class);
                updateTeacherIntent.putExtra("teacherID", teacherID);
                context.startActivity(updateTeacherIntent);

            }
        });

        holder.teacherDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlert myAlert = new MyAlert();
                myAlert.setWhichBtnClicked(whichBtnClicked);
                myAlert.setTitle("Confirm Dialog");
                myAlert.setMsg("This will deleted with its related date. Are You Confirmed To delete this");
                myAlert.show(fragmentManager, "teacherListDialog");
            }
        });

        holder.callTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + teacherList.get(position).getTeacherMobile()));
                context.startActivity(intent);
            }
        });

        holder.msgTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri sms_uri = Uri.parse("smsto:" + teacherList.get(position).getTeacherMobile());
                Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
                sms_intent.putExtra("sms_body", "Dear Sir...");
                context.startActivity(sms_intent);
            }
        });
        teacherID = teacherList.get(position).getTeacherID();
        return convertView;
    }

    private boolean isDeleted = false;


    @Override
    public void okBtnClicked(boolean isOk) {
        if (isOk) {

            isDeleted = new TeacherManager(context).deleteTeacherByID(teacherID);
            if (isDeleted) {

                CustomToast.SuccessToast(context, "Teacher Delete Successful");
                teacherList = null;
                teacherList = new TeacherManager(context).getAllTeacher();
                notifyDataSetChanged();
            } else
                CustomToast.FailToast(context, "failed");
        }
    }

    @Override
    public void cancelkBtnClicked(boolean isCancel) {

    }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        teacherList.clear();
        if (charText.length() == 0) {

            teacherList.addAll(new TeacherManager(context).getAllTeacher());
        } else {

            for (TeacherModel aTeacher : new TeacherManager(context).getAllTeacher()) {
                if (aTeacher.getTeacherName().toLowerCase(Locale.getDefault()).contains(charText) || aTeacher.getTeacherDesignation().toLowerCase(Locale.getDefault()).contains(charText) || aTeacher.getTeacherMobile().toLowerCase(Locale.getDefault()).contains(charText) || aTeacher.getTeacherEmail().toLowerCase(Locale.getDefault()).contains(charText) || new SemesterManager(context).getSemesterByID(aTeacher.getSemesterID()).getSemesterTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    teacherList.add(aTeacher);
                }
            }
        }
        notifyDataSetChanged();
    }
}

