package com.yeeyuntech.framework.data.exception;

import java.io.IOException;

/**
 * Created by adu on 2017/5/20.
 * 没有找到AESKey时抛出此异常
 */

public class NoAESKeyException extends IOException {

    public NoAESKeyException(String message) {
        super(message);
    }
}
