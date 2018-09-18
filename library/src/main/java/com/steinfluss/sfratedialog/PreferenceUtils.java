package com.steinfluss.sfratedialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {
    private static final String RENDER_COUNTS_PREF = "renderCountsPref";


    public static int getRenderCount(Context context){
        return getSharedPref(context).getInt(RENDER_COUNTS_PREF,0);
    }

    public static void setRenderCount(Context context , int count){
        getEditor(context).putInt(RENDER_COUNTS_PREF,count).apply();
    }

    private static SharedPreferences.Editor getEditor(Context context){
        return getSharedPref(context).edit();
    }

    private static SharedPreferences getSharedPref(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
