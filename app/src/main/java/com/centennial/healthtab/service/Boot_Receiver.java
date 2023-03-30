package com.centennial.healthtab.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class Boot_Receiver extends BroadcastReceiver {
    private Calendar mCalendar;

    public void onReceive(Context context, Intent intent) {
        intent.getAction().equals("android.intent.action.BOOT_COMPLETED");
    }
}
