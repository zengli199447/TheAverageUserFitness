package com.example.administrator.sportsfitness.ui.fragment.collection;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseFragment;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.CoachFormNetBean;
import com.example.administrator.sportsfitness.model.bean.CourseFormNetBean;
import com.example.administrator.sportsfitness.model.bean.GymFormNetBean;
import com.example.administrator.sportsfitness.model.bean.MyTripNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.ui.controller.ControllerCollection;

import java.util.List;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/12/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CollectionFragment extends BaseFragment implements NestedScrollView.OnScrollChangeListener, SwipeRefreshLayout.OnRefreshListener,
        ControllerCollection.OnGeneralFormRefreshListener {

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
    private ControllerCollection controllerCollection;

    private int pageNumber = 1;
    private String typeId;
    private String relatedId;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection;
    }

    @Override
    protected void initClass() {
        Bundle bundle = getArguments();
        typeId = bundle != null ? bundle.getString("typeId") : "";
        relatedId = bundle != null ? bundle.getString("relatedId") : "";
        controllerCollection = new ControllerCollection(recycler_view, typeId);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerCollection;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        swipe_layout.setVisibility(View.VISIBLE);
        footer_layout.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        swipe_layout.setOnRefreshListener(this);
        scrollView.setOnScrollChangeListener(this);
        controllerCollection.setOnGeneralFormRefreshListener(this);
    }

    @Override
    protected void onClickAble(View view) {

    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            pageNumber = pageNumber + 1;
            if (getString(R.string.gym).equals(typeId)) {
                controllerCollection.NetGymForm(pageNumber);
            } else if (getString(R.string.course).equals(typeId)) {
                controllerCollection.NetCourse(pageNumber);
            } else if (getString(R.string.coach).equals(typeId)) {
                controllerCollection.NetCoachForm(pageNumber);
            }

        }
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        if (getString(R.string.gym).equals(typeId)) {
            controllerCollection.NetGymForm(pageNumber);
        } else if (getString(R.string.course).equals(typeId)) {
            controllerCollection.NetCourse(pageNumber);
        } else if (getString(R.string.coach).equals(typeId)) {
            controllerCollection.NetCoachForm(pageNumber);
        }
    }

    @Override
    public void onGeneralFormRefreshListener(Object object) {
        int count = 0;
        footer_layout.setVisibility(View.VISIBLE);
        swipe_layout.setRefreshing(false);

        if (getString(R.string.gym).equals(typeId)) {
            GymFormNetBean.ResultBean shopBean = (GymFormNetBean.ResultBean) object;
            count = shopBean.getShop().size();
        } else if (getString(R.string.course).equals(typeId)) {
            CourseFormNetBean.ResultBean couresBean = (CourseFormNetBean.ResultBean) object;
            count = couresBean.getCourses().size();
        } else if (getString(R.string.coach).equals(typeId)) {
            CoachFormNetBean.ResultBean coachBean = (CoachFormNetBean.ResultBean) object;
            count = coachBean.getShop().size();
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
