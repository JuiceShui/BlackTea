/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.config;

import android.content.Context;
import android.os.Environment;

import com.shui.blacktea.App;
import com.shui.blacktea.BuildConfig;
import com.yeeyuntech.framework.utils.LogUtils;
import com.yeeyuntech.framework.utils.PrefUtils;

import java.io.File;

/**
 * Description:
 * Created by lsy on 2017/7/7 下午2:17.
 */
public class AppCfg {

    private static final String TAG = AppCfg.class.getSimpleName();

    // 常量
    private static final String APP_STORAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/" + BuildConfig.APPLICATION_ID;

    // 是否是正式环境
    private static boolean mIsRelease = BuildConfig.releaseEnvironment;

    // 测试环境 正式环境 server
    private static String mDebugServer = "http://api.tianapi.com/";
    private static String mReleaseServer = "http://api.tianapi.com/";

    private static String mServer = mDebugServer;

    /**
     * 初始化环境配置
     */
    public static void init(App app) {
        // 初始化第三方库
        LogUtils.init(BuildConfig.DEBUG, app.getPackageName(), LogUtils.VERBOSE);
        PrefUtils.initKey();
        // 路由环境配置
        if (mIsRelease) {
            mServer = mReleaseServer;
        }
    }

    public static File getStorageCache() {
        File dir = null;
        try {
            dir = new File(APP_STORAGE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } catch (Exception e) {

        }
        return dir;
    }

    public static File getImageCache() {
        File dir = null;
        try {
            dir = new File(APP_STORAGE_PATH + "/images");
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } catch (Exception e) {

        }
        return dir;
    }

    public static File getShareCache() {
        File dir = null;
        try {
            dir = new File(APP_STORAGE_PATH + "/share");
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } catch (Exception e) {

        }
        return dir;
    }

    public static String getCachePath(Context context) {
        File cache = context.getExternalCacheDir();
        if (cache == null) {
            return null;
        }
        return context.getExternalCacheDir().getPath();
    }

}
