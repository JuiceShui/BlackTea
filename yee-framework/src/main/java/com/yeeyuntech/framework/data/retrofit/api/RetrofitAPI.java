package com.yeeyuntech.framework.data.retrofit.api;


import com.yeeyuntech.framework.data.bean.RespBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by adu on 2017/4/24.
 * 不需要进行AES加密的retrofit请求从此接口创建
 * 仅用于本应用服务器接口，返回值需要统一格式
 */

public interface RetrofitAPI {

    @GET
    Observable<RespBean> get(@Url String relativeUrl);

    /**
     * 不需要进行AES加密的get请求
     *
     * @param relativeUrl
     * @param params
     * @return
     */
    @GET
    Observable<RespBean> get(@Url String relativeUrl, @QueryMap Map<String, String> params);

    @POST
    Observable<RespBean> post(@Url String relativeUrl);

    @POST
    @FormUrlEncoded
    Observable<RespBean> post(@Url String relativeUrl, @FieldMap Map<String, String> params);

    /**
     * 不需要进行AES加密
     * 带文件上传的post请求
     *
     * @param relativeUrl 请求地址
     * @param params      键值对
     * @param multipart   文件参数
     * @return
     */
    @Multipart
    @POST
    Observable<RespBean> post(@Url String relativeUrl, @QueryMap Map<String, String> params, @PartMap Map<String, RequestBody> multipart);
}
