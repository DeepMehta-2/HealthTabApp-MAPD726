package com.centennial.healthtab.utils;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import androidx.core.view.ViewCompat;

import com.centennial.healthtab.R;

public class CircularProgressBar extends ProgressBar {
    private static final int STROKE_WIDTH = 20;
    private static final String TAG = "Pocket_CircularProgressBar";
    private final Paint mBackgroundColorPaint = new Paint();
    private final RectF mCircleBounds = new RectF();
    private boolean mHasShadow = true;
    private final Paint mProgressColorPaint = new Paint();
    private int mShadowColor = ViewCompat.MEASURED_STATE_MASK;
    private int mStrokeWidth = 20;
    private String mSubTitle = "";
    private final Paint mSubtitlePaint = new Paint();
    private String mTitle = "";
    private final Paint mTitlePaint = new Paint();

    public interface ProgressAnimationListener {
        void onAnimationFinish();

        void onAnimationProgress(int i);

        void onAnimationStart();
    }

    public CircularProgressBar(Context context) {
        super(context);
        init(null, 0);
    }

    public CircularProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0);
    }

    public CircularProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }

    public void init(AttributeSet attributeSet, int i) {
        setLayerType(1, null);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.CircularProgressBar, i, 0);
        Resources resources = getResources();
        this.mHasShadow = obtainStyledAttributes.getBoolean(R.styleable.CircularProgressBar_cpb_hasShadow, true);
        String string = obtainStyledAttributes.getString(R.styleable.CircularProgressBar_cpb_progressColor);
        if (string == null) {
            this.mProgressColorPaint.setColor(resources.getColor(R.color.circular_progress_default_progress));
        } else {
            this.mProgressColorPaint.setColor(Color.parseColor(string));
        }
        string = obtainStyledAttributes.getString(R.styleable.CircularProgressBar_cpb_backgroundColor);
        if (string == null) {
            this.mBackgroundColorPaint.setColor(resources.getColor(R.color.circular_progress_default_background));
        } else {
            this.mBackgroundColorPaint.setColor(Color.parseColor(string));
        }
        string = obtainStyledAttributes.getString(R.styleable.CircularProgressBar_cpb_titleColor);
        if (string == null) {
            this.mTitlePaint.setColor(resources.getColor(R.color.circular_progress_default_title));
        } else {
            this.mTitlePaint.setColor(Color.parseColor(string));
        }
        string = obtainStyledAttributes.getString(R.styleable.CircularProgressBar_cpb_subtitleColor);
        if (string == null) {
            this.mSubtitlePaint.setColor(resources.getColor(R.color.circular_progress_default_subtitle));
        } else {
            this.mSubtitlePaint.setColor(Color.parseColor(string));
        }
        String string2 = obtainStyledAttributes.getString(R.styleable.CircularProgressBar_cpb_title);
        if (string2 != null) {
            this.mTitle = string2;
        }
        string2 = obtainStyledAttributes.getString(R.styleable.CircularProgressBar_cpb_subtitle);
        if (string2 != null) {
            this.mSubTitle = string2;
        }
        this.mStrokeWidth = obtainStyledAttributes.getInt(R.styleable.CircularProgressBar_cpb_strokeWidth, 20);
        obtainStyledAttributes.recycle();
        this.mProgressColorPaint.setAntiAlias(true);
        this.mProgressColorPaint.setStyle(Style.STROKE);
        this.mProgressColorPaint.setStrokeWidth((float) this.mStrokeWidth);
        this.mBackgroundColorPaint.setAntiAlias(true);
        this.mBackgroundColorPaint.setStyle(Style.STROKE);
        this.mBackgroundColorPaint.setStrokeWidth((float) this.mStrokeWidth);
        this.mTitlePaint.setTextSize(60.0f);
        this.mTitlePaint.setStyle(Style.FILL);
        this.mTitlePaint.setAntiAlias(true);
        this.mTitlePaint.setTypeface(Typeface.create("lato_mediun", Typeface.NORMAL));
        this.mTitlePaint.setShadowLayer(0.1f, 0.0f, 1.0f, -7829368);
        this.mSubtitlePaint.setTextSize(20.0f);
        this.mSubtitlePaint.setStyle(Style.FILL);
        this.mSubtitlePaint.setAntiAlias(true);
        this.mSubtitlePaint.setTypeface(Typeface.create("lato_mediun", Typeface.BOLD));
    }

    protected synchronized void onDraw(Canvas canvas) {
        canvas.drawArc(this.mCircleBounds, 0.0f, 360.0f, false, this.mBackgroundColorPaint);
        float progress = getMax() > 0 ? (((float) getProgress()) / ((float) getMax())) * 360.0f : 0.0f;
        if (this.mHasShadow) {
            this.mProgressColorPaint.setShadowLayer(3.0f, 0.0f, 1.0f, this.mShadowColor);
        }
        canvas.drawArc(this.mCircleBounds, 270.0f, progress, false, this.mProgressColorPaint);
        if (!TextUtils.isEmpty(this.mTitle)) {
            int measuredWidth = (int) (((float) (getMeasuredWidth() / 2)) - (this.mTitlePaint.measureText(this.mTitle) / 2.0f));
            int measuredHeight = getMeasuredHeight() / 2;
            progress = Math.abs(this.mTitlePaint.descent() + this.mTitlePaint.ascent());
            if (TextUtils.isEmpty(this.mSubTitle)) {
                measuredHeight = (int) (((float) measuredHeight) + (progress / 2.0f));
            }
            float f = (float) measuredHeight;
            canvas.drawText(this.mTitle, (float) measuredWidth, f, this.mTitlePaint);
            canvas.drawText(this.mSubTitle, (float) ((int) (((float) (getMeasuredWidth() / 2)) - (this.mSubtitlePaint.measureText(this.mSubTitle) / 2.0f))), (float) ((int) (f + progress)), this.mSubtitlePaint);
        }
        super.onDraw(canvas);
    }

    protected void onMeasure(int i, int i2) {
        i = Math.min(getDefaultSize(getSuggestedMinimumWidth(), i), getDefaultSize(getSuggestedMinimumHeight(), i2));
        i2 = i + 40;
        setMeasuredDimension(i2, i2);
        float f = (float) (i + 20);
        this.mCircleBounds.set(20.0f, 20.0f, f, f);
    }

    public synchronized void setProgress(int i) {
        super.setProgress(i);
        invalidate();
    }

    public void animateProgressTo(int i, final int i2, final ProgressAnimationListener progressAnimationListener) {
        if (i != 0) {
            setProgress(i);
        }
        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "animateProgress", (float) i, (float) i2);
        ofFloat.setDuration(1500);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.addListener(new AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                CircularProgressBar.this.setProgress(i2);
                if (progressAnimationListener != null) {
                    progressAnimationListener.onAnimationFinish();
                }
            }

            public void onAnimationStart(Animator animator) {
                if (progressAnimationListener != null) {
                    progressAnimationListener.onAnimationStart();
                }
            }
        });
        ofFloat.addUpdateListener(new AnimatorUpdateListener() {
            @SuppressLint("LongLogTag")
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Float) valueAnimator.getAnimatedValue()).intValue();
                if (intValue != CircularProgressBar.this.getProgress()) {
                    String str = CircularProgressBar.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(intValue);
                    stringBuilder.append("");
                    CircularProgressBar.this.setProgress(intValue);
                    if (progressAnimationListener != null) {
                        progressAnimationListener.onAnimationProgress(intValue);
                    }
                }
            }
        });
        ofFloat.start();
    }

    public synchronized void setTitle(String str) {
        this.mTitle = str;
        invalidate();
    }

    public synchronized void setSubTitle(String str) {
        this.mSubTitle = str;
        invalidate();
    }

    public synchronized void setSubTitleColor(int i) {
        this.mSubtitlePaint.setColor(i);
        invalidate();
    }

    public synchronized void setTitleColor(int i) {
        this.mTitlePaint.setColor(i);
        invalidate();
    }

    public synchronized void setHasShadow(boolean z) {
        this.mHasShadow = z;
        invalidate();
    }

    public synchronized void setShadow(int i) {
        this.mShadowColor = i;
        invalidate();
    }

    public String getTitle() {
        return this.mTitle;
    }

    public boolean getHasShadow() {
        return this.mHasShadow;
    }
}
