package com.example.administrator.sportsFitness.ui.activity.component;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.MyTripNetBean;
import com.example.administrator.sportsFitness.model.bean.StudentFormNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerGeneralForm;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class PrivateGeneralFormActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener, SwipeRefreshLayout.OnRefreshListener
        , ControllerGeneralForm.OnGeneralFormRefreshListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.progress_bar)
    RelativeLayout progress_bar;
    @BindView(R.id.footer_layout)
    RelativeLayout footer_layout;

    private int flags;
    private String relatedId;
    private String relatedName;
    private ControllerGeneralForm controllerGeneralForm;

    private int pageNumber = 1;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_private_genera_form;
    }

    @Override
    protected void initClass() {
        Intent intent = getIntent();
        flags = intent.getFlags();
        relatedId = intent.getStringExtra("relatedId");
        relatedName = intent.getStringExtra("relatedName");
        controllerGeneralForm = new ControllerGeneralForm(recycler_view, flags, relatedId);

    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerGeneralForm;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        swipe_layout.setVisibility(View.VISIBLE);
        switch (flags) {
            case EventCode.MY_TRIP:
                title_name.setText(getString(R.string.my_trip));
                break;
            case EventCode.STUDENT_FORM:
                title_name.setText(getString(R.string.student_form));
                break;
            case EventCode.COACH_STUDENT_FORM:
                title_name.setText(getString(R.string.student_form_log));
                break;
        }
    }

    @Override
    protected void initListener() {
        swipe_layout.setOnRefreshListener(this);
        scrollView.setOnScrollChangeListener(this);
        controllerGeneralForm.setOnGeneralFormRefreshListener(this);
    }

    @OnClick({R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            pageNumber = pageNumber + 1;
            switch (flags) {
                case EventCode.MY_TRIP:
                    controllerGeneralForm.NetMyTrip(pageNumber);
                    break;
                case EventCode.STUDENT_FORM:
                case EventCode.COACH_STUDENT_FORM:
                    controllerGeneralForm.NetStudentForm(pageNumber);
                    break;
            }
        }
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        switch (flags) {
            case EventCode.MY_TRIP:
                controllerGeneralForm.NetMyTrip(pageNumber);
                break;
            case EventCode.STUDENT_FORM:
            case EventCode.COACH_STUDENT_FORM:
                controllerGeneralForm.NetStudentForm(pageNumber);
                break;
        }

    }

    @Override
    public void onGeneralFormRefreshListener(Object object) {
        int count = 0;
        footer_layout.setVisibility(View.VISIBLE);
        swipe_layout.setRefreshing(false);
        switch (flags) {
            case EventCode.MY_TRIP:
                MyTripNetBean.ResultBean myTripBean = (MyTripNetBean.ResultBean) object;
                count = myTripBean.getNeeddo().size();
                break;
            case EventCode.STUDENT_FORM:
            case EventCode.COACH_STUDENT_FORM:
                StudentFormNetBean.ResultBean studentBean = (StudentFormNetBean.ResultBean) object;
                count = studentBean.getStudent().size();
                break;
        }
        switch (count == 0 ? 0 : 1) {
            case 0:
                progress_bar.setVisibility(View.GONE);
                if (pageNumber == 1) {
                    footer_layout.setVisibility(View.GONE);
                    empty_layout.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                empty_layout.setVisibility(View.GONE);
                if (count < DataClass.DefaultInformationFlow) {
                    progress_bar.setVisibility(View.GONE);
                } else {
                    progress_bar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


}
