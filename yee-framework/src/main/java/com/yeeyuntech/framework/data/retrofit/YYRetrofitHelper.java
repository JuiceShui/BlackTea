package com.yeeyuntech.framework.data.retrofit;


import com.yeeyuntech.framework.data.retrofit.api.AESRetrofitAPI;
import com.yeeyuntech.framework.data.retrofit.api.PublicRetrofitAPI;
import com.yeeyuntech.framework.data.retrofit.api.RetrofitAPI;
import com.yeeyuntech.framework.data.retrofit.converter.AESConverterFactory;
import com.yeeyuntech.framework.data.retrofit.converter.JsonConverterFactory;
import com.yeeyuntech.framework.data.retrofit.converter.RSAConverterFactory;

import java.io.File;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by adu on 2017/4/24.
 * retrofit辅助类
 */

public class YYRetrofitHelper {

    private OkHttpClient mOkHttpClient = null;

    // 用于请求非本应用服务器的外网接口
    private PublicRetrofitAPI mPublicRetrofitAPI = null;

    // 用于请求本服务器不需要加密的请求
    private RetrofitAPI mRetrofitAPI = null;

    // 用于请求本服务器不需要加密的请求
    private RetrofitAPI mRSARetrofitAPI = null;

    // 用于需要AES加密的请求
    private AESRetrofitAPI mAESRetrofitAPI = null;

    // 设置参数
    // 服务器地址
    private String mServer;
    private Converter.Factory mConverterFactory;
    private Converter.Factory mAESConverterFactory;
    private Converter.Factory mRSAConverterFactory;
    private IProxyHandler mProxyHandler;

    public YYRetrofitHelper(String server,
                            IProxyHandler proxyHandler) {
        mServer = server;
        mConverterFactory = JsonConverterFactory.create();
        mAESConverterFactory = AESConverterFactory.create();
        mRSAConverterFactory = RSAConverterFactory.create();
        mProxyHandler = proxyHandler;
        init();
    }

    private void init() {
        initOkHttpClient();
    }

    private void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置超时
        builder.connectTimeout(12, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.build();
    }


    /**
     * 用户发起非本应用服务器的外网接口
     *
     * @return mPublicRetrofitAPI
     */
    public PublicRetrofitAPI getPublicRetrofitAPI() {
        if (mPublicRetrofitAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    // baseUrl不能设置为空，预置为本应用服务器，实际并没有使用
                    // 最终调用接口时，将被覆盖
                    .baseUrl(mServer)
                    .client(mOkHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mPublicRetrofitAPI = retrofit.create(PublicRetrofitAPI.class);
        }
        return mPublicRetrofitAPI;
    }


    /**
     * 用户发起本应用服务器不需要加密的接口
     *
     * @return mRetrofitAPI
     */
    public RetrofitAPI getRetrofitAPI() {
        if (mRetrofitAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(mServer)
                    .client(mOkHttpClient)
                    .addConverterFactory(mConverterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mRetrofitAPI = retrofit.create(RetrofitAPI.class);
        }
        return mRetrofitAPI;
    }


    /**
     * 用于发起已进行RSA加密的请求
     * RSA加密请求没有对加密参数进行统一处理
     * 需要将参数加密后调用请求
     *
     * @return mRSARetrofitAPI
     */
    public RetrofitAPI getRSARetrofitAPI() {
        if (mRSARetrofitAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(mServer)
                    .client(mOkHttpClient)
                    .addConverterFactory(mRSAConverterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mRSARetrofitAPI = retrofit.create(RetrofitAPI.class);
        }
        return mRSARetrofitAPI;
    }


    /**
     * 用于发起需要进行AES加密的请求
     * 内部实现对参数进行统一的AES加密处理
     * 需要使用GetMap类型的参数，否则不会进行加密
     *
     * @return mAESRetrofitAPI
     */
    public AESRetrofitAPI getAESRetrofitAPI() {
        if (mAESRetrofitAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(mServer)
                    .client(mOkHttpClient)
                    .addConverterFactory(mAESConverterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            AESRetrofitAPI service = retrofit.create(AESRetrofitAPI.class);
            // 使用代理类实现AES加密和AES密钥获取
            mAESRetrofitAPI = (AESRetrofitAPI) Proxy.newProxyInstance(
                    AESRetrofitAPI.class.getClassLoader(),
                    new Class<?>[]{AESRetrofitAPI.class},
                    mProxyHandler.getProxy(service));
        }
        return mAESRetrofitAPI;
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
}
