/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.widget.recycleAdapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by adu on 2017/6/23.
 */

public class ItemPaddingDecoration extends RecyclerView.ItemDecoration {

    private int mLeftOffset = -1;
    private int mRightOffset = -1;
    private int mTopOffset = -1;
    private int mBottomOffset = -1;

    public ItemPaddingDecoration(int left, int top, int right, int bottom) {
        this.mLeftOffset = left;
        this.mTopOffset = top;
        this.mRightOffset = right;
        this.mBottomOffset = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mLeftOffset > 0) {
            outRect.left = mLeftOffset;
        }
        if (mTopOffset > 0) {
            outRect.top = mTopOffset;
        }
        if (mRightOffset > 0) {
            outRect.right = mRightOffset;
        }
        if (mBottomOffset > 0) {
            outRect.bottom = mBottomOffset;
        }
    }
}
