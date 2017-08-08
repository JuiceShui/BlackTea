/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.ui;

import android.content.Context;

import com.shui.blacktea.App;
import com.shui.blacktea.inject.component.DaggerFragmentComponent;
import com.shui.blacktea.inject.component.FragmentComponent;
import com.shui.blacktea.inject.module.FragmentModule;
import com.yeeyuntech.framework.ui.IYYPresenter;
import com.yeeyuntech.framework.ui.YYFragment;
import com.yeeyuntech.framework.utils.bus.AppBus;

import java.util.List;

/**
 * Description: 需要网络加载的loading在基类中添加
 * Created by lsy on 2017/7/7 下午4:07.
 */
public abstract class BaseFragment extends YYFragment {

    protected final String TAG = getClass().getSimpleName();

    protected App mApp;

    @Override
    public void onAttach(Context context) {
        mApp = App.getInstance();
        super.onAttach(context);
        AppBus.register(this);
    }

    @Override
    public void onDetach() {
        AppBus.unregister(this);
        super.onDetach();
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
    public void handleTokenException(Throwable throwable) {
        ((BaseActivity) mActivity).handleTokenException(throwable);
    }

    // 获取dagger 的实例
    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

}
