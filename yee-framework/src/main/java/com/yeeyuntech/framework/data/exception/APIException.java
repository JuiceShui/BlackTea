package com.yeeyuntech.framework.data.exception;

/**
 * Created by adu on 2017/5/20.
 * 网络请求返回code不为0时抛出此异常
 */

public class APIException extends Exception {

    public APIException(String message) {
        super(message);
    }
}
