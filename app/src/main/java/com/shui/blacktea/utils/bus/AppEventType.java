/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.utils.bus;

/**
 * Created by ADU on 2016/4/24.
 * 定义事件类型
 */
public enum AppEventType {

    // 首次设置用户信息
    SET_USER_INFO_SUCCESS,

    //头像变化
    AVATAR_CHANGE,

    //userInfo变化
    USER_INFO_CHANGE,

    // 修改某项信息，主动要求刷新UserInfo
    UPDATE_USER_INFO,

    // 登录成功
    LOGIN_SUCCESS
}
