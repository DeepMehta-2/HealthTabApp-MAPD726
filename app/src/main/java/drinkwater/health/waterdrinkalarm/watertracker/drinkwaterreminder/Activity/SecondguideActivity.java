package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.Guidebar;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;

public class SecondguideActivity extends AppCompatActivity implements OnClickListener {
    Button btnkg;
    Button btnlb;
    Button btnnext;
    private Guidebar guidebar;
    boolean tutorialFromNav = false;
    TextView txtskip;
    TextView txtweight;
    private float weight = 65.0f;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_secondguide);
        this.guidebar = (Guidebar) findViewById(R.id.guidebar);
        this.btnnext = (Button) findViewById(R.id.buttonext);
        this.txtskip = (TextView) findViewById(R.id.textviewskip);
        this.btnkg = (Button) findViewById(R.id.buttonkg);
        this.btnlb = (Button) findViewById(R.id.buttonlb);
        this.txtweight = (TextView) findViewById(R.id.textviewweight);
        this.btnkg.setOnClickListener(this);
        this.btnlb.setOnClickListener(this);
        this.txtskip.setOnClickListener(this);
        this.btnnext.setOnClickListener(this);
        bundle = getIntent().getExtras();
        this.guidebar.setState(2);
        if (bundle != null) {
            this.tutorialFromNav = bundle.getBoolean("TutorialFromNav", false);
        }
        ActivityManager.getInstance().setCurrentActivity(this);
        if (this.tutorialFromNav) {
            this.txtskip.setVisibility(8);
        } else if (AppPref.isFirstLaunch(this)) {
            ActivityManager.getInstance().openNewActivity(HomeActivity.class, false);
            return;
        }
        TextView textView = this.txtweight;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AppPref.getWeight(this));
        stringBuilder.append("");
        textView.setText(stringBuilder.toString());
        setBtnBack();
    }

    @SuppressLint({"NewApi"})
    private void setBtnBack() {
        Resources resources;
        int i;
        Button button = this.btnkg;
        if (AppPref.isKglb(this)) {
            resources = getResources();
            i = R.drawable.kgback;
        } else {
            resources = getResources();
            i = R.drawable.kgss;
        }
        button.setBackground(resources.getDrawable(i));
        this.btnkg.setTextColor(AppPref.isKglb(this) ? getResources().getColor(R.color.white) : getResources().getColor(R.color.darkblue));
        this.btnlb.setTextColor(!AppPref.isKglb(this) ? getResources().getColor(R.color.white) : getResources().getColor(R.color.darkblue));
        button = this.btnlb;
        if (AppPref.isKglb(this)) {
            resources = getResources();
            i = R.drawable.lbss;
        } else {
            resources = getResources();
            i = R.drawable.lbback;
        }
        button.setBackground(resources.getDrawable(i));
    }

    public void onClick(View view) {
        try {
            if (view == this.txtskip) {
                HomeActivity.defaultcup = true;
                AppPref.setKglb(this, true);
                AppPref.setFirstLaunch(this, true);
                ActivityManager.getInstance().openNewActivity(HomeActivity.class, false);
            }
            if (view == this.btnnext) {
                if (this.txtweight.getText().length() < 0 || Integer.parseInt(this.txtweight.getText().toString()) > 0) {
                    int round = (int) Math.round(((double) Integer.parseInt(this.txtweight.getText().toString())) / 2.20462d);
                    if (Boolean.valueOf(AppPref.isKglb(this)).booleanValue()) {
                        AppPref.setWeight(this, Integer.parseInt(this.txtweight.getText().toString()));
                    } else {
                        AppPref.setWeight(this, round);
                    }
                    this.guidebar.setState(2);
                    startActivity(new Intent(getApplicationContext(), ThirdguideActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Enter your weight(Weight should not less than 10)", 0).show();
                    return;
                }
            }
            if (view == this.btnkg) {
                AppPref.setKglb(this, true);
                setBtnBack();
            }
            if (view == this.btnlb) {
                AppPref.setKglb(this, false);
                setBtnBack();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
