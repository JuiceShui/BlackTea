package com.shui.blacktea.ui.video.contract;

import com.shui.blacktea.entity.VideoEntity;
import com.yeeyuntech.framework.ui.IYYLoadView;

import java.util.List;

/**
 * Created by JuiceShui on 2017/8/4.
 * To strive,to seek,to find,and not to give up
 */
public interface VideoContract {
    interface View extends IYYLoadView {
        void showVideoList(List<VideoEntity> list, boolean isLoadMore);
    }

    interface Presenter {
        void getVideoList(String cate, boolean isLoadMore);
    }
}