package com.example.administrator.sportsFitness.ui.controller;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.adapter.GymInfomationAdapter;
import com.example.administrator.sportsFitness.ui.view.SlideRecyclerView;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.ArrayList;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerGymInfomation extends ControllerClassObserver {

    RecyclerView recycler_view;
    private GymInfomationAdapter gymInfomationAdapter;
    private ArrayList<Object> objects;

    public ControllerGymInfomation(RecyclerView recycler_view) {
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
        initAdapter();
        refreshView();

    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        gymInfomationAdapter = new GymInfomationAdapter(context, null);
        recycler_view.setAdapter(gymInfomationAdapter);

    }

    private void refreshView() {
        gymInfomationAdapter.notifyDataSetChanged();
    }


}
