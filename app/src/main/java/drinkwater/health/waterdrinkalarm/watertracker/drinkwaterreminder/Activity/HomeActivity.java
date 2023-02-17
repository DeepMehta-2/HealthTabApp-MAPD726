package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppConstants;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.NotificationHelper;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.ReceiverAndService.AlarmUtill;

@SuppressLint({"NewApi"})
public class HomeActivity extends AppCompatActivity implements OnClickListener {
    public static boolean adfromrecive = false;
    public static Context context = null;
    public static boolean defaultcup = false;
    public static NotificationHelper notificationHelper;
    public static TabLayout tabLayout;
    String TAG = "HOMEACTIFVTy";
    public boolean aBoolean;
    TabPagerAdapter adapter;
    ImageView notifiy;
    private Toolbar toolbar;
    /* access modifiers changed from: private */
    public ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_drop,
            R.drawable.ic_history,
            R.drawable.ic_settings
    };
    private Dialog dialog;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_home);
        context = this;
        this.notifiy = (ImageView) findViewById(R.id.cuppp);
        this.notifiy.setOnClickListener(this);
        this.aBoolean = getIntent().getBooleanExtra("FromLock", false);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(this.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(this.viewPager);
        setupTabIcons();
        if (AppPref.getToggle(this)) {
            new NotificationHelper(this).getnotification(this);
        }
        AlarmUtill.callReminderOnHome(this);
        exitDialog();
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        this.adapter = new TabPagerAdapter(getSupportFragmentManager());
        this.adapter.addFragment(new Homefragment(), null);
        this.adapter.addFragment(new Historyfragment(), null);
        this.adapter.addFragment(new Settingfragment(), null);
        viewPager.setAdapter(this.adapter);
    }

    public void onClick(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.reminderautodialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Button button = (Button) dialog.findViewById(R.id.buttonokauto);
        Button button2 = (Button) dialog.findViewById(R.id.buttoncancelauto);
        final RadioButton radioButton = (RadioButton) dialog.findViewById(R.id.turnoff);
        final RadioButton radioButton2 = (RadioButton) dialog.findViewById(R.id.turnmute);
        final RadioButton radioButton3 = (RadioButton) dialog.findViewById(R.id.auto);
        ((RadioGroup) dialog.findViewById(R.id.redauto)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioButton3.isChecked()) {
                    radioButton3.setTextColor(HomeActivity.this.getResources().getColor(R.color.blue));
                    radioButton2.setTextColor(HomeActivity.this.getResources().getColor(R.color.noti));
                    radioButton.setTextColor(HomeActivity.this.getResources().getColor(R.color.noti));
                }
                if (radioButton2.isChecked()) {
                    radioButton3.setTextColor(HomeActivity.this.getResources().getColor(R.color.noti));
                    radioButton2.setTextColor(HomeActivity.this.getResources().getColor(R.color.blue));
                    radioButton.setTextColor(HomeActivity.this.getResources().getColor(R.color.noti));
                }
                if (radioButton.isChecked()) {
                    radioButton3.setTextColor(HomeActivity.this.getResources().getColor(R.color.noti));
                    radioButton2.setTextColor(HomeActivity.this.getResources().getColor(R.color.noti));
                    radioButton.setTextColor(HomeActivity.this.getResources().getColor(R.color.blue));
                }
            }
        });
        switch (AppPref.getAuto(this)) {
            case 0:
                radioButton3.setChecked(true);
                break;
            case 1:
                radioButton2.setChecked(true);
                break;
            case 2:
                radioButton.setChecked(true);
                break;
            default:
                radioButton3.setChecked(true);
                break;
        }
        final Dialog dialog2 = dialog;
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (radioButton3.isChecked()) {
                    AppPref.setAuto(HomeActivity.this.getApplicationContext(), 0);
                    HomeActivity.this.getRemindersound();
                } else if (radioButton2.isChecked()) {
                    AppPref.setAuto(HomeActivity.this.getApplicationContext(), 1);
                    HomeActivity.this.getRemindersound();
                } else if (radioButton.isChecked()) {
                    AppPref.setAuto(HomeActivity.this.getApplicationContext(), 2);
                    HomeActivity.this.getRemindersound();
                }
                if (Settingfragment.textreminderauto != null) {
                    Settingfragment.textreminderauto.setText(HomeActivity.this.getRemindersound());
                }
                dialog2.dismiss();
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        if (intent.getBooleanExtra("FromLock", false)) {
            cancelNotification(this, 101);
            this.viewPager.setCurrentItem(0);
            if (this.adapter.getItem(0) instanceof Homefragment) {
                ((Homefragment) this.adapter.getItem(0)).drinkWater();
            }
        }
        super.onNewIntent(intent);
    }

    static void cancelNotification(Context context, int i) {
        ((NotificationManager) context.getSystemService("notification")).cancel(i);
    }

    /* access modifiers changed from: private */
    public String getRemindersound() {
        switch (AppPref.getAuto(getApplicationContext())) {
            case 0:
                return "Auto";
            case 1:
                return "Mute";
            case 2:
                return "Turn Off";
            default:
                return "Auto";
        }
    }

    public void onBackPressed() {
        dialog.show();
    }


    private void exitDialog() {
        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dailog_exit);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    return false;
                }
                return true;
            }
        });

        FrameLayout native_ad_container = (FrameLayout) dialog.findViewById(R.id.native_ad_container);
        TextView send_feedback = (TextView) dialog.findViewById(R.id.send_feedback);
        send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri1 = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                Intent inrate = new Intent(Intent.ACTION_VIEW, uri1);
                try {
                    startActivity(inrate);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(HomeActivity.this, "You don't have Google Play installed", Toast.LENGTH_LONG).show();
                }
            }
        });

        TextView tv_yes = (TextView) dialog.findViewById(R.id.tv_yes);
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        TextView tv_no = (TextView) dialog.findViewById(R.id.tv_no);
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}
