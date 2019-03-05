package com.example.administrator.sportsFitness.widget.download;

import android.os.Handler;
import android.os.Looper;

import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.http.api.MyApis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 作者：真理 Created by Administrator on 2019/3/5.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class DownloadUtil {

    private static final String TAG = DownloadUtil.class.getSimpleName();
    private static final int DEFAULT_TIMEOUT = 15;
    private DownloadProgressListener listener;
    private final OkHttpClient client;
    private final Executor executor;

    public DownloadUtil(final DownloadProgressListener listener) {
        this.listener = listener;
        executor = new MainThreadExecutor();
        DownloadInterceptor interceptor = new DownloadInterceptor(executor, listener);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    public void downloadFile(final String rUrl, final String filePath) {

        final MyApis api = new Retrofit.Builder()
                .baseUrl(DataClass.URL)
                .client(client)
                .build()
                .create(MyApis.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<ResponseBody> result = api.DownloadFile(rUrl).execute();
                    final File file = writeFile(filePath, result.body().byteStream());
                    if (listener != null) {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFinish(file);
                            }
                        });
                    }

                } catch (final IOException e) {
                    if (listener != null) {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFailed(e.getMessage());
                            }
                        });

                    }
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private File writeFile(String filePath, InputStream ins) {
        if (ins == null)
            return null;
        File file = new File(filePath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len;
            while ((len = ins.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new DownloadException(e.getMessage(), e);
        } finally {
            try {
                ins.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private class DownloadException extends RuntimeException {
        public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private class MainThreadExecutor implements Executor {
        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable r) {
            handler.post(r);
        }
    }


}
