package com.yeeyuntech.framework.data.retrofit.converter;

import com.alibaba.fastjson.JSON;
import com.yeeyuntech.framework.data.bean.RespBean;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by adu on 2017/4/27.
 */

class RSAResponseBodyConverter<T> implements Converter<ResponseBody, RespBean<T>> {

    @Override
    public RespBean<T> convert(ResponseBody responseBody) throws IOException {
        String responseString = responseBody.string();
        Response response = JSON.parseObject(responseString, Response.class);
        try {
            return new RespBean<>(response.getCode(), response.getMsg(), response.getRSADecryptData());
        } finally {
            responseBody.close();
        }
    }
}
