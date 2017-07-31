package com.yeeyuntech.framework.data.retrofit.api;


import com.yeeyuntech.framework.data.bean.ReqMap;
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
 * Created by adu on 2017/4/27.
 * 进行AES加密的retrofit请求从此接口创建
 * 使用ReqMap参数时，该参数参与AES加密
 */

public interface AESRetrofitAPI {

    /**
     * AES加密get请求
     *
     * @param relativeUrl 相对地址，不包含baseUrl的地址
     * @param map         需要进行AES加密的业务参数，不能传null
     * @return
     */
    @GET
    Observable<RespBean> get(@Url String relativeUrl, @QueryMap ReqMap map);


    /**
     * AES加密post请求
     *
     * @param relativeUrl 相对地址，不包含baseUrl的地址
     * @param map         需要进行AES加密的业务参数，不能传null
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<RespBean> post(@Url String relativeUrl, @FieldMap ReqMap map);


    /**
     * 带文件上传的AES加密post请求
     *
     * @param relativeUrl 相对地址，不包含baseUrl的地址
     * @param map         需要进行AES加密的业务参数，不能传null
     * @param multipart   文件参数
     * @return
     */
    @POST
    @Multipart
    Observable<RespBean> post(@Url String relativeUrl, @QueryMap ReqMap map, @PartMap Map<String, RequestBody> multipart);
}
