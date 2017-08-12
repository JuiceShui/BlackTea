package com.shui.blacktea.ui.music.contract;

import com.shui.blacktea.entity.BaiduSong.BaiduSongBillboard;
import com.shui.blacktea.entity.BaiduSong.BaiduSongBillboardEntity;
import com.shui.blacktea.entity.MusicEntity;
import com.yeeyuntech.framework.ui.IYYLoadView;

import java.util.List;

/**
 * Created by JuiceShui on 2017/8/11.
 * To strive,to seek,to find,and not to give up
 */
public interface OnlineMusicListContract {
    interface View extends IYYLoadView {
        void showData(List<BaiduSongBillboardEntity> songList, BaiduSongBillboard billboardInfo, boolean isLoadMore);

        void playOnlineMusic(MusicEntity entity);
    }

    interface Presenter {
        void getData(int cate, boolean isLoadMore);

        void getMusic(String songId);

        void downloadSong(String url);
    }
}