package com.example.administrator.sportsFitness.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.global.MyApplication;
import com.example.administrator.sportsFitness.model.bean.ChangeViewLayoutParamsBean;
import com.example.administrator.sportsFitness.ui.holder.MyViewHolder;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.MediaMetadataRetrieverBuilder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 作者：真理 Created by Administrator on 2018/11/13.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class RecyclerChildAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<String> list;
    boolean viewStatus;
    boolean status;
    int parentIndex;

    /**
     * @param context 文络
     * @param list    数据集
     * @param status  列表、详情 宽度切换标示
     */
    public RecyclerChildAdapter(Context context, List<String> list, boolean status, int parentIndex) {
        this.context = context;
        this.status = status;
        this.parentIndex = parentIndex;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic_photo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final String path = list.get(position);
        final ImageView photo_img = holder.itemView.findViewById(R.id.photo_img);
        final ImageView photo_cover_img = holder.itemView.findViewById(R.id.photo_cover_img);
        final TextView gif_tag = holder.itemView.findViewById(R.id.gif_tag);

        int width = 0;
        if (status) {
            width = DataClass.WINDOWS_WIDTH - 70;
        } else {
            width = DataClass.WINDOWS_WIDTH - 30;
        }
        photo_cover_img.setVisibility(View.GONE);

        if (path.contains(".gif")) {
            gif_tag.setVisibility(View.VISIBLE);
        } else {
            gif_tag.setVisibility(View.GONE);
        }

        if (viewStatus) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (list.size() > 2) {
                layoutParams.height = SystemUtil.dp2px(context, (width / 3) - 2);
                holder.itemView.setLayoutParams(layoutParams);
                intoImgLoadingType(photo_img, path, null);
            } else if (list.size() == 2) {
                layoutParams.height = SystemUtil.dp2px(context, (width / 3) + 10);
                holder.itemView.setLayoutParams(layoutParams);
                intoImgLoadingType(photo_img, path, null);
            } else {
                if (SystemUtil.JudgeNetFilePathType(path)) {
                    ChangeVideoCover(layoutParams, holder.itemView, photo_img, photo_cover_img, path, width, position);
                } else {
                    ChangeViewLayoutParams(layoutParams, holder.itemView, photo_img, photo_cover_img, path, width, position);
                }
            }
        }

        photo_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childClickListener != null)
                    childClickListener.onChildClickListener(position, parentIndex);
            }
        });
    }

    private void ChangeVideoCover(final ViewGroup.LayoutParams layoutParams, final View view, final ImageView photo_img, ImageView photo_cover_img, final String path, final int width, int position) {
        view.setLayoutParams(layoutParams);
        photo_cover_img.setVisibility(View.VISIBLE);
        if (DataClass.ChangeViewLayoutParamsHashMap.get(path) != null)
            RefreshViewLayoutParams(true, layoutParams, view, path, photo_img, width, null);

        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                final Bitmap videoThumb = MediaMetadataRetrieverBuilder.getNetVideoBitmap(SystemUtil.JudgeUrl(path));
                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                videoThumb.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                photo_img.post(new Runnable() {
                    @Override
                    public void run() {
                        RefreshViewLayoutParams(false, layoutParams, view, path, photo_img, width, videoThumb);
                    }
                });
            }
        });
    }

    private void ChangeViewLayoutParams(final ViewGroup.LayoutParams layoutParams, final View view, final ImageView photo_img, ImageView photo_cover_img, final String path, final int width, final int position) {
        if (DataClass.ChangeViewLayoutParamsHashMap.get(path) != null) {
            RefreshViewLayoutParams(true, layoutParams, view, path, photo_img, width, null);
        } else {
            LogUtil.e(getClass().getSimpleName(), "flash");
            Glide.with(photo_cover_img.getContext())
                    .load(SystemUtil.JudgeUrl(path))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(final Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            MyApplication.executorService.submit(new Runnable() {
                                @Override
                                public void run() {
                                    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                    RefreshViewLayoutParams(false, layoutParams, view, path, photo_img, width, bitmap);
                                }
                            });
                        }
                    });
        }
    }

    private void RefreshViewLayoutParams(boolean status, final ViewGroup.LayoutParams layoutParams, final View view, final String path, final ImageView photo_img, int width, final Bitmap bitmap) {
        final int widthPx = SystemUtil.dp2px(context, width);
        if (status) {
            LogUtil.e(getClass().getSimpleName(), "true");
            ChangeViewLayoutParamsBean changeViewLayoutParamsBean = DataClass.ChangeViewLayoutParamsHashMap.get(path);
            layoutParams.width = changeViewLayoutParamsBean.getWidth();
            layoutParams.height = changeViewLayoutParamsBean.getHight();
//          layoutParams.height = SystemUtil.dp2px(context, width * 2 / 5);
            view.setLayoutParams(layoutParams);
            if (!SystemUtil.JudgeNetFilePathType(path))
                intoImgLoadingType(photo_img, path, null);
        } else {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            photo_img.post(new Runnable() {
                @Override
                public void run() {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();

                    int cWidth = widthPx * width / (height + width);
                    int cHeight = widthPx * height / (height + width);

                    layoutParams.width = cWidth;
                    layoutParams.height = cHeight;
//                  layoutParams.height = SystemUtil.dp2px(context, width * 2 / 5);
                    view.setLayoutParams(layoutParams);

                    DataClass.ChangeViewLayoutParamsHashMap.put(path, new ChangeViewLayoutParamsBean(path, cWidth, cHeight, true));

                    intoImgLoadingType(photo_img, path, byteArrayOutputStream.toByteArray());
                }
            });
        }
    }

    private void intoImgLoadingType(ImageView photo_img, String path, byte[] bytes) {
        if (SystemUtil.JudgeNetFilePathType(path)) {
            Glide.with(photo_img.getContext()).load(bytes).fitCenter().diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.banner_off).into(photo_img);
        } else {
            if (path.contains(".gif")) {
                Glide.with(photo_img.getContext()).load(SystemUtil.JudgeUrl(path)).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).fitCenter().error(R.drawable.banner_off).into(photo_img);
            } else {
                Glide.with(photo_img.getContext()).load(SystemUtil.JudgeUrl(path)).fitCenter().error(R.drawable.banner_off).into(photo_img);
            }
        }
    }

    @Override
    public int getItemCount() {
//        if (list.size() == 1 || list.size() > 2)
        viewStatus = true;
        return list.size() == 0 ? 0 : list.size();
    }

    public interface ChildClickListener {
        void onChildClickListener(int position, int parentIndex);
    }

    private ChildClickListener childClickListener;

    public void setChildClickListener(ChildClickListener childClickListener) {
        this.childClickListener = childClickListener;
    }


}
