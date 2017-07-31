package com.yeeyuntech.framework.ui.widget.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.yeeyuntech.framework.R;


/**
 * Created by Adu on 2016/11/6.
 */

public class UINoPreloadViewPager extends NoPreloadViewPager {

    private boolean isCanScroll;

    private Context mContext;

    public UINoPreloadViewPager(Context context) {
        super(context);
    }

    public UINoPreloadViewPager(Context context, AttributeSet attrs) {
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
