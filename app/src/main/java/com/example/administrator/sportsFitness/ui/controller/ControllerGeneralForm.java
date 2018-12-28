package com.example.administrator.sportsFitness.ui.controller;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.activity.component.DynamicDetailsActivity;
import com.example.administrator.sportsFitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsFitness.ui.adapter.ControllerCommentsAdapter;
import com.example.administrator.sportsFitness.ui.adapter.ControllerFriendsAdapter;
import com.example.administrator.sportsFitness.ui.adapter.FitnessCourseAdapter;
import com.example.administrator.sportsFitness.ui.adapter.MyTripAdapter;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.CustomConditionsPopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 作者：真理 Created by Administrator on 2018/12/25.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerGeneralForm extends ControllerClassObserver implements CustomConditionsPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener, ControllerFriendsAdapter.DynamicParentClickListener, FitnessCourseAdapter.onFitnessCourseClickListener,
        ControllerCommentsAdapter.CommentsParentClickListener,MyTripAdapter.OnTripClickListener{

    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;
    RecyclerView recycler_view;

    private int flags;
    private String relatedId;
    private AlbumBuilder albumBuilder;
    private CustomConditionsPopupWindow customConditionsPopupWindow;
    private ControllerFriendsAdapter controllerFriendsAdapter;
    private ArrayList<String> photoList = new ArrayList<>();
    private FitnessCourseAdapter fitnessCourseAdapter;
    private ControllerCommentsAdapter controllerCommentsAdapter;
    private MyTripAdapter myTripAdapter;

    public ControllerGeneralForm(SwipeRefreshLayout swipe_layout, TextView empty_layout, RecyclerView recycler_view, int flags, String relatedId) {
        this.swipe_layout = swipe_layout;
        this.empty_layout = empty_layout;
        this.recycler_view = recycler_view;
        this.flags = flags;
        this.relatedId = relatedId;
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
        customConditionsPopupWindow = new CustomConditionsPopupWindow(context);
        customConditionsPopupWindow.setOnItemClickListener(this);
        customConditionsPopupWindow.setOnDismissListener(this);
        initAdapter();
        switch (flags) {
            case EventCode.DYNAMIC:

                break;
            case EventCode.COMMENTS:

                break;
            case EventCode.COURSE_ZOO:

                break;
            case EventCode.MY_TRIP:

                break;
        }
        refreshView();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        switch (flags) {
            case EventCode.DYNAMIC:
                controllerFriendsAdapter = new ControllerFriendsAdapter(context, null, false);
                recycler_view.setAdapter(controllerFriendsAdapter);
                controllerFriendsAdapter.setDynamicParentClickListener(this);
                break;
            case EventCode.COMMENTS:
                controllerCommentsAdapter = new ControllerCommentsAdapter(context, null, false);
                recycler_view.setAdapter(controllerCommentsAdapter);
                controllerCommentsAdapter.setCommentsParentClickListener(this);
                break;
            case EventCode.COURSE_ZOO:
                fitnessCourseAdapter = new FitnessCourseAdapter(context, null, false);
                recycler_view.setAdapter(fitnessCourseAdapter);
                fitnessCourseAdapter.setOnFitnessCourseClickListener(this);
                break;
            case EventCode.MY_TRIP:
                myTripAdapter = new MyTripAdapter(context, null);
                recycler_view.setAdapter(myTripAdapter);
                myTripAdapter.setOnTripClickListener(this);
                break;
        }

    }

    private void refreshView() {
        switch (flags) {
            case EventCode.DYNAMIC:
                controllerFriendsAdapter.notifyDataSetChanged();
                break;
            case EventCode.COMMENTS:
                controllerCommentsAdapter.notifyDataSetChanged();
                break;
            case EventCode.COURSE_ZOO:
                fitnessCourseAdapter.notifyDataSetChanged();
                break;
            case EventCode.MY_TRIP:
                myTripAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(context);
    }

    //pop事件
    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.report:
                ShowDialog.getInstance().showInputDialog(context, EventCode.REPORT_INPUT);
                break;
        }
    }

    //动态事件
    @Override
    public void onChildClickListener(int position, String object) {
        photoList.clear();
        photoList.addAll(Arrays.asList(context.getResources().getStringArray(R.array.photo_list)));
        albumBuilder.ImageTheExhibition(photoList, false, position);
    }

    @Override
    public void onCheckClickListener(int position, int id) {
        switch (id) {
            case -1:
                context.startActivity(new Intent(context, DynamicDetailsActivity.class));
                break;
            case R.id.about:
                customConditionsPopupWindow.showAtLocation(recycler_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
                SystemUtil.windowToDark(context);
                break;
            case R.id.forwarding:

                break;
            case R.id.support_check:

                break;
            case R.id.comments:

                break;
        }
    }

    @Override
    public void onClearCheckClickListener(int position) {
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
            case R.id.user_img:
                toastUtil.showToast("position : " + position);
                break;
        }
    }

    //行程事件
    @Override
    public void OnTripClickListener(int position) {

    }

}
