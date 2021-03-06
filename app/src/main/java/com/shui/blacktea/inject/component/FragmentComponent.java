/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.inject.component;

import android.app.Activity;

import com.shui.blacktea.inject.module.FragmentModule;
import com.shui.blacktea.ui.chat.ChatFragment;
import com.shui.blacktea.ui.chat.ConnectFragment;
import com.shui.blacktea.ui.chat.OnChattingFragment;
import com.shui.blacktea.ui.chat.child.ChattingFragment;
import com.shui.blacktea.ui.chat.child.DiscoverFragment;
import com.shui.blacktea.ui.collection.CollectionFragment;
import com.shui.blacktea.ui.download.DownLoadFragment;
import com.shui.blacktea.ui.img.ImgFragment;
import com.shui.blacktea.ui.music.FragmentMusicLocal;
import com.shui.blacktea.ui.music.FragmentMusicOnline;
import com.shui.blacktea.ui.music.FragmentPlayingMusic;
import com.shui.blacktea.ui.music.MusicFragment;
import com.shui.blacktea.ui.news.NewsFragment;
import com.shui.blacktea.ui.setting.SettingFragment;
import com.shui.blacktea.ui.user.UserFragment;
import com.shui.blacktea.ui.video.VideoFragment;
import com.yeeyuntech.framework.inject.scope.FragmentScope;

import dagger.Component;


@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void Inject(CollectionFragment collectionFragment);

    void Inject(DownLoadFragment downLoadFragment);

    void Inject(ImgFragment imgFragment);

    void Inject(NewsFragment newsFragment);

    void Inject(SettingFragment settingFragment);

    void Inject(UserFragment userFragment);

    void Inject(VideoFragment videoFragment);

    void Inject(ChatFragment chatFragment);

    void Inject(MusicFragment musicFragment);

    void Inject(FragmentMusicLocal fragmentMusicLocal);

    void Inject(FragmentMusicOnline fragmentMusicOnline);

    void Inject(FragmentPlayingMusic fragmentPlayingMusic);

    void Inject(ChattingFragment chattingFragment);

    void Inject(ConnectFragment connectFragment);

    void Inject(DiscoverFragment discoverFragment);

    void Inject(OnChattingFragment onChattingFragment);
}
