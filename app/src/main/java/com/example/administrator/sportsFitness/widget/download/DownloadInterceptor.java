package com.example.administrator.sportsFitness.widget.download;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 作者：真理 Created by Administrator on 2019/3/5.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class DownloadInterceptor implements Interceptor {
    private DownloadProgressListener listener;
    private Executor executor;

    public DownloadInterceptor(Executor executor, DownloadProgressListener listener) {
        this.listener = listener;
        this.executor = executor;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder().body(new DownloadResponseBody(originalResponse.body(), executor, listener)).build();
    }

}
