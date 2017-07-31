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
 * Created by adu on 2017/5/20.
 * 如果需要自己处理RxJava错误逻辑，使用该类，覆盖accept方法
 */

public class CustomErrorConsumer implements Consumer<Throwable> {

    private final String TAG = getClass().getSimpleName();

    private WeakReference<IYYLoadView> mView;

    public CustomErrorConsumer(IYYLoadView view) {
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
            // ignore this error
            LogUtils.w(TAG, "received an ignored api exception!");
            throwable.printStackTrace();
        } else {
            // 抛出非网络请求返回的的API异常
            LogUtils.e(TAG, "received a not api exception!");
            throwable.printStackTrace();
        }
    }
}
