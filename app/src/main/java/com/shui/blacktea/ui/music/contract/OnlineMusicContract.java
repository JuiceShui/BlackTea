package com.shui.blacktea.ui.music.contract;

import com.shui.blacktea.entity.BaiduSong.SongListEntity;
import com.yeeyuntech.framework.ui.IYYLoadView;

import java.util.List;

/**
 * Created by JuiceShui on 2017/8/10.
 * To strive,to seek,to find,and not to give up
 */
public interface OnlineMusicContract {
    interface View extends IYYLoadView {
        void showData(List<SongListEntity> list);
    }

    interface Presenter {
        void getData();
    }
}