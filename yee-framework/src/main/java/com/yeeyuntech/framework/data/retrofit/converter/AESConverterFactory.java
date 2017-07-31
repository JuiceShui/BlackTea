package com.yeeyuntech.framework.data.retrofit.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class AESConverterFactory extends Converter.Factory {

    public static AESConverterFactory create() {
        return new AESConverterFactory();
    }

    private AESConverterFactory() {
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return new AESResponseBodyConverter<>();
    }
}