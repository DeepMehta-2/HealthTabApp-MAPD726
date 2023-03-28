package com.centennial.healthtab.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.centennial.healthtab.R;
import com.centennial.healthtab.activity.SetData_Activity;
import com.centennial.healthtab.object.Notification_Data;
import com.centennial.healthtab.service.Alarm_Receiver;
import com.centennial.healthtab.utils.Constant;
import com.centennial.healthtab.utils.Pref_Utils;
import com.centennial.healthtab.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("WrongConstant")

public class RemainderFragment extends Fragment {
    boolean[] checkedState;
    private String interval;
    private final boolean isNewDate = false;
    ListView listView;
    ArrayList<Notification_Data> notificationDataArrayList;
    ArrayList<Notification_Data> notificationDataList;
    private PackageManager pm;
    private String sleepTime;
    Type type = new TypeToken<List<Notification_Data>>() {
    }.getType();
    View view;
    private String wakeupTime;

    class ListAdapter extends BaseAdapter {
        String[] date;
        int hour;
        LinearLayout remainder_click;
        ImageView remainder_notification_icon;
        String timeSet;

        public long getItemId(int i) {
            return 0;
        }

        ListAdapter() {
        }

        public int getCount() {
            return RemainderFragment.this.notificationDataArrayList.size();
        }

