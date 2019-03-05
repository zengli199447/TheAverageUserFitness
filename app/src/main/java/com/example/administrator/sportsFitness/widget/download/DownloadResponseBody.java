package com.example.administrator.sportsFitness.widget.download;

import java.io.IOException;
import java.util.concurrent.Executor;


import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 作者：真理 Created by Administrator on 2019/3/5.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class DownloadResponseBody extends ResponseBody {
    private ResponseBody responseBody;
    private DownloadProgressListener downloadListener;
    private BufferedSource bufferedSource;
    private Executor executor;

    public DownloadResponseBody(ResponseBody responseBody, Executor executor, DownloadProgressListener downloadListener) {
        this.responseBody = responseBody;
        this.downloadListener = downloadListener;
        this.executor = executor;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                final long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                if (null != downloadListener) {
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    final int progress = (int) (totalBytesRead * 100 / responseBody.contentLength());
                    if (executor != null) {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                downloadListener.onProgress(progress);
                            }
                        });
                    } else {
                        downloadListener.onProgress(progress);
                    }
                }
                return bytesRead;
            }
        };
    }

}
