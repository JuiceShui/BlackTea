/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.inject.component;

import android.app.Activity;

import com.shui.blacktea.inject.module.ActivityModule;
import com.yeeyuntech.framework.inject.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    public Activity getActivity();

}
