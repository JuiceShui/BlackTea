/*
 * Copyright (C) 2016 david.wei (lighters)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shui.blacktea.data.retrofit;

import com.alibaba.fastjson.JSON;
import com.shui.blacktea.App;
import com.shui.blacktea.data.AESManager;
import com.shui.blacktea.data.AuthManager;
import com.yeeyuntech.framework.data.bean.ReqMap;
import com.yeeyuntech.framework.data.exception.AESEncryptException;
import com.yeeyuntech.framework.data.exception.NoAESKeyException;
import com.yeeyuntech.framework.data.exception.NoTokenException;
import com.yeeyuntech.framework.utils.TxUtils;
import com.yeeyuntech.framework.utils.crypto.AES;
import com.yeeyuntech.framework.utils.crypto.Digest;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Created by david on 16/8/21.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class ProxyHandler implements InvocationHandler {

    private final String TAG = "ProxyHandler";

    private Throwable mRefreshTokenError = null;

    private Object mProxyObject;

    public ProxyHandler(Object proxyObject) {
        mProxyObject = proxyObject;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        return Observable.just(0).flatMap(new Function<Object, Observable<?>>() {
            @Override
            public Observable<?> apply(Object o) throws IOException {
                try {
                    try {
                        // 加密请求参数
                        encryptParams(args,
                                AESManager.getInstance().getKey(),
                                App.getInstance().getToken());
                        return (Observable<?>) method.invoke(mProxyObject, args);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) throws Exception {
                return observable.flatMap(new Function<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> apply(Throwable throwable) {
                        if (throwable instanceof NoAESKeyException) {
                            return refreshTokenWhenNoAESKey();
                        }
                        return Observable.error(throwable);
                    }
                });
            }
        });
    }

    /**
     * Refresh the token when the current token is invalid.
     *
     * @return Observable
     */
    private Observable<?> refreshTokenWhenNoAESKey() {
        synchronized (ProxyHandler.class) {
            if (AESManager.getInstance().getKey() != null) {
                // 如果已经获取key，不发起请求，直接返回
                return Observable.just(true);
            }
            Map<String, String> params = AuthManager.getInstance().getRefreshTokenParams();
            mRefreshTokenError = null;
            // call the refresh token api.
//            RetrofitHelper.getInstance().refreshToken(params)
//                    .subscribe(new Consumer<RespBean>() {
//                        @Override
//                        public void accept(@NonNull RespBean respBean) throws Exception {
//                            AuthManager.getInstance().saveAuthInfo(respBean.getData());
//                        }
//                    }, new Consumer<Throwable>() {
//                        @Override
//                        public void accept(@NonNull Throwable throwable) throws Exception {
//                            mRefreshTokenError = throwable;
//                        }
//                    });
            if (mRefreshTokenError != null) {
                return Observable.error(mRefreshTokenError);
            } else {
                return Observable.just(true);
            }
        }
    }


    /**
     * 由AES密钥加密参数
     */
    private void encryptParams(Object[] args, String key, String token) throws IOException {

        if (token == null || token.length() == 0) {
            // 没有token
            throw new NoTokenException("the token is empty");
        }

        if (key == null || key.length() == 0) {
            // 没有AES密钥
            throw new NoAESKeyException("the AES key is empty");
        }

        ReqMap map = new ReqMap();
        // 记录参数位置
        int argsIndex = 0;
        for (int i = 0, len = args.length; i < len; i++) {
            if (args[i] instanceof ReqMap) {
                map = (ReqMap) args[i];
                argsIndex = i;
            }
        }

        // 处理业务参数
        String timestamp = Long.toString(System.currentTimeMillis()).substring(0, 10);
        String encryptData = AES.encrypt(JSON.toJSONString(map), key);
        if (encryptData == null) {
            throw new AESEncryptException("AES encrypt failed");
        }
        String randomStr = UUID.randomUUID().toString();
        ReqMap reqMap = new ReqMap();
        reqMap.put("timestamp", timestamp);
        reqMap.put("data", encryptData);
        reqMap.put("str", randomStr);
        String sign = Digest.md5(TxUtils.sortValues(reqMap) + Digest.md5(timestamp));
        reqMap.put("sign", sign);
        reqMap.put("token", token);
        args[argsIndex] = reqMap;
    }
}