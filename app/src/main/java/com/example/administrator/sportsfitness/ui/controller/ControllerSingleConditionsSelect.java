package com.example.administrator.sportsfitness.ui.controller;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxBus;
import com.example.administrator.sportsfitness.ui.adapter.SingleConditionsSelectAdapter;

import java.util.ArrayList;

/**
 * 作者：真理 Created by Administrator on 2019/1/8.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerSingleConditionsSelect extends ControllerClassObserver implements SingleConditionsSelectAdapter.OnSelectClickListener {

    RecyclerView recycler_view;
    TextView empty_layout;
    SwipeRefreshLayout swipe_layout;
    private String typeId;
    private int flags;
    private SingleConditionsSelectAdapter singleConditionsSelectAdapter;
    private ArrayList<Object> list = new ArrayList<>();

    public ControllerSingleConditionsSelect(RecyclerView recycler_view, TextView empty_layout, SwipeRefreshLayout swipe_layout, String typeId, int flags) {
        this.recycler_view = recycler_view;
        this.empty_layout = empty_layout;
        this.swipe_layout = swipe_layout;
        this.typeId = typeId;
        this.flags = flags;
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
        switch (flags) {
            case EventCode.SITE_SINGLE_CONDITIONS:

                break;
        }
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
        refreshView();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        singleConditionsSelectAdapter = new SingleConditionsSelectAdapter(context, null);
        recycler_view.setAdapter(singleConditionsSelectAdapter);
        singleConditionsSelectAdapter.setOnSelectClickListener(this);

    }

    private void refreshView() {
        singleConditionsSelectAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSelectClickListener(int position) {
        RxBus.getDefault().post(new CommonEvent(EventCode.SITE_SINGLE_CONDITIONS, "场地", position));
        ((Activity) context).finish();
    }

}