package com.centennial.healthtab.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.centennial.healthtab.R;
import com.centennial.healthtab.activity.SetData_Activity;
import com.centennial.healthtab.object.Notification_Data;
import com.centennial.healthtab.service.Alarm_Receiver;
import com.centennial.healthtab.utils.Constant;
import com.centennial.healthtab.utils.Pref_Utils;
import com.centennial.healthtab.utils.Utils;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SettingFragment extends Fragment implements OnTimeSetListener {
    String Time = "";
    TextView etWeight;
    String interval;
    ImageView ivDatetime;
    RelativeLayout loutDatetime;

    LinearLayout lout_interval_click;

    LinearLayout lout_units_click;
    LinearLayout lout_weight;
    RelativeLayout lout_weight_click;
    TextView etgender;
    TextView gender;
    ArrayList<Notification_Data> notificationDataArrayList;
    String sleepingTime;
    TextView tvInterval;
    TextView tvTime;
    TextView tvWeightUnit;

    TextView units_txt;

    String wakeupTime;
    double water;
    private String[] waterunit;
    int weightDefault;
    View view;

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.activity_setting, viewGroup, false);
        init();
        return this.view;
    }

    @SuppressLint({"NewApi"})
    public void init() {

        this.lout_weight_click = view.findViewById(R.id.lout_weight_click);
        this.lout_units_click = view.findViewById(R.id.lout_units_click);
        this.lout_interval_click = view.findViewById(R.id.lout_interval_click);


        this.lout_weight = view.findViewById(R.id.lout_weight);

        this.etWeight = view.findViewById(R.id.etWeight);
        this.gender = view.findViewById(R.id.gender);
        this.etgender = view.findViewById(R.id.etgender);
        this.tvTime = view.findViewById(R.id.tvTime);
        this.tvInterval = view.findViewById(R.id.tvInterval);
        this.tvWeightUnit = view.findViewById(R.id.tvWeightUnit);
        this.units_txt = view.findViewById(R.id.units_txt);

        this.water = Utils.getIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK);
        this.sleepingTime = Utils.getSleepTimeDefaults(getActivity(), Constant.PARAM_VALID_SLEEP_TIME);
        this.wakeupTime = Utils.getWakeupTimeDefaults(getActivity(), Constant.PARAM_VALID_WAKE_UP_TIME);
        TextView textView2 = this.tvTime;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Utils.getWakeupTimeDefaults(getActivity(), Constant.PARAM_VALID_WAKE_UP_TIME));
        stringBuilder.append(" To ");
        stringBuilder.append(Utils.getSleepTimeDefaults(getActivity(), Constant.PARAM_VALID_SLEEP_TIME));
        textView2.setText(stringBuilder.toString());
        this.interval = Utils.getIntervalDefaults(getActivity(), Constant.PARAM_VALID_INTERVAL_TIME);
        this.tvInterval.setText(Utils.getIntervalDefaults(getActivity(), Constant.PARAM_VALID_INTERVAL_TIME));
        this.ivDatetime = view.findViewById(R.id.ivDatetime);
        this.loutDatetime = view.findViewById(R.id.loutDatetime);


        if (Utils.getTimerDefaults(getActivity(), Constant.PARAM_VALID_TIMER).equals("24 hour")) {
            this.ivDatetime.setBackgroundResource(R.drawable.iv_on);
        } else {
            this.ivDatetime.setBackgroundResource(R.drawable.iv_off);
        }
        gender.setOnClickListener(new OnClickListener() {
            public TextView lout_male, lout_female;

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.dialog_gender);
                this.lout_male = dialog.findViewById(R.id.lout_male);
                this.lout_female = dialog.findViewById(R.id.lout_female);
                this.lout_male.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Utils.saveToUserDefaults(getActivity(), Constant.PARAM_VALID_GENDER_TYPE, "Male");
                        etgender.setText("Male");
                        lout_male.setTextColor(getResources().getColor(R.color.colorAccent));
                        lout_female.setTextColor(getResources().getColor(R.color.black));
//                        lout_female.setCompoundDrawableTintMode(R.color.black));
                        dialog.dismiss();
                    }
                });
                this.lout_female.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Utils.saveToUserDefaults(getActivity(), Constant.PARAM_VALID_GENDER_TYPE, "Female");
                        etgender.setText("Female");
                        lout_female.setTextColor(getResources().getColor(R.color.colorAccent));
                        lout_male.setTextColor(getResources().getColor(R.color.black));
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        if (Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_GENDER_TYPE).equals("Female")) {
            etgender.setText("Female");
        } else {
            etgender.setText("Male");
        }
        this.weightDefault = Utils.getIntDefaults(getActivity(), Constant.PARAM_VALID_WEIGHT);
        this.waterunit = Utils.getFromWaterUnitsDefaults(getActivity(), Constant.WATER_UNITES).split(",");
        textView2 = this.tvWeightUnit;
        stringBuilder = new StringBuilder();
        stringBuilder.append(" ");
        stringBuilder.append(this.waterunit[0]);
        textView2.setText(stringBuilder.toString());
        if (this.waterunit[0].equals("kg")) {
            Utils.saveToUserDefaults(getActivity(), Constant.PARAM_VALID_WEIGHT_TYPE, this.waterunit[0]);
            textView2 = this.etWeight;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(Math.round((float) this.weightDefault));
            textView2.setText(stringBuilder.toString());
        } else {
            Utils.saveToUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE, this.waterunit[1]);
            double d = this.weightDefault;
            double d2 = Constant.lbs;
            Double.isNaN(d);
            d *= d2;
            TextView textView3 = this.etWeight;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(Math.round(d));
            textView3.setText(stringBuilder2.toString());
        }
        this.units_txt.setText(Utils.getFromWaterUnitsDefaults(getActivity(), Constant.WATER_UNITES));
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_waterunits);
        final TextView textView4 = dialog.findViewById(R.id.units_1);
        final TextView textView5 = dialog.findViewById(R.id.units_2);
        final TextView textView6 = dialog.findViewById(R.id.units_3);
        final TextView textView7 = dialog.findViewById(R.id.units_4);
        dialog.findViewById(R.id.units_close).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        this.lout_units_click.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (units_txt.getText().toString().equals("kg,ml")) {
                    textView4.setBackground(getResources().getDrawable(R.drawable.setect_waterunits));
                } else if (units_txt.getText().toString().equals("kg,fl.oz")) {
                    textView6.setBackground(getResources().getDrawable(R.drawable.setect_waterunits));
                } else if (units_txt.getText().toString().equals("lb,ml")) {
                    textView5.setBackground(getResources().getDrawable(R.drawable.setect_waterunits));
                } else if (units_txt.getText().toString().equals("lb,fl.oz")) {
                    textView7.setBackground(getResources().getDrawable(R.drawable.setect_waterunits));
                }
                textView4.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        textView4.setBackground(getResources().getDrawable(R.drawable.setect_waterunits));
                        textView5.setBackgroundColor(getResources().getColor(R.color.white));
                        textView6.setBackgroundColor(getResources().getColor(R.color.white));
                        textView7.setBackgroundColor(getResources().getColor(R.color.white));
                        WaterUnitsMode(textView4.getText().toString(), dialog);
                    }
                });
                textView5.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        textView5.setBackground(getResources().getDrawable(R.drawable.setect_waterunits));
                        textView7.setBackgroundColor(getResources().getColor(R.color.white));
                        textView4.setBackgroundColor(getResources().getColor(R.color.white));
                        textView6.setBackgroundColor(getResources().getColor(R.color.white));
                        WaterUnitsMode(textView5.getText().toString(), dialog);
                    }
                });
                textView6.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        textView6.setBackground(getResources().getDrawable(R.drawable.setect_waterunits));
                        textView5.setBackgroundColor(getResources().getColor(R.color.white));
                        textView4.setBackgroundColor(getResources().getColor(R.color.white));
                        textView7.setBackgroundColor(getResources().getColor(R.color.white));
                        WaterUnitsMode(textView6.getText().toString(), dialog);
                    }
                });
                textView7.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        textView7.setBackground(getResources().getDrawable(R.drawable.setect_waterunits));
                        textView5.setBackgroundColor(getResources().getColor(R.color.white));
                        textView4.setBackgroundColor(getResources().getColor(R.color.white));
                        textView6.setBackgroundColor(getResources().getColor(R.color.white));
                        WaterUnitsMode(textView7.getText().toString(), dialog);
                    }
                });
                dialog.show();
            }
        });
        clickEvent();
    }

    private void WaterUnitsMode(String str, Dialog dialog) {
        Utils.saveToWaterUnitsDefaults(getActivity(), Constant.WATER_UNITES, str);
        this.waterunit = str.split(",");
        TextView textView = this.tvWeightUnit;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ");
        stringBuilder.append(this.waterunit[0]);
        textView.setText(stringBuilder.toString());
        if (this.waterunit[0].equals("kg")) {
            Utils.saveToUserDefaults(getActivity(), Constant.PARAM_VALID_WEIGHT_TYPE, this.waterunit[0]);
            Utils.saveToUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE, this.waterunit[1]);
            textView = this.etWeight;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(Math.round((float) this.weightDefault));
            textView.setText(stringBuilder.toString());
        } else {
            Utils.saveToUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE, this.waterunit[1]);
            double d = this.weightDefault;
            double d2 = Constant.lbs;
            Double.isNaN(d);
            d *= d2;
            textView = this.etWeight;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(Math.round(d));
            textView.setText(stringBuilder2.toString());
        }
        this.units_txt.setText(Utils.getFromWaterUnitsDefaults(getActivity(), Constant.WATER_UNITES));
        dialog.dismiss();
    }

    @SuppressLint({"NewApi", "ResourceAsColor"})


    public void clickEvent() {
        this.lout_weight_click.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(1);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_enterweight);
                TextView textView = dialog.findViewById(R.id.weight_cancel);
                final EditText editText = dialog.findViewById(R.id.weight_txt);
                dialog.findViewById(R.id.weight_done).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (editText.getText().toString().equals("")) {
                            editText.setError("Please enter weight");
                            return;
                        }
                        editText.getText().toString();
                        Utils.saveIntDefaults(getActivity(), Constant.PARAM_VALID_WEIGHT, Integer.parseInt(editText.getText().toString()));
                        TextView textView = etWeight;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(Utils.getIntDefaults(getActivity(), Constant.PARAM_VALID_WEIGHT));
                        textView.setText(stringBuilder.toString());
                        dialog.dismiss();
                    }
                });
                textView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        this.loutDatetime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Utils.getTimerDefaults(getActivity(), Constant.PARAM_VALID_TIMER).equals("24 hour")) {
                    ivDatetime.setBackgroundResource(R.drawable.iv_off);
                    Utils.saveTimerDefaults(getActivity(), Constant.PARAM_VALID_TIMER, "12 hour");
                    return;
                }
                ivDatetime.setBackgroundResource(R.drawable.iv_on);
                Utils.saveTimerDefaults(getActivity(), Constant.PARAM_VALID_TIMER, "24 hour");
            }
        });

        this.lout_interval_click.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String[] split = Utils.getIntervalDefaults(getActivity(), Constant.PARAM_VALID_INTERVAL_TIME).split(":");
                TimePickerDialog newInstance = TimePickerDialog.newInstance(new OnTimeSetListener() {
                    @SuppressLint("WrongConstant")
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
                        TextView textView = tvInterval;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(stringBuilder2);
                        stringBuilder.append(":");
                        stringBuilder.append(stringBuilder3);
                        textView.setText(stringBuilder.toString());
                        SettingFragment settingActivity = SettingFragment.this;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(stringBuilder2);
                        stringBuilder.append(":");
                        stringBuilder.append(stringBuilder3);
                        settingActivity.interval = stringBuilder.toString();
                        Utils.saveIntervalDefaults(getActivity(), Constant.PARAM_VALID_INTERVAL_TIME, interval);
                        notificationDataArrayList = new ArrayList();
                        String[] split = Utils.getSleepTimeDefaults(getActivity(), Constant.PARAM_VALID_SLEEP_TIME).split(":");
                        Calendar instance = Calendar.getInstance();
                        instance.set(11, Integer.parseInt(split[0]));
                        instance.set(12, Integer.parseInt(split[1]));
                        String[] split2 = Utils.getSleepTimeDefaults(getActivity(), Constant.PARAM_VALID_WAKE_UP_TIME).split(":");
                        Calendar instance2 = Calendar.getInstance();
                        instance2.set(11, Integer.parseInt(split2[0]));
                        instance2.set(12, Integer.parseInt(split2[1]));
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Notification_Data notificationData = new Notification_Data();
                        StringBuilder stringBuilder5 = new StringBuilder();
                        stringBuilder5.append("");
                        stringBuilder5.append(simpleDateFormat.format(instance2.getTime()));
                        notificationData.setDate(stringBuilder5.toString());
                        notificationData.setID(SetData_Activity.NotificationID.getID());
                        notificationData.setOnOffNotification(true);
                        notificationDataArrayList.add(notificationData);
                        do {
                            String[] split3 = Utils.getIntervalDefaults(getActivity(), Constant.PARAM_VALID_INTERVAL_TIME).split(":");
                            instance2.add(11, Integer.parseInt(split3[0]));
                            instance2.add(12, Integer.parseInt(split3[1]));
                            if (instance2.get(11) <= Integer.parseInt(split[0])) {
                                notificationData = new Notification_Data();
                                stringBuilder5 = new StringBuilder();
                                stringBuilder5.append("");
                                stringBuilder5.append(simpleDateFormat.format(instance2.getTime()));
                                notificationData.setDate(stringBuilder5.toString());
                                notificationData.setID(SetData_Activity.NotificationID.getID());
                                notificationData.setOnOffNotification(true);
                                notificationDataArrayList.add(notificationData);
                            }
                        } while (instance2.getTimeInMillis() < instance.getTimeInMillis());
                        Pref_Utils.setDefaultNotificationListInfo(getActivity(), new Gson().toJson(notificationDataArrayList));
                        Alarm_Receiver alarmReceiver = new Alarm_Receiver();
                        Context applicationContext = getActivity();
                        StringBuilder stringBuilder6 = new StringBuilder();
                        stringBuilder6.append("");
                        stringBuilder6.append(simpleDateFormat.format(instance2.getTime()));
                        alarmReceiver.setRepeatAlarm(applicationContext, 11, instance2, stringBuilder6.toString());
                    }
                }, Integer.parseInt(split[0]), Integer.parseInt(split[1]), true);
                newInstance.setThemeDark(false);
                newInstance.show(getActivity().getFragmentManager(), "Timepickerdialog");
            }
        });
        this.tvTime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                showTimePicker();
            }
        });
    }

    public void showTimePicker() {
        String[] split = Utils.getWakeupTimeDefaults(getActivity(), Constant.PARAM_VALID_WAKE_UP_TIME).split(":");
        TimePickerDialog newInstance = TimePickerDialog.newInstance(this, Integer.parseInt(split[0]), Integer.parseInt(split[1]), true);
        newInstance.setThemeDark(false);
        newInstance.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    public void showSleepPicker() {
        String[] split = Utils.getSleepTimeDefaults(getActivity(), Constant.PARAM_VALID_SLEEP_TIME).split(":");
        TimePickerDialog newInstance = TimePickerDialog.newInstance(this, Integer.parseInt(split[0]), Integer.parseInt(split[1]), true);
        newInstance.setThemeDark(false);
        newInstance.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    public void onTimeSet(TimePickerDialog timePickerDialog, int i, int i2, int i3) {
        StringBuilder stringBuilder;
        String stringBuilder2;
        StringBuilder stringBuilder3;
        String stringBuilder4;
        StringBuilder stringBuilder5;
        if (this.Time.equals("")) {
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
            if (i2 < 10) {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("0");
                stringBuilder3.append(i2);
                stringBuilder4 = stringBuilder3.toString();
            } else {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("");
                stringBuilder3.append(i2);
                stringBuilder4 = stringBuilder3.toString();
            }
            stringBuilder5 = new StringBuilder();
            stringBuilder5.append("");
            stringBuilder5.append(stringBuilder2);
            stringBuilder5.append(":");
            stringBuilder5.append(stringBuilder4);
            this.Time = stringBuilder5.toString();
            showSleepPicker();
            return;
        }
        this.wakeupTime = this.Time;
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
        if (i2 < 10) {
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("0");
            stringBuilder3.append(i2);
            stringBuilder4 = stringBuilder3.toString();
        } else {
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append("");
            stringBuilder3.append(i2);
            stringBuilder4 = stringBuilder3.toString();
        }
        stringBuilder5 = new StringBuilder();
        stringBuilder5.append(this.Time);
        stringBuilder5.append(" To ");
        stringBuilder5.append(stringBuilder2);
        stringBuilder5.append(":");
        stringBuilder5.append(stringBuilder4);
        this.Time = stringBuilder5.toString();
        this.tvTime.setText(this.Time);
        this.Time = "";
        stringBuilder5 = new StringBuilder();
        stringBuilder5.append("");
        stringBuilder5.append(stringBuilder2);
        stringBuilder5.append(":");
        stringBuilder5.append(stringBuilder4);
        this.sleepingTime = stringBuilder5.toString();
        Utils.saveSleepTimeDefaults(getActivity(), Constant.PARAM_VALID_SLEEP_TIME, this.sleepingTime);
        Utils.saveWakeupTimeDefaults(getActivity(), Constant.PARAM_VALID_WAKE_UP_TIME, this.wakeupTime);
    }

    public void onBackPressed() {
//        setResult(-1);
//        finish();
    }
}
