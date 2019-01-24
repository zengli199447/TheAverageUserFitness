package com.example.administrator.sportsFitness.ui.controller;

import android.util.Log;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.bean.UserInfoNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2019/1/17.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerPersonalContent extends ControllerClassObserver {


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
        NetPersonalContent();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    /**
     * 提交个人信息修改
     *
     * @param photo
     * @param secondName
     * @param gender
     * @param brithday
     * @param about
     * @param city
     * @param faceImage
     * @param dynamicVisible
     */
    public void personalChangeNetWork(String photo, String secondName, final String gender, String brithday,
                                      String city, String faceImage, int dynamicVisible) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_INFO_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("photo", photo);
        linkedHashMap.put("secondname", secondName);
        linkedHashMap.put("sex", gender);
        linkedHashMap.put("brithday", brithday);
        linkedHashMap.put("remark", "");
        linkedHashMap.put("province", "");
        linkedHashMap.put("city", city);
        linkedHashMap.put("face", faceImage);
        linkedHashMap.put("newsshow", dynamicVisible);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            LogUtil.e(TAG, "修改成功");
                            DataClass.GENDER = gender;
                            RxBus.getDefault().post(new CommonEvent(EventCode.MINE_INFO_REFRESH));
                        }
                        instance.showHelpfulHintsDialog(context, upLoadStatusNetBean.getMessage(), EventCode.PERSONAL_CHANGE_REFRESH);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //获取用户信息
    public void NetPersonalContent() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UserInfo(map)
                .compose(RxUtil.<UserInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UserInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(UserInfoNetBean userInfoNetBean) {
                        if (userInfoNetBean.getStatus() == 1) {
                            UserInfoNetBean.ResultBean result = userInfoNetBean.getResult();
                            if (onPersonalListener != null)
                                onPersonalListener.OnPersonalListener(result);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface OnPersonalListener {
        void OnPersonalListener(UserInfoNetBean.ResultBean result);
    }

    private OnPersonalListener onPersonalListener;

    public void setOnPersonalListener(OnPersonalListener onPersonalListener) {
        this.onPersonalListener = onPersonalListener;
    }
}
