package com.centennial.healthtab.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
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
    public static final String ANDROID_CHANNEL_ID = "HealthTab_Notification_Channel";
    public static final String ANDROID_CHANNEL_NAME = "DrinkWater_Channel";
    private static final String TAG = "Alarm_Receiver";
    AlarmManager mAlarmManager;
    PendingIntent mPendingIntent;
    ArrayList<Notification_Data> notificationDataArrayList;
    Type type = new TypeToken<List<Notification_Data>>() {
    }.getType();

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        this.mPendingIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_IMMUTABLE | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.mAlarmManager.setExactAndAllowWhileIdle(0, SystemClock.elapsedRealtime(), this.mPendingIntent);
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, Boot_Receiver.class), 1, 1);
    }

    @SuppressLint("WrongConstant")
    public void cancelAlarm(Context context, int i) {
        this.mAlarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.mPendingIntent = PendingIntent.getBroadcast(context, i, new Intent(context, Alarm_Receiver.class), 0);
        this.mAlarmManager.cancel(this.mPendingIntent);
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, Boot_Receiver.class), 2, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void NoRemainder(Context context, int i) {
        if (((int) (Utils.getUnitFromDefaults(context, Constant.PARAM_VALID_UNIT) * Utils.getNotificationPositionDefaults(context, Constant.NOTIFICATION_POSITION))) >= Math.round(Utils.getWaterDrunkFromDefaults(context, Constant.PARAM_VALID_DRUNK_WATER))) {
            showNotification(context, i);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    private void showNotification(Context context, int i) {
        Log.e("Check", "Notification Showed");

        Intent editIntent;
        if (Utils.checkAppRunningInBG(context)) {
            editIntent = new Intent(context, StartActivity.class);
            editIntent.putExtra("action", "ShowDialog");
        } else {
            editIntent = new Intent(context, Tab_activity.class);
            editIntent.putExtra("action", true);
        }

        PendingIntent activity = PendingIntent.getActivity(context, i, editIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intent = new Intent(context, Notification_Receiver.class);
        intent.putExtra("notification_id", i);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        String stringBuilder = "android.resource://" +
                context.getPackageName() +
                "/" +
                R.raw.message2;
        Uri parse = Uri.parse(stringBuilder);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String CHANNEL_ID = "my_channel_01";
        ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            mNotificationManager.createNotificationChannel(mChannel);
        }

        Notification notification = new Notification.Builder(context, CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.iv_small_notification).addAction(R.drawable.iv_small_notification, "Drink", activity)
                .addAction(R.drawable.ic_clear_black_24dp, "Snooze", broadcast)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText("Keep your body and skin healthy.")
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setOngoing(false)
                .setVibrate(new long[]{1000, 1000, 0, 0, 1000})
                .setSound(parse)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();
        ;
        mNotificationManager.notify(i, notification);
    }
}
