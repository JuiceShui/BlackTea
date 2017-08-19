/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.shui.blacktea.config.AppCfg;
import com.shui.blacktea.entity.type.MessageType;
import com.shui.blacktea.inject.component.AppComponent;
import com.shui.blacktea.inject.component.DaggerAppComponent;
import com.shui.blacktea.inject.module.AppModule;
import com.shui.blacktea.ui.chat.UserProvider;
import com.shui.blacktea.utils.CircularAnim;
import com.shui.blacktea.utils.RxBus;
import com.shui.blacktea.utils.SharedPreferenceUtils;
import com.yeeyuntech.framework.YYApplication;

import cn.leancloud.chatkit.LCChatKit;

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
    private static String mUser;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        HandlerThread thread = new HandlerThread(getPackageName() + TAG);
        thread.start();
        mHandlerLooper = thread.getLooper();
        mHandler = new Handler(mHandlerLooper);
        CircularAnim.init(700, 500, R.color.colorPrimary);
        AppCfg.init(this);
        AVOSCloud.initialize(this, "rrpQ9orLMgOn96iDoxNm6gLX-gzGzoHsz", "0AB4mgpHOGketYN71m98rOye");
        AVOSCloud.setDebugLogEnabled(true);
        AVIMMessageManager.registerDefaultMessageHandler(new MessageHandler());
        LCChatKit.getInstance().setProfileProvider(UserProvider.getInstance());
        LCChatKit.getInstance().init(this, "rrpQ9orLMgOn96iDoxNm6gLX-gzGzoHsz", "0AB4mgpHOGketYN71m98rOye");
        getUser();
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
    private void getUser() {
        String userInfo = SharedPreferenceUtils.getUser();
        if (!TextUtils.isEmpty(userInfo)) {
            mUser = userInfo;
        }
    }

    public static String getUserInfo() {
        return mUser;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public static AVUser getCurrentAVUser() {
        if (!TextUtils.isEmpty(getUserInfo())) {
            return AVUser.getCurrentUser();
        }
        return null;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mHandlerLooper.quit();
    }

    private static class MessageHandler extends AVIMMessageHandler {
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
            super.onMessage(message, conversation, client);
            RxBus.getDefault().post(new MessageType(message, conversation, client));
        }

        @Override
        public void onMessageReceipt(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
            super.onMessageReceipt(message, conversation, client);
        }
    }
}
