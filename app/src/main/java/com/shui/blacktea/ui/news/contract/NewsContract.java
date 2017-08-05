package com.shui.blacktea.ui.news.contract;

import com.shui.blacktea.entity.WeiBoEntity;
import com.yeeyuntech.framework.ui.IYYLoadView;

import java.util.List;

/**
 * Created by JuiceShui on 2017/8/1.
 * To strive,to seek,to find,and not to give up
 */
public interface NewsContract {
    interface View extends IYYLoadView {
        void showNewsResult(List<WeiBoEntity> list, boolean isLoadMore);
    }

    interface Presenter {
        void getNewsList(int type, boolean isLoadMore);
    }
}