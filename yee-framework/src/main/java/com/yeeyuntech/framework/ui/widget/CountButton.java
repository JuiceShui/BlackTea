package com.yeeyuntech.framework.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;

import com.yeeyuntech.framework.R;

public class CountButton extends UITextView {
    private Context context;

    // 倒计时时间60s
    private final int DEFAULT_DURATION = 60;

    private int duration = DEFAULT_DURATION;
    private int time = 0;
    private boolean isCounting = false;

    // 倒计时
    private Runnable runnable = null;

    public CountButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountButton);
        duration = a.getInt(R.styleable.CountButton_count_duration, 0);
        a.recycle();
    }

    //初始化按钮状态
    private void init() {
        setGravity(Gravity.CENTER);
        runnable = new Runnable() {
            @Override
            public void run() {
                count();
            }
        };
    }

    //重置button状态,可点击
    private void reset() {
        setText("重新获取");
        setEnabled(true);
        isCounting = false;
        time = 0;
    }

    //定时更新TextView
    private void count() {
        setText(String.format("%ds", time));
        time = time - 1;
        if (time <= 0) {
            stop();
        } else {
            postDelayed(runnable, 1000);
        }
    }

    //获取中,不可点击
    public void setGetting() {
        setText("获取中");
        setEnabled(false);
    }

    //开始计时
    public void start() {
        // 清空消息队列，重新开始
        removeCallbacks(runnable);
        time = duration;
        isCounting = true;
        setEnabled(false);
        count();
    }


    //停止计时
    public void stop() {
        if (isCounting) {
            removeCallbacks(runnable);
        }
        reset();
    }
}