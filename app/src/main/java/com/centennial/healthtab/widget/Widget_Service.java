package com.centennial.healthtab.widget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

import com.centennial.healthtab.R;
import com.centennial.healthtab.utils.Constant;
import com.centennial.healthtab.utils.Utils;

public class Widget_Service extends Service {
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onStart(Intent intent, int i) {
        super.onStart(intent, i);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_notification_mode);
        if (intent.getStringExtra("notify").equals("vibrate")) {
            Utils.saveNotificationModeDefaults(getApplicationContext(), Constant.NOTIFICATION_MODE, "vibrate");
        } else if (intent.getStringExtra("notify").equals("mute")) {
            Utils.saveNotificationModeDefaults(getApplicationContext(), Constant.NOTIFICATION_MODE, "mute");
        } else if (intent.getStringExtra("notify").equals("minuse")) {
            Utils.saveNotificationModeDefaults(getApplicationContext(), Constant.NOTIFICATION_MODE, "minuse");
        } else if (intent.getStringExtra("notify").equals("volume")) {
            Utils.saveNotificationModeDefaults(getApplicationContext(), Constant.NOTIFICATION_MODE, "volume");
        }
        new WidgetNotification_Mode().NotificationMode(getApplicationContext(), remoteViews);
    }
}
