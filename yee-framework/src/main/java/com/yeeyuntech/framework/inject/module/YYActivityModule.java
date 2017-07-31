package com.yeeyuntech.framework.inject.module;

import android.app.Activity;

import com.yeeyuntech.framework.inject.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class YYActivityModule {

    protected Activity mActivity;

    public YYActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
