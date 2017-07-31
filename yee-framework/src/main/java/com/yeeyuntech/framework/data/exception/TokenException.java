package com.yeeyuntech.framework.data.exception;

/**
 * Created by adu on 2017/5/20.
 * token过期或异常登录导致失效时抛出此异常
 */

public class TokenException extends APIException {

    public TokenException(String message) {
        super(message);
    }
}
