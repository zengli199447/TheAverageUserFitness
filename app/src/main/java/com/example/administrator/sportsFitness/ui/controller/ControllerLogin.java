package com.example.administrator.sportsFitness.ui.controller;

import android.content.Intent;
import android.util.Log;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.LoginInfoNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.activity.LoginActivity;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：真理 Created by Administrator on 2018/10/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerLogin extends ControllerClassObserver {

    public ControllerLogin() {
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }


    public void NetLogin(String userName, String Password) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.LOGIN);
        linkedHashMap.put("phone", userName);
        linkedHashMap.put("pwd", Password);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.fetchLogin(map)
                .compose(RxUtil.<LoginInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<LoginInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(LoginInfoNetBean loginInfoNetBean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public void NetRegisteredLogin(String phoneNumber, String Password) {

    }


}
