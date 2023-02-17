package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.Notification.Action;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioAttributes.Builder;
import android.net.Uri;
import android.os.Build.VERSION;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import java.text.SimpleDateFormat;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.NotificationHelper;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.ReceiverAndService.AlarmUtill;

public class recieve extends BroadcastReceiver {
    public final String NOTIFICATION_CHANNEL_ID1 = "waterreminder";
    private final int NOTIFICATION_ID1 = 101;
    DatabaseHelper db;

    public void setfirsttimenotification(Context context) {
        int i;
        switch (AppPref.getReminder(context)) {
            case 1:
                i = 30;
                break;
            case 2:
                i = 45;
                break;
            case 3:
                i = 60;
                break;
            case 4:
                i = 90;
                break;
            default:
                i = 0;
                break;
        }
        ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).setInexactRepeating(0, System.currentTimeMillis(), (long) ((i * 60) * 1000), PendingIntent.getBroadcast(context, 100, new Intent(context, recieve.class), 134217728));
        AppPref.setReceive(context, false);
    }

    public void onReceive(Context context, Intent intent) {
        try {
            this.db = new DatabaseHelper(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent.getBooleanExtra("Dismiss", false)) {
            HomeActivity.cancelNotification(context, 101);
            return;
        }
        int intExtra = intent.getIntExtra("AlarmRequestCode", 0);
        intent.getLongExtra("AlarmTime", 0);
        if (intExtra == 999) {
            if (System.currentTimeMillis() - AppPref.getLastNotificationreminder(context) >= 120000 && AppPref.getAuto(context) != 2) {
                try {
                    HomeActivity.adfromrecive = true;
                    Log.d("isAdLoaded", "From Recive");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (!AppPref.getNotificationType(context)) {
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                    intent = new Intent(context, LockScreen.class);
                    intent.setFlags(335544320);
                    context.startActivity(intent);
                } else if (VERSION.SDK_INT >= 20) {
                    getnotification_simple(context);
                }
            }
            AppPref.setLastNotificationreminder(context, System.currentTimeMillis());
        }
        if (Homefragment.nextReminder == null) {
            return;
        }
        if (AppPref.getNextreminderTime(context) != 0) {
            Homefragment.nextReminder.setVisibility(0);
            TextView textView = Homefragment.nextReminder;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Next Reminder : ");
            stringBuilder.append(AlarmUtill.toHHMMSS(AppPref.getNextreminderTime(context)));
            textView.setText(stringBuilder.toString());
            return;
        }
        Homefragment.nextReminder.setVisibility(4);
    }

    public String setnextrimnfer(long j, long j2, long j3) {
        return (j < j2 || j > j3) ? "" : new SimpleDateFormat("HH:mm").format(Long.valueOf(j));
    }

    public void getnotification_simple(Context context) {
        String str;
        int ml;
        Context context2 = context;
        int auto = AppPref.getAuto(context);
        int currentDrinkedWater;
        if (AppPref.isKglb(context)) {
            str = " ml";
            ml = this.db.getMl(context2);
            currentDrinkedWater = ml - this.db.getCurrentDrinkedWater();
        } else {
            str = " fl oz";
            ml = Homefragment.returnfloz(this.db.getMl(context2));
            currentDrinkedWater = ml - Homefragment.returnfloz(this.db.getCurrentDrinkedWater());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unfinished: ");
        stringBuilder.append(currentDrinkedWater);
        stringBuilder.append(str);
        stringBuilder.append(" Total: ");
        stringBuilder.append(ml);
        stringBuilder.append(str);
        String stringBuilder2 = stringBuilder.toString();
        Log.e("print", "getnotification_simple: " + currentDrinkedWater);
        Log.e("print", "getnotification_simple1: " + ml);
        Log.e("print", "getnotification_simple2: " + str);
        if (AppPref.getToggle(context)) {
            new NotificationHelper(context2).getnotification(context2);
        }
        Intent intent = new Intent(context2, HomeActivity.class);
        intent.putExtra("FromLock", true);
        Intent intent2 = new Intent(context2, recieve.class);
        intent2.putExtra("Dismiss", true);
        PendingIntent broadcast = PendingIntent.getBroadcast(context2, 10, intent2, 134217728);
        PendingIntent activity = PendingIntent.getActivity(context2, 11, intent, 134217728);
        Action action = new Action(0, "Drink", activity);
        Action action2 = new Action(0, "Dismiss", broadcast);
        NotificationManager notificationManager = (NotificationManager) context2.getSystemService("notification");
        if (VERSION.SDK_INT >= 26) {
            AudioAttributes build = new Builder().setContentType(4).setUsage(4).build();
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("android.resource://");
            stringBuilder3.append(context.getPackageName());
            stringBuilder3.append("/");
            stringBuilder3.append(R.raw.watersound);
            Uri parse = Uri.parse(stringBuilder3.toString());
            int i = 7;
            long[] jArr = new long[]{3000, 500, 1000, 500, 1000, 500, 1000};
            NotificationChannel notificationChannel = new NotificationChannel("waterreminder", "notification", 3);
            notificationChannel.setLightColor(-16776961);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(jArr);
            if (auto == 0) {
                notificationChannel.setSound(parse, build);
            }
            notificationChannel.enableLights(true);
            notificationChannel.setLockscreenVisibility(1);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(101, new Notification.Builder(context2, "waterreminder")
                    .setSmallIcon(R.mipmap.ic_launcher).setColor(ContextCompat.getColor(context2, R.color.colorPrimaryDark))
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle("It's time to Hydrate").setContentText(stringBuilder2)
                    .setAutoCancel(true).addAction(action).addAction(action2).build());
            return;
        }
        Notification build2;
        if (VERSION.SDK_INT >= 20) {
            build2 = new Notification.Builder(context2)
                    .setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.ic_launcher)).setAutoCancel(true).setContentTitle("It's time to Hydrate")
                    .setContentText(stringBuilder2).addAction(action).addAction(action2).build();
        } else {
            build2 = new Notification.Builder(context2).setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setAutoCancel(true).setContentTitle("It's time to Hydrate").setContentText(stringBuilder2)
                    .addAction(0, "Drink", activity).addAction(0, "Dismiss", broadcast).build();
        }
        if (auto == 0) {
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("android.resource://");
            stringBuilder4.append(context.getPackageName());
            stringBuilder4.append("/");
            stringBuilder4.append(R.raw.watersound);
            build2.sound = Uri.parse(stringBuilder4.toString());
            build2.defaults = 2;
        }
        if (VERSION.SDK_INT >= 21) {
            build2.color = ContextCompat.getColor(context2, R.color.colorPrimaryDark);
        }
        notificationManager.notify(101, build2);
    }
}