        public Object getItem(int i) {
            return RemainderFragment.this.notificationDataArrayList.get(i);
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(RemainderFragment.this.getContext()).inflate(R.layout.item_remainder, null);
            TextView textView = view.findViewById(R.id.remainder_notification_txt);
            TextView textView1 = view.findViewById(R.id.remainder_notification_txt1);
            this.remainder_click = view.findViewById(R.id.remainder_click);
            this.remainder_notification_icon = view.findViewById(R.id.remainder_notification_icon);
            if (Utils.getTimerDefaults(RemainderFragment.this.getContext(), Constant.PARAM_VALID_TIMER).equals("12 hour")) {
                this.date = RemainderFragment.this.notificationDataArrayList.get(i).getDate().split(" ");
                String[] split = this.date[1].split(":");
                this.hour = Integer.parseInt(split[0]);
                if (this.hour > 12) {
                    this.hour -= 12;
                    this.timeSet = "PM";
                } else if (this.hour == 0) {
                    this.hour += 12;
                    this.timeSet = "AM";
                } else if (this.hour == 12) {
                    this.timeSet = "PM";
                } else {
                    this.timeSet = "AM";
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.hour);
                stringBuilder.append(":");
                stringBuilder.append(split[1]);
//                stringBuilder.append(this.timeSet);
                textView.setText(stringBuilder.toString());
                textView1.setText(timeSet);

            } else {
                this.date = RemainderFragment.this.notificationDataArrayList.get(i).getDate().split(" ");
                String[] split = this.date[1].split(":");
                this.hour = Integer.parseInt(split[0]);
                if (this.hour > 12) {
                    this.hour -= 12;
                    this.timeSet = "PM";
                } else if (this.hour == 0) {
                    this.hour += 12;
                    this.timeSet = "AM";
                } else if (this.hour == 12) {
                    this.timeSet = "PM";
                } else {
                    this.timeSet = "AM";
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.hour);
                stringBuilder.append(":");
                stringBuilder.append(split[1]);
                textView.setText(stringBuilder.toString());
                textView1.setText(timeSet);
            }
            if (RemainderFragment.this.notificationDataArrayList.get(i).isOnOffNotification()) {
                this.remainder_notification_icon.setImageResource(R.drawable.iv_on);
            } else {
                this.remainder_notification_icon.setImageResource(R.drawable.iv_off);
            }
            this.remainder_click.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (RemainderFragment.this.notificationDataArrayList.get(i).isOnOffNotification()) {
                        new Alarm_Receiver().cancelAlarm(RemainderFragment.this.getContext(), RemainderFragment.this.notificationDataArrayList.get(i).getID());
                        RemainderFragment.this.notificationDataArrayList.get(i).setOnOffNotification(false);
                    } else {
                        Date parse;
                        Calendar instance = Calendar.getInstance();
                        try {
                            parse = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(RemainderFragment.this.notificationDataArrayList.get(i).getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            parse = null;
                        }
                        instance.setTime(parse);
                        if (instance.getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
                            new Alarm_Receiver().setRepeatAlarm(RemainderFragment.this.getContext(), RemainderFragment.this.notificationDataArrayList.get(i).getID(), instance, RemainderFragment.this.notificationDataArrayList.get(i).getDate());
                        }
                        RemainderFragment.this.notificationDataArrayList.get(i).setOnOffNotification(true);
                    }
                    Pref_Utils.setDefaultNotificationListInfo(RemainderFragment.this.getContext(), new Gson().toJson(RemainderFragment.this.notificationDataArrayList));
                    ListAdapter.this.notifyDataSetChanged();
                }
            });
            return view;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.fragment_remainder, viewGroup, false);
        inti();
        return this.view;
    }

    private void inti() {
        this.notificationDataArrayList = new ArrayList();
        Calendar instance = Calendar.getInstance();
        int i = instance.get(5);
        int i2 = instance.get(2) + 1;
        instance.get(1);
        instance.get(11);
        instance.get(12);
        instance.get(13);
        if (i < 10) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0");
            stringBuilder.append(i);
            stringBuilder.toString();
        } else {
            String.valueOf(i);
        }
        if (i2 < 10) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("0");
            stringBuilder2.append(i2);
            stringBuilder2.toString();
        } else {
            String.valueOf(i2);
        }
        try {
            Alarm_Receiver alarmReceiver;
            Context activity;
            int id;
            StringBuilder stringBuilder3;
            this.pm = getActivity().getPackageManager();
            new SimpleDateFormat("dd/MM/yyyy").format(new Date(this.pm.getPackageInfo(getActivity().getPackageName(), 0).firstInstallTime));
            this.sleepTime = Utils.getSleepTimeDefaults(getActivity(), Constant.PARAM_VALID_SLEEP_TIME);
            String[] split = this.sleepTime.split(":");
            Calendar instance2 = Calendar.getInstance();
            instance2.set(11, Integer.parseInt(split[0]));
            instance2.set(12, Integer.parseInt(split[1]));
            this.wakeupTime = Utils.getSleepTimeDefaults(getActivity(), Constant.PARAM_VALID_WAKE_UP_TIME);
            split = this.wakeupTime.split(":");
            Calendar instance3 = Calendar.getInstance();
            instance3.set(11, Integer.parseInt(split[0]));
            instance3.set(12, Integer.parseInt(split[1]));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Notification_Data notificationData = new Notification_Data();
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("");
            stringBuilder4.append(simpleDateFormat.format(instance3.getTime()));
            notificationData.setDate(stringBuilder4.toString());
            notificationData.setID(SetData_Activity.NotificationID.getID());
            if (Utils.notification.equals("")) {
                notificationData.setOnOffNotification(true);
                if (instance3.getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
                    alarmReceiver = new Alarm_Receiver();
                    activity = getActivity();
                    id = notificationData.getID();
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("");
                    stringBuilder3.append(simpleDateFormat.format(instance3.getTime()));
                    alarmReceiver.setRepeatAlarm(activity, id, instance3, stringBuilder3.toString());
                }
            } else {
                notificationData.setOnOffNotification(false);
            }
            this.notificationDataArrayList.add(notificationData);
            do {
                this.interval = Utils.getIntervalDefaults(getActivity(), Constant.PARAM_VALID_INTERVAL_TIME);
                String[] split2 = this.interval.split(":");
                instance3.add(11, Integer.parseInt(split2[0]));
                instance3.add(12, Integer.parseInt(split2[1]));
                notificationData = new Notification_Data();
                stringBuilder4 = new StringBuilder();
                stringBuilder4.append("");
                stringBuilder4.append(simpleDateFormat.format(instance3.getTime()));
                notificationData.setDate(stringBuilder4.toString());
                notificationData.setID(SetData_Activity.NotificationID.getID());
                if (Utils.notification.equals("")) {
                    notificationData.setOnOffNotification(true);
                    if (instance3.getTimeInMillis() <= instance2.getTimeInMillis()) {
                        if (instance3.getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
                            alarmReceiver = new Alarm_Receiver();
                            activity = getActivity();
                            id = notificationData.getID();
                            stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("");
                            stringBuilder3.append(simpleDateFormat.format(instance3.getTime()));
                            alarmReceiver.setRepeatAlarm(activity, id, instance3, stringBuilder3.toString());
                        }
                        this.notificationDataArrayList.add(notificationData);
                    }
                } else {
                    notificationData.setOnOffNotification(false);
                    if (instance3.getTimeInMillis() <= instance2.getTimeInMillis()) {
                        this.notificationDataArrayList.add(notificationData);
                    }
                }
            } while (instance3.getTimeInMillis() <= instance2.getTimeInMillis());
            Pref_Utils.setDefaultNotificationListInfo(getActivity(), new Gson().toJson(this.notificationDataArrayList));
            Utils.saveToUserDefaults(getActivity(), Constant.PARAM_VALID_NOTIFICATION, Utils.notification);
            Utils.saveSleepTimeDefaults(getActivity(), Constant.PARAM_VALID_SLEEP_TIME, this.sleepTime);
            Utils.saveWakeupTimeDefaults(getActivity(), Constant.PARAM_VALID_WAKE_UP_TIME, this.wakeupTime);
            Utils.saveIntervalDefaults(getActivity(), Constant.PARAM_VALID_INTERVAL_TIME, this.interval);
            this.listView = this.view.findViewById(R.id.remainder_listview);
            this.listView.setAdapter(new ListAdapter());
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void onResume() {
        super.onResume();
        inti();
    }
}
