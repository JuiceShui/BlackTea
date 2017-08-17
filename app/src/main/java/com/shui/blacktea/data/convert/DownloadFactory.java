package com.shui.blacktea.data.convert;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.annotations.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Description:
 * Created by Juice_ on 2017/8/14.
 */

public class DownloadFactory extends Converter.Factory {
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new DownloadConvert();
    }

    private class DownloadConvert implements Converter<ResponseBody, InputStream> {

        @Override
        public InputStream convert(@NonNull ResponseBody value) throws IOException {
            return value.byteStream();
        }
    }
}
