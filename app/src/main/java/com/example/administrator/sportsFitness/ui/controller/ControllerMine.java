package com.example.administrator.sportsFitness.ui.controller;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.adapter.MineToolsAdapter;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

/**
 * 作者：真理 Created by Administrator on 2018/12/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerMine extends ControllerClassObserver implements MineToolsAdapter.MineToolsClickListener {

    RecyclerView recycler_view;
    private MineToolsAdapter mineToolsAdapter;

    public ControllerMine(RecyclerView recycler_view) {
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
        recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        mineToolsAdapter = new MineToolsAdapter(context);
        recycler_view.setAdapter(mineToolsAdapter);
        mineToolsAdapter.setMineToolsClickListener(this);
        mineToolsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMineToolsClickListener(int position) {
        switch (position) {
            case 0:

                break;
            case 1:

                break;
        }
    }

}
