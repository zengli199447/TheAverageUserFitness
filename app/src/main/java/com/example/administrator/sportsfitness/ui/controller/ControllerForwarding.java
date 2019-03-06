package com.example.administrator.sportsfitness.ui.controller;

import android.util.Log;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.DynamicDetailsNetBean;
import com.example.administrator.sportsfitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2019/1/17.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerForwarding extends ControllerClassObserver{

    private String forwardingId;
    private ShowDialog instance;

    public ControllerForwarding(String forwardingId) {
        this.forwardingId = forwardingId;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    @Override
    protected void onClassCreate() {
        super.onClassCreate();
        instance = ShowDialog.getInstance();
        NetDynamicDetails();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    /**
     * 动态详情
     */
    public void NetDynamicDetails() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", forwardingId);
        linkedHashMap.put("pagenum", 1);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.DynamicDetails(map)
                .compose(RxUtil.<DynamicDetailsNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<DynamicDetailsNetBean>(toastUtil) {
                    @Override
                    public void onNext(DynamicDetailsNetBean dynamicDetailsNetBean) {
                        if (dynamicDetailsNetBean.getStatus() == 1) {
                            DynamicDetailsNetBean.ResultBean result = dynamicDetailsNetBean.getResult();
                            if (onDynamicDetailsListener != null)
                                onDynamicDetailsListener.onDynamicDetailsListener(result);
                        } else {
                            toastUtil.showToast(dynamicDetailsNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //转发动态
    public void NetForwarding() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.NEWS_ZHUAN_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("newsid", forwardingId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.forwarding_dynamic_successful), EventCode.FORWARDING_DYNAMIC_SUCCESSFUL);
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

    public interface OnDynamicDetailsListener {
        void onDynamicDetailsListener(DynamicDetailsNetBean.ResultBean result);
    }

    private OnDynamicDetailsListener onDynamicDetailsListener;

    public void setOnDynamicDetailsListener(OnDynamicDetailsListener onDynamicDetailsListener) {
        this.onDynamicDetailsListener = onDynamicDetailsListener;
    }

}
