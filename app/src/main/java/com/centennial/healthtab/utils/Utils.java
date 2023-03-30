package com.centennial.healthtab.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    private static final boolean isInBG = false;
    public static String notification = "";
    private static PackageManager pm;
    private static String strWakeupTime;

    public static boolean checkAppRunningInBG(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        boolean z = true;
        for (RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
            if (runningAppProcessInfo.importance == 100) {
                boolean z2 = z;
                for (String equals : runningAppProcessInfo.pkgList) {
                    if (equals.equals(context.getPackageName())) {
                        z2 = false;
                    }
                }
                z = z2;
            }
        }
        return z;
    }

    public static void saveToUserDefaults(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static String getFromUserDefaults(Context context, String str) {
        return context.getSharedPreferences(Constant.SHARED_PREFS, 0).getString(str, "");
    }

    public static void saveGlassToDefaults(Context context, String str, int i) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public static int getGlassFromDefaults(Context context, String str) {
        return context.getSharedPreferences(Constant.SHARED_PREFS, 0).getInt(str, 0);
    }

    public static void saveUnitToDefaults(Context context, String str, float f) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.putFloat(str, f);
        edit.apply();
    }

    public static Float getUnitFromDefaults(Context context, String str) {
        return Float.valueOf(context.getSharedPreferences(Constant.SHARED_PREFS, 0).getFloat(str, 100.0f));
    }

    public static void saveSleepTimeDefaults(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static String getSleepTimeDefaults(Context context, String str) {
        return context.getSharedPreferences(Constant.SHARED_PREFS, 0).getString(str, "23:00");
    }

    public static void saveWakeupTimeDefaults(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static String getWakeupTimeDefaults(Context context, String str) {
        String stringBuilder;
        String stringBuilder2;
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFS, 0);
        Calendar instance = Calendar.getInstance();
        int i = instance.get(5);
        int i2 = instance.get(2) + 1;
        int i3 = instance.get(1);
        int i4 = instance.get(11);
        instance.get(12);
        instance.get(13);
        if (i < 10) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("0");
            stringBuilder3.append(i);
            stringBuilder = stringBuilder3.toString();
            Log.e("print", "getWakeupTimeDefaults: " + stringBuilder);
        } else {
            stringBuilder = String.valueOf(i);
            Log.e("print", "getWakeupTimeDefaults2: " + stringBuilder);
        }
        if (i2 < 10) {
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("0");
            stringBuilder4.append(i2);
            stringBuilder2 = stringBuilder4.toString();
            Log.e("print", "getWakeupTimeDefaults3: " + stringBuilder2);
        } else {
            stringBuilder2 = String.valueOf(i2);
            Log.e("print", "getWakeupTimeDefaults31: " + stringBuilder2);
        }
        if (i4 > 24) {
            strWakeupTime = sharedPreferences.getString(str, "08:00");
            Log.e("print", "getWakeupTimeDefaults4: " + strWakeupTime);
        } else {
            pm = context.getPackageManager();
            try {
                long j = pm.getPackageInfo(context.getPackageName(), 0).firstInstallTime;
                String format = new SimpleDateFormat("dd/MM/yyyy").format(new Date(j));
                StringBuilder stringBuilder5 = new StringBuilder();
                stringBuilder5.append(stringBuilder);
                stringBuilder5.append("/");
                stringBuilder5.append(stringBuilder2);
                stringBuilder5.append("/");
                stringBuilder5.append(i3);
                Log.e("print", "getWakeupTimeDefaults12: " + stringBuilder5);
                if (format.equals(stringBuilder5.toString())) {
                    String format2 = new SimpleDateFormat("HH:mm").format(new Date(j));
                    strWakeupTime = sharedPreferences.getString(str, "08:00");
                    Log.e("print", "getWakeupTimeDefaults41: " + strWakeupTime);
                } else {
                    saveWakeupTimeDefaults(context, str, "08:00");
                    strWakeupTime = sharedPreferences.getString(str, "08:00");
                    Log.e("print", "getWakeupTimeDefaults42: " + strWakeupTime);
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return strWakeupTime;
    }

    public static void saveIntervalDefaults(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static String getIntervalDefaults(Context context, String str) {
        return context.getSharedPreferences(Constant.SHARED_PREFS, 0).getString(str, "01:30");
    }

    public static void saveIntDefaults(Context context, String str, int i) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public static void clearIntDefaults(Context context, String str) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.remove(str);
        edit.apply();
    }

    public static int getIntDefaults(Context context, String str) {
        return context.getSharedPreferences(Constant.SHARED_PREFS, 0).getInt(str, 0);
    }

    public static void saveWaterDrunkDefaults(Context context, String str, float f) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.putFloat(str, f);
        edit.apply();
    }

    public static float getWaterDrunkFromDefaults(Context context, String str) {
        return context.getSharedPreferences(Constant.SHARED_PREFS, 0).getFloat(str, 0.0f);
    }

    public static void saveTimerDefaults(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static String getTimerDefaults(Context context, String str) {
        return context.getSharedPreferences(Constant.SHARED_PREFS, 0).getString(str, "24 hour");
    }

    public static void saveNotificationModeDefaults(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static String getNotificationModeDefaults(Context context, String str) {
        return context.getSharedPreferences(Constant.SHARED_PREFS, 0).getString(str, "vibrate");
    }

    public static void saveToWaterUnitsDefaults(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static String getFromWaterUnitsDefaults(Context context, String str) {
        return context.getSharedPreferences(Constant.SHARED_PREFS, 0).getString(str, "kg,ml");
    }

    public static void saveNotificationPositionDefaults(Context context, String str, float f) {
        Editor edit = context.getSharedPreferences(Constant.SHARED_PREFS, 0).edit();
        edit.putFloat(str, f);
        edit.apply();
    }

    public static float getNotificationPositionDefaults(Context context, String str) {
        return context.getSharedPreferences(Constant.SHARED_PREFS, 0).getFloat(str, 1.0f);
    }
}
