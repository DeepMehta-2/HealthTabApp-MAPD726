package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.WaterIntake;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;

public class LockScreen extends AppCompatActivity {
    public static Fragment fragment;
    public static Fragment fragment1;
    static FragmentTransaction fragmentTransaction;
    public static MediaPlayer mySong;
    ArrayList<cupmaster> cupmasters;
    int currentcupsize;
    DatabaseHelper db;
    LinearLayout dismissActivity;
    LinearLayout drinkwater;
    private LayoutManager mLayoutManager;
    public List<WaterIntake> mWaterIntakeList;
    private int snoozeCounter;
    TextView time;
    public Vibrator vib;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(6815872);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_lock_screen);
        this.time = (TextView) findViewById(R.id.time);
        this.dismissActivity = (LinearLayout) findViewById(R.id.dismissActivity);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.drinkwater = (LinearLayout) findViewById(R.id.drinkwater);
        this.db = new DatabaseHelper(this);
        this.cupmasters = new ArrayList();
        this.time.setText(new SimpleDateFormat("HH:mm ").format(new Date()));
        this.mWaterIntakeList = this.db.getAllWaterIntake();
        Homefragment.adapter = new WaterIntakeAdapter(this.mWaterIntakeList, this, Homefragment.mRecyclerView);
        if (AppPref.getAuto(this) == 0) {
            mySong = MediaPlayer.create(this, R.raw.watersound);
            mySong.start();
            this.vib = (Vibrator) getSystemService("vibrator");
            this.vib.vibrate(500);
        }
        this.drinkwater.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LockScreen.this.startActivity(new Intent(LockScreen.this, HomeActivity.class).putExtra("FromLock", true));
                LockScreen.this.finish();
            }
        });
        this.dismissActivity.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LockScreen.this.finish();
            }
        });
    }
}
