/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.ui;

import com.shui.blacktea.App;
import com.shui.blacktea.data.retrofit.RetrofitHelper;
import com.yeeyuntech.framework.ui.IYYLoadView;
import com.yeeyuntech.framework.ui.YYRxPresenter;


/**
 * Description:
 * Created by lsy on 2017/7/7 下午4:13.
 */


public abstract class BaseRxPresenter<T extends IYYLoadView> extends YYRxPresenter<T> {

    protected final String TAG = getClass().getSimpleName();
    protected App mApp;
    protected RetrofitHelper mRetrofitHelper;

    public BaseRxPresenter(App mApp, RetrofitHelper mRetrofitHelper) {
        super();
        this.mApp = mApp;
        this.mRetrofitHelper = mRetrofitHelper;
    }
}
