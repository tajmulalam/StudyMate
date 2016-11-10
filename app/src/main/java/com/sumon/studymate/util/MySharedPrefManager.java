package com.sumon.studymate.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Md Tajmul Alam Sumon on 10/29/2016.
 */

public class MySharedPrefManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private String preference = "StudyMateInfos";

    public MySharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void insertIntoPreferenceInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getIntFromPreference(String key) {
        return sharedPreferences.getInt(key, 0);

    }

    public void putString(String key, String value) {
        editor.putString(key, value);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "bla");
    }
}
