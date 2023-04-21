package com.centennial.healthtab.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.centennial.healthtab.R;

public class DistanceRun extends AppCompatActivity {

    TextView txt2,txt3,txt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_run);

        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        SharedPreferences saves = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        long stepsRunToday = saves.getLong("STEPS_RUN_TODAY", 0);
        long stepsRunThisMonth = saves.getLong("STEPS_RUN_THIS_MONTH", 0);
        long highestStepsRunInOneDay = saves.getLong("HIGHEST_STEPS_RUN_IN_ONE_DAY", 0);

        txt2.setText("HIGHEST: " + highestStepsRunInOneDay);
        txt3.setText("TODAY: " + stepsRunToday);
        txt4.setText("THIS MONTH: " + stepsRunThisMonth);
    }

    public void resetScores (View view) {
        SharedPreferences saves = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = saves.edit();

        editor.putLong("STEPS_RUN_TODAY", 0);
        editor.putLong("STEPS_RUN_THIS_MONTH", 0);
        editor.putLong("HIGHEST_STEPS_RUN_IN_ONE_DAY", 0);
        editor.commit();

        long stepsRunToday = saves.getLong("STEPS_RUN_TODAY", 0);
        long stepsRunThisMonth = saves.getLong("STEPS_RUN_THIS_MONTH", 0);
        long highestStepsRunInOneDay = saves.getLong("HIGHEST_STEPS_RUN_IN_ONE_DAY", 0);

        txt2.setText("HIGHEST: " + highestStepsRunInOneDay);
        txt3.setText("TODAY: " + stepsRunToday);
        txt4.setText("THIS MONTH: " + stepsRunThisMonth);

    }
}
