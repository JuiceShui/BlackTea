package com.yeeyuntech.framework.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yeeyuntech.framework.R;

/**
 * Created by adu on 2017/6/7.
 */

public class CircleWaveView extends View {

    private final int CIRCLE_NUM = 1;

    private float mCenterRadius;
    private boolean mCenterFillEnabled;

    private float mMaxRadius;
    private float mMaxOffset;

    private float[] mCircleOffsets;

    private float mCenterX;
    private float mCenterY;

    private Paint mPaint;

    public CircleWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleWaveView);
        mCenterFillEnabled = a.getBoolean(R.styleable.CircleWaveView_centerFill, false);
        int width = a.getDimensionPixelSize(R.styleable.CircleWaveView_centerWidth, 0);
        mCenterRadius = width / 2;
        a.recycle();

        mPaint = new Paint();
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        mCircleOffsets = new float[CIRCLE_NUM + 1];
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMaxRadius == 0) {
            mMaxRadius = getWidth() / 2;
            mCenterX = getWidth() / 2;
            mCenterY = getHeight() / 2;
            mMaxOffset = mMaxRadius - mCenterRadius;
            float offset = mMaxOffset / (CIRCLE_NUM + 1);
            for (int i = 0; i < mCircleOffsets.length; i++) {
                mCircleOffsets[i] = offset * i;
            }
        }

        mPaint.setAlpha(255);
        if (mCenterFillEnabled) {
            mPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
        }
        // 画中心
        canvas.drawCircle(mCenterX, mCenterY, mCenterRadius, mPaint);
        // 画波纹
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0, len = mCircleOffsets.length; i < len; i++) {
            mPaint.setAlpha((int) (255 * (1 - mCircleOffsets[i] / mMaxOffset)));
            canvas.drawCircle(mCenterX, mCenterY, mCenterRadius + mCircleOffsets[i], mPaint);
            mCircleOffsets[i] = mCircleOffsets[i] + 1;
            mCircleOffsets[i] = mCircleOffsets[i] % mMaxOffset;
        }
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
            start();
        }
    };

    private void start() {
        postDelayed(runnable, 50);
    }

    private void stop() {
        removeCallbacks(runnable);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }
}
