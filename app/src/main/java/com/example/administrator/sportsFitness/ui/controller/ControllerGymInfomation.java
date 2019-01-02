package com.example.administrator.sportsFitness.ui.controller;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.activity.component.SelectDiversifiedFormActivity;
import com.example.administrator.sportsFitness.ui.adapter.GymInfomationAdapter;
import com.example.administrator.sportsFitness.ui.view.SlideRecyclerView;
import com.example.administrator.sportsFitness.widget.ViewBuilder;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerGymInfomation extends ControllerClassObserver implements SwipeMenuItemClickListener, SwipeMenuCreator, GymInfomationAdapter.OnAddClickListener {

    SwipeMenuRecyclerView recycler_view;
    private GymInfomationAdapter gymInfomationAdapter;
    private ArrayList<Object> list = new ArrayList<>();

    public ControllerGymInfomation(SwipeMenuRecyclerView recycler_view) {
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
        initAdapter();
        refreshView();

    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context));
        recycler_view.setSwipeMenuCreator(this);
        recycler_view.setSwipeMenuItemClickListener(this);

        list.add("");
        list.add("");
        gymInfomationAdapter = new GymInfomationAdapter(context, list);
        recycler_view.setAdapter(gymInfomationAdapter);
        gymInfomationAdapter.setOnAddClickListener(this);
    }

    private void refreshView() {
        gymInfomationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
        SwipeMenuItem deleteItem = new SwipeMenuItem(context)
                .setBackground(R.drawable.red_text)
                .setTextColor(context.getResources().getColor(R.color.white))
                .setText(R.string.delete)
                .setWidth(context.getResources().getDimensionPixelSize(R.dimen.dp80))
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        if (viewType == 0)
            swipeRightMenu.addMenuItem(deleteItem);
    }

    @Override
    public void onItemClick(SwipeMenuBridge menuBridge) {
        menuBridge.closeMenu();
        int direction = menuBridge.getDirection();
        int adapterPosition = menuBridge.getAdapterPosition();
        int position = menuBridge.getPosition();
        if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
            list.remove(adapterPosition);//删除item
            gymInfomationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAddClickListener() {
        Intent intent = new Intent(context, SelectDiversifiedFormActivity.class);
        intent.setFlags(0);
        intent.putExtra("typeId", "");
        context.startActivity(intent);
    }

}
