package com.example.administrator.sportsfitness.ui.fragment.order;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseFragment;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.ui.controller.ControllerOrderForm;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2019/1/3.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class OrderFormFragment extends BaseFragment {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.recycler_view_layout)
    RelativeLayout recycler_view_layout;
    private ControllerOrderForm controllerOrderForm;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_form;
    }

    @Override
    protected void initClass() {
        Bundle bundle = getArguments();
        String typeId = bundle != null ? bundle.getString("typeId") : "";
        controllerOrderForm = new ControllerOrderForm(swipe_layout, empty_layout, recycler_view, typeId);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerOrderForm;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        swipe_layout.setBackground(getResources().getDrawable(R.color.gray_));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onClickAble(View view) {

    }


}
