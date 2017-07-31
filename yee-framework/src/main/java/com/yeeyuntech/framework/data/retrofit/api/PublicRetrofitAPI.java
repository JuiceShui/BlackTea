package com.yeeyuntech.framework.data.retrofit.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by adu on 2017/4/27.
 * 用户调用外网接口，统一使用String参数
 */

public interface PublicRetrofitAPI {

    @GET
    Observable<String> get(@Url String url);

    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, String> params);

    @POST
    Observable<String> post(@Url String url);

    @POST
    @FormUrlEncoded
    Observable<String> post(@Url String url, @FieldMap Map<String, String> params);

    @Multipart
    @POST
    Observable<String> post(@Url String url, @QueryMap Map<String, String> params, @PartMap Map<String, RequestBody> multipart);

    @GET
    Observable<ResponseBody> loadVideo(@Url String url);
}
