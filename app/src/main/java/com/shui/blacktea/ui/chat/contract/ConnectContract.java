package com.shui.blacktea.ui.chat.contract;

import com.shui.blacktea.entity.UserEntity;
import com.yeeyuntech.framework.ui.IYYLoadView;

import java.util.List;

/**
 * Created by JuiceShui on 2017/8/19.
 * To strive,to seek,to find,and not to give up
 */
public interface ConnectContract {
    interface View extends IYYLoadView {
        void showUserLists(List<UserEntity> list);
    }

    interface Presenter {
        void getUserLists();
    }
}