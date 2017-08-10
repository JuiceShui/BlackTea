package com.shui.blacktea.data.API;

import com.shui.blacktea.entity.BaiduSong.BaiduSongArtistSongsListEntity;
import com.shui.blacktea.entity.BaiduSong.BaiduSongArtisteEntity;
import com.shui.blacktea.entity.BaiduSong.BaiduSongBillboardListEntity;
import com.shui.blacktea.entity.BaiduSong.BaiduSongLrcEntity;
import com.shui.blacktea.entity.BaiduSong.BaiduSongPlayEntity;
import com.shui.blacktea.entity.BaiduSong.BaiduSongSearchEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Description:
 * Created by Juice_ on 2017/8/10.
 */

public interface BaiduMusicApi {
    String HOST = "http://tingapi.ting.baidu.com/";
    String SUB_HOST = "v1/restserver/ting";
    String METHOD_LIST = "baidu.ting.billboard.billList";
    String METHOD_SEARCH = "baidu.ting.search.catalogSug";
    String METHOD_PLAY = "baidu.ting.song.play";
    String METHOD_LRC = "baidu.ting.song.lry";
    String METHOD_ARTIST = "baidu.ting.artist.getInfo";
    String METHOD_ARTIST_SONGS = "baidu.ting.artist.getSongList";
    String use_cluster = "use_cluster";
    int DEFAULT_LIST_SIZE = 3;
    int DEFAULT_OFFSET = 0;

    int DEFAULT_LIST_SIZE_10 = 10;
    int TYPE_NEW = 1;//新歌
    int TYPE_HOT = 2;//热歌
    int TYPE_ROCK = 11;//摇滚榜单
    int TYPE_JAZZ = 12;//爵士榜单
    int TYPE_POP = 16;//流行榜单
    int TYPE_EUR = 21;//欧美金曲榜单
    int TYPE_CLASSICAL = 22;//经典老歌
    int TYPE_LOVER = 23;//情歌
    int TYPE_TV = 24;//影视
    int TYPE_NET = 25;//网络
    int TYPE_BILLBOARD = 8;//Billboard
    int TYPE_CHINESE = 18;//中文榜单
    int TYPE_KTV = 6;//KTV榜单
    int order = 2;

    /**
     * 获取列表
     *
     * @param method
     * @param type
     * @param size
     * @param offset
     * @return
     */
    @GET(SUB_HOST)
    Observable<BaiduSongBillboardListEntity> getSongList(@Query("method") String method, @Query("type") int type,
                                                         @Query("size") int size, @Query("offset") int offset);

    /**
     * 搜索歌曲
     *
     * @param method
     * @param keyWord
     * @return
     */
    @GET(SUB_HOST)
    Observable<BaiduSongSearchEntity> searchSong(@Query("method") String method, @Query("query") String keyWord);

    /**
     * 根据id 播放音乐
     *
     * @param method
     * @param songId
     * @return
     */
    @GET(SUB_HOST)
    Observable<BaiduSongPlayEntity> playSong(@Query("method") String method, @Query("songid") String songId);

    /**
     * 根据id 获取歌词
     *
     * @param method
     * @param songId
     * @return
     */
    @GET(SUB_HOST)
    Observable<BaiduSongLrcEntity> getSongLrc(@Query("method") String method, @Query("songid") String songId);

    /**
     * 根据歌手id  获取歌手信息
     *
     * @param method
     * @param tinguid
     * @return
     */
    @GET(SUB_HOST)
    Observable<BaiduSongArtisteEntity> getArtist(@Query("method") String method, @Query("tinguid") String tinguid);

    /**
     * 根据歌手id  获取歌手歌曲信息
     * method=baidu.ting.artist.getSongList&tinguid=877578&limits=6&use_cluster=1&order=2
     * tinguid = 877578//歌手ting id
     * limits = 6//返回条目数量
     * 其他就不用管了
     *
     * @param method
     * @param tinguid
     * @return
     */
    @GET(SUB_HOST)
    Observable<BaiduSongArtistSongsListEntity> getArtistSongs(@Query("method") String method, @Query("tinguid") String tinguid,
                                                              @Query("limits") int limits, @Query("use_cluster") String use_cluster, @Query("order") int order);
}
