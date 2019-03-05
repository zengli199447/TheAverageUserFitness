package com.example.administrator.sportsFitness.widget;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.sportsFitness.R;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GlideAlbumLoader implements AlbumLoader {

    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {
        if (url.contains(".gif")) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.banner_off)
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(url)
                    .error(R.drawable.banner_off)
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(imageView);
        }
    }

}
