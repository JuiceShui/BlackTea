package com.yeeyuntech.framework.data.bean;

import com.alibaba.fastjson.JSON;

/**
 * Created by adu on 2017/4/24.
 * 服务器返回数据解密后的封装类
 */

public class RespBean<T> {

    private int code;
    private String msg;
    private String data;

    private T bean;

    public RespBean() {
    }

    public RespBean(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        if (data != null) {
            setBean(data);
        }
    }

    // 判断是否是登录过期
    public boolean isLoginExpire() {
        return code == 5;
    }

    // 判断是否是异常登录导致token失效
    public boolean isLoginAbnormal() {
        return code == 6 || code == 4;
    }

    // 无效的时间戳
    public boolean isTimestampExpire() {
        return code == 1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public T getBean() {
        return bean;
    }

    @SuppressWarnings("unchecked")
    private void setBean(String s) {
        try {
            bean = (T) JSON.parse(data);
        } catch (Exception ignore) {

        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
