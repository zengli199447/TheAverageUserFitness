package com.example.administrator.sportsFitness.ui.activity.component;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.controller.ControllerGymClass;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/1/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GymClassSelectActivity extends BaseActivity {


    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.category_recycler)
    RecyclerView category_recycler;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.fitness_course_recycler)
    RecyclerView fitness_course_recycler;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    private ControllerGymClass controllerGymClass;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_gym_class_room;
    }

    @Override
    protected void initClass() {
        controllerGymClass = new ControllerGymClass(category_recycler, empty_layout, fitness_course_recycler, swipe_layout);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerGymClass;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.select_gym));
    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.img_btn_black)
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

}
