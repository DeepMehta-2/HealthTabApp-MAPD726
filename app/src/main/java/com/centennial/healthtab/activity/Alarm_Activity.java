package com.centennial.healthtab.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.centennial.healthtab.R;
import com.centennial.healthtab.object.Notification_Data;
import com.centennial.healthtab.service.Alarm_Receiver;
import com.centennial.healthtab.utils.Constant;
import com.centennial.healthtab.utils.Pref_Utils;
import com.centennial.healthtab.utils.Utils;
import com.centennial.healthtab.widget.WidgetNotification_Mode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Alarm_Activity extends AppCompatActivity {

    LinearLayout select_notification_background;
    TextView select_notification_mode;
    ImageView minuse_icon;
    ImageView mute_icon;
    ImageView vibrate_icon;
    ImageView volume_icon;
    LinearLayout lout_notification_minuse;
    LinearLayout lout_notification_mute;
    LinearLayout lout_notification_vibrate;
    LinearLayout lout_notification_volume;
    RelativeLayout loutNotification;
    ImageView ivNotification;
    Type type = new TypeToken<List<Notification_Data>>() {
    }.getType();
    ArrayList<Notification_Data> notificationDataArrayList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.select_notification_background = findViewById(R.id.select_notification_background);
        this.lout_notification_mute = findViewById(R.id.lout_notification_mute);
        this.lout_notification_minuse = findViewById(R.id.lout_notification_minuse);
        this.lout_notification_vibrate = findViewById(R.id.lout_notification_vibrate);
        this.lout_notification_volume = findViewById(R.id.lout_notification_volume);
        this.mute_icon = findViewById(R.id.mute_icon);
        this.minuse_icon = findViewById(R.id.minuse_icon);
        this.vibrate_icon = findViewById(R.id.vibrate_icon);
        this.volume_icon = findViewById(R.id.volume_icon);
        this.ivNotification = findViewById(R.id.ivNotification);
        this.loutNotification = findViewById(R.id.loutNotification);
        this.select_notification_mode = findViewById(R.id.select_notification_mode);
        NotificationMode();
        clickEvent();
        if (Utils.getFromUserDefaults(this, Constant.PARAM_VALID_NOTIFICATION).equals("")) {
            this.ivNotification.setBackgroundResource(R.drawable.iv_on);
        } else {
            this.ivNotification.setBackgroundResource(R.drawable.iv_off);
        }

        this.loutNotification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Pref_Utils.getDefaultNotificationListInfo(getApplicationContext()).equals("")) {
                    notificationDataArrayList = new ArrayList();
                } else {
                    notificationDataArrayList = new Gson().fromJson(Pref_Utils.getDefaultNotificationListInfo(getApplicationContext()), type);
                }
                int i = 0;
                if (Utils.getFromUserDefaults(getApplicationContext(), Constant.PARAM_VALID_NOTIFICATION).equals("")) {
                    if (notificationDataArrayList.size() > 0) {
                        for (int i2 = 0; i2 < notificationDataArrayList.size(); i2++) {
                            if (notificationDataArrayList.get(i2).isOnOffNotification()) {
                                new Alarm_Receiver().cancelAlarm(getApplicationContext(), notificationDataArrayList.get(i2).getID());
                            }
                            notificationDataArrayList.get(i2).setOnOffNotification(false);
                        }
                        Pref_Utils.setDefaultNotificationListInfo(getApplicationContext(), new Gson().toJson(notificationDataArrayList));
                    }
                    ivNotification.setBackgroundResource(R.drawable.iv_off);
                    Utils.saveToUserDefaults(getApplicationContext(), Constant.PARAM_VALID_NOTIFICATION, "0");
                    return;
                }
                if (notificationDataArrayList.size() > 0) {
                    while (i < notificationDataArrayList.size()) {
                        Date parse;
                        Calendar instance = Calendar.getInstance();
                        try {
                            parse = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(notificationDataArrayList.get(i).getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            parse = null;
                        }
                        instance.setTime(parse);
                        if (instance.getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
                            new Alarm_Receiver().setRepeatAlarm(getApplicationContext(), notificationDataArrayList.get(i).getID(), instance, notificationDataArrayList.get(i).getDate());
                        }
                        notificationDataArrayList.get(i).setOnOffNotification(true);
                        i++;
                    }
                    Pref_Utils.setDefaultNotificationListInfo(getApplicationContext(), new Gson().toJson(notificationDataArrayList));
                }
                ivNotification.setBackgroundResource(R.drawable.iv_on);
                Utils.saveToUserDefaults(getApplicationContext(), Constant.PARAM_VALID_NOTIFICATION, "");
            }
        });
    }

    private void NotificationMode() {
        if (Utils.getNotificationModeDefaults(this, Constant.NOTIFICATION_MODE).equals("mute")) {
            this.select_notification_mode.setText(getResources().getString(R.string.mute));
            this.mute_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_mute_select));
            this.minuse_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_notification_minus));
            this.vibrate_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_notification_vibrate));
            this.volume_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_notification_volume));
            this.lout_notification_mute.setBackground(getResources().getDrawable(R.drawable.round_fillcolor));
            this.lout_notification_minuse.setBackground(getResources().getDrawable(R.drawable.round));
            this.lout_notification_vibrate.setBackground(getResources().getDrawable(R.drawable.round));
            this.lout_notification_volume.setBackground(getResources().getDrawable(R.drawable.round));
            new Alarm_Receiver().setRepeatAlarm(this, 11, Calendar.getInstance(), "");
        } else if (Utils.getNotificationModeDefaults(this, Constant.NOTIFICATION_MODE).equals("minuse")) {
            this.select_notification_mode.setText(getResources().getString(R.string.minuse));
            this.mute_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_notification_mute));
            this.minuse_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_minuse_select));
            this.vibrate_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_notification_vibrate));
            this.volume_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_notification_volume));
            this.lout_notification_mute.setBackground(getResources().getDrawable(R.drawable.round));
            this.lout_notification_minuse.setBackground(getResources().getDrawable(R.drawable.round_fillcolor));
            this.lout_notification_vibrate.setBackground(getResources().getDrawable(R.drawable.round));
            this.lout_notification_volume.setBackground(getResources().getDrawable(R.drawable.round));
            new Alarm_Receiver().setRepeatAlarm(this, 11, Calendar.getInstance(), "");
        } else if (Utils.getNotificationModeDefaults(this, Constant.NOTIFICATION_MODE).equals("vibrate")) {
            this.select_notification_mode.setText(getResources().getString(R.string.vibrate));
            this.mute_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_notification_mute));
            this.minuse_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_notification_minus));
            this.vibrate_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_voluse_vibret));
            this.volume_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_notification_volume));
            this.lout_notification_mute.setBackground(getResources().getDrawable(R.drawable.round));
            this.lout_notification_minuse.setBackground(getResources().getDrawable(R.drawable.round));
            this.lout_notification_vibrate.setBackground(getResources().getDrawable(R.drawable.round_fillcolor));
            this.lout_notification_volume.setBackground(getResources().getDrawable(R.drawable.round));
            new Alarm_Receiver().setRepeatAlarm(this, 11, Calendar.getInstance(), "");
        } else if (Utils.getNotificationModeDefaults(this, Constant.NOTIFICATION_MODE).equals("volume")) {
            this.select_notification_mode.setText(getResources().getString(R.string.volume));
            this.mute_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_notification_mute));
            this.minuse_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_notification_minus));
            this.vibrate_icon.setImageDrawable(getResources().getDrawable(R.drawable.iv_notification_vibrate));
            this.volume_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_voluse_select));
            this.lout_notification_mute.setBackground(getResources().getDrawable(R.drawable.round));
            this.lout_notification_minuse.setBackground(getResources().getDrawable(R.drawable.round));
            this.lout_notification_vibrate.setBackground(getResources().getDrawable(R.drawable.round));
            this.lout_notification_volume.setBackground(getResources().getDrawable(R.drawable.round_fillcolor));
            new Alarm_Receiver().setRepeatAlarm(this, 11, Calendar.getInstance(), "");
        }
        new WidgetNotification_Mode().NotificationMode(this, new RemoteViews(this.getPackageName(), R.layout.widget_notification_mode));
    }

    public void clickEvent() {
        this.lout_notification_mute.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Utils.saveNotificationModeDefaults(getApplicationContext(), Constant.NOTIFICATION_MODE, "mute");
                NotificationMode();
            }
        });
        this.lout_notification_minuse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Utils.saveNotificationModeDefaults(getApplicationContext(), Constant.NOTIFICATION_MODE, "minuse");
                NotificationMode();
            }
        });
        this.lout_notification_vibrate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Utils.saveNotificationModeDefaults(getApplicationContext(), Constant.NOTIFICATION_MODE, "vibrate");
                NotificationMode();
            }
        });
        this.lout_notification_volume.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Utils.saveNotificationModeDefaults(getApplicationContext(), Constant.NOTIFICATION_MODE, "volume");
                NotificationMode();
            }
        });
    }

}
