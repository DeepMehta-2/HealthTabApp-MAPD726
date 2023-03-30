package com.centennial.healthtab.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.centennial.healthtab.R;
import com.centennial.healthtab.object.Notification_Data;
import com.centennial.healthtab.service.Alarm_Receiver;
import com.centennial.healthtab.utils.Constant;
import com.centennial.healthtab.utils.Pref_Utils;
import com.centennial.healthtab.utils.Utils;
import com.google.gson.Gson;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.StateProgressBar.StateNumber;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class SetData_Activity extends AppCompatActivity {
    EditText etWater;
    EditText etWeight;
    String gender = "Male";
    String interval = "";
    ImageView ivNotification;
    LinearLayout lout_final;
    LinearLayout lout_first;
    LinearLayout lout_fourth;
    LinearLayout lout_second;
    LinearLayout lout_third;
    String notification = "";
    ArrayList<Notification_Data> notificationDataArrayList;
    RadioButton radioFemale;
    RadioButton radioFloz;
    RadioGroup radioGroupWater;
    RadioGroup radioGroupWeight;
    RadioGroup radioGroupmale;
    RadioButton radioKg;
    RadioButton radioLb;
    RadioButton radioMale;
    RadioButton radioMl;
    String sleepTime = "";
    protected StateProgressBar stateprogressbar;
    Toolbar toolbar;
    TextView tvFinal;
    TextView tvNext;
    TextView tvNextFourth;
    TextView tvNextThird;
    TextView tvNextsecond;
    TextView tvSleep;
    TextView tvWakeup;
    TextView tvnotiRange;
    String wakeupTime = "";
    double water = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
    String waterUnit = "ml";
    double weight;
    String weightUnit = "kg";

    public static class NotificationID {
        public static AtomicInteger c = new AtomicInteger(0);

        public static int getID() {
            return c.incrementAndGet();
        }
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_set_data);
        init();
    }

    public void init() {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        this.stateprogressbar = findViewById(R.id.usage_stateprogressbar);
        this.stateprogressbar.setCurrentStateNumber(StateNumber.ONE);
        this.lout_first = findViewById(R.id.lout_first);
        this.lout_first.setVisibility(0);
        this.lout_second = findViewById(R.id.lout_second);
        this.lout_third = findViewById(R.id.lout_third);
        this.lout_fourth = findViewById(R.id.lout_fourth);
        this.lout_final = findViewById(R.id.lout_final);
        this.tvNext = findViewById(R.id.tvNext);
        this.tvNextsecond = findViewById(R.id.tvNextsecond);
        this.tvNextThird = findViewById(R.id.tvNextThird);
        this.tvNextFourth = findViewById(R.id.tvNextFourth);
        this.tvFinal = findViewById(R.id.tvFinal);
        this.tvSleep = findViewById(R.id.tvSleep);
        this.tvWakeup = findViewById(R.id.tvWakeup);
        this.tvnotiRange = findViewById(R.id.tvnotiRange);
        this.tvSleep.setText(Utils.getSleepTimeDefaults(getApplicationContext(), Constant.PARAM_VALID_SLEEP_TIME));
        this.tvWakeup.setText(Utils.getWakeupTimeDefaults(getApplicationContext(), Constant.PARAM_VALID_WAKE_UP_TIME));
        Log.e("print", "init4: " + tvWakeup.toString());
        Log.e("print", "init3: " + tvSleep.toString());
        Log.e("print", "init2: " + tvnotiRange.toString());
        Log.e("print", "init1: " + Utils.getWakeupTimeDefaults(getApplicationContext(), Constant.PARAM_VALID_WAKE_UP_TIME));
        this.tvnotiRange.setText(Utils.getIntervalDefaults(getApplicationContext(), Constant.PARAM_VALID_INTERVAL_TIME));
        this.ivNotification = findViewById(R.id.ivNotification);
        if (Utils.getFromUserDefaults(getApplicationContext(), Constant.PARAM_VALID_NOTIFICATION).equals("")) {
            this.ivNotification.setBackgroundResource(R.drawable.iv_on);
        } else {
            this.ivNotification.setBackgroundResource(R.drawable.iv_off);
        }
        this.etWeight = findViewById(R.id.etWeight);
        this.etWater = findViewById(R.id.etWater);
        this.radioGroupWeight = findViewById(R.id.radioGroupWeight);
        this.radioGroupWater = findViewById(R.id.radioGroupWater);
        this.radioGroupmale = findViewById(R.id.radioGroupmale);
        this.radioKg = findViewById(R.id.radioKg);
        this.radioLb = findViewById(R.id.radioLb);
        this.radioMl = findViewById(R.id.radioMl);
        this.radioFloz = findViewById(R.id.radioFloz);
        this.radioMale = findViewById(R.id.radioMale);
        this.radioFemale = findViewById(R.id.radioFemale);
        this.radioGroupWeight.check(this.radioKg.getId());
        this.radioGroupWater.check(this.radioMl.getId());
        this.radioGroupmale.check(this.radioMale.getId());
        this.radioGroupWeight.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                SetData_Activity.this.weightUnit = radioButton.getText().toString();
            }
        });
        this.radioGroupWater.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                SetData_Activity.this.waterUnit = radioButton.getText().toString();
            }
        });
        this.radioGroupmale.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                SetData_Activity.this.gender = radioButton.getText().toString();
            }
        });
        clickEvent();
    }

    public void clickEvent() {
        this.tvSleep.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String[] split = Utils.getSleepTimeDefaults(SetData_Activity.this.getApplicationContext(), Constant.PARAM_VALID_SLEEP_TIME).split(":");
                TimePickerDialog newInstance = TimePickerDialog.newInstance(new OnTimeSetListener() {
                    public void onTimeSet(TimePickerDialog timePickerDialog, int i, int i2, int i3) {
                        StringBuilder stringBuilder;
                        String stringBuilder2;
                        String stringBuilder3;
                        if (i < 10) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("0");
                            stringBuilder.append(i);
                            stringBuilder2 = stringBuilder.toString();
                        } else {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("");
                            stringBuilder.append(i);
                            stringBuilder2 = stringBuilder.toString();
                        }
                        StringBuilder stringBuilder4;
                        if (i2 < 10) {
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("0");
                            stringBuilder4.append(i2);
                            stringBuilder3 = stringBuilder4.toString();
                        } else {
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("");
                            stringBuilder4.append(i2);
                            stringBuilder3 = stringBuilder4.toString();
                        }
                        TextView textView = SetData_Activity.this.tvSleep;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(stringBuilder2);
                        stringBuilder.append(":");
                        stringBuilder.append(stringBuilder3);
                        textView.setText(stringBuilder.toString());
                    }
                }, Integer.parseInt(split[0]), Integer.parseInt(split[1]), false);
                newInstance.setThemeDark(false);
                newInstance.show(SetData_Activity.this.getFragmentManager(), "Timepickerdialog");
                newInstance.is24HourMode();
            }
        });
        this.tvWakeup.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String[] split = Utils.getWakeupTimeDefaults(SetData_Activity.this.getApplicationContext(), Constant.PARAM_VALID_WAKE_UP_TIME).split(":");
                TimePickerDialog newInstance = TimePickerDialog.newInstance(new OnTimeSetListener() {
                    public void onTimeSet(TimePickerDialog timePickerDialog, int i, int i2, int i3) {
                        StringBuilder stringBuilder;
                        String stringBuilder2;
                        String stringBuilder3;
                        if (i < 10) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("0");
                            stringBuilder.append(i);
                            stringBuilder2 = stringBuilder.toString();
                        } else {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("");
                            stringBuilder.append(i);
                            stringBuilder2 = stringBuilder.toString();
                        }
                        StringBuilder stringBuilder4;
                        if (i2 < 10) {
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("0");
                            stringBuilder4.append(i2);
                            stringBuilder3 = stringBuilder4.toString();
                        } else {
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("");
                            stringBuilder4.append(i2);
                            stringBuilder3 = stringBuilder4.toString();
                        }
                        TextView textView = SetData_Activity.this.tvWakeup;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(stringBuilder2);
                        stringBuilder.append(":");
                        stringBuilder.append(stringBuilder3);
                        textView.setText(stringBuilder.toString());
                    }
                }, Integer.parseInt(split[0]), Integer.parseInt(split[1]), false);
                newInstance.setThemeDark(false);
                newInstance.show(SetData_Activity.this.getFragmentManager(), "Timepickerdialog");
                newInstance.is24HourMode();
            }
        });
        this.tvnotiRange.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Calendar.getInstance();
                String[] split = Utils.getIntervalDefaults(SetData_Activity.this.getApplicationContext(), Constant.PARAM_VALID_INTERVAL_TIME).split(":");
                TimePickerDialog newInstance = TimePickerDialog.newInstance(new OnTimeSetListener() {
                    public void onTimeSet(TimePickerDialog timePickerDialog, int i, int i2, int i3) {
                        StringBuilder stringBuilder;
                        String stringBuilder2;
                        String stringBuilder3;
                        if (i < 10) {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("0");
                            stringBuilder.append(i);
                            stringBuilder2 = stringBuilder.toString();
                        } else {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("");
                            stringBuilder.append(i);
                            stringBuilder2 = stringBuilder.toString();
                        }
                        StringBuilder stringBuilder4;
                        if (i2 < 10) {
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("0");
                            stringBuilder4.append(i2);
                            stringBuilder3 = stringBuilder4.toString();
                        } else {
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("");
                            stringBuilder4.append(i2);
                            stringBuilder3 = stringBuilder4.toString();
                        }
                        TextView textView = SetData_Activity.this.tvnotiRange;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(stringBuilder2);
                        stringBuilder.append(":");
                        stringBuilder.append(stringBuilder3);
                        textView.setText(stringBuilder.toString());
                    }
                }, Integer.parseInt(split[0]), Integer.parseInt(split[1]), false);
                newInstance.setThemeDark(false);
                newInstance.show(SetData_Activity.this.getFragmentManager(), "Timepickerdialog");
                newInstance.is24HourMode();
            }
        });
        this.ivNotification.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SetData_Activity.this.notification.equals("")) {
                    SetData_Activity.this.ivNotification.setBackgroundResource(R.drawable.iv_off);
                    SetData_Activity.this.notification = "0";
                    Utils.notification = SetData_Activity.this.notification;
                    return;
                }
                SetData_Activity.this.ivNotification.setBackgroundResource(R.drawable.iv_on);
                SetData_Activity.this.notification = "";
                Utils.notification = SetData_Activity.this.notification;
            }
        });
        this.tvNext.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SetData_Activity.this.lout_first.setVisibility(8);
                SetData_Activity.this.lout_second.setVisibility(0);
                SetData_Activity.this.stateprogressbar.setCurrentStateNumber(StateNumber.TWO);
                if (SetData_Activity.this.weightUnit.equals("kg")) {
                    SetData_Activity.this.etWeight.setHint("in kg");
                } else {
                    SetData_Activity.this.etWeight.setHint("in lb");
                }
            }
        });
        this.tvNextsecond.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SetData_Activity.this.etWeight.getText().length() == 0) {
                    Toast.makeText(SetData_Activity.this.getApplicationContext(), "Please enter weight.", 0).show();
                    return;
                }
                SetData_Activity.this.weight = Integer.parseInt(SetData_Activity.this.etWeight.getText().toString());
                double d = SetData_Activity.this.weight;
                if (!SetData_Activity.this.weightUnit.equals("kg")) {
                    d = SetData_Activity.this.weight / Constant.lbs;
                }
                if (SetData_Activity.this.gender.equals("Female")) {
                    SetData_Activity.this.water = (double) Math.round(d * 35.0d);
                } else {
                    SetData_Activity.this.water = (double) Math.round(d * 50.0d);
                }
                EditText editText;
                if (SetData_Activity.this.waterUnit.equals("ml")) {
                    editText = SetData_Activity.this.etWater;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(Math.round(SetData_Activity.this.water));
                    editText.setText(stringBuilder.toString());
                    Log.e("print", "onClick: " + editText);
                } else {
                    d = SetData_Activity.this.water * Constant.oz;
                    editText = SetData_Activity.this.etWater;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(Math.round(d));
                    editText.setText(stringBuilder2.toString());
                }
                SetData_Activity.this.lout_second.setVisibility(8);
                SetData_Activity.this.lout_third.setVisibility(0);
                SetData_Activity.this.stateprogressbar.setCurrentStateNumber(StateNumber.THREE);
            }
        });
        this.tvNextThird.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SetData_Activity.this.etWater.getText().length() == 0) {
                    Toast.makeText(SetData_Activity.this.getApplicationContext(), "Please enter water.", 0).show();
                    return;
                }
                SetData_Activity.this.water = Integer.parseInt(SetData_Activity.this.etWater.getText().toString());
                SetData_Activity.this.lout_third.setVisibility(8);
                SetData_Activity.this.lout_fourth.setVisibility(0);
                SetData_Activity.this.stateprogressbar.setCurrentStateNumber(StateNumber.FOUR);
            }
        });
        this.tvNextFourth.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                SetData_Activity.this.sleepTime = SetData_Activity.this.tvSleep.getText().toString();
                SetData_Activity.this.wakeupTime = SetData_Activity.this.tvWakeup.getText().toString();
                SetData_Activity.this.interval = SetData_Activity.this.tvnotiRange.getText().toString();
                SetData_Activity.this.lout_fourth.setVisibility(8);
                SetData_Activity.this.lout_final.setVisibility(0);
                SetData_Activity.this.stateprogressbar.setAllStatesCompleted(true);
                SetData_Activity.this.stateprogressbar.enableAnimationToCurrentState(false);
            }
        });
        this.tvFinal.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                Alarm_Receiver alarmReceiver;
                Context applicationContext;
                int id;
                StringBuilder stringBuilder;
                SetData_Activity.this.notificationDataArrayList = new ArrayList();
                String[] split = SetData_Activity.this.sleepTime.split(":");
                Calendar instance = Calendar.getInstance();
                instance.set(11, Integer.parseInt(split[0]));
                instance.set(12, Integer.parseInt(split[1]));
                split = SetData_Activity.this.wakeupTime.split(":");
                Calendar instance2 = Calendar.getInstance();
                instance2.set(11, Integer.parseInt(split[0]));
                instance2.set(12, Integer.parseInt(split[1]));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Notification_Data notificationData = new Notification_Data();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(simpleDateFormat.format(instance2.getTime()));
                notificationData.setDate(stringBuilder2.toString());
                notificationData.setID(NotificationID.getID());
                if (SetData_Activity.this.notification.equals("")) {
                    notificationData.setOnOffNotification(true);
                    if (instance2.getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
                        alarmReceiver = new Alarm_Receiver();
                        applicationContext = SetData_Activity.this.getApplicationContext();
                        id = notificationData.getID();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(simpleDateFormat.format(instance2.getTime()));
                        alarmReceiver.setRepeatAlarm(applicationContext, id, instance2, stringBuilder.toString());
                    }
                } else {
                    notificationData.setOnOffNotification(false);
                }
                SetData_Activity.this.notificationDataArrayList.add(notificationData);
                do {
                    String[] split2 = SetData_Activity.this.interval.split(":");
                    instance2.add(11, Integer.parseInt(split2[0]));
                    instance2.add(12, Integer.parseInt(split2[1]));
                    notificationData = new Notification_Data();
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(simpleDateFormat.format(instance2.getTime()));
                    notificationData.setDate(stringBuilder2.toString());
                    notificationData.setID(NotificationID.getID());
                    if (SetData_Activity.this.notification.equals("")) {
                        notificationData.setOnOffNotification(true);
                        if (instance2.getTimeInMillis() <= instance.getTimeInMillis()) {
                            if (instance2.getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
                                alarmReceiver = new Alarm_Receiver();
                                applicationContext = SetData_Activity.this.getApplicationContext();
                                id = notificationData.getID();
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("");
                                stringBuilder.append(simpleDateFormat.format(instance2.getTime()));
                                alarmReceiver.setRepeatAlarm(applicationContext, id, instance2, stringBuilder.toString());
                            }
                            SetData_Activity.this.notificationDataArrayList.add(notificationData);
                        }
                    } else {
                        notificationData.setOnOffNotification(false);
                        if (instance2.getTimeInMillis() <= instance.getTimeInMillis()) {
                            SetData_Activity.this.notificationDataArrayList.add(notificationData);
                        }
                    }
                } while (instance2.getTimeInMillis() <= instance.getTimeInMillis());
                Pref_Utils.setDefaultNotificationListInfo(SetData_Activity.this.getApplicationContext(), new Gson().toJson(SetData_Activity.this.notificationDataArrayList));
                Utils.saveToUserDefaults(SetData_Activity.this.getApplicationContext(), Constant.PARAM_VALID_NOTIFICATION, SetData_Activity.this.notification);
                Utils.saveSleepTimeDefaults(SetData_Activity.this.getApplicationContext(), Constant.PARAM_VALID_SLEEP_TIME, SetData_Activity.this.sleepTime);
                Utils.saveWakeupTimeDefaults(SetData_Activity.this.getApplicationContext(), Constant.PARAM_VALID_WAKE_UP_TIME, SetData_Activity.this.wakeupTime);
                Utils.saveIntervalDefaults(SetData_Activity.this.getApplicationContext(), Constant.PARAM_VALID_INTERVAL_TIME, SetData_Activity.this.interval);
                Utils.saveToUserDefaults(SetData_Activity.this.getApplicationContext(), Constant.PARAM_VALID_WATER_TYPE, SetData_Activity.this.waterUnit);
                Utils.saveToUserDefaults(SetData_Activity.this.getApplicationContext(), Constant.PARAM_VALID_WEIGHT_TYPE, SetData_Activity.this.weightUnit);
                Utils.saveToUserDefaults(SetData_Activity.this.getApplicationContext(), Constant.PARAM_VALID_GENDER_TYPE, SetData_Activity.this.gender);
                Utils.saveIntDefaults(SetData_Activity.this.getApplicationContext(), Constant.PARAM_VALID_WEIGHT, (int) SetData_Activity.this.weight);
                Utils.saveIntDefaults(SetData_Activity.this.getApplicationContext(), Constant.DAILY_WATER_DRINK, (int) SetData_Activity.this.water);
                SetData_Activity.this.startActivityForResult(new Intent(SetData_Activity.this, Tab_activity.class), 1);
            }
        });
    }

    @SuppressLint("WrongConstant")
    public void onBackPressed() {
        if (this.lout_final.getVisibility() == 0) {
            this.stateprogressbar.setAllStatesCompleted(false);
            this.lout_final.setVisibility(8);
            this.lout_fourth.setVisibility(0);
            this.stateprogressbar.setCurrentStateNumber(StateNumber.FOUR);
        } else if (this.lout_fourth.getVisibility() == View.VISIBLE) {
            this.lout_fourth.setVisibility(8);
            this.lout_third.setVisibility(0);
            this.stateprogressbar.setCurrentStateNumber(StateNumber.THREE);
        } else if (this.lout_third.getVisibility() == 0) {
            this.lout_third.setVisibility(8);
            this.lout_second.setVisibility(0);
            this.stateprogressbar.setCurrentStateNumber(StateNumber.TWO);
        } else if (this.lout_second.getVisibility() == 0) {
            this.lout_second.setVisibility(8);
            this.lout_first.setVisibility(0);
            this.stateprogressbar.setCurrentStateNumber(StateNumber.ONE);
        } else {
            setResult(-1);
            finish();
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 1) {
            setResult(-1);
            finish();
        }
    }
}
