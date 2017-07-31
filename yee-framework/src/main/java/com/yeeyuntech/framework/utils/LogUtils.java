/*
 * Created by lsy on 2017/7/25.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.yeeyuntech.framework.utils;

import android.util.Log;

import timber.log.Timber;


/**
 * Log相关工具类
 */
public class LogUtils {

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;

    private static int LEVEL = VERBOSE;
    private static String GLOBAL_TAG = LogUtils.class.getSimpleName();

    public static void init(boolean isDebug, String globalTag, int logLevel) {
        GLOBAL_TAG = globalTag;
        LEVEL = logLevel;
        if (isDebug) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static void v(String msg) {
        v(GLOBAL_TAG, msg);
    }

    public static void d(String msg) {
        d(GLOBAL_TAG, msg);
    }

    public static void i(String msg) {
        i(GLOBAL_TAG, msg);
    }

    public static void w(String msg) {
        w(GLOBAL_TAG, msg);
    }

    public static void e(String msg) {
        e(GLOBAL_TAG, msg);
    }


    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
//            Log.v(tag, msg);
            Timber.tag(tag).v(msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
//            Log.i(tag, msg);
            Timber.tag(tag).i(msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }
}