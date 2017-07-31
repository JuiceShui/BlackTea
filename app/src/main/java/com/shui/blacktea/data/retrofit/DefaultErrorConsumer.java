/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.data.retrofit;

import com.yeeyuntech.framework.data.exception.APIException;
import com.yeeyuntech.framework.data.exception.NoTokenException;
import com.yeeyuntech.framework.data.exception.TokenException;
import com.yeeyuntech.framework.ui.IYYLoadView;
import com.yeeyuntech.framework.utils.LogUtils;

import java.lang.ref.WeakReference;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by adu on 2017/6/23.
 * 默认的RxJava错误逻辑，对于非token异常的其他API异常，默认以弹出toast提示处理
 * 自动处理token 异常，他异常弹出相应toast
 */

public class DefaultErrorConsumer implements Consumer<Throwable> {

    private final String TAG = getClass().getSimpleName();

    private WeakReference<IYYLoadView> mView;

    public DefaultErrorConsumer(IYYLoadView view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public void accept(@NonNull final Throwable throwable) throws Exception {
        final IYYLoadView view = mView.get();
        // default handle throwable
        if (throwable instanceof TokenException || throwable instanceof NoTokenException) {
            if (view != null) {
                // 到主线程中执行
                view.safeRun(new Runnable() {
                    @Override
                    public void run() {
                        view.handleTokenException(throwable);
                    }
                });
            }
        } else if (throwable instanceof APIException) {
            if (view != null) {
                // 到主线程中执行
                view.safeRun(new Runnable() {
                    @Override
                    public void run() {
                        view.showToast(throwable.getMessage());
                    }
                });
            }
        } else {
            // 抛出非网络请求返回的的API异常
            LogUtils.e(TAG, "received a not api exception!");
            throwable.printStackTrace();
            if (view != null) {
                // 到主线程中执行
                view.safeRun(new Runnable() {
                    @Override
                    public void run() {
                        view.showToast("网络不给力，请稍后再试~");
                    }
                });
            }
        }
    }
}
