/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.widget.recycleAdapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Description: RecyclerView的装饰类
 * Created by JuiceShui on 2017/5/17.
 * To strive,to seek,to find,and not to give up
 * <p>
 * Description: 代码规范
 * Modify by Juice_ on 2017/5/22 上午10:43
 */

public class ItemSpaceDecoration extends RecyclerView.ItemDecoration {
    
    private int space;

    public ItemSpaceDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//		outRect.left = space;
//		outRect.right = space;
//		outRect.bottom = space;
//		if (parent.getChildAdapterPosition(view) == 0) {
        outRect.top = space;
//		}
    }
}
