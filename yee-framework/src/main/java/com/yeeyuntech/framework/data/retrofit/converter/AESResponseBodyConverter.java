package com.yeeyuntech.framework.data.retrofit.converter;

import com.alibaba.fastjson.JSON;
import com.yeeyuntech.framework.data.bean.RespBean;
import com.yeeyuntech.framework.utils.LogUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

class AESResponseBodyConverter<T> implements Converter<ResponseBody, RespBean<T>> {

    private static final String TAG = AESResponseBodyConverter.class.getSimpleName();

    @Override
    public RespBean<T> convert(ResponseBody responseBody) throws IOException {
        String responseString = responseBody.string();
        Response response = JSON.parseObject(responseString, Response.class);
        try {
            return new RespBean<>(response.getCode(), response.getMsg(), response.getAESDecryptData());
        } catch (Exception e) {
            LogUtils.w(TAG, "convert failed! the response is " + responseString);
            throw e;
        } finally {
            responseBody.close();
        }
    }
}