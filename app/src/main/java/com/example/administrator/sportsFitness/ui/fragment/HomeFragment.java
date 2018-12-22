package com.example.administrator.sportsFitness.ui.fragment;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseFragment;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.ui.controller.ControllerArrange;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.banner_layout)
    RelativeLayout banner_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private ControllerArrange controllerArrange;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initClass() {
        controllerArrange = new ControllerArrange(banner_layout, recycler_view);

    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerArrange;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        recycler_view.setFocusable(false);
    }

    @Override
    protected void initListener() {


    }

    @OnClick({R.id.location_city, R.id.qr_code, R.id.search_layout})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.location_city:

                break;
            case R.id.qr_code:

                break;
            case R.id.search_layout:
                RxBus.getDefault().post(new CommonEvent(EventCode.OPEN_SEARCH));
                break;
        }
    }


}
