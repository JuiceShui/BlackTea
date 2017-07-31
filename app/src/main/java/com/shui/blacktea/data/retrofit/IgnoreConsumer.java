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
 * Created by adu on 2017/5/4.
 * 自动处理token 异常，忽略其他异常
 * 如需自定义异常处理，覆盖accept方法处理相应逻辑，调用super方法处理token异常
 */
public class IgnoreConsumer implements Consumer<Throwable> {

    private final String TAG = getClass().getSimpleName();

    private WeakReference<IYYLoadView> mView;

    public IgnoreConsumer(IYYLoadView view) {
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
