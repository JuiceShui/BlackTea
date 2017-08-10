package com.shui.blacktea.config;

import com.shui.blacktea.entity.MusicEntity;
import com.shui.blacktea.ui.music.service.PlayService;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/8.
 */

public class AppCache {
    private PlayService mService;
    private static AppCache mInstance;
    private List<MusicEntity> mMusicList = new ArrayList<>();

    private AppCache() {

    }

    public void setPlayService(PlayService playService) {
        mInstance.mService = playService;
    }

    public PlayService getPlayService() {
        return mInstance.mService;
    }

    public static AppCache getInstance() {
        synchronized (AppCache.class) {
            if (mInstance == null) {
                mInstance = new AppCache();
            }
            return mInstance;
        }
    }

    public List<MusicEntity> getMusicList() {
        return mInstance.mMusicList;
    }

    public void setMusicList(List<MusicEntity> list) {
        mInstance.mMusicList = list;
    }
}
