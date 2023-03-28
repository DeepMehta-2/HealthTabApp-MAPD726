package com.centennial.healthtab.service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Notification_Receiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        ((NotificationManager) context.getSystemService("notification")).cancel(intent.getIntExtra("notification_id", 0));
    }
}
