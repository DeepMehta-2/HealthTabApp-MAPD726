package com.centennial.healthtab.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.annotation.RequiresApi;

import com.centennial.healthtab.service.Alarm_Receiver;

public class Notification_Utils extends ContextWrapper {
    private NotificationManager mManager;

    @RequiresApi(api = 26)
    public Notification_Utils(Context context) {
        super(context);
        createChannels();
    }

    @RequiresApi(api = 26)
    public void createChannels() {
        NotificationChannel notificationChannel = new NotificationChannel(Alarm_Receiver.ANDROID_CHANNEL_ID, Alarm_Receiver.ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(-16711936);
        notificationChannel.setLockscreenVisibility(1);
        getManager().createNotificationChannel(notificationChannel);
    }

    private NotificationManager getManager() {
        if (this.mManager == null) {
            this.mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return this.mManager;
    }
}
