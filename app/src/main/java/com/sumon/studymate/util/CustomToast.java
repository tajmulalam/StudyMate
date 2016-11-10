package com.sumon.studymate.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.View;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

/**
 * Created by Md Tajmul Alam Sumon on 10/30/2016.
 */

public class CustomToast {

    public static void FailToast(Context context, String msg) {
        SuperActivityToast.create(context, new Style(), Style.TYPE_STANDARD)
                .setProgressBarColor(Color.WHITE)
                .setText(msg)
                .setDuration(Style.DURATION_LONG)
                .setFrame(Style.FRAME_STANDARD)
                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED))
                .setAnimations(Style.ANIMATIONS_SCALE).show();
    }

    public static void SuccessToast(Context context, String msg) {
        SuperActivityToast.create(context, new Style(), Style.TYPE_BUTTON)
                .setButtonText("OK")
                .setButtonIconResource(android.R.drawable.ic_input_add)
                .setOnButtonClickListener("good_tag_name", null, new SuperActivityToast.OnButtonClickListener() {
                    @Override
                    public void onClick(View view, Parcelable token) {

                    }
                })
                .setProgressBarColor(Color.WHITE)
                .setText(msg)
                .setDuration(Style.DURATION_LONG)
                .setFrame(Style.FRAME_LOLLIPOP)
                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN))
                .setAnimations(Style.ANIMATIONS_POP).show();
    }
}
