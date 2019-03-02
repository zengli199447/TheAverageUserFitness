package com.example.administrator.sportsFitness.ui.controller;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.CommentsNetBean;
import com.example.administrator.sportsFitness.model.bean.CourseFormNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.adapter.FitnessCourseAdapter;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
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
public class ControllerCourseForm extends ControllerClassObserver implements SwipeRefreshLayout.OnRefreshListener,FitnessCourseAdapter.onFitnessCourseClickListener {

    SwipeRefreshLayout swipe_layout;
    TextView empty_layout;
    RecyclerView recycler_view;
    List<Object> list = new ArrayList<>();
    private int pageNumber = 1;
    private int newDataSize;
    String aboutId;
    private FitnessCourseAdapter fitnessCourseAdapter;

    public ControllerCourseForm(SwipeRefreshLayout swipe_layout, TextView empty_layout, RecyclerView recycler_view, String aboutId) {
        this.swipe_layout = swipe_layout;
        this.empty_layout = empty_layout;
        this.recycler_view = recycler_view;
        this.aboutId = aboutId;
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
        NetCourseForm();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fitnessCourseAdapter = new FitnessCourseAdapter(context, list, false);
        recycler_view.setAdapter(fitnessCourseAdapter);

    }

    private void initListener() {
        fitnessCourseAdapter.setOnFitnessCourseClickListener(this);
        recycler_view.addOnScrollListener(scrollListener);
        swipe_layout.setOnRefreshListener(this);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadMore() {
            if (fitnessCourseAdapter != null) {
                fitnessCourseAdapter.setLoadState(fitnessCourseAdapter.LOADING);
                if (list.size() > DataClass.DefaultInformationFlow || list.size() == DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    NetCourseForm();
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
        NetCourseForm();
    }

    private void refreshView() {
        if (pageNumber != 1) {
            fitnessCourseAdapter.notifyItemRangeInserted(list.size() - newDataSize, newDataSize);
        } else {
            fitnessCourseAdapter.notifyDataSetChanged();
        }
        swipe_layout.setRefreshing(false);
    }

    @Override
    public void onFitnessCourseClickListener(View view, int position) {

    }

    /**
     * 课程列表
     *
     * @param collect 收藏 1 其他为空
     * @param keyword 检索内容
     */
    public void NetCourseForm() {
        swipe_layout.setRefreshing(true);
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.KECHENG_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("userid_view", "");
        linkedHashMap.put("shopid", aboutId);
        linkedHashMap.put("if_collect", "");
        linkedHashMap.put("keyword", "");
        linkedHashMap.put("datewhere", "");
        linkedHashMap.put("courseclassid", "");
        linkedHashMap.put("orderbytype", "");
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.CourseForm(map)
                .compose(RxUtil.<CourseFormNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CourseFormNetBean>(toastUtil) {
                    @Override
                    public void onNext(CourseFormNetBean courseFormNetBean) {
                        if (courseFormNetBean.getStatus() == 1) {
                            CourseFormNetBean.ResultBean result = courseFormNetBean.getResult();
                            List<CourseFormNetBean.ResultBean.CoursesBean> courses = result.getCourses();
                            newDataSize = courses.size();
                            if (pageNumber == 1) {
                                list.clear();
                                if (courses.size() == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            list.addAll(courses);
                            refreshView();
                        } else {
                            toastUtil.showToast(courseFormNetBean.getMessage());
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
