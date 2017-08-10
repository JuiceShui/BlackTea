package com.shui.blacktea.ui.music.imp;

import com.shui.blacktea.entity.MusicEntity;

/**
 * Description:
 * Created by Juice_ on 2017/8/10.
 */

public interface ILocalMusic {
    void onItemPlay();

    void onMusicListUpdate();

    void shareMusic(MusicEntity musicEntity);

    void updateView();
}
