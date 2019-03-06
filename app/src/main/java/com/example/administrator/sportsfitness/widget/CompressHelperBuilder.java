package com.example.administrator.sportsfitness.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.nanchen.compresshelper.CompressHelper;

import java.io.File;

/**
 * 作者：真理 Created by Administrator on 2019/2/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CompressHelperBuilder {


    public static File CompressHelperFile(Context context, File file, String fileName) {

        File newFile = new CompressHelper.Builder(context)
                .setMaxWidth(720)  // 默认最大宽度为720
                .setMaxHeight(960) // 默认最大高度为960
                .setQuality(80)    // 默认压缩质量为80
                .setFileName(fileName) // 设置你需要修改的文件名
                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .build()
                .compressToFile(file);

        return newFile;
    }


}
