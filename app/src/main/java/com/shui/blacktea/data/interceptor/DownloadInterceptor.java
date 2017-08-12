package com.shui.blacktea.data.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Description:
 * Created by Juice_ on 2017/8/12.
 */

public class DownloadInterceptor implements Interceptor {
    private DownloadListener mListener;

    public DownloadInterceptor(DownloadListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originResponse = chain.proceed(chain.request());
        return originResponse.newBuilder()
                .body(new DownloadResponseBody(originResponse.body(), mListener))
                .build();
    }
}
