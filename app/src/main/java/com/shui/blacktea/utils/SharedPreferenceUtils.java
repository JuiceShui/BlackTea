package com.shui.blacktea.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.shui.blacktea.App;
import com.shui.blacktea.config.Constants;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class SharedPreferenceUtils {
    private static final String SP_NAME = "mysp";
    private static final String MUSIC_ID = "music_id";
    private static final String PLAY_MODE = "play_mode";
    private static final String FILTER_SIZE = "filter_size";
    private static final String FILTER_TIME = "filter_time";
    private static final int DEFAULT_ITEM = Constants.TYPE_NEWS;//默认

    public static SharedPreferences getAppSp() {
        return App.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static int getCurrentItem() {
        return getAppSp().getInt(Constants.SP_CURRENT_ITEM, DEFAULT_ITEM);
    }

    public static void setCurrentItem(int item) {
        getAppSp().edit().putInt(Constants.SP_CURRENT_ITEM, item).apply();
    }

    public static long getCurrentSongId() {
        return getAppSp().getLong(MUSIC_ID, -1);
    }

    public static void setCurrentSongId(long id) {
        getAppSp().edit().putLong(MUSIC_ID, id).apply();
    }

    public static int getPlayMode() {
        return getAppSp().getInt(PLAY_MODE, 1);
    }

    public static void setPlayMode(int mode) {
        getAppSp().edit().putInt(PLAY_MODE, mode).apply();
    }

    public static long getFilterSize() {
        return getAppSp().getInt(FILTER_SIZE, 1);
    }

    public static void setFilterSize(long size) {
        getAppSp().edit().putLong(FILTER_SIZE, size).apply();
    }

    public static long getFilterTime() {
        return getAppSp().getInt(FILTER_TIME, 1);
    }

    public static void setFilterTime(long time) {
        getAppSp().edit().putLong(FILTER_TIME, time).apply();
    }
}
