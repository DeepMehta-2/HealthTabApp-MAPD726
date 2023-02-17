package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.Guidebar;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;

public class FirstguideActivity extends AppCompatActivity implements OnClickListener {
    Button btnfemale;
    Button btnmale;
    Button btnnext;
    boolean gender = true;
    Guidebar guidebar;
    boolean tutorialFromNav = false;
    TextView txtskip;
    private RelativeLayout relmale,relfemale;

    /* access modifiers changed from: protected */
    @SuppressLint({"ResourceAsColor"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_firstguide);
        this.guidebar = (Guidebar) findViewById(R.id.guidebar);
        this.btnmale = (Button) findViewById(R.id.Male);
        this.relmale = (RelativeLayout) findViewById(R.id.relmale);
        this.relfemale = (RelativeLayout) findViewById(R.id.relfemale);
        this.btnfemale = (Button) findViewById(R.id.female);
        this.btnnext = (Button) findViewById(R.id.Next);
        this.guidebar.setState(1);
        this.btnnext.setOnClickListener(this);
        this.txtskip = (TextView) findViewById(R.id.Skip);
        this.txtskip.setOnClickListener(this);
        this.btnmale.setOnClickListener(this);
        this.btnfemale.setOnClickListener(this);
        this.relmale.setOnClickListener(this);
        this.relfemale.setOnClickListener(this);
        bundle = getIntent().getExtras();
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
        setBtnBack();
    }

    @SuppressLint({"NewApi"})
    private void setBtnBack() {
        this.btnmale.setBackground(AppPref.isMale(this) ? getResources().getDrawable(R.drawable.gradiante_gotit) : getResources().getDrawable(R.drawable.maleback));
        this.btnmale.setTextColor(AppPref.isMale(this) ? getResources().getColor(R.color.white) : getResources().getColor(R.color.darkblue));
        this.btnfemale.setTextColor(!AppPref.isMale(this) ? getResources().getColor(R.color.white) : getResources().getColor(R.color.darkblue));
        this.btnfemale.setBackground(!AppPref.isMale(this) ? getResources().getDrawable(R.drawable.gradiante_gotit) : getResources().getDrawable(R.drawable.maleback));
    }

    public void onClick(View view) {
        if (view == this.txtskip) {
            HomeActivity.defaultcup = true;
            AppPref.setFirstLaunch(this, true);
            ActivityManager.getInstance().openNewActivity(HomeActivity.class, false);
        }
        if (view == this.btnnext) {
            this.guidebar.setState(1);
            startActivity(new Intent(getApplicationContext(), SecondguideActivity.class));
            finish();
        }
        if (view == this.relmale) {
            AppPref.setMale(this, true);
            setBtnBack();
        }
        if (view == this.relfemale) {
            AppPref.setMale(this, false);
            setBtnBack();
        }
    }
}
