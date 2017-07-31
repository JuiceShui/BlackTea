package com.yeeyuntech.framework.data.exception;

/**
 * Created by adu on 2017/5/20.
 * token过期时抛出此异常，一般为长时间未登录导致
 */

public class TokenExpireException extends TokenException {

    public TokenExpireException(String message) {
        super(message);
    }
}
