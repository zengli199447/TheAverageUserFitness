package com.example.administrator.sportsFitness.ui.activity.component;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.FriendsCircleRelatedNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerSelectDiversifiedForm;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class SelectDiversifiedFormActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener, SwipeRefreshLayout.OnRefreshListener,
        ControllerSelectDiversifiedForm.OnSelectDiversifiedFormRefreshListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.progress_bar)
    RelativeLayout progress_bar;
    @BindView(R.id.footer_layout)
    RelativeLayout footer_layout;

    private String typeId;
    private int flags;
    private int pageNumber = 1;
    private ControllerSelectDiversifiedForm controllerSelectDiversifiedForm;
    private String stringValueId;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_select_diversified_form;
    }

    @Override
    protected void initClass() {
        Intent intent = getIntent();
        typeId = intent.getStringExtra("typeId");
        flags = intent.getFlags();
        stringValueId = intent.getStringExtra("stringValueId");
        controllerSelectDiversifiedForm = new ControllerSelectDiversifiedForm(recycler_view, typeId, flags, stringValueId);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerSelectDiversifiedForm;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        switch (flags) {
            case EventCode.INVITE_FRIENDS:
                title_name.setText(getString(R.string.invite_friends));
                break;
        }
        title_about_text.setText(getString(R.string.complete));
        title_about_text.setTextColor(getResources().getColor(R.color.blue_bar));
        footer_layout.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        swipe_layout.setOnRefreshListener(this);
        scrollView.setOnScrollChangeListener(this);
        controllerSelectDiversifiedForm.setOnSelectDiversifiedFormRefreshListener(this);
    }

    @OnClick({R.id.img_btn_black, R.id.title_about_text})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.title_about_text:
                controllerSelectDiversifiedForm.SendLineData();
                finish();
                break;
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            pageNumber = pageNumber + 1;
            switch (flags) {
                case EventCode.INVITE_FRIENDS:
                    controllerSelectDiversifiedForm.NetFriendsCircleRelated(pageNumber);
                    break;
            }
        }
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        switch (flags) {
            case EventCode.INVITE_FRIENDS:
                controllerSelectDiversifiedForm.NetFriendsCircleRelated(pageNumber);
                break;
        }
    }

    @Override
    public void onSelectDiversifiedFormRefreshListener(Object object) {
        int ambiguity = 0;
        footer_layout.setVisibility(View.VISIBLE);
        progress_bar.setVisibility(View.VISIBLE);
        swipe_layout.setRefreshing(false);
        switch (flags) {
            case EventCode.INVITE_FRIENDS:
                //判断listSize 是否>0  0?1
                FriendsCircleRelatedNetBean.ResultBean userlistBean = (FriendsCircleRelatedNetBean.ResultBean) object;
                List<FriendsCircleRelatedNetBean.ResultBean.UserlistBean> userlist = userlistBean.getUserlist();
                if (userlist != null)
                    ambiguity = userlist.size() == 0 ? 0 : userlist.size();
                break;
        }
        if (ambiguity == 0) {
            progress_bar.setVisibility(View.GONE);
            if (pageNumber == 1) {
                footer_layout.setVisibility(View.GONE);
                empty_layout.setVisibility(View.VISIBLE);
            }
        } else if (ambiguity < DataClass.DefaultInformationFlow) {
            progress_bar.setVisibility(View.GONE);
            empty_layout.setVisibility(View.GONE);
        } else {
            progress_bar.setVisibility(View.VISIBLE);
            empty_layout.setVisibility(View.GONE);
        }
    }


}
