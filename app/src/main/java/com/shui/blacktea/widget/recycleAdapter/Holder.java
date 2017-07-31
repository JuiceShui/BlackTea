/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.widget.recycleAdapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Description: ViewHolder的基类
 * Created by lsy on 2017/5/5.
 * <p>
 * Description: 修改规范
 * Modify by Juice_ on 2017/5/22 上午10:42
 */

public class Holder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private T mViewDataBinding;

    public Holder(T viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.mViewDataBinding = viewDataBinding;
    }

    public T getBinding() {
        return mViewDataBinding;
    }

}