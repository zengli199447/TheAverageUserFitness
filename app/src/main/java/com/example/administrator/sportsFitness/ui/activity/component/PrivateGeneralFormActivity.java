package com.example.administrator.sportsFitness.ui.activity.component;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerGeneralForm;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class PrivateGeneralFormActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private int flags;
    private String relatedId;
    private String relatedName;
    private ControllerGeneralForm controllerGeneralForm;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_private_genera_form;
    }

    @Override
    protected void initClass() {
        Intent intent = getIntent();
        flags = intent.getFlags();
        relatedId = intent.getStringExtra("relatedId");
        relatedName = intent.getStringExtra("relatedName");
        controllerGeneralForm = new ControllerGeneralForm(swipe_layout, empty_layout, recycler_view, flags, relatedId);

    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerGeneralForm;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        swipe_layout.setVisibility(View.VISIBLE);
        switch (flags) {
            case EventCode.MY_TRIP:
                title_name.setText(getString(R.string.my_trip));
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
