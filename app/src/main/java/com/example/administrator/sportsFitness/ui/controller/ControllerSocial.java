package com.example.administrator.sportsFitness.ui.controller;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;


/**
 * 作者：真理 Created by Administrator on 2018/12/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerSocial extends ControllerClassObserver{


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
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }



}
