package com.centennial.healthtab.splashexit.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.centennial.healthtab.R;
import com.centennial.healthtab.activity.SetData_Activity;
import com.centennial.healthtab.activity.Tab_activity;
import com.centennial.healthtab.object.Bottle_Data;
import com.centennial.healthtab.object.Chart_Data;
import com.centennial.healthtab.object.Notification_Data;
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
import java.util.Locale;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivStart;
    boolean showDialogTrue = false;
    ArrayList<Bottle_Data> bottleDataArrayList = new ArrayList();
    ArrayList<Notification_Data> notificationDataArrayList;
    ArrayList<Chart_Data> chartDataArrayList;
    private Dialog dialog;
    Type type = new TypeToken<List<Notification_Data>>() {
    }.getType();
    Type type1 = new TypeToken<List<Chart_Data>>() {
    }.getType();

    protected String[] mMonths = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first);

        init();
        String stringExtra = getIntent().getStringExtra("action");
        if (stringExtra != null && stringExtra.equals("ShowDialog")) {
            this.showDialogTrue = true;
        }
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
        init1();
        exitDialog();

    }

    public void init1() {
        int i = 0;
        if (Pref_Utils.getDefaultBottleListInfo(getApplicationContext()).equals("")) {
            int i2 = 100;
            for (int i3 = 0; i3 < 7; i3++) {
                Bottle_Data bottleData = new Bottle_Data();
                if (i3 == 0) {
                    bottleData.setUnit("0");
                    bottleData.setGlass(-1);
                    bottleData.setType(-1);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(i2);
                    bottleData.setUnit(stringBuilder.toString());
                    bottleData.setGlass(i3 - 1);
                    bottleData.setType(0);
                    i2 += 100;
                }
                this.bottleDataArrayList.add(bottleData);
            }
            Pref_Utils.setDefaultBottileInfo(getApplicationContext(), new Gson().toJson(this.bottleDataArrayList));
        }

        if (!Pref_Utils.getDefaultNotificationListInfo(getApplicationContext()).equals("")) {
            this.notificationDataArrayList = new Gson().fromJson(Pref_Utils.getDefaultNotificationListInfo(getApplicationContext()), this.type);
            if (this.notificationDataArrayList.size() > 0) {
                Date parse;
                String date = this.notificationDataArrayList.get(0).getDate();
                Calendar instance = Calendar.getInstance();
                try {
                    parse = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    parse = null;
                }
                instance.setTime(parse);
                int i4 = instance.get(2);
                int i5 = instance.get(5);
                Date time = instance.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String format = simpleDateFormat.format(time);
                String format2 = simpleDateFormat.format(Calendar.getInstance().getTime());
                if (format.compareTo(format2) < 0) {
                    while (i < this.notificationDataArrayList.size()) {
                        String[] split = this.notificationDataArrayList.get(i).getDate().split(" ");
                        Notification_Data notificationData = this.notificationDataArrayList.get(i);
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(format2);
                        stringBuilder2.append(" ");
                        stringBuilder2.append(split[1]);
                        notificationData.setDate(stringBuilder2.toString());
                        i++;
                    }
                    Pref_Utils.setDefaultNotificationListInfo(getApplicationContext(), new Gson().toJson(this.notificationDataArrayList));
                    long Daybetween = Daybetween(format, format2, "dd/MM/yyyy");
                    if (Pref_Utils.getDefaultChartListInfo(getApplicationContext()).equals("")) {
                        this.chartDataArrayList = new ArrayList();
                    } else {
                        this.chartDataArrayList = new Gson().fromJson(Pref_Utils.getDefaultChartListInfo(getApplicationContext()), this.type1);
                    }
                    if (Daybetween == 1) {
                        Chart_Data chartData = new Chart_Data();
                        chartData.setDrinkWater(String.valueOf(Utils.getWaterDrunkFromDefaults(getApplicationContext(), Constant.PARAM_VALID_DRUNK_WATER)));
                        chartData.setDate(format);
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("");
                        stringBuilder3.append(i5);
                        stringBuilder3.append(" ");
                        stringBuilder3.append(this.mMonths[i4]);
                        chartData.setChartDate(stringBuilder3.toString());
                        chartData.setTotalWater(String.valueOf(Utils.getIntDefaults(getApplicationContext(), Constant.DAILY_WATER_DRINK)));
                        this.chartDataArrayList.add(chartData);
                    } else {
                        Chart_Data chartData2 = new Chart_Data();
                        chartData2.setDrinkWater(String.valueOf(Utils.getWaterDrunkFromDefaults(getApplicationContext(), Constant.PARAM_VALID_DRUNK_WATER)));
                        chartData2.setDate(format);
                        StringBuilder stringBuilder4 = new StringBuilder();
                        stringBuilder4.append("");
                        stringBuilder4.append(i5);
                        stringBuilder4.append(" ");
                        stringBuilder4.append(this.mMonths[i4]);
                        chartData2.setChartDate(stringBuilder4.toString());
                        chartData2.setTotalWater(String.valueOf(Utils.getIntDefaults(getApplicationContext(), Constant.DAILY_WATER_DRINK)));
                        this.chartDataArrayList.add(chartData2);
                        for (i = 1; ((long) i) < Daybetween - 1; i++) {
                            instance.add(6, 1);
                            i4 = instance.get(2);
                            i5 = instance.get(5);
                            Chart_Data chartData3 = new Chart_Data();
                            StringBuilder stringBuilder5 = new StringBuilder();
                            stringBuilder5.append("");
                            stringBuilder5.append(i5);
                            stringBuilder5.append(" ");
                            stringBuilder5.append(this.mMonths[i4]);
                            chartData3.setChartDate(stringBuilder5.toString());
                            chartData3.setDrinkWater("0");
                            chartData3.setDate(simpleDateFormat.format(instance.getTime()));
                            chartData3.setTotalWater(String.valueOf(Utils.getIntDefaults(getApplicationContext(), Constant.DAILY_WATER_DRINK)));
                            this.chartDataArrayList.add(chartData3);
                        }
                    }
                    Pref_Utils.setDefaultChartListInfo(getApplicationContext(), new Gson().toJson(this.chartDataArrayList));
                    Utils.saveWaterDrunkDefaults(getApplicationContext(), Constant.PARAM_VALID_DRUNK_WATER, 0.0f);
                    Pref_Utils.setDefaultGlassInfo(getApplicationContext(), new Gson().toJson(new ArrayList()));
                    Utils.saveToUserDefaults(getApplicationContext(), Constant.GOAL_REACHED, "");
                }
            }
        }

    }

    public long Daybetween(String str, String str2, String str3) {
        Date parse;
        Date parse2 = null;
        Exception e;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str3, Locale.ENGLISH);
        try {
            parse = simpleDateFormat.parse(str);
            try {
                parse2 = simpleDateFormat.parse(str2);
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            parse = null;
            e.printStackTrace();
            parse2 = null;
            return (parse2.getTime() - parse.getTime()) / 86400000;
        }
        return (parse2.getTime() - parse.getTime()) / 86400000;
    }


    private void init() {
        ivStart = findViewById(R.id.ivStart);
        ivStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivStart) {
            Intent intent;
            if (Pref_Utils.getDefaultNotificationListInfo(getApplicationContext()).equals("")) {
                intent = new Intent(StartActivity.this, SetData_Activity.class);
                startActivity(intent);
            } else {
                intent = new Intent(StartActivity.this, Tab_activity.class);
                intent.putExtra("action", StartActivity.this.showDialogTrue);
                startActivity(intent);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1010) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        dialog.show();
    }

    private void exitDialog() {
        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dailog_exit);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        TextView tv_yes = dialog.findViewById(R.id.tv_yes);
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        TextView tv_no = dialog.findViewById(R.id.tv_no);
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
