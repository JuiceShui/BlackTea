/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.inject.component;

import com.shui.blacktea.App;
import com.shui.blacktea.data.RetrofitHelper;
import com.shui.blacktea.inject.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    App getApp();  // 提供App的Context

    RetrofitHelper getRetrofitHelper();  //提供retrofit的帮助类
}
