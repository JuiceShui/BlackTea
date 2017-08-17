package com.shui.blacktea.config;

import android.os.Environment;

import com.shui.blacktea.App;

import java.io.File;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class Constants {
    public static final int TYPE_NEWS = 0;
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_IMG = 2;
    public static final int TYPE_CHAT = 3;
    public static final int TYPE_SETTING = 4;
    public static final int TYPE_COLLECTION = 5;
    public static final int TYPE_DOWNLOAD = 6;
    public static final int TYPE_USER = 7;
    public static final int TYPE_MUSIC = 8;
    public static final String SP_CURRENT_ITEM = "current_item";
    public static final String SP_USER = "user";
    public static final String SP_IS_LOGINED = "is_login";
    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + "/netCache";
    public static final String PATH_EXTERNAL_CARD = Environment.getExternalStorageDirectory() + File.separator + "Shui" + File.separator + "blackTea";


    public static final String TRANSITION_NAME = "ShareView";
}
