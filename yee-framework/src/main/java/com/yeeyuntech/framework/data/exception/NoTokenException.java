package com.yeeyuntech.framework.data.exception;

import java.io.IOException;

/**
 * Created by adu on 2017/5/3.
 */

public class NoTokenException extends IOException {

    public NoTokenException(String message) {
        super(message);
    }
}
