package com.shui.blacktea.ui.music.service;

import com.shui.blacktea.entity.MusicEntity;

/**
 * Description:
 * Created by Juice_ on 2017/8/8.
 */

public interface PlayServiceMethod {
    void play(MusicEntity musicEntity);

    void play(int position);

    //暂停 播放
    void playPause();

    //停止
    void stop();

    //下一曲
    void next();

    //上一曲
    void prev();

    //退出
    void quit();

    //跳转到指定时间
    void seekTo(int second);
}
