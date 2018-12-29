package com.example.administrator.sportsFitness.ui.controller;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;

/**
 * 作者：真理 Created by Administrator on 2018/12/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerShowGenera extends ControllerClassObserver {

    int flags;

    public ControllerShowGenera(int flags) {
        this.flags = flags;
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
        switch (flags) {
            case EventCode.MY_QR_CODE:

                break;
        }
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }


}
