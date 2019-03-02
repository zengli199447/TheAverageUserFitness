package com.example.administrator.sportsFitness.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.util.ArrayList;

/**
 * 作者：真理 Created by Administrator on 2018/10/31.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class AlbumBuilder {

    String TAG = getClass().getSimpleName();
    Context context;

    public AlbumBuilder(Context context) {
        this.context = context;
    }

    /**
     * 单选
     */
    public void ImageSingleSelection() {
        Album.image(context).singleChoice().camera(true).columnCount(3).widget(initWidget(context)).onResult(new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                String theAssignment = result.get(0).getPath();
                DataClass.USERPHOTO = theAssignment;
                RxBus.getDefault().post(new CommonEvent(EventCode.PICTURE, theAssignment));
                LogUtil.e(TAG, "albumFile : " + "file://" + theAssignment);
                if (onReturnPhotoListener != null)
                    onReturnPhotoListener.onReturnPhotoListener(new StringBuffer().append("file://").append(theAssignment).toString());
            }
        }).onCancel(new Action<String>() {
            @Override
            public void onAction(@NonNull String result) {
                if (onReturnPhotoListener != null)
                    onReturnPhotoListener.onReturnPhotoListener(null);
            }
        }).start();

    }

    /**
     * 视频单选
     */
    public void VideoSingleSelection() {
        Album.video(context).singleChoice()
                .camera(true)
                .columnCount(3)
                .widget(initWidget(context))
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        LogUtil.e(TAG, "albumFile : " + "file://" + result.get(0).getPath());
                        DataClass.AlbumVideoFileList.clear();
                        DataClass.AlbumVideoFileList.addAll(result);
                        RxBus.getDefault().post(new CommonEvent(EventCode.PHOTO_REFRESH));
                    }
                }).onCancel(new Action<String>() {
            @Override
            public void onAction(@NonNull String result) {

            }
        }).start();
    }

    /**
     * 多选
     *
     * @param count 数量限制
     */
    public void ImageSelection(int count) {
        Album.image(context).multipleChoice().camera(true).columnCount(3).selectCount(count).widget(initWidget(context)).checkedList(DataClass.AlbumFileList)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        DataClass.AlbumFileList.clear();
                        DataClass.AlbumFileList.addAll(result);
                        RxBus.getDefault().post(new CommonEvent(EventCode.PHOTO_REFRESH));
                    }
                }).onCancel(new Action<String>() {
            @Override
            public void onAction(@NonNull String result) {
            }
        }).start();
    }

    /**
     * 画廊
     *
     * @param imageList   画廊数据集
     * @param isCheckable 是否可以操作(正反选)
     * @param position    当前查看下标
     */
    public void ImageTheExhibition(ArrayList<String> imageList, final boolean isCheckable, int position) {
        Album.gallery(context).currentPosition(position).checkedList(imageList).checkable(isCheckable)
                .onResult(new Action<ArrayList<String>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<String> result) {
                        if (isCheckable) {
                            DataClass.AlbumFileList.clear();
                            for (String s : result) {
                                AlbumFile albumFile = new AlbumFile();
                                albumFile.setPath(s);
                                DataClass.AlbumFileList.add(albumFile);
                            }
                            RxBus.getDefault().post(new CommonEvent(EventCode.PHOTO_REFRESH));
                        }
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }

    //Album样式
    private Widget initWidget(Context context) {
        Widget widget = Widget.newDarkBuilder(context)
                .title("媒体浏览")
                .statusBarColor(context.getResources().getColor(R.color.blue_bar))
                .toolBarColor(context.getResources().getColor(R.color.blue_bar))
                .navigationBarColor(Color.WHITE)
                .mediaItemCheckSelector(Color.WHITE, Color.WHITE)
                .bucketItemCheckSelector(Color.WHITE, Color.WHITE)
                .buttonStyle(Widget.ButtonStyle.newDarkBuilder(context).setButtonSelector(Color.WHITE, Color.WHITE).build()).build();

        return widget;
    }

    public interface OnReturnPhotoListener {
        void onReturnPhotoListener(String photo);
    }

    private OnReturnPhotoListener onReturnPhotoListener;

    public void setOnReturnPhotoListener(OnReturnPhotoListener onReturnPhotoListener) {
        this.onReturnPhotoListener = onReturnPhotoListener;
    }


}
