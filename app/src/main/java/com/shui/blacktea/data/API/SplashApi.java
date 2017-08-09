package com.shui.blacktea.data.API;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * Description:
 * Created by Juice_ on 2017/8/9.
 */

public interface SplashApi {
    String HOST = "http://api.seqier.com/";
    String SPLASH_CATE = "api/bing/";

    @Streaming
    @GET(SPLASH_CATE + "{size}")
    Observable<ResponseBody> getSplashImage(@Path("size") String size);
}
