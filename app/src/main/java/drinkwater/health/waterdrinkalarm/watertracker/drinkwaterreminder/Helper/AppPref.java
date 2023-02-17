package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity.DatabaseHelper;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity.HomeActivity;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.ReceiverAndService.AlarmUtill;

public class AppPref {
    static final String IS_AWAKE_TIME = "IS_AWAKE_TIME";
    static final String IS_Auto_TIME = "IS_Auto_TIME";
    static final String IS_FIRST_LUNCH = "IS_FIRST_LUNCH";
    static final String IS_KG = "IS_KG";
    static final String IS_MALE = "IS_MALE";
    static final String IS_MONTH = "IS_MONTH";
    static final String IS_Notification = "IS_Notification_Type";
    static final String IS_RECEIVE = "IS_RECEIVE";
    static final String IS_Reminder_TIME = "IS_Reminder_TIME";
    static final String IS_SLEEP_TIME = "IS_SLEEP_TIME";
    static final String IS_TOGGLE = "IS_TOGGLE";
    static final String IS_WEIGHT = "IS_WEIGHT";
    static final String LAST_NOTIFICATION = "LAST_NOTIFICATION_TIME";
    static final String MyPref = "userPref";
    static final String NEXTREMINDER = "NEXTREMINDER";

    public static String getNextreminder(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getString(NEXTREMINDER, "");
    }

    public static long getLastNotificationreminder(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getLong(LAST_NOTIFICATION, 0);
    }

    public static void setLastNotificationreminder(Context context, long j) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putLong(LAST_NOTIFICATION, j);
        edit.commit();
    }

    public static void setNextreminderTime(Context context, Set<String> set) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putStringSet(NEXTREMINDER, set);
        edit.commit();
    }

    public static long getNextreminderTime(Context context) {
        Set<String> stringSet = context.getApplicationContext().getSharedPreferences(MyPref, 0).getStringSet(NEXTREMINDER, null);
        if (stringSet == null) {
            return 0;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            ArrayList arrayList = new ArrayList();
            for (String valueOf : stringSet) {
                arrayList.add(Long.valueOf(valueOf));
            }
            Collections.sort(arrayList);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Long l = (Long) it.next();
                try {
                    if (l.longValue() >= currentTimeMillis) {
                        return l.longValue();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return 0;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static boolean isFirstLaunch(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getBoolean(IS_FIRST_LUNCH, false);
    }

    public static void setFirstLaunch(Context context, boolean z) {
        if (!isFirstLaunch(context)) {
            firstdefaultinsertcup(context);
        }
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putBoolean(IS_FIRST_LUNCH, z);
        edit.commit();
        AlarmUtill.remind24(context);
        AlarmUtill.remind3hour(context);
        AlarmUtill.alarmList(context, false);
        AlarmUtill.setDataBy24Hour(context);
        setLastNotificationreminder(context, System.currentTimeMillis());
    }

    public static void firstdefaultinsertcup(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        int[] iArr = new int[]{100, 200, 300, 400, 500};
        int[] iArr2 = new int[]{0, 0, 1, 0, 0 };
        int[] iArr3 = new int[]{R.drawable.ic_hundred, R.drawable.ic_twohundred, R.drawable.ic_threehun, R.drawable.ic_fourhun, R.drawable.ic_fivehun};
        if (databaseHelper != null) {
            for (int i = 0; i < iArr.length; i++) {
                databaseHelper.insertData(iArr[i], iArr3[i], iArr2[i]);
            }
        }
        HomeActivity.defaultcup = false;
    }

    public static boolean isMale(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getBoolean(IS_MALE, true);
    }

    public static void setMale(Context context, boolean z) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putBoolean(IS_MALE, z);
        edit.commit();
    }

    public static boolean isReceive(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getBoolean(IS_RECEIVE, true);
    }

    public static void setReceive(Context context, boolean z) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putBoolean(IS_RECEIVE, z);
        edit.commit();
    }

    public static int getReminder(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getInt(IS_Reminder_TIME, 3);
    }

    public static void setReminder(Context context, int i) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putInt(IS_Reminder_TIME, i);
        edit.commit();
    }

    public static int getWeight(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getInt(IS_WEIGHT, 65);
    }

    public static void setWeight(Context context, int i) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putInt(IS_WEIGHT, i);
        edit.commit();
    }

    public static String getAwaketime(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getString(IS_AWAKE_TIME, "08:00");
    }

    public static void setAwaketime(Context context, String str) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putString(IS_AWAKE_TIME, str);
        edit.commit();
    }

    public static void setNextTimeList(Context context, String str) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putString(IS_AWAKE_TIME, str);
        edit.commit();
    }

    public static String getSleeptime(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getString(IS_SLEEP_TIME, "22:00");
    }

    public static void setSleeptime(Context context, String str) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putString(IS_SLEEP_TIME, str);
        edit.commit();
    }

    public static int getAuto(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getInt(IS_Auto_TIME, 0);
    }

    public static void setAuto(Context context, int i) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putInt(IS_Auto_TIME, i);
        edit.commit();
    }

    public static boolean isKglb(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getBoolean(IS_KG, true);
    }

    public static void setKglb(Context context, boolean z) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putBoolean(IS_KG, z);
        edit.commit();
    }

    public static boolean ismonth(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getBoolean(IS_MONTH, true);
    }

    public static void setmonth(Context context, boolean z) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putBoolean(IS_MONTH, z);
        edit.commit();
    }

    public static boolean getToggle(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getBoolean(IS_TOGGLE, false);
    }

    public static void setToggle(Context context, boolean z) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putBoolean(IS_TOGGLE, z);
        edit.commit();
    }

    public static boolean getNotificationType(Context context) {
        return context.getApplicationContext().getSharedPreferences(MyPref, 0).getBoolean(IS_Notification, true);
    }

    public static void setNotificationType(Context context, boolean z) {
        Editor edit = context.getApplicationContext().getSharedPreferences(MyPref, 0).edit();
        edit.putBoolean(IS_Notification, z);
        edit.commit();
    }

}
