package com.shui.blacktea.data.API;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Description:
 * Created by Juice_ on 2017/8/12.
 */

public interface DownloadApi {
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
