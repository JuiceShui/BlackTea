/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.shui.blacktea.config.AppCfg;
import com.shui.blacktea.inject.component.AppComponent;
import com.shui.blacktea.inject.component.DaggerAppComponent;
import com.shui.blacktea.inject.module.AppModule;
import com.yeeyuntech.framework.YYApplication;

/**
 * Description:
 * Created by lsy on 2017/7/7 下午1:57.
 */
public class App extends YYApplication {

    private static final String TAG = App.class.getSimpleName();

    /**
     * 缓存数据，跟随应用声明周期
     */
    // 用户信息
    /*private UserEntity mUser = null;*/
    // 当前应用实例
    private static App mInstance = null;
    // handler
    private static Handler mHandler;
    private Looper mHandlerLooper;

    // component
    public static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        HandlerThread thread = new HandlerThread(getPackageName() + TAG);
        thread.start();
        mHandlerLooper = thread.getLooper();
        mHandler = new Handler(mHandlerLooper);
        AppCfg.init(this);
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public static AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(mInstance))
                    .build();
        }
        return mAppComponent;
    }
/*
    public void setUser(UserEntity u) {
        mUser = u;
        setYYUser(mUser);
    }*/

    /*public UserEntity getUser() {
        return mUser;
    }*/

    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mHandlerLooper.quit();
    }
}
