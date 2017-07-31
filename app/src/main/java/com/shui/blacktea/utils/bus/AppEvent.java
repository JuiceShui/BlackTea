/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.utils.bus;

/**
 * Created by ADU on 2016/4/24.
 */
public class AppEvent {

    AppEventType type;
    String datas;

    public AppEvent() {
    }

    public AppEvent(AppEventType type) {
        this(type, "");
    }

    public AppEvent(AppEventType type, String datas) {
        this.type = type;
        this.datas = datas;
    }

    public AppEventType getType() {
        return type;
    }

    public void setType(AppEventType type) {
        this.type = type;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }
}
