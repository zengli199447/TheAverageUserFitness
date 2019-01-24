package com.example.administrator.sportsFitness.ui.controller;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.GymFormNetBean;
import com.example.administrator.sportsFitness.model.bean.GymTypeNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.adapter.CategoryAdapter;
import com.example.administrator.sportsFitness.ui.adapter.FitnessCourseAdapter;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.example.administrator.sportsFitness.widget.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerGymClass extends ControllerClassObserver implements CategoryAdapter.CategroyItemClickListener, FitnessCourseAdapter.onFitnessCourseClickListener, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView category_recycler;
    TextView empty_layout;
    RecyclerView fitness_course_recycler;
    SwipeRefreshLayout swipe_layout;
    private CategoryAdapter categoryAdapter;
    List<GymTypeNetBean.ResultBean.ShopclassBean> gymClassList = new ArrayList<>();
    List<Object> gymFormList = new ArrayList<>();
    private String category = "";
    private FitnessCourseAdapter fitnessCourseAdapter;
    private int pageNumber = 1;
    private int newDataSize;

    public ControllerGymClass(RecyclerView category_recycler, TextView empty_layout, RecyclerView fitness_course_recycler, SwipeRefreshLayout swipe_layout) {
        this.category_recycler = category_recycler;
        this.empty_layout = empty_layout;
        this.fitness_course_recycler = fitness_course_recycler;
        this.swipe_layout = swipe_layout;
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
        initListener();
        NetCourseType();
        NetGymForm();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        category_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        categoryAdapter = new CategoryAdapter(context, gymClassList);
        category_recycler.setAdapter(categoryAdapter);

        fitness_course_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fitnessCourseAdapter = new FitnessCourseAdapter(context, gymFormList, false);
        fitness_course_recycler.setAdapter(fitnessCourseAdapter);

    }

    private void initListener() {
        swipe_layout.setOnRefreshListener(this);
        categoryAdapter.setCategroyItemClickListener(this);
        fitnessCourseAdapter.setOnFitnessCourseClickListener(this);
        fitness_course_recycler.addOnScrollListener(scrollListener);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadMore() {
            if (fitnessCourseAdapter != null) {
                fitnessCourseAdapter.setLoadState(fitnessCourseAdapter.LOADING);
                if (gymFormList.size() > DataClass.DefaultInformationFlow || gymFormList.size() == DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    NetGymForm();
                } else {
                    // 显示加载到底的提示
                    fitnessCourseAdapter.setLoadState(fitnessCourseAdapter.LOADING_END);
                }
                if (newDataSize == 0 || newDataSize < DataClass.DefaultInformationFlow) {
                    fitnessCourseAdapter.setLoadState(fitnessCourseAdapter.LOADING_END);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView) {

        }
    };

    @Override
    public void onRefresh() {
        pageNumber = 1;
        NetGymForm();
    }

    @Override
    public void onCategroyItemClickListener(int positon) {
        category = gymClassList.get(positon).getShopclassid();
        pageNumber = 1;
        for (int i = 0; i < gymClassList.size(); i++) {
            gymClassList.get(i).setSelectStatus(i == positon ? true : false);
        }
        categoryAdapter.notifyDataSetChanged();
        NetGymForm();
    }

    @Override
    public void onFitnessCourseClickListener(View view, int position) {
        GymFormNetBean.ResultBean.ShopBean shopBean = (GymFormNetBean.ResultBean.ShopBean) gymFormList.get(position);
        RxBus.getDefault().post(new CommonEvent(EventCode.GYM_CLASS_SELECT, shopBean));
        ((Activity) context).finish();
    }

    private void refresView(int tag) {
        switch (tag) {
            case 0:
                categoryAdapter.notifyDataSetChanged();
                break;
            case 1:
                if (pageNumber != 1) {
                    fitnessCourseAdapter.notifyItemRangeInserted(gymFormList.size() - newDataSize, newDataSize);
                } else {
                    fitnessCourseAdapter.notifyDataSetChanged();
                }
                swipe_layout.setRefreshing(false);
                break;
        }
    }

    /**
     * 健身房类型
     */
    private void NetCourseType() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SHOPCLASS_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GymType(map)
                .compose(RxUtil.<GymTypeNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<GymTypeNetBean>(toastUtil) {
                    @Override
                    public void onNext(GymTypeNetBean gymTypeNetBean) {
                        if (gymTypeNetBean.getStatus() == 1) {
                            gymClassList.clear();
                            List<GymTypeNetBean.ResultBean.ShopclassBean> shopclass = gymTypeNetBean.getResult().getShopclass();
                            gymClassList.add(new GymTypeNetBean.ResultBean.ShopclassBean("", context.getString(R.string.all), "0", true));
                            gymClassList.addAll(shopclass);
                            refresView(0);
                        } else {
                            toastUtil.showToast(gymTypeNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    /**
     * 健身房列表
     */
    private void NetGymForm() {
        swipe_layout.setRefreshing(true);
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SHOP_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("if_collect", "");
        linkedHashMap.put("keyword", "");
        linkedHashMap.put("shopclassid", category);
        linkedHashMap.put("orderbytype", 2);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GymForm(map)
                .compose(RxUtil.<GymFormNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<GymFormNetBean>(toastUtil) {
                    @Override
                    public void onNext(GymFormNetBean gymFormNetBean) {
                        if (gymFormNetBean.getStatus() == 1) {
                            GymFormNetBean.ResultBean result = gymFormNetBean.getResult();
                            List<GymFormNetBean.ResultBean.ShopBean> shop = result.getShop();
                            for (GymFormNetBean.ResultBean.ShopBean shopBean : shop) {
                                shopBean.setSelectTag(context.getString(R.string.select));
                            }
                            newDataSize = shop.size();
                            if (pageNumber == 1) {
                                gymFormList.clear();
                                if (shop.size() == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            gymFormList.addAll(shop);
                            refresView(1);
                        } else {
                            toastUtil.showToast(gymFormNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }


}
