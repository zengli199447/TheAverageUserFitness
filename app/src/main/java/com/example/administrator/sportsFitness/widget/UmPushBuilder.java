package com.example.administrator.sportsFitness.widget;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.sportsFitness.ui.activity.component.PersonalActivity;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UHandler;
import com.umeng.message.entity.UMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 作者：真理 Created by Administrator on 2019/2/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class UmPushBuilder {

    private String TAG = getClass().getSimpleName();

    private Context context;
    private PushAgent instance;

    public UmPushBuilder(Context context) {
        this.context = context;
    }

    public void initPushSetting() {
        instance = PushAgent.getInstance(context);
        instance.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //声音
        instance.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SERVER);//呼吸灯
        instance.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SERVER);//振动
        instance.onAppStart();
        //注册推送服务，每次调用register方法都会回调该接口
        instance.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.e(TAG, "deviceToken : " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtil.e(TAG, "onFailure : " + s + " - " + s1);
            }
        });

        instance.setNotificationClickHandler(UmengNotificationClickHandler);//自定义点击事件监听
    }

    public void PushAlias() {

    }

    private UHandler UmengNotificationClickHandler = new UHandler() {
        @Override
        public void handleMessage(Context context, UMessage uMessage) {
            Map<String, String> extra = uMessage.extra;
            JSONObject raw = uMessage.getRaw();
            try {
                String appkey = raw.getString("appkey");
            } catch (JSONException e) {
                LogUtil.e(TAG, "JSONException : " + e.toString());
            }
            Intent intent = new Intent(context, PersonalActivity.class);
            context.startActivity(intent);
            LogUtil.e(TAG, "UmengNotificationClickHandler 通知栏点击事件");
        }
    };

}
