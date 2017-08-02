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
    private static final String SP_NAME="mysp";
    private static final int DEFAULT_ITEM= Constants.TYPE_NEWS;//默认
    public static SharedPreferences getAppSp() {
        return App.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }
    public static int getCurrentItem() {
        return getAppSp().getInt(Constants.SP_CURRENT_ITEM, DEFAULT_ITEM);
    }

    public static void setCurrentItem(int item) {
        getAppSp().edit().putInt(Constants.SP_CURRENT_ITEM, item).apply();
    }
}
