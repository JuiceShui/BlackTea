/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.ui;

import com.shui.blacktea.App;
import com.shui.blacktea.data.RetrofitHelper;
import com.yeeyuntech.framework.ui.IYYLoadView;
import com.yeeyuntech.framework.ui.YYRxPresenter;


/**
 * Description:
 * Created by lsy on 2017/7/7 下午4:13.
 */


public abstract class BaseRxPresenter<T extends IYYLoadView> extends YYRxPresenter<T> {

    protected final String TAG = getClass().getSimpleName();
    protected App mApp;
    protected final String KEY = "f8d6bf3d8a2e5daa7fd6eaf1cfbe5439";
    protected String mNum = "20";
    protected String mPage = "1";
    protected RetrofitHelper mRetrofitHelper;

    public BaseRxPresenter(App mApp, RetrofitHelper mRetrofitHelper) {
        super();
        this.mApp = mApp;
        this.mRetrofitHelper = mRetrofitHelper;
    }
}
