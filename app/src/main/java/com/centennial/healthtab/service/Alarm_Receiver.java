package com.centennial.healthtab.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.centennial.healthtab.R;
import com.centennial.healthtab.activity.Tab_activity;
import com.centennial.healthtab.object.Notification_Data;
import com.centennial.healthtab.splashexit.activity.StartActivity;
import com.centennial.healthtab.utils.Constant;
import com.centennial.healthtab.utils.Pref_Utils;
import com.centennial.healthtab.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Alarm_Receiver extends WakefulBroadcastReceiver {
    public static final String ANDROID_CHANNEL_ID = "com.jksol.water.reminder.tracker.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "DrinkWater_Channel";
    private static final String TAG = "Alarm_Receiver";
    private Intent editIntent;
    AlarmManager mAlarmManager;
    private Builder mBuilder;
    private Calendar mCalendar;
    PendingIntent mPendingIntent;
    private NotificationManager nManager;
    ArrayList<Notification_Data> notificationDataArrayList;
    Type type = new TypeToken<List<Notification_Data>>() {
    }.getType();

    public void onReceive(Context context, Intent intent) {
        int i = 0;
        int intExtra = intent.getIntExtra(Constant.NOTIFICATION_ID, 0);
        intent.getStringExtra(Constant.NOTIFICATION_DATE);
        String format = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime());
        this.notificationDataArrayList = new ArrayList();
        if (intExtra == 11 && Utils.getFromUserDefaults(context, Constant.PARAM_VALID_NOTIFICATION).equals("")) {
            this.notificationDataArrayList = new Gson().fromJson(Pref_Utils.getDefaultNotificationListInfo(context), this.type);
            while (i < this.notificationDataArrayList.size()) {
                if (format.contains(this.notificationDataArrayList.get(i).getDate())) {
                    Utils.saveNotificationPositionDefaults(context, Constant.NOTIFICATION_POSITION, (float) i);
                    if (this.notificationDataArrayList.get(i).isOnOffNotification()) {
                        if (Utils.getNotificationModeDefaults(context, Constant.NOTIFICATION_MODE).equals("vibrate") || Utils.getNotificationModeDefaults(context, Constant.NOTIFICATION_MODE).equals("volume")) {
                            showNotification(context, intExtra);
                        } else if (Utils.getNotificationModeDefaults(context, Constant.NOTIFICATION_MODE).equals("minuse")) {
                            NoRemainder(context, intExtra);
                        } else {
                            Utils.getNotificationModeDefaults(context, Constant.NOTIFICATION_MODE).equals("mute");
                        }
                    }
                }
                i++;
            }
        }
    }

    @SuppressLint("WrongConstant")
    public void setRepeatAlarm(Context context, int i, Calendar calendar, String str) {
        this.mAlarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        Intent intent = new Intent(context, Alarm_Receiver.class);
        intent.putExtra(Constant.NOTIFICATION_ID, i);
        intent.putExtra(Constant.NOTIFICATION_DATE, str);
        this.mPendingIntent = PendingIntent.getBroadcast(context, i, intent, 268435456);
        if (VERSION.SDK_INT >= 23) {
            this.mAlarmManager.setExactAndAllowWhileIdle(0, SystemClock.elapsedRealtime(), this.mPendingIntent);
        } else if (VERSION.SDK_INT >= 19) {
            this.mAlarmManager.setExact(0, SystemClock.elapsedRealtime(), this.mPendingIntent);
        } else {
            this.mAlarmManager.set(0, SystemClock.elapsedRealtime(), this.mPendingIntent);
        }
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, Boot_Receiver.class), 1, 1);
    }

    @SuppressLint("WrongConstant")
    public void cancelAlarm(Context context, int i) {
        this.mAlarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.mPendingIntent = PendingIntent.getBroadcast(context, i, new Intent(context, Alarm_Receiver.class), 0);
        this.mAlarmManager.cancel(this.mPendingIntent);
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, Boot_Receiver.class), 2, 1);
    }

    private void NoRemainder(Context context, int i) {
        if (((int) (Utils.getUnitFromDefaults(context, Constant.PARAM_VALID_UNIT).floatValue() * Utils.getNotificationPositionDefaults(context, Constant.NOTIFICATION_POSITION))) >= Math.round(Utils.getWaterDrunkFromDefaults(context, Constant.PARAM_VALID_DRUNK_WATER))) {
            showNotification(context, i);
        }
    }

    @SuppressLint("WrongConstant")
    private void showNotification(Context context, int i) {
        this.nManager = (NotificationManager) context.getSystemService("notification");
        if (Utils.checkAppRunningInBG(context)) {
            this.editIntent = new Intent(context, StartActivity.class);
            this.editIntent.putExtra("action", "ShowDialog");
        } else {
            this.editIntent = new Intent(context, Tab_activity.class);
            this.editIntent.putExtra("action", true);
        }
        PendingIntent activity = PendingIntent.getActivity(context, i, this.editIntent, 134217728);
        Intent intent = new Intent(context, Notification_Receiver.class);
        intent.putExtra("notification_id", i);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, i, intent, 134217728);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("android.resource://");
        stringBuilder.append(context.getPackageName());
        stringBuilder.append("/");
        stringBuilder.append(R.raw.message2);
        Uri parse = Uri.parse(stringBuilder.toString());
        Notification build;
        if (VERSION.SDK_INT >= 26) {
            Notification.Builder sound = new Notification.Builder(context, ANDROID_CHANNEL_ID).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher)).setSmallIcon(R.drawable.iv_small_notification).addAction(R.drawable.iv_small_notification, "Drink", activity).addAction(R.drawable.ic_clear_black_24dp, "Snooze", broadcast).setContentTitle(context.getResources().getString(R.string.app_name)).setContentText("Keep your body and skin healthy.").setAutoCancel(true).setOnlyAlertOnce(true).setOngoing(false).setVibrate(new long[]{1000, 1000, 0, 0, 1000}).setSound(parse);
            sound.setPriority(2);
            build = sound.build();
            build.defaults |= 2;
            this.nManager.notify(i, build);
            return;
        }
        Builder sound2 = new Builder(context).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher)).setSmallIcon(R.drawable.iv_small_notification).addAction(R.drawable.iv_small_notification, "Drink", activity).addAction(R.drawable.ic_clear_black_24dp, "Snooze", broadcast).setContentTitle(context.getResources().getString(R.string.app_name)).setContentText("Keep your body and skin healthy.").setAutoCancel(true).setOnlyAlertOnce(true).setOngoing(false).setVibrate(new long[]{1000, 1000, 0, 0, 1000}).setSound(parse);
        sound2.setPriority(2);
        build = sound2.build();
        build.defaults |= 2;
        this.nManager.notify(i, build);
    }
}