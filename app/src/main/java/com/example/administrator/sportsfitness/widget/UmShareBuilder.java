package com.example.administrator.sportsfitness.widget;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 作者：真理 Created by Administrator on 2019/2/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class UmShareBuilder {

    private final Bitmap bitmap;
    private String TAG = getClass().getSimpleName();
    private Activity activity;

    public UmShareBuilder(Activity activity) {
        this.activity = activity;
        bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_launcher);
    }

    public void initUmImageShare(int shareType, Bitmap bitmap) {
        UMImage umImage = new UMImage(activity, bitmap);
        switch (shareType) {
            case 0:
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.QQ)//传入QQ平台
                        .withMedia(umImage)
                        .setCallback(shareListener)
                        .share();
                break;
            case 1:
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入微信平台
                        .withMedia(umImage)
                        .setCallback(shareListener)
                        .share();
                break;
            case 2:
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入微信朋友圈平台
                        .withMedia(umImage)
                        .setCallback(shareListener)
                        .share();
                break;
        }
    }

    //分享链接可以使用UMWeb进行分享：
    public void initUmUrlShare(int shareType, String img_url, String url, String title, String description) {
//        url = "http://e.hiphotos.baidu.com/image/pic/item/d0c8a786c9177f3eeb8bedb57ccf3bc79e3d56ce.jpg";
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        if (img_url.isEmpty()) {
            web.setThumb(new UMImage(activity, bitmap));  //缩略图
        } else {
            web.setThumb(new UMImage(activity, img_url));  //缩略图
        }
        web.setDescription(description);//描述
        switch (shareType) {
            case 0:
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.QQ)//传入QQ平台
                        .withMedia(web)
                        .setCallback(shareListener)
                        .share();
                break;
            case 1:
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入微信平台
                        .withMedia(web)
                        .setCallback(shareListener)
                        .share();
                break;
            case 2:
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入微信朋友圈平台
                        .withMedia(web)
                        .setCallback(shareListener)
                        .share();
                break;
        }
    }

    public void initUMShare(String text) {
//        平台
//        枚举变量
//        QQ SHARE_MEDIA.QQ
//        Qzone SHARE_MEDIA.QZONE
//        微信还有 SHARE_MEDIA.WEIXIN
//        微信朋友圈 SHARE_MEDIA.WEIXIN_CIRCLE
//        微信收藏 SHARE_MEDIA.WEIXIN_FAVORITE

//        UMImage image = new UMImage(activity, "imageurl");//网络图片
//        UMImage image = new UMImage(activity, file);//本地文件
//        UMImage image = new UMImage(activity, R.drawable.ic_launcher);//资源文件
//        UMImage image = new UMImage(activity, bitmap);//bitmap文件
//        UMImage image = new UMImage(activity, byte[]);//字节流

//        new ShareAction(activity).withText("hello").withMedia(image).share();

//        推荐使用网络图片和资源文件的方式，平台兼容性更高。对于部分平台，分享的图片需要设置缩略图，缩略图的设置规则为：
//        UMImage thumb =  new UMImage(this, R.drawable.thumb);
//        image.setThumb(thumb);
//        用户设置的图片大小最好不要超过250k，缩略图不要超过18k，如果超过太多（最好不要分享1M以上的图片，压缩效率会很低），图片会被压缩。用户可以设置压缩的方式：
//        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
//        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
//        压缩格式设置
//        image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

//        分享链接可以使用UMWeb进行分享：
//        UMWeb  web = new UMWeb(Defaultcontent.url);
//        web.setTitle("This is music title");//标题
//        web.setThumb(thumb);  //缩略图
//        web.setDescription("my description");//描述
//        new ShareAction(ShareActivity.this)
//                .withMedia(web)
//                .share();
    }

    public UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(activity.getApplication(), "成功了", Toast.LENGTH_LONG).show();
            onShareListener.successful();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(activity.getApplication(), "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(activity.getApplication(), "取消了", Toast.LENGTH_LONG).show();
        }
    };

    public interface onShareListener {
        void successful();
    }

    private onShareListener onShareListener;

    public void setOnShareListener(onShareListener onShareListener) {
        this.onShareListener = onShareListener;
    }

}
