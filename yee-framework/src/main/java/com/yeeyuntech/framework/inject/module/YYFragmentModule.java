package com.yeeyuntech.framework.inject.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.yeeyuntech.framework.inject.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;


@Module
public class YYFragmentModule {

    protected Fragment mFragment;

    public YYFragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @FragmentScope
    public Fragment provideFragment() {
        return mFragment;
    }
}
