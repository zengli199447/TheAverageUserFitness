package com.example.administrator.sportsFitness.ui.controller;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.adapter.CardPageAdapter;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

/**
 * 作者：真理 Created by Administrator on 2018/12/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerCardPage extends ControllerClassObserver implements CardPageAdapter.OnClickListener {

    RecyclerView recycler_view;
    private CardPageAdapter cardPageAdapter;

    public ControllerCardPage(RecyclerView recycler_view) {
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
        cardPageAdapter = new CardPageAdapter(context, null);
        recycler_view.setAdapter(cardPageAdapter);
        cardPageAdapter.setOnClickListener(this);
    }

    private void refreshView() {
        cardPageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickListener(int position) {
        toastUtil.showToast("position : " + position);
    }

}
