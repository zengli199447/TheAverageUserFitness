package com.example.administrator.sportsFitness.ui.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.ArrangeBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.adapter.ArrangeAdapter;
import com.example.administrator.sportsFitness.ui.view.ImageSlideshow;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerArrange extends ControllerClassObserver implements ArrangeAdapter.OnItenClickListener, ImageSlideshow.OnItemClickListener {

    RelativeLayout banner_layout;
    RecyclerView recycler_view;
    ArrayList<ArrangeBean> arrangeBeanArrayList = new ArrayList<>();
    private ArrangeAdapter arrangeAdapter;
    private ImageSlideshow easyBanner;

    public ControllerArrange(RelativeLayout banner_layout, RecyclerView recycler_view) {
        this.banner_layout = banner_layout;
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
        initDate();
        initAdapter();
        initImageSlideshow();
    }

    private void initAdapter() {
        arrangeAdapter = new ArrangeAdapter(context, arrangeBeanArrayList);
        recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        recycler_view.setAdapter(arrangeAdapter);
        arrangeAdapter.notifyDataSetChanged();
        arrangeAdapter.setOnItenClickListener(this);
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initDate() {
        List<String> arrangeStrings = Arrays.asList(context.getResources().getStringArray(R.array.arrange));
        List<String> annotationStrings = Arrays.asList(context.getResources().getStringArray(R.array.arrange_annotation));
        for (int i = 0; i < arrangeStrings.size(); i++) {
            arrangeBeanArrayList.add(new ArrangeBean(arrangeStrings.get(i), annotationStrings.get(i), i));
        }
    }

    //轮播图
    private void initImageSlideshow() {
        banner_layout.removeAllViews();
        easyBanner = new ImageSlideshow(context);
        easyBanner.setOnItemClickListener(this);
        easyBanner.setDotSpace(24);
        easyBanner.setDotSize(12);
        easyBanner.setDelay(3000);
        banner_layout.addView(easyBanner);
        for (int i = 0; i < 2; i++) {
            easyBanner.addImageUrl((SystemUtil.JudgeUrl("http://wx1.sinaimg.cn/orj360/006pnLoLgy1ft6yichmarj30j60j675x.jpg")).toString());
        }
        easyBanner.commit();
    }

    @Override
    public void OnItenClickListener(int position) {
        switch (arrangeBeanArrayList.get(position).getIndex()) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }




}
