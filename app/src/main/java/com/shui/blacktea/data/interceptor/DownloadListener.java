package com.shui.blacktea.data.interceptor;

/**
 * Description:
 * Created by Juice_ on 2017/8/12.
 */

public interface DownloadListener {
    void update(long byteHasRead, long contentLength, boolean done);
}
