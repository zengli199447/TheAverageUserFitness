package com.example.administrator.sportsFitness.ui.controller;

import android.util.Log;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.TopUpNetBean;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerCoachPrivateInformation extends ControllerClassObserver {


    private ShowDialog instance;

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
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    /**
     * 预约
     *
     * @param datatype  1.场馆 2.课程 3.私教
     * @param shopid    场馆ID
     * @param coursesid 课程ID或私教ID
     * @param userids   参与人
     */
    public void NetConvention(String coursesid,String gymId) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.GROUP_JOIN_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("datatype", 3);
        linkedHashMap.put("shopid", gymId);
        linkedHashMap.put("coursesid", coursesid);
        linkedHashMap.put("userids", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.TopUp(map)
                .compose(RxUtil.<TopUpNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TopUpNetBean>(toastUtil) {
                    @Override
                    public void onNext(TopUpNetBean topUpNetBean) {
                        if (topUpNetBean.getStatus() == 1) {
                            if (onPayActionListener != null)
                                onPayActionListener.onPayActionListener(topUpNetBean.getResult());
                        } else {
                            toastUtil.showToast(topUpNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface OnPayActionListener {
        void onPayActionListener(TopUpNetBean.ResultBean result);
    }

    private OnPayActionListener onPayActionListener;

    public void setOnPayActionListener(OnPayActionListener onPayActionListener) {
        this.onPayActionListener = onPayActionListener;
    }


}
