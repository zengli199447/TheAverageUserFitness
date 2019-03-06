package com.example.administrator.sportsfitness.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseFragment;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.HomePageNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxBus;
import com.example.administrator.sportsfitness.ui.activity.component.CityScreeningActivity;
import com.example.administrator.sportsfitness.ui.controller.ControllerArrange;
import com.example.administrator.sportsfitness.widget.QrBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class HomeFragment extends BaseFragment implements ControllerArrange.OnHomePageListener{

    @BindView(R.id.banner_layout)
    RelativeLayout banner_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.slogan)
    TextView slogan;

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
        controllerArrange.setOnHomePageListener(this);

    }

    @OnClick({R.id.location_city, R.id.qr_code, R.id.search_layout})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.location_city:
                getActivity().startActivity(new Intent(getActivity(), CityScreeningActivity.class));
                break;
            case R.id.qr_code:
                QrBuilder.Integrator(getActivity());
                break;
            case R.id.search_layout:
                RxBus.getDefault().post(new CommonEvent(EventCode.OPEN_SEARCH, true));
                break;
        }
    }

    @Override
    public void OnHomePageListener(HomePageNetBean.ResultBean result) {
        HomePageNetBean.ResultBean.NoticeBean notice = result.getNotice();
        slogan.setText(notice.getContent());
    }

}
