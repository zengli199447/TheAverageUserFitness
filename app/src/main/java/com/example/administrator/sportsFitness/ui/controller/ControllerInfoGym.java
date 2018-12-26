package com.example.administrator.sportsFitness.ui.controller;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.activity.component.InfoCoachPrivateActivity;
import com.example.administrator.sportsFitness.ui.activity.component.InfoGymActivity;
import com.example.administrator.sportsFitness.ui.adapter.ControllerCommentsAdapter;
import com.example.administrator.sportsFitness.ui.adapter.FitnessCourseAdapter;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 作者：真理 Created by Administrator on 2018/12/26.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerInfoGym extends ControllerClassObserver implements FitnessCourseAdapter.onFitnessCourseClickListener, ControllerCommentsAdapter.CommentsParentClickListener {

    RecyclerView course_recycler_view;
    RecyclerView evaluation_recycler_view;
    private FitnessCourseAdapter fitnessCourseAdapter;
    private ControllerCommentsAdapter controllerCommentsAdapter;
    private AlbumBuilder albumBuilder;
    private ArrayList<String> photoList = new ArrayList<>();

    public ControllerInfoGym(RecyclerView course_recycler_view, RecyclerView evaluation_recycler_view) {
        this.course_recycler_view = course_recycler_view;
        this.evaluation_recycler_view = evaluation_recycler_view;
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
        albumBuilder = new AlbumBuilder(context);
        initAdapter();
        refreshView();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        course_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        fitnessCourseAdapter = new FitnessCourseAdapter(context, null, true, 2);
        course_recycler_view.setAdapter(fitnessCourseAdapter);
        fitnessCourseAdapter.setOnFitnessCourseClickListener(this);

        evaluation_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        controllerCommentsAdapter = new ControllerCommentsAdapter(context, null, true,1);
        evaluation_recycler_view.setAdapter(controllerCommentsAdapter);
        controllerCommentsAdapter.setCommentsParentClickListener(this);

    }

    private void refreshView() {
        fitnessCourseAdapter.notifyDataSetChanged();
        controllerCommentsAdapter.notifyDataSetChanged();
    }

    //课程事件
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

    }

    //评论事件
    @Override
    public void onCommentsChildClickListener(int position, String object) {
        photoList.clear();
        photoList.addAll(Arrays.asList(context.getResources().getStringArray(R.array.photo_list)));
        albumBuilder.ImageTheExhibition(photoList, false, position);
    }

    @Override
    public void onCommentsCheckClickListener(int position, int id) {
        switch (id) {
            case -1:
                toastUtil.showToast("position : " + position);
                break;
            case R.id.sign_up:
                toastUtil.showToast("position : " + position);
                break;
        }
    }


}
