package com.yeeyuntech.framework.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yeeyuntech.framework.R;
import com.yeeyuntech.framework.utils.LogUtils;

/**
 * Created by adu on 2017/6/7.
 */

public class UIProgressBar extends View {

    private long mDuration;
    private int mProgressColor = 0x00ffffff;

    private long mStartTime;
    private long mPastTime;


    private boolean mInverseEnabled;
    private Paint mPaint;

    private int mWidth, mHeight;

    public UIProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UIProgressBar);
        mInverseEnabled = a.getBoolean(R.styleable.UIProgressBar_inverse, false);
        mDuration = a.getInt(R.styleable.UIProgressBar_duration, 0);
        mProgressColor = a.getColor(R.styleable.UIProgressBar_progressColor, mProgressColor);
        a.recycle();

        mPaint = new Paint();
        mPaint.setColor(mProgressColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mWidth == 0) {
            mWidth = getWidth();
            mHeight = getHeight();
            mPaint.setStrokeWidth(mHeight);
        }

        if (mPastTime > 0) {
            if (mPastTime < mDuration) {
                if (mInverseEnabled) {
                    canvas.drawLine(0, mHeight / 2, mWidth - mWidth * mPastTime / mDuration, mHeight / 2, mPaint);
                } else {
                    canvas.drawLine(0, mHeight / 2, mWidth * mPastTime / mDuration, mHeight / 2, mPaint);
                }
            }
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mPastTime = SystemClock.uptimeMillis() - mStartTime;
            if (mPastTime > mDuration) {
                mPastTime = 0;
                invalidate();
                return;
            }
            invalidate();
            startProgress();
        }
    };

    private void startProgress() {
        postDelayed(runnable, 16);
    }

    public void start() {
        if (mPastTime != 0) {
            stop();
        }
        mStartTime = SystemClock.uptimeMillis();
        startProgress();
    }

    public void stop() {
        removeCallbacks(runnable);
        mPastTime = 0;
        invalidate();
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }
}
