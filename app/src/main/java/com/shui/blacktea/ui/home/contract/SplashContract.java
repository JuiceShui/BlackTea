package com.shui.blacktea.ui.home.contract;

import com.shui.blacktea.entity.SplashImgEntity;
import com.yeeyuntech.framework.ui.IYYLoadView;

/**
 * Created by JuiceShui on 2017/8/9.
 * To strive,to seek,to find,and not to give up
 */
public interface SplashContract {
    interface View extends IYYLoadView {
        void showSplashImg(SplashImgEntity entity);

        void jumpToMain();
    }

    interface Presenter {
        void getSplashImg();
    }
}