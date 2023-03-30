package com.centennial.healthtab.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.centennial.healthtab.R;
import com.centennial.healthtab.service.Alarm_Receiver;
import com.centennial.healthtab.utils.Constant;
import com.centennial.healthtab.utils.Utils;

import java.util.Calendar;

public class WidgetNotification_Mode extends AppWidgetProvider {
    String tag = "notify";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_notification_mode);
        NotificationMode(context, remoteViews);
        Intent intent = new Intent(context, Widget_Service.class);
        intent.putExtra(this.tag, "vibrate");
        remoteViews.setOnClickPendingIntent(R.id.lout_notification_vibrate, PendingIntent.getService(context, 1, intent, 0));
        intent = new Intent(context, Widget_Service.class);
        intent.putExtra(this.tag, "mute");
        remoteViews.setOnClickPendingIntent(R.id.lout_notification_mute, PendingIntent.getService(context, 2, intent, 0));
        intent = new Intent(context, Widget_Service.class);
        intent.putExtra(this.tag, "minuse");
        remoteViews.setOnClickPendingIntent(R.id.lout_notification_minuse, PendingIntent.getService(context, 3, intent, 0));
        intent = new Intent(context, Widget_Service.class);
        intent.putExtra(this.tag, "volume");
        remoteViews.setOnClickPendingIntent(R.id.lout_notification_volume, PendingIntent.getService(context, 4, intent, 0));
        appWidgetManager.updateAppWidget(i, remoteViews);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] iArr) {
        for (int updateAppWidget : iArr) {
            updateAppWidget(context, appWidgetManager, updateAppWidget);
        }
        super.onUpdate(context, appWidgetManager, iArr);
    }

    @SuppressLint({"NewApi", "ResourceAsColor"})
    public void NotificationMode(Context context, RemoteViews remoteViews) {
        Context context2 = context;
        RemoteViews remoteViews2 = remoteViews;
        if (Utils.getNotificationModeDefaults(context2, Constant.NOTIFICATION_MODE).equals("mute")) {
            remoteViews2.setTextViewText(R.id.select_notification_mode, context.getResources().getString(R.string.mute));
            remoteViews2.setImageViewResource(R.id.mute_icon, R.drawable.iv_mute_select);
            remoteViews2.setImageViewResource(R.id.minuse_icon, R.drawable.iv_notification_minus);
            remoteViews2.setImageViewResource(R.id.vibrate_icon, R.drawable.iv_notification_vibrate);
            remoteViews2.setImageViewResource(R.id.volume_icon, R.drawable.iv_notification_volume);
            remoteViews2.setInt(R.id.lout_notification_mute, "setBackgroundResource", R.drawable.round_fillcolor);
            remoteViews2.setInt(R.id.lout_notification_minuse, "setBackgroundResource", R.drawable.round);
            remoteViews2.setInt(R.id.lout_notification_vibrate, "setBackgroundResource", R.drawable.round);
            remoteViews2.setInt(R.id.lout_notification_volume, "setBackgroundResource", R.drawable.round);
            new Alarm_Receiver().setRepeatAlarm(context2, 11, Calendar.getInstance(), "");
        } else if (Utils.getNotificationModeDefaults(context2, Constant.NOTIFICATION_MODE).equals("minuse")) {
            remoteViews2.setTextViewText(R.id.select_notification_mode, context.getResources().getString(R.string.minuse));
            remoteViews2.setImageViewResource(R.id.mute_icon, R.drawable.iv_notification_mute);
            remoteViews2.setImageViewResource(R.id.minuse_icon, R.drawable.iv_minuse_select);
            remoteViews2.setImageViewResource(R.id.vibrate_icon, R.drawable.iv_notification_vibrate);
            remoteViews2.setImageViewResource(R.id.volume_icon, R.drawable.iv_notification_volume);
            remoteViews2.setInt(R.id.lout_notification_mute, "setBackgroundResource", R.drawable.round);
            remoteViews2.setInt(R.id.lout_notification_minuse, "setBackgroundResource", R.drawable.round_fillcolor);
            remoteViews2.setInt(R.id.lout_notification_vibrate, "setBackgroundResource", R.drawable.round);
            remoteViews2.setInt(R.id.lout_notification_volume, "setBackgroundResource", R.drawable.round);
            new Alarm_Receiver().setRepeatAlarm(context2, 11, Calendar.getInstance(), "");
        } else if (Utils.getNotificationModeDefaults(context2, Constant.NOTIFICATION_MODE).equals("vibrate")) {
            remoteViews2.setTextViewText(R.id.select_notification_mode, context.getResources().getString(R.string.vibrate));
            remoteViews2.setImageViewResource(R.id.mute_icon, R.drawable.iv_notification_mute);
            remoteViews2.setImageViewResource(R.id.minuse_icon, R.drawable.iv_notification_minus);
            remoteViews2.setImageViewResource(R.id.vibrate_icon, R.drawable.iv_notification_vibrate);
            remoteViews2.setImageViewResource(R.id.volume_icon, R.drawable.iv_notification_volume);
            remoteViews2.setInt(R.id.lout_notification_mute, "setBackgroundResource", R.drawable.round);
            remoteViews2.setInt(R.id.lout_notification_minuse, "setBackgroundResource", R.drawable.round);
            remoteViews2.setInt(R.id.lout_notification_vibrate, "setBackgroundResource", R.drawable.round_fillcolor);
            remoteViews2.setInt(R.id.lout_notification_volume, "setBackgroundResource", R.drawable.round);
            new Alarm_Receiver().setRepeatAlarm(context2, 11, Calendar.getInstance(), "");
        } else if (Utils.getNotificationModeDefaults(context2, Constant.NOTIFICATION_MODE).equals("volume")) {
            remoteViews2.setTextViewText(R.id.select_notification_mode, context.getResources().getString(R.string.volume));
            remoteViews2.setImageViewResource(R.id.mute_icon, R.drawable.iv_notification_mute);
            remoteViews2.setImageViewResource(R.id.minuse_icon, R.drawable.iv_notification_minus);
            remoteViews2.setImageViewResource(R.id.vibrate_icon, R.drawable.iv_notification_vibrate);
            remoteViews2.setImageViewResource(R.id.volume_icon, R.drawable.ic_voluse_select);
            remoteViews2.setInt(R.id.lout_notification_mute, "setBackgroundResource", R.drawable.round);
            remoteViews2.setInt(R.id.lout_notification_minuse, "setBackgroundResource", R.drawable.round);
            remoteViews2.setInt(R.id.lout_notification_vibrate, "setBackgroundResource", R.drawable.round);
            remoteViews2.setInt(R.id.lout_notification_volume, "setBackgroundResource", R.drawable.round_fillcolor);
            new Alarm_Receiver().setRepeatAlarm(context2, 11, Calendar.getInstance(), "");
        }
        AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context2, WidgetNotification_Mode.class), remoteViews2);
    }
}
