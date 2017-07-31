package com.yeeyuntech.framework.ui.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Description: 带有滑动监听的scrollView，同时NestedScrollView可以解决滑动冲突
 * Created by lsy on 2017/3/25.
 * <p>
 * Description:添加注释 进行代码规范
 * modify by Juice_ on 2017/5/22 上午10:23
 */


public class MyScrollView extends NestedScrollView {

    private OnScrollerListener mListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener != null) {
            mListener.onScroller(l, t, oldl, oldt);
        }
    }

    public void setOnScrollerListener(OnScrollerListener listener) {
        this.mListener = listener;
    }

    public interface OnScrollerListener {
        void onScroller(int l, int t, int oldl, int oldt);
    }
}
