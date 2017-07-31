package com.yeeyuntech.framework.ui.widget.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.yeeyuntech.framework.R;

/**
 * Created by yaojie on 16/9/26.
 */

public class UIViewPager extends ViewPager {

    private boolean isCanScroll;

    private Context mContext;

    public UIViewPager(Context context) {
        super(context);
    }

    public UIViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UIViewPager);
        initAttrs(typedArray);
    }

    private void initAttrs(TypedArray typedArray) {
        isCanScroll = typedArray.getBoolean(R.styleable.UIViewPager_canScroll, true);
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            try {
                //ViewPager和PhotoView滑动冲突
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            try {
                //ViewPager和PhotoView滑动冲突
                return super.onTouchEvent(ev);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
            return false;
        } else {
            return false;
        }
    }
}
