package com.example.administrator.sportsFitness.ui.controller;

import android.annotation.SuppressLint;
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
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.activity.component.DynamicDetailsActivity;
import com.example.administrator.sportsFitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsFitness.ui.adapter.ControllerFriendsAdapter;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.CustomConditionsPopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerFriendsCircle extends ControllerClassObserver implements ControllerFriendsAdapter.DynamicParentClickListener, CustomConditionsPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener {

    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;
    RecyclerView recycler_view;
    private ControllerFriendsAdapter controllerFriendsAdapter;
    private ArrayList<String> photoList = new ArrayList<>();
    private AlbumBuilder albumBuilder;
    private CustomConditionsPopupWindow customConditionsPopupWindow;

    public ControllerFriendsCircle(SwipeRefreshLayout swipe_layout, TextView empty_layout, RecyclerView recycler_view) {
        this.swipe_layout = swipe_layout;
        this.empty_layout = empty_layout;
        this.recycler_view = recycler_view;
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
        refreshView();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        controllerFriendsAdapter = new ControllerFriendsAdapter(context, null, false);
        recycler_view.setAdapter(controllerFriendsAdapter);
        controllerFriendsAdapter.setDynamicParentClickListener(this);

    }

    private void refreshView() {
        controllerFriendsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildClickListener(int position, String object) {
        photoList.clear();
        photoList.addAll(Arrays.asList(context.getResources().getStringArray(R.array.photo_list)));
        albumBuilder.ImageTheExhibition(photoList, false, position);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onCheckClickListener(int position, int id) {
        switch (id) {
            case -1:
                context.startActivity(new Intent(context, DynamicDetailsActivity.class));
                break;
            case R.id.about:
                customConditionsPopupWindow.showAtLocation(recycler_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(context);
                break;
            case R.id.user_img:
                Intent theDetailsInformationIntent = new Intent(context, TheDetailsInformationActivity.class);
                theDetailsInformationIntent.setFlags(EventCode.OTHERS_PEOPLE);
                theDetailsInformationIntent.putExtra("userId", DataClass.USERID);
                context.startActivity(theDetailsInformationIntent);
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

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(context);
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.report:
                ShowDialog.getInstance().showInputDialog(context, EventCode.REPORT_INPUT);
                break;
        }
    }

}
