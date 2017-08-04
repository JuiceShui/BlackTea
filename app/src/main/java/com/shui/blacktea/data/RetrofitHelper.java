package com.shui.blacktea.data;

import com.shui.blacktea.App;
import com.shui.blacktea.BuildConfig;
import com.shui.blacktea.config.Constants;
import com.shui.blacktea.data.API.TXApi;
import com.shui.blacktea.data.API.VideoApi;
import com.shui.blacktea.data.response.TXResponse;
import com.shui.blacktea.entity.NewsEntity;
import com.shui.blacktea.entity.VideoChoiceEntity;
import com.shui.blacktea.entity.VideoEntertainmentEntity;
import com.shui.blacktea.entity.VideoFunEntity;
import com.shui.blacktea.entity.VideoHotEntity;
import com.shui.blacktea.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class RetrofitHelper {
    private static OkHttpClient okHttpClient = null;
    private static TXApi txApi = null;
    private static VideoApi videoApi = null;

    private void init() {
        initOkHttp();
        videoApi = getVideoApi();
        txApi = getTxApi();
    }

    @Inject
    public RetrofitHelper() {
        init();
    }

    /**
     * 初始化OkHttp
     */
    private void initOkHttp() {
        /**
         * 参考http://www.jianshu.com/p/93153b34310e
         */
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(Constants.PATH_CACHE);//缓存文件位置
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 64);//缓存文件
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkUtil.isNetWorkAvailable(App.getInstance())) {
                    //没网络时强制使用缓存
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetWorkUtil.isNetWorkAvailable(App.getInstance())) {
                    // 有网络时, 不缓存, 最大保存时长为0 有网络时 设置缓存超时时间0个小时
                    int maxAge = 0;
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("shui")//// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为30天
                    int maxTime = 60 * 60 * 24 * 30;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                            .removeHeader("shui")
                            .build();
                }
                return response;
            }
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        //设置超时
        builder.connectTimeout(12, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    /**
     * video
     *
     * @return
     */
    private static VideoApi getVideoApi() {
        Retrofit videoRetrofit = new Retrofit.Builder()
                .baseUrl(VideoApi.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return videoRetrofit.create(VideoApi.class);
    }

    /**
     * 新闻
     *
     * @return
     */
    private static TXApi getTxApi() {
        Retrofit txRetrofit = new Retrofit.Builder()
                .baseUrl(TXApi.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return txRetrofit.create(TXApi.class);
    }


    /******TXNEWS******/


    public Observable<TXResponse<List<NewsEntity>>> getTXNews(String cate, int num, int page) {
        return txApi.getTXNews(cate, TXApi.KEY, num, page);
    }


    /***********  Video List******************/
    /**
     * 热门视频
     *
     * @param startPage
     * @return
     */
    public Observable<VideoHotEntity> getHotVideoList(int startPage) {
        return videoApi.getHotVideoList(VideoApi.VIDEO_HOT_ID, startPage);
    }

    /**
     * 娱乐视频
     *
     * @param startPage
     * @return
     */
    public Observable<VideoEntertainmentEntity> getEntertainmentVideoList(int startPage) {
        return videoApi.getEntertainmentVideoList(VideoApi.VIDEO_ENTERTAINMENT_ID, startPage);
    }

    /**
     * 搞笑视频
     *
     * @param startPage
     * @return
     */
    public Observable<VideoFunEntity> getFunVideoList(int startPage) {
        return videoApi.getFunVideoList(VideoApi.VIDEO_HOT_ID, startPage);
    }

    /**
     * 精品视频
     *
     * @param startPage
     * @return
     */
    public Observable<VideoChoiceEntity> getChoiceVideoList(int startPage) {
        return videoApi.getChoiceVideoList(VideoApi.VIDEO_HOT_ID, startPage);
    }
}