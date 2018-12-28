package com.example.administrator.sportsFitness.ui.controller;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.adapter.SelectDiversifiedFormAdapter;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerSelectDiversifiedForm extends ControllerClassObserver implements SelectDiversifiedFormAdapter.CheckItemListener {

    RecyclerView recycler_view;
    TextView empty_layout;
    SwipeRefreshLayout swipe_layout;
    String typeId;
    int flags;
    private SelectDiversifiedFormAdapter selectDiversifiedFormAdapter;

    public ControllerSelectDiversifiedForm(RecyclerView recycler_view, TextView empty_layout, SwipeRefreshLayout swipe_layout, String typeId, int flags) {
        this.recycler_view = recycler_view;
        this.empty_layout = empty_layout;
        this.swipe_layout = swipe_layout;
        this.typeId = typeId;
        this.flags = flags;
    }

    int pageNumber = 1;
    String idChain = "";

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
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        selectDiversifiedFormAdapter = new SelectDiversifiedFormAdapter(context, null);
        recycler_view.setAdapter(selectDiversifiedFormAdapter);
        selectDiversifiedFormAdapter.setCheckItemListener(this);
    }

    private void refreshView() {
        selectDiversifiedFormAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCheckItemListener(int position, boolean status) {


    }

}
