package com.example.administrator.sportsfitness.ui.activity.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.ui.controller.ControllerGeneralForm;
import com.example.administrator.sportsfitness.ui.fragment.CourseFragment;
import com.example.administrator.sportsfitness.ui.fragment.SearchFragment;
import com.example.administrator.sportsfitness.widget.ViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/25.
 * 邮箱：229017464@qq.com
 * remark: 嵌入复用fragment  或 使用普通列表排版
 */
public class GeneralFormActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.fl_layout)
    FrameLayout fl_layout;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private FragmentTransaction fragmentTransaction;
    private CourseFragment courseFragment;
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
        return R.layout.activity_general_form;
    }

    @Override
    protected void initClass() {
        Intent intent = getIntent();
        flags = intent.getFlags();
        relatedId = intent.getStringExtra("relatedId");
        relatedName = intent.getStringExtra("relatedName");
        controllerGeneralForm = new ControllerGeneralForm(recycler_view, flags, relatedId);
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
        switch (flags) {
            case EventCode.COACH_PRIVATE:
                title_name.setText(getString(R.string.coach_private_contract));
                initFragment();
                break;
            case EventCode.GYM:
                title_name.setText(getString(R.string.gym_big));
                initFragment();
                break;
            case EventCode.ONSTART:
                initSwipeLayout();
                title_name.setText(new StringBuffer().append(relatedName).append(getString(R.string.about)).toString());
                break;
        }
    }

    private void initSwipeLayout() {
        swipe_layout.setVisibility(View.VISIBLE);
    }

    private void initFragment() {
        swipe_layout.setVisibility(View.GONE);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        courseFragment = new CourseFragment();
        Bundle data = new Bundle();
        data.putInt("flagStatus", flags);
        courseFragment.setArguments(data);
        fragmentTransaction.commit();
        fragmentTransaction.replace(R.id.fl_layout, courseFragment);
        fl_layout.setVisibility(View.VISIBLE);
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
