package com.example.administrator.sportsFitness.ui.controller;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.view.FlowLayout;
import com.example.administrator.sportsFitness.widget.FlowLayoutBuilder;

/**
 * 作者：真理 Created by Administrator on 2018/12/25.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerTheTagModify extends ControllerClassObserver {

    FlowLayout personal_tag_layout;
    FlowLayout hot_tag_layout;
    private FlowLayoutBuilder flowLayoutBuilder;

    public ControllerTheTagModify(FlowLayout personal_tag_layout, FlowLayout hot_tag_layout) {
        this.personal_tag_layout = personal_tag_layout;
        this.hot_tag_layout = hot_tag_layout;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.CORNERS_TAG:

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
        flowLayoutBuilder = new FlowLayoutBuilder(context, personal_tag_layout);
        initViewLayout();
        refreshView();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void refreshView() {
        flowLayoutBuilder.initLayout(null, false);
    }

    private void initViewLayout() {

    }


}
