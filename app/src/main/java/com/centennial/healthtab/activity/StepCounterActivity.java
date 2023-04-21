package com.centennial.healthtab.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.centennial.healthtab.R;

import java.util.Calendar;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {


    SensorManager sensormanager;
    TextView txt2, txt3;
    private long steps = 0;
    boolean isRunning = false;
    boolean startButtonPressed = false;
    private double MagnitudePrevious = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        sensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);

        Sensor countSensor = sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (countSensor != null) {
            sensormanager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "NO sensor Found", Toast.LENGTH_LONG).show();
        }

        Calendar newlogin = Calendar.getInstance();
        long currentyear = newlogin.get(Calendar.YEAR);
        long currentmonth = newlogin.get(Calendar.MONTH);
        long currentdate = newlogin.get(Calendar.DATE);

        SharedPreferences lastlogin = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        long lastyear = lastlogin.getLong("LAST_YEAR", 0);
        long lastmonth = lastlogin.getLong("LAST_MONTH", 0);
        long lastdate = lastlogin.getLong("LAST_DATE", 0);

        if ((currentyear - lastyear > 0) || (currentmonth - lastmonth > 0) || (currentdate - lastdate > 0)) {

            SharedPreferences saves = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = saves.edit();
            editor.putLong("STEPS_RUN_TODAY", 0);
            if ((currentyear - lastyear > 0) || (currentmonth - lastmonth > 0)) {
                editor.putLong("STEPS_RUN_THIS_MONTH", 0);
            }
            editor.commit();

            //Login Time Update
            SharedPreferences.Editor editor1 = lastlogin.edit();
            editor1.putLong("LAST_YEAR", currentyear);
            editor1.putLong("LAST_MONTH", currentmonth);
            editor1.putLong("LAST_DATE", currentdate);
            editor1.commit();
        }
    }

    public void stop(View v) {
        isRunning = false;
        startButtonPressed = false;
        if (steps < 1000)
            Toast.makeText(this, "Congrats!! You Have Run " + steps + " steps", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(this, "You are on Fire Today!!", Toast.LENGTH_LONG).show();
        }
        txt3.setText("Average calories burned:: " + (((float) steps / 1000) * 100) + " kcal");
    }

    public void reset(View v) {
        SharedPreferences saves = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        long stepsRunToday = saves.getLong("STEPS_RUN_TODAY", 0);
        long stepsRunThisMonth = saves.getLong("STEPS_RUN_THIS_MONTH", 0);
        long highestStepsRunInOneDay = saves.getLong("HIGHEST_STEPS_RUN_IN_ONE_DAY", 0);

        SharedPreferences.Editor editor = saves.edit();
        editor.putLong("STEPS_RUN_TODAY", steps + stepsRunToday);
        editor.putLong("STEPS_RUN_THIS_MONTH", steps + stepsRunThisMonth);
        if ((steps + stepsRunToday) > highestStepsRunInOneDay) {
            editor.putLong("HIGHEST_STEPS_RUN_IN_ONE_DAY", steps + stepsRunToday);
        }
        editor.commit();

        steps = 0;
        txt2.setText("" + steps);
        isRunning = true;
        startButtonPressed = false;
        txt3.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (startButtonPressed) isRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (startButtonPressed) isRunning = true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (isRunning && startButtonPressed && sensorEvent != null) {
            float x_acceleration = sensorEvent.values[0];
            float y_acceleration = sensorEvent.values[1];
            float z_acceleration = sensorEvent.values[2];
            double Magnitude = Math.sqrt(x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration);
            double MagnitudeDelta = Magnitude - MagnitudePrevious;
            MagnitudePrevious = Magnitude;
            if (sensorEvent.values[0] > 6) {
                steps++;
            }
            txt2.setText("" + steps);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences saves = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        long stepsRunToday = saves.getLong("STEPS_RUN_TODAY", 0);
        long stepsRunThisMonth = saves.getLong("STEPS_RUN_THIS_MONTH", 0);
        long highestStepsRunInOneDay = saves.getLong("HIGHEST_STEPS_RUN_IN_ONE_DAY", 0);

        SharedPreferences.Editor editor = saves.edit();
        editor.putLong("STEPS_RUN_TODAY", steps + stepsRunToday);
        editor.putLong("STEPS_RUN_THIS_MONTH", steps + stepsRunThisMonth);
        if ((steps + stepsRunToday) > highestStepsRunInOneDay) {
            editor.putLong("HIGHEST_STEPS_RUN_IN_ONE_DAY", steps + stepsRunToday);
        }
        editor.commit();
        sensormanager.unregisterListener(this);
    }

    public void start(View view) {
        isRunning = true;
        startButtonPressed = true;
    }

    public void track_record(View view) {
        SharedPreferences saves = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        long stepsRunToday = saves.getLong("STEPS_RUN_TODAY", 0);
        long stepsRunThisMonth = saves.getLong("STEPS_RUN_THIS_MONTH", 0);
        long highestStepsRunInOneDay = saves.getLong("HIGHEST_STEPS_RUN_IN_ONE_DAY", 0);

        SharedPreferences.Editor editor = saves.edit();
        editor.putLong("STEPS_RUN_TODAY", steps + stepsRunToday);
        editor.putLong("STEPS_RUN_THIS_MONTH", steps + stepsRunThisMonth);
        if ((steps + stepsRunToday) > highestStepsRunInOneDay) {
            editor.putLong("HIGHEST_STEPS_RUN_IN_ONE_DAY", steps + stepsRunToday);
        }
        editor.commit();

        Intent distanceRun = new Intent(StepCounterActivity.this, DistanceRun.class);
        startActivity(distanceRun);
    }
}