package com.example.administrator.sportsFitness.ui.fragment.im;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseFragment;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.controller.ControllerFriendsCircleRelated;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FriendsCircleRelatedFragment extends BaseFragment {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private ControllerFriendsCircleRelated controllerFriendsCircleRelated;
    private int type;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friends_circle_related;
    }

    @Override
    protected void initClass() {
        Bundle bundle = getArguments();
        type = bundle != null ? bundle.getInt("type") : -1;
        controllerFriendsCircleRelated = new ControllerFriendsCircleRelated(swipe_layout, empty_layout, recycler_view, type);

    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerFriendsCircleRelated;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        swipe_layout.setBackgroundColor(getResources().getColor(R.color.gray_));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onClickAble(View view) {

    }


}
