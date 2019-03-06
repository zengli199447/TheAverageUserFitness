package com.example.administrator.sportsfitness.ui.controller;

import android.util.Log;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxBus;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2019/2/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerReleaseNewVideoDynamic extends ControllerClassObserver {


    private ShowDialog instance;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.PHOTO_SAVE:

                break;
        }
    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    @Override
    protected void onClassCreate() {
        super.onClassCreate();
        instance = ShowDialog.getInstance();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    /**
     * 提示
     *
     * @param type (内容保存/图片删除)
     */
    public void ShowOrSelect(int type) {
        switch (type) {
            case EventCode.DYNAMIC_OR_SAVE:
                instance.showConfirmOrNoDialog(context, context.getString(R.string.dynamic_or_save), EventCode.ONSTART, EventCode.DYNAMIC_SAVE, EventCode.DYNAMIC_CANCLE);
                break;
            case EventCode.PHOTO_OR_REMOVER:
                instance.showConfirmOrNoDialog(context, context.getString(R.string.video_or_remover), EventCode.ONSTART, EventCode.PHOTO_SAVE, EventCode.PHOTO_CANCLE);
                break;
        }
    }

    /**
     * 发布动态
     *
     * @param newsid  动态ID(修改时加入)
     * @param imgs    附件
     * @param content 内容
     * @param limits  是否可见  0.所有人可见,1.仅好友可见
     */
    public void NetNewDynamic(String newsid, String imgs, String content, int limits) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_SET);
        linkedHashMap.put("newsid", newsid);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("imgs", imgs);
        linkedHashMap.put("content", content);
        linkedHashMap.put("limits", limits);
        linkedHashMap.put("newstype", 1);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            RxBus.getDefault().post(new CommonEvent(EventCode.DYNAMIC_REFRESH));
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.release_successful), EventCode.DYNAMIC_RELEASE_SUCCESSFUL);
                            if (onNewDynamicReleaseListener != null)
                                onNewDynamicReleaseListener.onNewDynamicReleaseListener();
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));

    }

    public interface OnNewDynamicReleaseListener {
        void onNewDynamicReleaseListener();
    }

    private OnNewDynamicReleaseListener onNewDynamicReleaseListener;

    public void setOnNewDynamicReleaseListener(OnNewDynamicReleaseListener onNewDynamicReleaseListener) {
        this.onNewDynamicReleaseListener = onNewDynamicReleaseListener;
    }

}
