package com.shui.blacktea.ui.music.service;

import com.shui.blacktea.entity.MusicEntity;

/**
 * Description:
 * Created by Juice_ on 2017/8/8.
 */

public interface OnPlayEventListener {
    //播放进度¬
    void onPublish(int progress);

    //暂停播放
    void onPlayerPause();

    //继续播放
    void onPlayerResume();

    //缓冲比
    void onBufferingUpdate(int percent);

    //切换歌曲
    void onChange(MusicEntity musicEntity);

    //更新定时播放时间
    void onTimer(int remain);

    void onMusicListUpdate();
}
