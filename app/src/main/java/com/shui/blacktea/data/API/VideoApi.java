package com.shui.blacktea.data.API;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public interface VideoApi {

    /**
     * 视频 http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/10-10.html
     */
    int PAGE_SIZE = 10;
    String HOST = "http://c.m.163.com/";
    String Video = "nc/video/list/";
    String VIDEO_CENTER = "/n/";
    String VIDEO_END_URL = "-10.html";
    // 热点视频
    String VIDEO_HOT_ID = "V9LG4B3A0";
    // 娱乐视频
    String VIDEO_ENTERTAINMENT_ID = "V9LG4CHOR";
    // 搞笑视频
    String VIDEO_FUN_ID = "V9LG4E6VR";
    // 精品视频
    String VIDEO_CHOICE_ID = "00850FRB";

    /**
     * 网易视频列表 例子：http://c.m.163.com/nc/video/list/V9LG4B3A0/n/0-10.html
     *
     * @param id        视频类别id
     * @param startPage 开始的页码
     * @return 被观察者
     */
    /*@GET("nc/video/list/{id}/n/{startPage}-10.html")
    Observable<VideoHotEntity> getHotVideoList(
            @Path("id") String id, @Path("startPage") int startPage);

    @GET("nc/video/list/{id}/n/{startPage}-10.html")
    Observable<VideoEntertainmentEntity> getEntertainmentVideoList(
            @Path("id") String id, @Path("startPage") int startPage);

    @GET("nc/video/list/{id}/n/{startPage}-10.html")
    Observable<VideoFunEntity> getFunVideoList(
            @Path("id") String id, @Path("startPage") int startPage);

    @GET("nc/video/list/{id}/n/{startPage}-10.html")
    Observable<VideoChoiceEntity> getChoiceVideoList(
            @Path("id") String id, @Path("startPage") int startPage);*/
}
