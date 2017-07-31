package com.yeeyuntech.framework.data.retrofit;

import com.yeeyuntech.framework.data.retrofit.api.AESRetrofitAPI;

import java.lang.reflect.InvocationHandler;

/**
 * Created by adu on 2017/5/20.
 * AES加密请求的代理接口，用于实现AES加密相关处理
 */

public interface IProxyHandler {

    InvocationHandler getProxy(AESRetrofitAPI service);
}
