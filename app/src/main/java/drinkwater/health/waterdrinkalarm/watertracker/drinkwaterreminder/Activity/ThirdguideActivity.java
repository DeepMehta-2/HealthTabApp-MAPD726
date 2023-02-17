package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.Guidebar;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;

public class ThirdguideActivity extends AppCompatActivity implements OnClickListener {
    Button btnstart;
    Calendar calendar;
    Calendar calendaredtawake;
    int cuHour;
    int cuMin;
    int currentHour;
    int currentMinute;
    DatabaseHelper db;
    EditText edtawake;
    EditText edtsleep;
    private Guidebar guidebar;
    Point p;
    TimePickerDialog timePickerDialog;
    TimePickerDialog timePickertawake;
    boolean tutorialFromNav = false;
    private RelativeLayout relsleep, relawake;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_thirdguide);
        initAdmobFullAd(this);
        loadAdmobAd();
        this.guidebar = (Guidebar) findViewById(R.id.guidebar);
        this.guidebar.setState(3);
        this.btnstart = (Button) findViewById(R.id.buttonstart);
        this.edtawake = (EditText) findViewById(R.id.edittexttimeawake);
        this.edtsleep = (EditText) findViewById(R.id.edittexttimesleep);
        this.relawake = (RelativeLayout) findViewById(R.id.relawake);
        this.relsleep = (RelativeLayout) findViewById(R.id.relsleep);
        this.db = new DatabaseHelper(getApplicationContext());
        this.btnstart.setOnClickListener(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            this.tutorialFromNav = bundle.getBoolean("TutorialFromNav", false);
        }
        ActivityManager.getInstance().setCurrentActivity(this);
        if (this.tutorialFromNav || !AppPref.isFirstLaunch(this)) {
            EditText editText = this.edtawake;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(AppPref.getAwaketime(this));
            stringBuilder.append("");
            editText.setText(stringBuilder.toString());
            editText = this.edtsleep;
            stringBuilder = new StringBuilder();
            stringBuilder.append(AppPref.getSleeptime(this));
            stringBuilder.append("");
            editText.setText(stringBuilder.toString());
            this.relawake.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ThirdguideActivity.this.calendaredtawake = Calendar.getInstance();
                    String[] split = AppPref.getAwaketime(ThirdguideActivity.this.getApplicationContext()).split(":");
                    int parseInt = Integer.parseInt(split[0]);
                    int parseInt2 = Integer.parseInt(split[1]);
                    ThirdguideActivity.this.cuHour = parseInt;
                    ThirdguideActivity.this.cuMin = parseInt2;
                    ThirdguideActivity.this.timePickertawake = new TimePickerDialog(ThirdguideActivity.this, new OnTimeSetListener() {
                        public void onTimeSet(TimePicker timePicker, int i, int i2) {
                            ThirdguideActivity.this.edtawake.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
                            ThirdguideActivity.this.cuHour = i;
                            ThirdguideActivity.this.cuMin = i2;
                        }
                    }, ThirdguideActivity.this.cuHour, ThirdguideActivity.this.cuMin, true);
                    ThirdguideActivity.this.timePickertawake.show();
                }
            });
            this.relsleep.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ThirdguideActivity.this.calendar = Calendar.getInstance();
                    String[] split = AppPref.getSleeptime(ThirdguideActivity.this.getApplicationContext()).split(":");
                    int parseInt = Integer.parseInt(split[0]);
                    int parseInt2 = Integer.parseInt(split[1]);
                    ThirdguideActivity.this.currentHour = parseInt;
                    ThirdguideActivity.this.currentMinute = parseInt2;
                    ThirdguideActivity.this.timePickerDialog = new TimePickerDialog(ThirdguideActivity.this, new OnTimeSetListener() {
                        public void onTimeSet(TimePicker timePicker, int i, int i2) {
                            ThirdguideActivity.this.edtsleep.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
                            ThirdguideActivity.this.currentHour = i;
                            ThirdguideActivity.this.currentMinute = i2;
                        }
                    }, ThirdguideActivity.this.currentHour, ThirdguideActivity.this.currentMinute, true);
                    try {
                        ThirdguideActivity.this.timePickerDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return;
        }
        ActivityManager.getInstance().openNewActivity(HomeActivity.class, false);
    }

    public void onClick(View view) {
        try {
            if (view == this.btnstart) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(AppPref.getWeight(this));
                stringBuilder.append("");
                int parseInt = Integer.parseInt(stringBuilder.toString());
                String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                int i = parseInt * 40;
                if (this.edtawake.getText().toString().length() <= 0) {
                    Toast.makeText(this, "Enter Wake up time", 0).show();
                } else if (this.edtsleep.getText().toString().length() <= 0) {
                    Toast.makeText(this, "Enter Sleep up time", 0).show();
                } else {
                    boolean call_history = this.db.call_history(format, i, parseInt);
                    HomeActivity.defaultcup = true;
                    AppPref.setFirstLaunch(this, true);
                    if (call_history) {
                        AppPref.setAwaketime(this, this.edtawake.getText().toString());
                        AppPref.setSleeptime(this, this.edtsleep.getText().toString());
                        ActivityManager.getInstance().openNewActivity(HomeActivity.class, false);
                        finish();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
