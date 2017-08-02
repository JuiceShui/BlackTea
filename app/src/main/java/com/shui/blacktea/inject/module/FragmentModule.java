/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.inject.module;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;


import com.shui.blacktea.databinding.FragmentNewsBinding;
import com.shui.blacktea.databinding.FragmentTestBinding;
import com.shui.blacktea.databinding.FragmentVideoBinding;
import com.yeeyuntech.framework.inject.module.YYFragmentModule;
import com.yeeyuntech.framework.inject.scope.FragmentScope;
import com.yeeyuntech.framework.ui.YYFragment;

import dagger.Module;
import dagger.Provides;


@Module
public class FragmentModule extends YYFragmentModule {

    public FragmentModule(Fragment fragment) {
        super(fragment);
    }

    @Provides
    @FragmentScope
    FragmentTestBinding provideFragmentTestBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(mFragment.getActivity()), ((YYFragment) mFragment).getLayoutId(), null, false);
    }

    @Provides
    @FragmentScope
    FragmentNewsBinding provideFragmentNewsBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(mFragment.getActivity()), ((YYFragment) mFragment).getLayoutId(), null, false);
    }

    @Provides
    @FragmentScope
    FragmentVideoBinding provideFragmentVideoBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(mFragment.getActivity()), ((YYFragment) mFragment).getLayoutId(), null, false);
    }
}


