/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yeeyuntech.framework.utils.DisplayUtils;

/**
 * Description:
 * Created by lsy on 2017/7/14 下午8:44.
 */
public class ViewPagerIndicator extends View {

    private Paint mPaint;
    private Context mContext;
    private int amount = 3;
    private int selectIndex = 0;
    private int normalColor;
    private int selectColor;
    private float indicatorWidth;
    private float indicatorPadding;

    public ViewPagerIndicator(Context context) {
        super(context);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(DisplayUtils.dip2px(context, 2));
        indicatorWidth = DisplayUtils.dip2px(context, 10);
        indicatorPadding = DisplayUtils.dip2px(context, 4);
        normalColor = Color.WHITE;
        selectColor = Color.RED;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width = getWidth() - DisplayUtils.dip2px(mContext, 16);
        float height = getHeight();
        for (int i = 0; i < amount; i++) {
            if (amount - i - 1 == selectIndex) {
                mPaint.setColor(selectColor);
            } else {
                mPaint.setColor(normalColor);
            }
            canvas.drawLine(width - (indicatorWidth * (i + 1) + indicatorPadding * i), height / 2, width - (indicatorWidth + indicatorPadding) * (i), height / 2, mPaint);
        }
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        // 重绘
        invalidate();
    }
}
