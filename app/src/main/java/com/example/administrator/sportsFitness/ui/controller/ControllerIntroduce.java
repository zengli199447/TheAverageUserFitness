package com.example.administrator.sportsFitness.ui.controller;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.activity.component.DynamicDetailsActivity;
import com.example.administrator.sportsFitness.ui.activity.component.ForwardingActivity;
import com.example.administrator.sportsFitness.ui.activity.component.TheTagModifyActivity;
import com.example.administrator.sportsFitness.ui.adapter.ControllerCommentsAdapter;
import com.example.administrator.sportsFitness.ui.adapter.ControllerFriendsAdapter;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.CustomConditionsPopupWindow;
import com.example.administrator.sportsFitness.ui.view.FlowLayout;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.FlowLayoutBuilder;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 作者：真理 Created by Administrator on 2018/12/24.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerIntroduce extends ControllerClassObserver implements ControllerFriendsAdapter.DynamicParentClickListener, CustomConditionsPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener, FlowLayoutBuilder.FlowClickListener, ControllerCommentsAdapter.CommentsParentClickListener {

    RecyclerView dynamic_recycler_view;
    RecyclerView comments_recycler_view;
    FlowLayout flow_layout;
    private AlbumBuilder albumBuilder;
    private CustomConditionsPopupWindow customConditionsPopupWindow;
    private ControllerFriendsAdapter controllerFriendsAdapter;
    private ArrayList<String> photoList = new ArrayList<>();
    private FlowLayoutBuilder flowLayoutBuilder;
    private ControllerCommentsAdapter controllerCommentsAdapter;

    public ControllerIntroduce(RecyclerView dynamic_recycler_view, RecyclerView comments_recycler_view, FlowLayout flow_layout) {
        this.dynamic_recycler_view = dynamic_recycler_view;
        this.comments_recycler_view = comments_recycler_view;
        this.flow_layout = flow_layout;
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
        flowLayoutBuilder = new FlowLayoutBuilder(context, flow_layout);
        customConditionsPopupWindow = new CustomConditionsPopupWindow(context);
        customConditionsPopupWindow.setOnItemClickListener(this);
        customConditionsPopupWindow.setOnDismissListener(this);
        flowLayoutBuilder.setFlowClickListener(this);
        initAdapter();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
        if (controllerFriendsAdapter != null)
            refreshView();
    }

    private void initAdapter() {
        dynamic_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        controllerFriendsAdapter = new ControllerFriendsAdapter(context, null, true, 1);
        dynamic_recycler_view.setAdapter(controllerFriendsAdapter);
        controllerFriendsAdapter.setDynamicParentClickListener(this);

        comments_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        controllerCommentsAdapter = new ControllerCommentsAdapter(context, null, true, 1);
        comments_recycler_view.setAdapter(controllerCommentsAdapter);
        controllerCommentsAdapter.setCommentsParentClickListener(this);

    }

    private void refreshView() {
        controllerFriendsAdapter.notifyDataSetChanged();
        flowLayoutBuilder.initLayout(null, true);
        if (netWorkResLisrtener != null)
            netWorkResLisrtener.onNetWorkResLisrtener();
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(context);
    }

    @Override
    public void onChildClickListener(int position, String object) {
        photoList.clear();
        photoList.addAll(Arrays.asList(context.getResources().getStringArray(R.array.photo_list)));
        albumBuilder.ImageTheExhibition(photoList, false, position);
    }

    //动态事件
    @Override
    public void onCheckClickListener(int position, int id) {
        switch (id) {
            case -1:
                context.startActivity(new Intent(context, DynamicDetailsActivity.class));
                break;
            case R.id.about:
                customConditionsPopupWindow.showAtLocation(dynamic_recycler_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(context);
                break;
            case R.id.forwarding:
                Intent forwardingIntent = new Intent(context, ForwardingActivity.class);
                forwardingIntent.putExtra("forwardingId", "");
                context.startActivity(forwardingIntent);
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

    //pop事件
    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.report:
                ShowDialog.getInstance().showInputDialog(context, EventCode.REPORT_INPUT);
                break;
        }
    }

    //标签事件
    @Override
    public void onFlowClickListener(View view, int position) {
        switch (view.getId()) {
            case R.id.contentTextView:
                context.startActivity(new Intent(context, TheTagModifyActivity.class));
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

    public interface NetWorkResLisrtener {
        void onNetWorkResLisrtener();
    }

    private NetWorkResLisrtener netWorkResLisrtener;

    public void setNetWorkResLisrtener(NetWorkResLisrtener netWorkResLisrtener) {
        this.netWorkResLisrtener = netWorkResLisrtener;
    }

}
