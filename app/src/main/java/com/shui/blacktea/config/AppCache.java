package com.shui.blacktea.config;

import com.shui.blacktea.ui.music.service.PlayService;

/**
 * Description:
 * Created by Juice_ on 2017/8/8.
 */

public class AppCache {
    private static PlayService mService;
    private AppCache mInstance;

    private AppCache() {

    }

    public static void setPlayService(PlayService playService) {
        mService = playService;
    }

    public static PlayService getPlayService() {
        return mService;
    }

    public AppCache getInstance() {
        synchronized (AppCache.class) {
            if (mInstance == null) {
                mInstance = new AppCache();
            }
            return mInstance;
        }
    }
}
