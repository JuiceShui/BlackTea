package com.yeeyuntech.framework.data.exception;

/**
 * Created by adu on 2017/5/20.
 * token异常时抛出此异常，一般为异常登录导致
 */

public class TokenInvalidException extends TokenException {

    public TokenInvalidException(String message) {
        super(message);
    }
}
