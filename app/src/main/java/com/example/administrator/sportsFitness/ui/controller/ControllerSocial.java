package com.example.administrator.sportsFitness.ui.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.ui.adapter.FriendsCircleSelectAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerSocial extends ControllerClassObserver implements FriendsCircleSelectAdapter.FriendsCircleSelectListener {

    LinearLayout layout_dynamic_select;
    RecyclerView recycler_view;
    private FriendsCircleSelectAdapter friendsCircleSelectAdapter;

    public ControllerSocial(LinearLayout layout_dynamic_select, RecyclerView recycler_view) {
        this.recycler_view = recycler_view;
        this.layout_dynamic_select = layout_dynamic_select;
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
        initAdapter();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        friendsCircleSelectAdapter = new FriendsCircleSelectAdapter(context, Arrays.asList(context.getResources().getStringArray(R.array.friends_circle)));
        recycler_view.setAdapter(friendsCircleSelectAdapter);
        friendsCircleSelectAdapter.setFriendsCircleSelectListener(this);
        friendsCircleSelectAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }


    @Override
    public void onFriendsCircleSelectListener(String content) {
        layout_dynamic_select.setVisibility(View.GONE);
        RxBus.getDefault().post(new CommonEvent(EventCode.DYNAMIC_SELECT, content));
    }


}
