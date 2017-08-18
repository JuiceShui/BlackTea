/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.inject.module;

import android.app.Activity;
import android.databinding.DataBindingUtil;

import com.shui.blacktea.databinding.ActivityChattingBinding;
import com.shui.blacktea.databinding.ActivityHomeBinding;
import com.shui.blacktea.databinding.ActivityLoginBinding;
import com.shui.blacktea.databinding.ActivityNewsDetialBinding;
import com.shui.blacktea.databinding.ActivityNewsSelectCateBinding;
import com.shui.blacktea.databinding.ActivityOnlineMusicListBinding;
import com.shui.blacktea.databinding.ActivityRegisterBinding;
import com.shui.blacktea.databinding.ActivitySplashBinding;
import com.yeeyuntech.framework.inject.module.YYActivityModule;
import com.yeeyuntech.framework.inject.scope.ActivityScope;
import com.yeeyuntech.framework.ui.YYActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule extends YYActivityModule {

    public ActivityModule(Activity activity) {
        super(activity);
    }

    @Provides
    @ActivityScope
    ActivityHomeBinding provideActivityHomeBinding() {
        return DataBindingUtil.setContentView(mActivity, ((YYActivity) (mActivity)).getLayoutId());
    }

    @Provides
    @ActivityScope
    ActivityNewsSelectCateBinding provideActivityNewsSelectCateBinding() {
        return DataBindingUtil.setContentView(mActivity, ((YYActivity) (mActivity)).getLayoutId());
    }

    @Provides
    @ActivityScope
    ActivityNewsDetialBinding provideActivityNewsDetialBinding() {
        return DataBindingUtil.setContentView(mActivity, ((YYActivity) (mActivity)).getLayoutId());
    }

    @Provides
    @ActivityScope
    ActivitySplashBinding provideActivitySplashBinding() {
        return DataBindingUtil.setContentView(mActivity, ((YYActivity) (mActivity)).getLayoutId());
    }

    @Provides
    @ActivityScope
    ActivityOnlineMusicListBinding provideActivityOnlineMusicListBinding() {
        return DataBindingUtil.setContentView(mActivity, ((YYActivity) (mActivity)).getLayoutId());
    }

    @Provides
    @ActivityScope
    ActivityLoginBinding provideActivityLoginBinding() {
        return DataBindingUtil.setContentView(mActivity, ((YYActivity) (mActivity)).getLayoutId());
    }

    @Provides
    @ActivityScope
    ActivityRegisterBinding provideActivityRegisterBinding() {
        return DataBindingUtil.setContentView(mActivity, ((YYActivity) (mActivity)).getLayoutId());
    }

    @Provides
    @ActivityScope
    ActivityChattingBinding provideActivityChattingBinding() {
        return DataBindingUtil.setContentView(mActivity, ((YYActivity) (mActivity)).getLayoutId());
    }

}
