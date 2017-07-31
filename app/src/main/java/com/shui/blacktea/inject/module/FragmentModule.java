/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.inject.module;

import android.support.v4.app.Fragment;

import com.yeeyuntech.framework.inject.module.YYFragmentModule;

import dagger.Module;


@Module
public class FragmentModule extends YYFragmentModule {

    public FragmentModule(Fragment fragment) {
        super(fragment);
    }
}


