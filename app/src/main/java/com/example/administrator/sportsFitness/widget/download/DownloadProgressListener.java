package com.example.administrator.sportsFitness.widget.download;

import java.io.File;

/**
 * 作者：真理 Created by Administrator on 2019/3/4.
 * 邮箱：229017464@qq.com
 * remark:
 */
public interface DownloadProgressListener {

    void onFinish(File file);
    void onProgress(int progress);
    void onFailed(String errMsg);

}
