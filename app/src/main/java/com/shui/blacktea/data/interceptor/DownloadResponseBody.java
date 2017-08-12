package com.shui.blacktea.data.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Description:
 * Created by Juice_ on 2017/8/12.
 */

public class DownloadResponseBody extends ResponseBody {
    private ResponseBody mResponseBody;
    private DownloadListener mListener;
    private BufferedSource mBufferedSource;

    public DownloadResponseBody(ResponseBody mResponseBody, DownloadListener mListener) {
        this.mResponseBody = mResponseBody;
        this.mListener = mListener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long byteHasRead = super.read(sink, byteCount);
                totalBytesRead += byteHasRead != -1 ? byteHasRead : 0;
                if (null != mListener) {
                    mListener.update(totalBytesRead, contentLength(), byteHasRead == -1);
                }
                return byteHasRead;
            }
        };
    }
}
