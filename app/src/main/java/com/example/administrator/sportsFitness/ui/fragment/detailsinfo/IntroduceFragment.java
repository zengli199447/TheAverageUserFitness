package com.example.administrator.sportsFitness.ui.fragment.detailsinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseFragment;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.activity.component.GeneralFormActivity;
import com.example.administrator.sportsFitness.ui.controller.ControllerIntroduce;
import com.example.administrator.sportsFitness.ui.view.FlowLayout;
import com.example.administrator.sportsFitness.ui.view.ShinyView;


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

    private ControllerIntroduce controllerIntroduce;

    private String UserId = DataClass.USERID;
    private String UserName = DataClass.UNAME;

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
        controllerIntroduce = new ControllerIntroduce(dynamic_recycler_view, comments_recycler_view, flow_layout);

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
        Intent intent = new Intent(getActivity(), GeneralFormActivity.class);
        switch (view.getId()) {
            case R.id.dynamic_more:
                intent.setFlags(EventCode.DYNAMIC);
                intent.putExtra("relatedId", UserId);
                intent.putExtra("relatedName", UserName);
                getActivity().startActivity(intent);
                break;
            case R.id.comments_more:
                intent.setFlags(EventCode.COMMENTS);
                intent.putExtra("relatedId", UserId);
                intent.putExtra("relatedName", UserName);
                getActivity().startActivity(intent);
                break;
        }
    }

    @Override
    public void onNetWorkResLisrtener() {
        handler.sendEmptyMessageDelayed(0, 300);

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            scrollView.scrollTo(0, 0);
        }
    };

}
