package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.ReceiverAndService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity.DatabaseHelper;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity.recieve;

public class AlarmUtill {
    public static String LOGATAG = "LOGTAGAGG";
    public static int SLEEP_TIME_PENDING_INTENT_REQUEST_CODE = 307;
    public static String sleepTimeReceiverCode = "SleepTimeReceiverCode";

    public static int getReminderIntervalTime(Context context) {
        switch (AppPref.getReminder(context)) {
            case 1:
                return 30;
            case 2:
                return 45;
            case 3:
                return 60;
            case 4:
                return 90;
            default:
                return 0;
        }
    }

    public static void callReminderOnHome(Context context) {
        if (System.currentTimeMillis() > getWakeupTime(context) && System.currentTimeMillis() - AppPref.getLastNotificationreminder(context) > 3600000) {
            AppPref.setLastNotificationreminder(context, System.currentTimeMillis());
            alarmList(context, false);
            remind24(context);
            remind3hour(context);
        }
    }

    public static long getWakeupTime(Context context) {
        String[] split = AppPref.getAwaketime(context).split(":");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        Calendar instance = Calendar.getInstance();
        instance.set(11, parseInt);
        instance.set(12, parseInt2);
        instance.set(13, 0);
        return instance.getTimeInMillis();
    }

    public static long getSleepTime(Context context) {
        String[] split = AppPref.getSleeptime(context).split(":");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        Calendar instance = Calendar.getInstance();
        instance.set(11, parseInt);
        instance.set(12, parseInt2);
        instance.set(13, 0);
        return instance.getTimeInMillis();
    }

    public static void alarmList(Context context, boolean z) {
        String[] split = AppPref.getAwaketime(context).split(":");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        Calendar instance = Calendar.getInstance();
        instance.set(11, parseInt);
        instance.set(12, parseInt2);
        instance.set(13, 0);
        String[] split2 = AppPref.getSleeptime(context).split(":");
        int parseInt3 = Integer.parseInt(split2[0]);
        int parseInt4 = Integer.parseInt(split2[1]);
        Calendar instance2 = Calendar.getInstance();
        instance2.set(11, parseInt3);
        instance2.set(12, parseInt4);
        instance2.set(13, 0);
        long timeInMillis = instance.getTimeInMillis();
        long timeInMillis2 = instance2.getTimeInMillis();
        ArrayList arrayList = new ArrayList();
        if (timeInMillis <= timeInMillis2) {
            int reminderIntervalTime = getReminderIntervalTime(context);
            while (instance.getTime().before(instance2.getTime())) {
                if (System.currentTimeMillis() <= instance.getTimeInMillis()) {
                    arrayList.add(Long.valueOf(instance.getTimeInMillis()));
                }
                instance.add(12, reminderIntervalTime);
            }
            check(context, arrayList);
        }
        setAlarm(context, arrayList);
    }

    public static void setDataBy24Hour(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AppPref.getWeight(context));
        stringBuilder.append("");
        int parseInt = Integer.parseInt(stringBuilder.toString());
        databaseHelper.call_history(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), parseInt * 40, parseInt);
    }

    public static void remind24(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarmRequestCode", 111);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 134217728);
        Calendar instance = Calendar.getInstance();
        instance.set(11, 24);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        if (VERSION.SDK_INT < 19) {
            Log.i("`ww", "onReceive: 11111!");
            alarmManager.set(0, instance.getTimeInMillis(), broadcast);
        } else if (VERSION.SDK_INT >= 19 && VERSION.SDK_INT < 23) {
            Log.i("`ww", "onReceive: 22222!");
            alarmManager.setExact(0, instance.getTimeInMillis(), broadcast);
        } else if (VERSION.SDK_INT >= 23) {
            Log.i("`ww", "onReceive: 33333!");
            alarmManager.setExactAndAllowWhileIdle(0, instance.getTimeInMillis(), broadcast);
        }
    }

    public static void remind3hour(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarmRequestCode", 112);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 1, intent, 134217728);
        Calendar instance = Calendar.getInstance();
        instance.set(11, 6);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        if (System.currentTimeMillis() <= instance.getTimeInMillis()) {
            Log.i(LOGATAG, "onReceive: 11111!");
            if (VERSION.SDK_INT < 19) {
                alarmManager.set(0, instance.getTimeInMillis(), broadcast);
            } else if (VERSION.SDK_INT >= 19 && VERSION.SDK_INT < 23) {
                alarmManager.setExact(0, instance.getTimeInMillis(), broadcast);
            } else if (VERSION.SDK_INT >= 23) {
                alarmManager.setExactAndAllowWhileIdle(0, instance.getTimeInMillis(), broadcast);
            }
        }
    }

    public static void setAlarm(Context context, List<Long> list) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        int i = 0;
        while (i < list.size()) {
            Intent intent = new Intent(context, recieve.class);
            intent.putExtra("AlarmRequestCode", 999);
            intent.putExtra("AlarmTime", (Serializable) list.get(i));
            int i2 = i + 1;
            PendingIntent broadcast = PendingIntent.getBroadcast(context, i2, intent, 134217728);
            if (VERSION.SDK_INT > 22) {
                String str = LOGATAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setAlarm: ");
                stringBuilder.append(((Long) list.get(i)).longValue());
                Log.i(str, stringBuilder.toString());
                alarmManager.setExactAndAllowWhileIdle(0, ((Long) list.get(i)).longValue(), broadcast);
            } else {
                alarmManager.set(0, ((Long) list.get(i)).longValue(), broadcast);
            }
            i = i2;
        }
    }

    public static void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        for (int i = 0; i <= 50; i++) {
            try {
                alarmManager.cancel(PendingIntent.getBroadcast(context, i, new Intent(context, AlarmReceiver.class), 134217728));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void check(Context context, ArrayList<Long> arrayList) {
        Set hashSet = new HashSet();
        for (int i = 0; i < arrayList.size(); i++) {
            hashSet.add(((Long) arrayList.get(i)).toString());
        }
        AppPref.setNextreminderTime(context, hashSet);
    }

    public static String toYYYYMMDDHHMMSS(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(j));
    }

    public static String toHHMMSS(long j) {
        return new SimpleDateFormat("HH:mm").format(new Date(j));
    }
}
