/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.data.retrofit;


import android.text.TextUtils;

import com.shui.blacktea.App;
import com.shui.blacktea.config.AppCfg;
import com.shui.blacktea.data.AESManager;
import com.shui.blacktea.data.APIPath;
import com.shui.blacktea.data.RSAManager;
import com.yeeyuntech.framework.data.bean.ReqMap;
import com.yeeyuntech.framework.data.bean.RespBean;
import com.yeeyuntech.framework.data.retrofit.IProxyHandler;
import com.yeeyuntech.framework.data.retrofit.YYRetrofitHelper;
import com.yeeyuntech.framework.data.retrofit.api.AESRetrofitAPI;
import com.yeeyuntech.framework.data.retrofit.api.PublicRetrofitAPI;
import com.yeeyuntech.framework.data.retrofit.api.RetrofitAPI;
import com.yeeyuntech.framework.utils.crypto.Digest;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by adu on 2017/4/24.
 * retrofit辅助类
 */

public class RetrofitHelper implements APIPath {

    private YYRetrofitHelper mHelper;

    private static class InstanceHolder {
        private static final RetrofitHelper instance = new RetrofitHelper();
    }

    public static RetrofitHelper getInstance() {
        return RetrofitHelper.InstanceHolder.instance;
    }

    private RetrofitHelper() {
        mApp = App.getInstance();
        mHelper = new YYRetrofitHelper(AppCfg.getServer(), new IProxyHandler() {
            @Override
            public InvocationHandler getProxy(AESRetrofitAPI service) {
                return new ProxyHandler(service);
            }
        });
    }


    /**
     * 用户发起非本应用服务器的外网接口
     *
     * @return mPublicRetrofitAPI
     */
    private PublicRetrofitAPI getPublicRetrofitAPI() {
        return mHelper.getPublicRetrofitAPI();
    }


    /**
     * 用户发起本应用服务器不需要加密的接口
     *
     * @return mRetrofitAPI
     */
    private RetrofitAPI getRetrofitAPI() {
        return mHelper.getRetrofitAPI();
    }


    /**
     * 用于发起已进行RSA加密的请求
     * RSA加密请求没有对加密参数进行统一处理
     * 需要将参数加密后调用请求
     *
     * @return mRSARetrofitAPI
     */
    private RetrofitAPI getRSARetrofitAPI() {
        return mHelper.getRSARetrofitAPI();
    }


    /**
     * 用于发起需要进行AES加密的请求
     * 内部实现对参数进行统一的AES加密处理
     * 需要使用GetMap类型的参数，否则不会进行加密
     *
     * @return mAESRetrofitAPI
     */
    private AESRetrofitAPI getAESRetrofitAPI() {
        return mHelper.getAESRetrofitAPI();
    }


    /**
     * 普通get请求
     * 发起外网请求时可以使用此方法
     * 以String类型读取返回值
     *
     * @param url    完整的url地址，包含服务器地址
     * @param params 请求参数
     * @return
     */
    public Observable<String> get(String url, Map<String, String> params) {
        Observable<String> request;
        if (params == null || params.size() == 0) {
            request = getPublicRetrofitAPI().get(url);
        } else {
            request = getPublicRetrofitAPI().get(url, params);
        }
        return request;
    }


    /**
     * 普通post请求
     * 发起外网请求时可以使用此方法
     * 以String类型读取返回值
     *
     * @param url    完整的url地址，包含服务器地址
     * @param params 请求参数，可以包含文件类型，可上传文件
     * @return
     */
    public Observable<String> post(String url, Map<String, Object> params) {
        boolean isMultipart = false;
        Observable<String> request;
        if (params == null || params.size() == 0) {
            request = getPublicRetrofitAPI().post(url);
        } else {
            Map<String, String> fieldMap = new HashMap<>();
            Map<String, RequestBody> partMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Object obj = entry.getValue();
                if (obj == null) {
                    fieldMap.put(entry.getKey(), "");
                } else if (obj instanceof File) {
                    RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), (File) obj);
                    partMap.put(entry.getKey(), body);
                    isMultipart = true;
                } else {
                    fieldMap.put(entry.getKey(), obj.toString());
                }
            }
            if (isMultipart) {
                request = getPublicRetrofitAPI().post(url, fieldMap, partMap);
            } else {
                request = getPublicRetrofitAPI().post(url, fieldMap);
            }
        }
        return request;
    }

    // ==================================================  未登录 使用 不加密的请求方式  登陆之后 采用加密的请求 =================================
    private App mApp;

    /**
     * get请求方式
     *
     * @param url
     * @param param
     * @return
     */
    private Observable<RespBean> getApi(String url, Map<String, String> param) {
        if (mApp.isLogin()) {
            ReqMap reqmap = new ReqMap();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                reqmap.put(entry.getKey(), entry.getValue());
            }
            return getAESRetrofitAPI().get(url, reqmap);
        } else {
            return getRetrofitAPI().get(url, param);
        }
    }

    /**
     * post 请求方式
     *
     * @param url
     * @param param
     * @return
     */
    private Observable<RespBean> postApi(String url, Map<String, String> param) {
        if (mApp.isLogin()) {
            ReqMap reqmap = new ReqMap();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                reqmap.put(entry.getKey(), entry.getValue());
            }
            return getAESRetrofitAPI().post(url, reqmap);
        } else {
            return getRetrofitAPI().post(url, param);
        }
    }
    // ==================================================  未登录 使用 不加密的请求方式  登陆之后 采用加密的请求 =================================
}
