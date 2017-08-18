package com.shui.blacktea.entity.type;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;

/**
 * Description:
 * Created by Juice_ on 2017/8/18.
 */

public class MessageType {
    private AVIMMessage message;
    private AVIMConversation conversation;
    private AVIMClient client;

    public MessageType(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
        this.message = message;
        this.conversation = conversation;
        this.client = client;
    }

    public AVIMMessage getMessage() {
        return message;
    }

    public void setMessage(AVIMMessage message) {
        this.message = message;
    }

    public AVIMConversation getConversation() {
        return conversation;
    }

    public void setConversation(AVIMConversation conversation) {
        this.conversation = conversation;
    }

    public AVIMClient getClient() {
        return client;
    }

    public void setClient(AVIMClient client) {
        this.client = client;
    }
}
