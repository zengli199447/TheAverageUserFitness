package com.example.administrator.sportsFitness.ui.controller;

import android.util.Log;
import android.view.View;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.InfoCoachPrivateNetBean;
import com.example.administrator.sportsFitness.model.bean.PraiseStatusNetBean;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerInfoCoachPrivate extends ControllerClassObserver {

    String coachId;
    private ShowDialog instance;

    public ControllerInfoCoachPrivate(String coachId) {
        this.coachId = coachId;
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
        NetInfoCoachPrivate();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    /**
     * 私教详情
     *
     * @param coursesid 指定私教
     */
    public void NetInfoCoachPrivate() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.JIAOLIAN_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("coursesid", coachId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.InfoCoachPrivate(map)
                .compose(RxUtil.<InfoCoachPrivateNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<InfoCoachPrivateNetBean>(toastUtil) {
                    @Override
                    public void onNext(InfoCoachPrivateNetBean infoCoachPrivateNetBean) {
                        if (infoCoachPrivateNetBean.getStatus() == 1) {
                            InfoCoachPrivateNetBean.ResultBean result = infoCoachPrivateNetBean.getResult();
                            if (onNetInfoCoachPrivateListener != null)
                                onNetInfoCoachPrivateListener.onNetInfoCoachPrivateListener(result);

                        } else {
                            toastUtil.showToast(infoCoachPrivateNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    /**
     * 收藏
     *
     * @param datatype 1.场馆 2.课程 3.教练
     * @param refid    1.场馆id 2.课程id 3.教练id
     */
    public void NetCollection() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.COLLECT_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("datatype", 3);
        linkedHashMap.put("refid", coachId);
        map.put("version", "v1");
        map.put("vars", new Gson().toJson(linkedHashMap));
        addSubscribe(dataManager.Praise(map)
                .compose(RxUtil.<PraiseStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<PraiseStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(PraiseStatusNetBean praiseStatusNetBean) {
                        if (praiseStatusNetBean.getStatus() == 1) {
                            instance.showHelpfulHintsDialog(context, praiseStatusNetBean.getMessage(), EventCode.ONSTART);
                            if (onNetInfoCoachPrivateListener != null)
                                onNetInfoCoachPrivateListener.onCollectionSuccessfulListener(praiseStatusNetBean.getIfcollect_cleck() == 1 ? true : false);
                        } else {
                            toastUtil.showToast(praiseStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));

    }

    public interface OnNetInfoCoachPrivateListener {
        void onNetInfoCoachPrivateListener(InfoCoachPrivateNetBean.ResultBean result);

        void onCollectionSuccessfulListener(boolean status);
    }

    private OnNetInfoCoachPrivateListener onNetInfoCoachPrivateListener;

    public void setOnNetInfoCoachPrivateListener(OnNetInfoCoachPrivateListener onNetInfoCoachPrivateListener) {
        this.onNetInfoCoachPrivateListener = onNetInfoCoachPrivateListener;
    }

}
