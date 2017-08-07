/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.shui.blacktea.App;
import com.shui.blacktea.inject.component.ActivityComponent;
import com.shui.blacktea.inject.component.DaggerActivityComponent;
import com.shui.blacktea.inject.module.ActivityModule;
import com.shui.blacktea.widget.loading.LoadingDialog;
import com.yeeyuntech.framework.ui.ILoadingIndicator;
import com.yeeyuntech.framework.ui.IYYPresenter;
import com.yeeyuntech.framework.ui.YYActivity;
import com.yeeyuntech.framework.utils.bus.AppBus;

import java.util.List;

/**
 * Description:需要网络加载的loading在基类中添加 实现YYLoadView中的方法
 * Created by lsy on 2017/7/7 下午3:39.
 */
public abstract class BaseActivity extends YYActivity {

    protected final String TAG = getClass().getSimpleName();

    // BaseApplication实例
    protected App mApp;
    protected LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApp = App.getInstance();
        mInflater = LayoutInflater.from(this);
        super.onCreate(savedInstanceState);
        AppBus.register(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!mApp.isAppVisible()) {
            // 手势密码逻辑处理
        }
    }

    @Override
    protected void onDestroy() {
        AppBus.unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initBinding() {

    }

    protected void setToolBar(Toolbar toolBar, String title) {
        setSupportActionBar(toolBar);
        toolBar.setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    @Override
    protected IYYPresenter getPresenter() {
        return null;
    }

    @Override
    protected List<IYYPresenter> getExtraPresenters() {
        return null;
    }

    @Override
    protected ILoadingIndicator getLoadingDialog() {
        return new LoadingDialog(this);
    }

    @Override
    public void handleTokenException(Throwable throwable) {
        // 处理登录异常 固定的异常处理  一般为token异常
       /* if (throwable instanceof TokenExpireException) {
            LoginActivity.expire(mActivity);
            mApp.finishAllActivities(LoginActivity.class);
        } else if (throwable instanceof TokenInvalidException) {
            LoginActivity.abnormal(mActivity);
            mApp.finishAllActivities(LoginActivity.class);
        } else if (throwable instanceof NoTokenException) {
            LoginActivity.launch(mActivity);
            mApp.finishAllActivities(LoginActivity.class);
        }*/
    }

    // 得到dagger activity 的实例
    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

}
