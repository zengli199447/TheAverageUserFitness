package com.example.administrator.sportsFitness.widget;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;

/**
 * 作者：真理 Created by Administrator on 2018/11/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class UmComprehensiveBuilder {

    private String TAG = getClass().getSimpleName();
    private Activity activity;
    private ToastUtil toastUtil;
    private Bitmap bitmap;

    public UmComprehensiveBuilder(Activity activity, ToastUtil toastUtil) {
        this.activity = activity;
        this.toastUtil = toastUtil;
    }

    //友盟登录
    public void initUmLogin(int status) {
        UMShareAPI.get(activity).setShareConfig(new UMShareConfig().isNeedAuthOnGetUserInfo(true));
        switch (status) {
            case 0:
                UMShareAPI.get(activity).getPlatformInfo(activity, SHARE_MEDIA.QQ, authListener);
                break;
            case 1:
                UMShareAPI.get(activity).getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, authListener);
                break;
        }
    }

    //清除登录状态
    public void deleteOauth(int status) {
        switch (status) {
            case 1:
                UMShareAPI.get(activity).deleteOauth(activity, SHARE_MEDIA.QQ, authListener);
                break;
            case 2:
                UMShareAPI.get(activity).deleteOauth(activity, SHARE_MEDIA.WEIXIN, authListener);
                break;
        }
    }

    //登陆监听
    private UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            LogUtil.e(TAG, "授权成功的回调  action : " + action);
            if (onCompleteListener != null)
                onCompleteListener.comlete(data);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            LogUtil.e(TAG, "授权失败的回调 : " + t.getMessage());
            if (onCompleteListener != null)
                onCompleteListener.notReach();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            LogUtil.e(TAG, "授权取消的回调");
            if (onCompleteListener != null)
                onCompleteListener.notReach();
        }
    };

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
        bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_launcher);
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
            LogUtil.e(TAG, "分享成功");
            onShareListener.successful();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            LogUtil.e(TAG, "分享失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };

    public interface onCompleteListener {
        void comlete(Map<String, String> data);

        void notReach();
    }

    public interface onShareListener {
        void successful();
    }

    private onCompleteListener onCompleteListener;

    private onShareListener onShareListener;

    public void setOnCompleteListener(onCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public void setOnShareListener(onShareListener onShareListener) {
        this.onShareListener = onShareListener;
    }

}
