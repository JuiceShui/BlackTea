package com.shui.blacktea.ui.chat.contract;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.shui.blacktea.entity.type.MessageType;
import com.yeeyuntech.framework.ui.IYYLoadView;

/**
 * Created by JuiceShui on 2017/8/18.
 * To strive,to seek,to find,and not to give up
 */
public interface OnChattingContract {
    interface View extends IYYLoadView {
        //展示信息
        void showMessage(AVIMMessage message);
    }

    interface Presenter {
        //获取信息
        void getMessage(MessageType message);

        //发送信息
        void postMessage();
    }
}