package com.shui.blacktea.data.API;

import com.shui.blacktea.data.response.YYResponse;
import com.shui.blacktea.entity.VideoEntity;
import com.shui.blacktea.entity.WeiBoEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Description:
 * Created by Juice_ on 2017/8/5.
 */

public interface YYApi {
    String HOST = "http://route.showapi.com/";
    String APPID = "43457";
    String APPSIGN = "a15896a6369b47cf89591702f74f9970";
    int PAGE_SIZE = 20;
    //视频
    String CATE_VIDEO = "255-1";
    String TYPE_VIDEO = "41";
    //微博热搜
    String CATE_WEIBO = "254-1";
    int TYPE_WEIBO_PET = 9;//宠物
    int TYPE_WEIBO_FUN = 11;//搞笑
    int TYPE_WEIBO_FILM = 18;//电影
    int TYPE_WEIBO_MIX = 19;//综艺
    int TYPE_WEIBO_ENTERTAINMENT = 41;//娱乐
    int TYPE_WEIBO_GAME = 38;//游戏
    int TYPE_WEIBO_SPORT = 23;//体育
    int TYPE_WEIBO_HISTOR = 3;//历史
    int TYPE_WEIBO_ARMY = 3;//军事
    String TYPE_WEIBO_SPACE_DAY = "day";
    String TYPE_WEIBO_SPACE_WEEK = "week";

    /**
     * 视频
     *
     * @param type
     * @param title
     * @param page
     * @return
     */
    @GET(CATE_VIDEO + "?showapi_appid=" + APPID + "&showapi_sign=" + APPSIGN)
    Observable<YYResponse<VideoEntity>> getYYVideo(@Query("type") String type, @Query("title") String title, @Query("page") int page);

    /**
     * 微博
     *
     * @param typeId
     * @param space
     * @param page
     * @return
     */
    @GET(CATE_WEIBO + "?showapi_appid=" + APPID + "&showapi_sign=" + APPSIGN)
    Observable<YYResponse<WeiBoEntity>> getYYWeiBo(@Query("typeId") int typeId, @Query("space") String space, @Query("page") int page);

}
