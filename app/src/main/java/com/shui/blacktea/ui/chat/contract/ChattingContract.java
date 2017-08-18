package com.shui.blacktea.ui.chat.contract;

import com.yeeyuntech.framework.ui.IYYLoadView;

/**
 * Created by JuiceShui on 2017/8/18.
 * To strive,to seek,to find,and not to give up
 */
public interface ChattingContract {
    interface View extends IYYLoadView {
        //展示新消息
        void showMessage();

        //展示对话信息列表
        void showConversationInfo();
    }

    interface Presenter {
        //获取信息
        void getMessage();
    }
}