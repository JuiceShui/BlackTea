/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.inject.module;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;


import com.shui.blacktea.databinding.FragmentChatBinding;
import com.shui.blacktea.databinding.FragmentContractBinding;
import com.shui.blacktea.databinding.FragmentImgBinding;
import com.shui.blacktea.databinding.FragmentLocalMusicBinding;
import com.shui.blacktea.databinding.FragmentMusicBinding;
import com.shui.blacktea.databinding.FragmentMusicPlayingBinding;
import com.shui.blacktea.databinding.FragmentNewsBinding;
import com.shui.blacktea.databinding.FragmentOnChattingBinding;
import com.shui.blacktea.databinding.FragmentOnlineMusicBinding;
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

    @Provides
    @FragmentScope
    FragmentMusicBinding provideFragmentMusicBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(mFragment.getActivity()), ((YYFragment) mFragment).getLayoutId(), null, false);
    }

    @Provides
    @FragmentScope
    FragmentLocalMusicBinding provideFragmentLocalMusicBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(mFragment.getActivity()), ((YYFragment) mFragment).getLayoutId(), null, false);
    }

    @Provides
    @FragmentScope
    FragmentOnlineMusicBinding provideFragmentOnlineMusicBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(mFragment.getActivity()), ((YYFragment) mFragment).getLayoutId(), null, false);
    }

    @Provides
    @FragmentScope
    FragmentMusicPlayingBinding provideFragmentMusicPlayingBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(mFragment.getActivity()), ((YYFragment) mFragment).getLayoutId(), null, false);
    }

    @Provides
    @FragmentScope
    FragmentImgBinding provideFragmentImgBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(mFragment.getActivity()), ((YYFragment) mFragment).getLayoutId(), null, false);
    }

    @Provides
    @FragmentScope
    FragmentChatBinding provideFragmentChatBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(mFragment.getActivity()), ((YYFragment) mFragment).getLayoutId(), null, false);
    }

    @Provides
    @FragmentScope
    FragmentOnChattingBinding provideFragmentOnChatBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(mFragment.getActivity()), ((YYFragment) mFragment).getLayoutId(), null, false);
    }

    @Provides
    @FragmentScope
    FragmentContractBinding provideFragmentContractBinding() {
        return DataBindingUtil.inflate(LayoutInflater.from(mFragment.getActivity()), ((YYFragment) mFragment).getLayoutId(), null, false);
    }
}


