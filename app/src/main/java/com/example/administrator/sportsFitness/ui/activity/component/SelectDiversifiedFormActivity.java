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
import com.example.administrator.sportsFitness.ui.controller.ControllerSelectDiversifiedForm;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class SelectDiversifiedFormActivity extends BaseActivity {

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
    private ControllerSelectDiversifiedForm controllerSelectDiversifiedForm;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_select_diversified_form;
    }

    @Override
    protected void initClass() {
        Intent intent = getIntent();
        typeId = intent.getStringExtra("typeId");
        flags = intent.getFlags();
        controllerSelectDiversifiedForm = new ControllerSelectDiversifiedForm(recycler_view, empty_layout, swipe_layout, typeId, flags);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerSelectDiversifiedForm;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        switch (flags) {
            case 0:
                title_name.setText(getString(R.string.invite_friends));
                break;
            case 1:
                title_name.setText(getString(R.string.select_site));
                break;
            case 2:
                title_name.setText(getString(R.string.select_student));
                break;
        }
        title_about_text.setText(getString(R.string.complete));
        title_about_text.setTextColor(getResources().getColor(R.color.blue_bar));
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.img_btn_black, R.id.title_about_text})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:

                break;
            case R.id.title_about_text:

                break;
        }
    }

}
