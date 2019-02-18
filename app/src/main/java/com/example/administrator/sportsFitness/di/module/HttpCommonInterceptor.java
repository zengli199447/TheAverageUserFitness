package com.example.administrator.sportsFitness.di.module;

import android.util.Log;

import com.example.administrator.sportsFitness.utils.LogUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;


/**
 * Created by Administrator on 2017/10/30.
 */

public class HttpCommonInterceptor implements Interceptor {
    private Map<String, String> mHeaderParamsMap = new HashMap<>();

    public HttpCommonInterceptor() {
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Log.d("HttpCommonInterceptor", "add common params");
        Request oldRequest = chain.request();

        // 添加新的参数，添加到url 中
        /*HttpUrl.Builder authorizedUrlBuilder = oldRequest.url().newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());*/

        // 新的请求
        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(), oldRequest.body());

//        LogUtil.e("HttpCommonInterceptor - body", oldRequest.body().toString());
//        LogUtil.e("HttpCommonInterceptor - url", oldRequest.url().toString());
//        LogUtil.e("HttpCommonInterceptor - method", oldRequest.method().toString());
//        LogUtil.e("HttpCommonInterceptor - oldRequest", oldRequest.toString());
//        LogUtil.e("HttpCommonInterceptor - headers", oldRequest.headers().toString());
//        LogUtil.e("HttpCommonInterceptor - vars", oldRequest.url().queryParameter("vars").toString());

        //添加公共参数,添加到header中
        if (mHeaderParamsMap.size() > 0) {
            for (Map.Entry<String, String> params : mHeaderParamsMap.entrySet()) {
                requestBuilder.header(params.getKey(), params.getValue());
            }
        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }

    public static class Builder {
        HttpCommonInterceptor mHttpCommonInterceptor;

        public Builder() {
            mHttpCommonInterceptor = new HttpCommonInterceptor();
        }

        public Builder addHeaderParams(String key, String value) {
            mHttpCommonInterceptor.mHeaderParamsMap.put(key, value);
            return this;
        }

        public Builder addHeaderParams(String key, int value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, float value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, long value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, double value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public HttpCommonInterceptor build() {
            return mHttpCommonInterceptor;
        }
    }
}
