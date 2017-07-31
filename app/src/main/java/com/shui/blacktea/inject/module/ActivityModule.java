/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.inject.module;

import android.app.Activity;

import com.yeeyuntech.framework.inject.module.YYActivityModule;

import dagger.Module;

@Module
public class ActivityModule extends YYActivityModule {

    public ActivityModule(Activity activity) {
        super(activity);
    }

}
