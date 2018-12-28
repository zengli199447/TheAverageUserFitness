package com.example.administrator.sportsFitness.ui.controller;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.bean.VipTopUpSelectNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.adapter.BillingAdapter;
import com.example.administrator.sportsFitness.ui.adapter.TopUpSelectAdapter;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ConreollerWallet extends ControllerClassObserver implements TopUpSelectAdapter.OnSelectClickListener {

    RecyclerView top_up_recycler_view;
    RecyclerView billing_recycler_view;
    private TopUpSelectAdapter topUpSelectAdapter;
    private List<VipTopUpSelectNetBean> vipTopUpSelectNetBeanList = new ArrayList<>();
    private BillingAdapter billingAdapter;

    public ConreollerWallet(RecyclerView top_up_recycler_view, RecyclerView billing_recycler_view) {
        this.top_up_recycler_view = top_up_recycler_view;
        this.billing_recycler_view = billing_recycler_view;
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
        initNetWork();
        refreshView();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        top_up_recycler_view.setLayoutManager(ViewBuilder.getFullyGridLayoutManager(context, false, 2));
        topUpSelectAdapter = new TopUpSelectAdapter(context, vipTopUpSelectNetBeanList);
        top_up_recycler_view.setAdapter(topUpSelectAdapter);
        topUpSelectAdapter.setOnSelectClickListener(this);

        billing_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        billingAdapter = new BillingAdapter(context, null);
        billing_recycler_view.setAdapter(billingAdapter);

    }

    private void refreshView() {
        topUpSelectAdapter.notifyDataSetChanged();
        billingAdapter.notifyDataSetChanged();
    }

    private void initNetWork() {
        vipTopUpSelectNetBeanList.add(new VipTopUpSelectNetBean(100, 10, true));
        vipTopUpSelectNetBeanList.add(new VipTopUpSelectNetBean(500, 80, false));
        vipTopUpSelectNetBeanList.add(new VipTopUpSelectNetBean(1000, 200, false));
        vipTopUpSelectNetBeanList.add(new VipTopUpSelectNetBean(2000, 500, false));
    }

    @Override
    public void OnSelectClickListener(int position) {
        for (int i = 0; i < vipTopUpSelectNetBeanList.size(); i++) {
            if (i == position) {
                vipTopUpSelectNetBeanList.get(i).setSelectStatus(true);
            } else {
                vipTopUpSelectNetBeanList.get(i).setSelectStatus(false);
            }
        }
        topUpSelectAdapter.notifyDataSetChanged();
    }

}
