package com.example.administrator.sportsfitness.ui.activity.component;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.ui.controller.ControllerSingleConditionsSelect;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/1/8.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class SingleConditionsSelectActivity extends BaseActivity{

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;

    private String typeId;
    private int flags;
    private ControllerSingleConditionsSelect controllerSingleConditionsSelect;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_single_conditions_select;
    }

    @Override
    protected void initClass() {
        Intent intent = getIntent();
        typeId = intent.getStringExtra("typeId");
        flags = intent.getFlags();
        controllerSingleConditionsSelect = new ControllerSingleConditionsSelect(recycler_view, empty_layout, swipe_layout, typeId, flags);




    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerSingleConditionsSelect;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        switch (flags) {
            case EventCode.SITE_SINGLE_CONDITIONS:
                title_name.setText(getString(R.string.select_site));
                break;
        }
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

}
