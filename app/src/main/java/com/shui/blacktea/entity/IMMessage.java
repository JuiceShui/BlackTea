package com.shui.blacktea.entity;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMLocationMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.avos.avoscloud.im.v2.messages.AVIMVideoMessage;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Description:
 * Created by Juice_ on 2017/8/18.
 */

public class IMMessage implements MultiItemEntity {
    public static final int TYPE_MESSAGE_TEXT = 1;
    public static final int TYPE_MESSAGE_IMAGE = 2;
    public static final int TYPE_MESSAGE_AUDIO = 3;
    public static final int TYPE_MESSAGE_VIDEO = 4;
    public static final int TYPE_MESSAGE_LOCATION = 5;
    public static final int TYPE_MESSAGE_FILE = 6;
    public static final int TYPE_COMMING = 1;
    public static final int TYPE_POST = 2;
    private int type;
    private AVIMMessage message;
    private int messageType;
    private String avatar;
    private String userId;

    public IMMessage(AVIMMessage message) {
        this.message = message;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getMessageType() {
        return messageType;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUserId() {
        return userId;
    }

    public int getType() {
        if (message instanceof AVIMTextMessage) {
            return TYPE_MESSAGE_TEXT;
        } else if (message instanceof AVIMImageMessage) {
            return TYPE_MESSAGE_IMAGE;
        } else if (message instanceof AVIMAudioMessage) {
            return TYPE_MESSAGE_AUDIO;
        } else if (message instanceof AVIMVideoMessage) {
            return TYPE_MESSAGE_VIDEO;
        } else if (message instanceof AVIMLocationMessage) {
            return TYPE_MESSAGE_LOCATION;
        } else {
            return TYPE_MESSAGE_FILE;
        }
    }

    /*public void setType(int type) {
        this.type = type;
    }*/

    public AVIMMessage getMessage() {
        return message;
    }

   /* public void setMessage(AVIMMessage message) {
        this.message = message;
    }*/

    @Override
    public int getItemType() {
        return getType();
    }
}
