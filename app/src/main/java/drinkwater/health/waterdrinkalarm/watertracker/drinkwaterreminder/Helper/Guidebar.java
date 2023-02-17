package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;

public class Guidebar extends RelativeLayout {
    View view1;
    View view2;
    TextView txt1;
    TextView txt2;
    TextView txt3;

    public Guidebar(Context context) {
        super(context);
        Guidebar(context);
    }

    public Guidebar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Guidebar(context);
    }

    public Guidebar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Guidebar(context);
    }

    private void Guidebar(Context context) {
        LayoutInflater.from(context).inflate(R.layout.activity_guidebar, this, true);
        this.txt1 = (TextView) findViewById(R.id.textView);
        this.txt2 = (TextView) findViewById(R.id.textView2);
        this.txt3 = (TextView) findViewById(R.id.textView3);
        this.view1 = (View) findViewById(R.id.view1);
        this.view2 = (View) findViewById(R.id.view2);
    }

    @SuppressLint({"WrongConstant"})
    public void setState(int i) {
        switch (i) {
            case 2:
                this.txt2.setVisibility(0);
                this.view1.setBackgroundResource(R.drawable.view_fill);
                this.txt2.setBackgroundResource(R.drawable.b3);
                this.txt2.setTextColor(getResources().getColor(R.color.white));
                return;
            case 3:
                this.view1.setBackgroundResource(R.drawable.view_fill);
                this.view2.setBackgroundResource(R.drawable.view_fill);
                this.txt3.setBackgroundResource(R.drawable.b3);
                this.txt2.setBackgroundResource(R.drawable.b3);
                this.txt2.setTextColor(getResources().getColor(R.color.white));
                this.txt3.setTextColor(getResources().getColor(R.color.white));
                return;
            default:
                return;
        }
    }
}
