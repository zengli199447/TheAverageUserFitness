package com.example.administrator.sportsFitness.ui.controller;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.bean.CategoryNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.activity.component.InfoCoachPrivateActivity;
import com.example.administrator.sportsFitness.ui.activity.component.InfoGymActivity;
import com.example.administrator.sportsFitness.ui.adapter.CategoryAdapter;
import com.example.administrator.sportsFitness.ui.adapter.FitnessCourseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerCourse extends ControllerClassObserver implements CategoryAdapter.CategroyItemClickListener, SwipeRefreshLayout.OnRefreshListener, FitnessCourseAdapter.onFitnessCourseClickListener {

    RecyclerView category_recycler;
    TextView empty_layout;
    RecyclerView fitness_course_recycler;
    SwipeRefreshLayout swipe_layout;

    private int flagStatus;
    int pageNumber = 1;
    String reason = "";
    String time = "";
    String category = "";

    private CategoryAdapter categoryAdapter;

    private List<CategoryNetBean> categoryNetBeansList = new ArrayList<>();
    private FitnessCourseAdapter fitnessCourseAdapter;

    public ControllerCourse(RecyclerView category_recycler, TextView empty_layout, RecyclerView fitness_course_recycler, SwipeRefreshLayout swipe_layout, int flagStatus) {
        this.category_recycler = category_recycler;
        this.empty_layout = empty_layout;
        this.fitness_course_recycler = fitness_course_recycler;
        this.swipe_layout = swipe_layout;
        this.flagStatus = flagStatus;
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
        switch (flagStatus) {
            case EventCode.COURSE:

                break;
            case EventCode.COACH_PRIVATE:

                break;
            case EventCode.GYM:

                break;
        }
        GetNetWork();
        refreshView();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        category_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        categoryAdapter = new CategoryAdapter(context, categoryNetBeansList);
        category_recycler.setAdapter(categoryAdapter);
        categoryAdapter.setCategroyItemClickListener(this);

        fitness_course_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fitnessCourseAdapter = new FitnessCourseAdapter(context, null, false);
        fitness_course_recycler.setAdapter(fitnessCourseAdapter);
        fitnessCourseAdapter.setOnFitnessCourseClickListener(this);
    }

    private void refreshView() {
        categoryAdapter.notifyDataSetChanged();
        fitnessCourseAdapter.notifyDataSetChanged();
    }

    public void changePageNumber() {
        pageNumber = 1;
    }

    public void changeReason(String value) {
        reason = value;
    }

    public void RefreshNetWorkData(String time) {
        this.time = time;
    }

    @Override
    public void onCategroyItemClickListener(int positon) {
        category = categoryNetBeansList.get(positon).getType();
        for (int i = 0; i < categoryNetBeansList.size(); i++) {
            categoryNetBeansList.get(i).setSelectStatus(i == positon ? true : false);
        }
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFitnessCourseClickListener(View view, int position) {
        switch (view.getId()) {
            case -1:
                toastUtil.showToast("position : " + position);
                break;
            case R.id.sign_up:
                toastUtil.showToast("position : " + position);
                break;
        }
        switch (flagStatus) {
            case EventCode.COURSE:
                toastUtil.showToast("课程");

                break;
            case EventCode.COACH_PRIVATE:
                toastUtil.showToast("私教");
                Intent infoCoachPrivateIntent = new Intent(context, InfoCoachPrivateActivity.class);
                context.startActivity(infoCoachPrivateIntent);
                break;
            case EventCode.GYM:
                toastUtil.showToast("健身房");
                Intent infoGymIntent = new Intent(context, InfoGymActivity.class);
                context.startActivity(infoGymIntent);
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    public void GetNetWork() {
        categoryNetBeansList.add(new CategoryNetBean("全部", "0", true));
        categoryNetBeansList.add(new CategoryNetBean("瑜伽", "1", false));
        categoryNetBeansList.add(new CategoryNetBean("拳击", "2", false));
    }


}
