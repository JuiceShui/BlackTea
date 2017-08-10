package com.shui.blacktea.data;

import com.shui.blacktea.App;
import com.shui.blacktea.BuildConfig;
import com.shui.blacktea.config.Constants;
import com.shui.blacktea.data.API.BaiduMusicApi;
import com.shui.blacktea.data.API.TXApi;
import com.shui.blacktea.data.API.YYApi;
import com.shui.blacktea.data.response.TXResponse;
import com.shui.blacktea.data.response.YYResponse;
import com.shui.blacktea.entity.BaiduSong.BaiduSongArtistSongsListEntity;
import com.shui.blacktea.entity.BaiduSong.BaiduSongArtisteEntity;
import com.shui.blacktea.entity.BaiduSong.BaiduSongBillboardListEntity;
import com.shui.blacktea.entity.BaiduSong.BaiduSongLrcEntity;
import com.shui.blacktea.entity.BaiduSong.BaiduSongPlayEntity;
import com.shui.blacktea.entity.BaiduSong.BaiduSongSearchEntity;
import com.shui.blacktea.entity.NewsEntity;
import com.shui.blacktea.entity.SplashImgEntity;
import com.shui.blacktea.entity.VideoEntity;
import com.shui.blacktea.entity.WeiBoEntity;
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
    private static YYApi yyApi = null;
    private static BaiduMusicApi bdApi = null;

    private void init() {
        initOkHttp();
        txApi = getTxApi();
        yyApi = getYYApi();
        bdApi = getBDApi();
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

    /**
     * @return
     */
    private YYApi getYYApi() {
        Retrofit yyRetrofit = new Retrofit.Builder()
                .baseUrl(YYApi.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return yyRetrofit.create(YYApi.class);
    }

    /**
     * 百度音乐
     *
     * @return
     */
    private BaiduMusicApi getBDApi() {
        Retrofit baiduRetrofit = new Retrofit.Builder()
                .baseUrl(BaiduMusicApi.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return baiduRetrofit.create(BaiduMusicApi.class);
    }

    /******TXNEWS******/


    public Observable<TXResponse<List<NewsEntity>>> getTXNews(String cate, int num, int page) {
        return txApi.getTXNews(cate, TXApi.KEY, num, page);
    }
    /********YY API************/
    /**
     * 获取视频
     *
     * @param type
     * @param title
     * @param page
     * @return
     */
    public Observable<YYResponse<VideoEntity>> getVideo(String type, String title, int page) {
        return yyApi.getYYVideo(type, title, page);
    }

    /**
     * 获取微博
     *
     * @param typeId
     * @param space
     * @param page
     * @return
     */
    public Observable<YYResponse<WeiBoEntity>> getWeiBoNew(int typeId, String space, int page) {
        return yyApi.getYYWeiBo(typeId, space, page);
    }

    /**
     * 获取splash图片
     *
     * @return
     */
    public Observable<SplashImgEntity> getSplashImg() {
        return yyApi.getYYSplashImg();
    }


    /********baidu APi***********/
    /**
     * 获取榜单列表
     *
     * @param type
     * @return
     */
    public Observable<BaiduSongBillboardListEntity> getBillboard(int type, int size, int offset) {
        return bdApi.getSongList(BaiduMusicApi.METHOD_LIST, type, size, offset);
    }

    /**
     * 搜索音乐
     *
     * @param keyWord
     * @return
     */
    public Observable<BaiduSongSearchEntity> searchMusic(String keyWord) {
        return bdApi.searchSong(BaiduMusicApi.METHOD_SEARCH, keyWord);
    }

    /**
     * 根据id播放音乐
     *
     * @param songId
     * @return
     */
    public Observable<BaiduSongPlayEntity> playSong(String songId) {
        return bdApi.playSong(BaiduMusicApi.METHOD_PLAY, songId);
    }

    /**
     * 根据音乐id 获取歌词
     *
     * @param songId
     * @return
     */
    public Observable<BaiduSongLrcEntity> getSongLrc(String songId) {
        return bdApi.getSongLrc(BaiduMusicApi.METHOD_LRC, songId);
    }

    /**
     * 根据歌手id 获取歌手信息
     *
     * @param tingUid
     * @return
     */
    public Observable<BaiduSongArtisteEntity> getArtistInfo(String tingUid) {
        return bdApi.getArtist(BaiduMusicApi.METHOD_ARTIST, tingUid);
    }

    /**
     * 根据歌手id获取 歌手的作品列表
     *
     * @param tingUid
     * @param limits
     * @return
     */
    public Observable<BaiduSongArtistSongsListEntity> getArtistSongs(String tingUid, int limits) {
        return bdApi.getArtistSongs(BaiduMusicApi.METHOD_ARTIST_SONGS,
                tingUid, limits, BaiduMusicApi.use_cluster, BaiduMusicApi.order);
    }
}