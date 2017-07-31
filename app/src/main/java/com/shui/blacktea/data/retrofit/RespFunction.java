/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.data.retrofit;

import com.shui.blacktea.App;
import com.yeeyuntech.framework.data.bean.RespBean;
import com.yeeyuntech.framework.data.exception.APIException;
import com.yeeyuntech.framework.data.exception.TokenExpireException;
import com.yeeyuntech.framework.data.exception.TokenInvalidException;
import com.yeeyuntech.framework.utils.LogUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by adu on 2017/4/28.
 * 针对接口请求返回的统一处理
 */

public class RespFunction implements Function<RespBean, RespBean> {

    private static final String TAG = RespFunction.class.getSimpleName();

    @Override
    public RespBean apply(@NonNull RespBean respBean) throws Exception {
        LogUtils.v(TAG, "http response \n" + respBean.toString());
        if (respBean.isLoginExpire()) {
            LogUtils.w(TAG, "token expired! token is " + App.getInstance().getToken());
            throw new TokenExpireException(respBean.getMsg());
        } else if (respBean.isLoginAbnormal()) {
            LogUtils.w(TAG, "token abnormal! token is " + App.getInstance().getToken());
            throw new TokenInvalidException(respBean.getMsg());
        } else if (respBean.getCode() != 0) {
            throw new APIException(respBean.getMsg());
        } else if (respBean.getMsg().equals("登录异常，请重新登录")) {
            throw new TokenInvalidException(respBean.getMsg());
        } else {
            return respBean;
        }
    }
}
