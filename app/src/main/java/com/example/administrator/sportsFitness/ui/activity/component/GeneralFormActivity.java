package com.example.administrator.sportsFitness.ui.activity.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerGeneralForm;
import com.example.administrator.sportsFitness.ui.fragment.CourseFragment;
import com.example.administrator.sportsFitness.ui.fragment.SearchFragment;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/25.
 * 邮箱：229017464@qq.com
 * remark:
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
    private String userId;
    private String userName;
    private String gymName;
    private String gymId;
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
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");
        gymId = intent.getStringExtra("gymId");
        gymName = intent.getStringExtra("gymName");

        controllerGeneralForm = new ControllerGeneralForm(swipe_layout, empty_layout, recycler_view, flags, userId, gymId);

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
            case EventCode.DYNAMIC:
                initSwipeLayout();
                title_name.setText(new StringBuffer().append(userName).append(getString(R.string.the_personal)).append(getString(R.string.dynamic)).toString());
                break;
            case EventCode.COACH_PRIVATE:
                title_name.setText(getString(R.string.coach_private));
                initFragment();
                break;
            case EventCode.GYM:
                title_name.setText(getString(R.string.gym));
                initFragment();
                break;
            case EventCode.COMMENTS:
                initSwipeLayout();
                title_name.setText(new StringBuffer().append(userName).append(getString(R.string.the_related)).append(getString(R.string.comments)).toString());
                break;
            case EventCode.COURSE_ZOO:
                initSwipeLayout();
                title_name.setText(new StringBuffer().append(gymName).append(getString(R.string.coures_form)).toString());
                break;

        }
    }

    private void initSwipeLayout() {
        ViewBuilder.ProgressStyleChange(swipe_layout, 0);
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
