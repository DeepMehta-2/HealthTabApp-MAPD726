package com.centennial.healthtab.utils;

import android.content.Context;
import android.preference.PreferenceManager;

public class Pref_Utils {
    public static final String PREF_DEFAULT_BOTTLE_LIST = "Pref_default_bottle_list";
    public static final String PREF_DEFAULT_CHART_DATA = "Pref_default_chart_data";
    public static final String PREF_DEFAULT_GLASS_LIST = "Pref_default_glass_list";
    public static final String PREF_NOTIFICATION_ARRAYLIST = "Pref_default_notification_list";

    public static String getDefaultBottleListInfo(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_DEFAULT_BOTTLE_LIST, "");
    }

    public static void setDefaultBottileInfo(Context context, String str) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_DEFAULT_BOTTLE_LIST, str).apply();
    }

    public static String getDefaultGlassListInfo(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_DEFAULT_GLASS_LIST, "");
    }

    public static void setDefaultGlassInfo(Context context, String str) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_DEFAULT_GLASS_LIST, str).apply();
    }

    public static String getDefaultNotificationListInfo(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_NOTIFICATION_ARRAYLIST, "");
    }

    public static void setDefaultNotificationListInfo(Context context, String str) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_NOTIFICATION_ARRAYLIST, str).apply();
    }

    public static String getDefaultChartListInfo(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_DEFAULT_CHART_DATA, "");
    }

    public static void setDefaultChartListInfo(Context context, String str) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_DEFAULT_CHART_DATA, str).apply();
    }
}
