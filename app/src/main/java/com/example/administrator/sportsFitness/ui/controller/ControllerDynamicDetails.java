package com.example.administrator.sportsFitness.ui.controller;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.adapter.RecyclerChildAdapter;
import com.example.administrator.sportsFitness.ui.view.CustomConditionsPopupWindow;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerDynamicDetails extends ControllerClassObserver implements RecyclerChildAdapter.ChildClickListener {

    RecyclerView recycler_view;
    RecyclerView recycler_view_comments;
    private ArrayList<String> photoList = new ArrayList<>();
    private AlbumBuilder albumBuilder;

    public ControllerDynamicDetails(RecyclerView recycler_view, RecyclerView recycler_view_comments) {
        this.recycler_view = recycler_view;
        this.recycler_view_comments = recycler_view_comments;
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
        initRecyclerView(Arrays.asList(context.getResources().getStringArray(R.array.photo_list)));
    }

    private void initAdapter() {

    }

    //类图模式(单张、两张、更多)
    private void initRecyclerView(List<String> imgList) {
        int spanCount = 0;
        if (imgList.size() == 1) {
            spanCount = 1;
        } else if (imgList.size() == 2) {
            spanCount = 2;
        } else {
            spanCount = 3;
        }
        recycler_view.setLayoutManager(ViewBuilder.getFullyGridLayoutManager(context, false, spanCount));
        RecyclerChildAdapter recyclerChildAdapter = new RecyclerChildAdapter(context, imgList, false);
        recycler_view.setAdapter(recyclerChildAdapter);
        recyclerChildAdapter.notifyDataSetChanged();
        recyclerChildAdapter.setChildClickListener(this);
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }


    @Override
    public void onChildClickListener(int position, String object) {
        photoList.clear();
        photoList.addAll(Arrays.asList(context.getResources().getStringArray(R.array.photo_list)));
        albumBuilder.ImageTheExhibition(photoList, false, position);
    }


}
