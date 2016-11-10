package com.sumon.studymate.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Md Tajmul Alam Sumon on 11/1/2016.
 */

public  class MyAlert extends DialogFragment {
    private String title;
    private String msg;
    private boolean isOk = false;
    private boolean isCancel = false;
    private WhichBtnClicked whichBtnClicked;

    public WhichBtnClicked getWhichBtnClicked() {
        return whichBtnClicked;
    }

    public void setWhichBtnClicked(WhichBtnClicked whichBtnClicked) {
        this.whichBtnClicked = whichBtnClicked;
    }

    public MyAlert() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isOk = true;
                whichBtnClicked.okBtnClicked(isOk);
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isCancel=true;
                whichBtnClicked.cancelkBtnClicked(isCancel);
            }
        });

        Dialog dialog = builder.create();
        return dialog;
    }

    public interface WhichBtnClicked {
        public void okBtnClicked(boolean isOk);

        public void cancelkBtnClicked(boolean isCancel);
    }
}
