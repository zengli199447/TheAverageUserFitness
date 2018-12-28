package com.example.administrator.sportsFitness.ui.controller;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.adapter.FriendsCircleRelatedAdapter;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerFriendsCircleRelated extends ControllerClassObserver {

    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;
    RecyclerView recycler_view;
    private FriendsCircleRelatedAdapter friendsCircleRelatedAdapter;

    public ControllerFriendsCircleRelated(SwipeRefreshLayout swipe_layout, TextView empty_layout, RecyclerView recycler_view) {
        this.swipe_layout = swipe_layout;
        this.empty_layout = empty_layout;
        this.recycler_view = recycler_view;
    }

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
        initAdpater();
        refreshView();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdpater() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        friendsCircleRelatedAdapter = new FriendsCircleRelatedAdapter(context, null);
        recycler_view.setAdapter(friendsCircleRelatedAdapter);

    }

    private void refreshView() {
        friendsCircleRelatedAdapter.notifyDataSetChanged();
    }


}
