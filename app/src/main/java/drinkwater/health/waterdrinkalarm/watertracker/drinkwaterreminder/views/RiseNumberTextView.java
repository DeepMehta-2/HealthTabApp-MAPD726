package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.views;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

public class RiseNumberTextView extends AppCompatTextView implements AnimatorListener {
    private Boolean RUNNING = Boolean.valueOf(false);
    private int decimal = 2;
    private int duration = 0;
    private float endValue = 0.0f;
    private RiseListener mRiseListener = null;
    private Boolean riseInt = Boolean.valueOf(true);
    private float startValue = 0.0f;

    public interface RiseListener {
        void onRiseEndFinish();
    }

    public void onAnimationCancel(Animator animator) {
    }

    public void onAnimationRepeat(Animator animator) {
    }

    public void onAnimationStart(Animator animator) {
    }

    public RiseNumberTextView(Context context) {
        super(context);
    }

    public RiseNumberTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public RiseNumberTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void run() {
        ValueAnimator ofInt;
        this.RUNNING = Boolean.valueOf(true);
        if (this.riseInt.booleanValue()) {
            ofInt = ValueAnimator.ofInt(new int[]{(int) this.startValue, (int) this.endValue});
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RiseNumberTextView.this.setText(valueAnimator.getAnimatedValue().toString());
                }
            });
        } else {
            ofInt = ValueAnimator.ofFloat(new float[]{this.startValue, this.endValue});
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RiseNumberTextView.this.setText(RiseNumberTextView.this.FormatFloat(valueAnimator.getAnimatedValue().toString()));
                }
            });
        }
        ofInt.setDuration((long) this.duration);
        ofInt.addListener(this);
        ofInt.start();
    }

    public void onAnimationEnd(Animator animator) {
        this.RUNNING = Boolean.valueOf(false);
        if (this.mRiseListener != null) {
            this.mRiseListener.onRiseEndFinish();
        }
    }

    /* access modifiers changed from: private */
    public String FormatFloat(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        int lastIndexOf = str.lastIndexOf(46);
        int i = this.decimal;
        if (lastIndexOf == -1) {
            stringBuilder.append('.');
            lastIndexOf = str.length();
        } else {
            i = this.decimal - ((str.length() - lastIndexOf) - 1);
        }
        for (int i2 = 0; i2 < i; i2++) {
            stringBuilder.append('0');
        }
        return stringBuilder.substring(0, (this.decimal + lastIndexOf) + 1);
    }

    public RiseNumberTextView setStartValue(float f) {
        this.startValue = f;
        return this;
    }

    public RiseNumberTextView setEndValue(float f) {
        this.endValue = f;
        return this;
    }

    public RiseNumberTextView setRiseInterval(float f, float f2) {
        this.startValue = f;
        this.endValue = f2;
        return this;
    }

    public RiseNumberTextView setDuration(int i) {
        this.duration = i;
        return this;
    }

    public RiseNumberTextView runInt(boolean z) {
        if (!this.RUNNING.booleanValue()) {
            this.riseInt = Boolean.valueOf(z);
        }
        return this;
    }

    public RiseNumberTextView setDecimal(int i) {
        if (!this.RUNNING.booleanValue()) {
            this.decimal = i;
        }
        return this;
    }

    public void start() {
        if (!this.RUNNING.booleanValue()) {
            this.RUNNING = Boolean.valueOf(true);
            run();
        }
    }

    public void setOnRiseEndListener(RiseListener riseListener) {
        this.mRiseListener = riseListener;
    }
}
