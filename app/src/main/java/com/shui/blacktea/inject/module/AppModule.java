/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.inject.module;


import com.shui.blacktea.App;
import com.shui.blacktea.data.RetrofitHelper;
import com.yeeyuntech.framework.inject.module.YYAppModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule extends YYAppModule {

    private App mApp;

    public AppModule(App app) {
        this.mApp = app;
    }

    @Provides
    @Singleton
    App provideApp() {
        return mApp;
    }

    @Provides
    @Singleton
    RetrofitHelper provideRetrofitHelper() {
        return new RetrofitHelper();
    }
}
