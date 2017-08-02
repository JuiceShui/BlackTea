package com.shui.blacktea.data.API;

import com.shui.blacktea.data.response.TXResponse;
import com.shui.blacktea.entity.NewsEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public interface TXApi {
    int PAGE_SIZE = 10;
    String HOST = "http://api.tianapi.com/";
    String KEY = "f8d6bf3d8a2e5daa7fd6eaf1cfbe5439";
    String NBA = "nba";//Nba新闻
    String FOOTBALL = "football";//足球
    String SOCIAL = "social";//社会
    String WORLD = "world";//国际新闻
    String ENTERTAINMENT = "huabian";//娱乐新闻
    String SPORT = "tiyu";//体育新闻
    String NATION = "guonei";//国内新闻
    String WAR = "military";//军事新闻

    @GET("{cate}/")
    Observable<TXResponse<List<NewsEntity>>> getTXNews(@Path("cate") String cate, @Query("key") String key, @Query("num") int num, @Query("page") int page);
}
