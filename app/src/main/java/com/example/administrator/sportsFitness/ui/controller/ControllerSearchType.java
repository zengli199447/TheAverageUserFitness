package com.example.administrator.sportsFitness.ui.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.adapter.FitnessCourseAdapter;

/**
 * 作者：真理 Created by Administrator on 2018/12/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerSearchType extends ControllerClassObserver implements FitnessCourseAdapter.onFitnessCourseClickListener {

    TextView empty_layout;
    RecyclerView recycler_view;
    private FitnessCourseAdapter fitnessCourseAdapter;

    public ControllerSearchType(TextView empty_layout, RecyclerView recycler_view) {
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
        initAdapter();

    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fitnessCourseAdapter = new FitnessCourseAdapter(context, null, true);
        recycler_view.setAdapter(fitnessCourseAdapter);
        fitnessCourseAdapter.setOnFitnessCourseClickListener(this);
    }

    @Override
    public void onFitnessCourseClickListener(View view, int position) {
        toastUtil.showToast("position : " + position);
    }


}
