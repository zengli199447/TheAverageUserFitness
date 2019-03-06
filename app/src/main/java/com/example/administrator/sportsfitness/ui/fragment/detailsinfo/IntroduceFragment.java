package com.example.administrator.sportsfitness.ui.fragment.detailsinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseFragment;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.model.bean.HomePageInfoNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.ui.activity.component.MoreDataReferenceActivity;
import com.example.administrator.sportsfitness.ui.controller.ControllerIntroduce;
import com.example.administrator.sportsfitness.ui.view.FlowLayout;
import com.example.administrator.sportsfitness.ui.view.ShinyView;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/24.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class IntroduceFragment extends BaseFragment implements ControllerIntroduce.NetWorkResLisrtener {

    @BindView(R.id.user_content)
    TextView user_content;
    @BindView(R.id.shiny_view)
    ShinyView shiny_view;
    @BindView(R.id.experience)
    TextView experience;
    @BindView(R.id.signature)
    TextView signature;
    @BindView(R.id.dynamic)
    TextView dynamic;
    @BindView(R.id.dynamic_recycler_view)
    RecyclerView dynamic_recycler_view;
    @BindView(R.id.comments_recycler_view)
    RecyclerView comments_recycler_view;
    @BindView(R.id.flow_layout)
    FlowLayout flow_layout;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.signature_layout)
    RelativeLayout signature_layout;
    @BindView(R.id.commtent_layout)
    RelativeLayout commtent_layout;

    private ControllerIntroduce controllerIntroduce;

    private String userId;
    private String userName;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_introduce;
    }

    @Override
    protected void initClass() {
        Bundle bundle = getArguments();
        userId = bundle != null ? bundle.getString("userId") : "";
        userName = bundle != null ? bundle.getString("userName") : "";
        controllerIntroduce = new ControllerIntroduce(dynamic_recycler_view, comments_recycler_view, flow_layout, userId);

    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerIntroduce;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        controllerIntroduce.setNetWorkResLisrtener(this);

    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.dynamic_more, R.id.comments_more})
    @Override
    protected void onClickAble(View view) {
        Intent intent = new Intent(getActivity(), MoreDataReferenceActivity.class);
        switch (view.getId()) {
            case R.id.dynamic_more:
                intent.setFlags(EventCode.DYNAMIC);
                intent.putExtra("relatedId", userId);
                intent.putExtra("relatedName", userName);
                intent.putExtra("relatedType", "3");
                getActivity().startActivity(intent);
                break;
            case R.id.comments_more:
                intent.setFlags(EventCode.COMMENTS);
                intent.putExtra("relatedId", userId);
                intent.putExtra("relatedName", userName);
                intent.putExtra("relatedType", "3");
                getActivity().startActivity(intent);
                break;
        }
    }

    @Override
    public void onNetWorkResLisrtener() {
        handler.sendEmptyMessageDelayed(0, 300);

    }

    @Override
    public void onHomePageDataListener(HomePageInfoNetBean.ResultBean result) {
        HomePageInfoNetBean.ResultBean.UserBean user = result.getUser();
        if (user.getRole().equals("1")) {
            experience.setText(new StringBuffer().append(user.getTotal_yuyue()).append(getString(R.string.experience)).toString());
        } else {
            commtent_layout.setVisibility(View.VISIBLE);
            comments_recycler_view.setVisibility(View.VISIBLE);
            shiny_view.setVisibility(View.VISIBLE);
            signature_layout.setVisibility(View.VISIBLE);
            shiny_view.setStarMark(Float.valueOf(user.getScore()));
            experience.setText(new StringBuffer().append(user.getTotal_yuyue()).append(getString(R.string.appointment_count)).toString());
            signature.setText(user.getRemark());
        }

        String userContent = new StringBuffer().append("  ").append(user.getSex()).append("   ").append(user.getAge()).append("   ").append(user.getCity()).toString();
        user_content.setText(userContent);

        dynamic.setText(new StringBuffer().append(user.getSecondname()).append(getString(R.string.the_dynamic)));
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (scrollView != null)
                scrollView.scrollTo(0, 0);
        }
    };

}
