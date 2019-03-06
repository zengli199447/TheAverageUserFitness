package com.example.administrator.sportsfitness.ui.fragment.social;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseFragment;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.ui.controller.ControllerFriendsCircle;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.example.administrator.sportsfitness.widget.ViewBuilder;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/12/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FriendsCircleFragment extends BaseFragment {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private ControllerFriendsCircle controllerFriendsCircle;

    int type;
    String userId;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friend_circle;
    }

    @Override
    protected void initClass() {
        controllerFriendsCircle = new ControllerFriendsCircle(swipe_layout, empty_layout, recycler_view ,type, userId);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerFriendsCircle;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onClickAble(View view) {

    }


}
