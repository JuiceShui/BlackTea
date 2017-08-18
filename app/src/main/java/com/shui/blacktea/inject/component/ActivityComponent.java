/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.inject.component;

import android.app.Activity;

import com.shui.blacktea.inject.module.ActivityModule;
import com.shui.blacktea.ui.chat.OnChattingActivity;
import com.shui.blacktea.ui.home.LoginActivity;
import com.shui.blacktea.ui.home.RegisterActivity;
import com.shui.blacktea.ui.music.OnlineMusicListActivity;
import com.shui.blacktea.ui.home.HomeActivity;
import com.shui.blacktea.ui.home.SplashActivity;
import com.shui.blacktea.ui.news.NewsCateSelectActivity;
import com.shui.blacktea.ui.news.NewsDetailActivity;
import com.yeeyuntech.framework.inject.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    public Activity getActivity();

    void Inject(HomeActivity homeActivity);

    void Inject(NewsCateSelectActivity newsCateSelectActivity);

    void Inject(NewsDetailActivity newsDetailActivity);

    void Inject(SplashActivity splashActivity);

    void Inject(OnlineMusicListActivity onlineMusicListActivity);

    void Inject(LoginActivity loginActivity);

    void Inject(RegisterActivity registerActivity);

    void Inject(OnChattingActivity chattingActivity);
}
