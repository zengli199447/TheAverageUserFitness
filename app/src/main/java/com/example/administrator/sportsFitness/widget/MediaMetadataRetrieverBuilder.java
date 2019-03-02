package com.example.administrator.sportsFitness.widget;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.util.HashMap;

/**
 * 作者：真理 Created by Administrator on 2019/2/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MediaMetadataRetrieverBuilder {

    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try { //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            // 获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }


}
