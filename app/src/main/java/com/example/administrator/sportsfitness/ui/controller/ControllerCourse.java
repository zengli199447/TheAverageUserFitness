package com.example.administrator.sportsfitness.ui.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.CategoryNetBean;
import com.example.administrator.sportsfitness.model.bean.CoachPrivateNetBean;
import com.example.administrator.sportsfitness.model.bean.CourseFormNetBean;
import com.example.administrator.sportsfitness.model.bean.GymFormNetBean;
import com.example.administrator.sportsfitness.model.bean.GymTypeNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.activity.component.CoachPrivateInformationActivity;
import com.example.administrator.sportsfitness.ui.activity.component.CourseInformationActivity;
import com.example.administrator.sportsfitness.ui.activity.component.GymInfomationActivity;
import com.example.administrator.sportsfitness.ui.activity.component.InfoCoachPrivateActivity;
import com.example.administrator.sportsfitness.ui.activity.component.InfoCourseActivity;
import com.example.administrator.sportsfitness.ui.activity.component.InfoGymActivity;
import com.example.administrator.sportsfitness.ui.adapter.CategoryAdapter;
import com.example.administrator.sportsfitness.ui.adapter.FitnessCourseAdapter;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.utils.LogUtil;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.example.administrator.sportsfitness.widget.EndlessRecyclerOnScrollListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    private CategoryAdapter categoryAdapter;
    List<GymTypeNetBean.ResultBean.ShopclassBean> classList = new ArrayList<>();
    List<Object> formList = new ArrayList<>();
    private FitnessCourseAdapter fitnessCourseAdapter;

    private int flagStatus;
    int pageNumber = 1;
    private int newDataSize;
    String reason = "";
    String time = "";
    String category = "";
    private CourseFormNetBean.ResultBean.CoursesBean coursesBean;
    private CoachPrivateNetBean.ResultBean.CoursesBean coursesPrivateBean;
    private GymFormNetBean.ResultBean.ShopBean shopBean;
    private ShowDialog instance;

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
        instance = ShowDialog.getInstance();
        initAdapter();
        initListener();
        switch (flagStatus) {
            case EventCode.COURSE:
                NetCourse();
                NetClassType(1);
                break;
            case EventCode.COACH_PRIVATE:
                NetCoachPrivateForm();
                NetClassType(1);
                break;
            case EventCode.GYM:
                NetGymForm();
                NetClassType(2);
                break;
        }
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        category_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        categoryAdapter = new CategoryAdapter(context, classList);
        category_recycler.setAdapter(categoryAdapter);

        fitness_course_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fitnessCourseAdapter = new FitnessCourseAdapter(context, formList, false);
        fitness_course_recycler.setAdapter(fitnessCourseAdapter);
    }

    private void initListener() {
        categoryAdapter.setCategroyItemClickListener(this);
        fitnessCourseAdapter.setOnFitnessCourseClickListener(this);
        swipe_layout.setOnRefreshListener(this);
        fitness_course_recycler.addOnScrollListener(scrollListener);
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadMore() {
            if (fitnessCourseAdapter != null) {
                fitnessCourseAdapter.setLoadState(fitnessCourseAdapter.LOADING);
                if (formList.size() > DataClass.DefaultInformationFlow || formList.size() == DataClass.DefaultInformationFlow) {
                    pageNumber = pageNumber + 1;
                    switch (flagStatus) {
                        case EventCode.COURSE:
                            NetCourse();
                            break;
                        case EventCode.COACH_PRIVATE:
                            NetCoachPrivateForm();
                            break;
                        case EventCode.GYM:
                            NetGymForm();
                            break;
                    }
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

    private void refreshView(int tag) {
        switch (tag) {
            case 0:
                categoryAdapter.notifyDataSetChanged();
                break;
            case 1:
                if (pageNumber != 1) {
                    fitnessCourseAdapter.notifyItemRangeInserted(formList.size() - newDataSize, newDataSize);
                } else {
                    fitnessCourseAdapter.notifyDataSetChanged();
                }
                swipe_layout.setRefreshing(false);
                break;
        }
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
        category = classList.get(positon).getShopclassid();
        pageNumber = 1;
        for (int i = 0; i < classList.size(); i++) {
            classList.get(i).setSelectStatus(i == positon ? true : false);
        }
        categoryAdapter.notifyDataSetChanged();
        switch (flagStatus) {
            case EventCode.COURSE:
                NetCourse();
                break;
            case EventCode.COACH_PRIVATE:
                NetCoachPrivateForm();
                break;
            case EventCode.GYM:
                NetGymForm();
                break;
        }
    }

    @Override
    public void onFitnessCourseClickListener(View view, int position) {
        Object object = formList.get(position);
        switch (view.getId()) {
            case -1:
                switch (flagStatus) {
                    case EventCode.COURSE:
                        coursesBean = (CourseFormNetBean.ResultBean.CoursesBean) object;
                        Intent infoCourseIntent = new Intent(context, InfoCourseActivity.class);
                        infoCourseIntent.putExtra("CoursesId", coursesBean.getCoursesid());
                        context.startActivity(infoCourseIntent);
                        break;
                    case EventCode.COACH_PRIVATE:
                        coursesPrivateBean = (CoachPrivateNetBean.ResultBean.CoursesBean) object;
                        Intent infoCoachPrivateIntent = new Intent(context, InfoCoachPrivateActivity.class);
                        infoCoachPrivateIntent.putExtra("CoachId", coursesPrivateBean.getCoursesid());
                        context.startActivity(infoCoachPrivateIntent);
                        break;
                    case EventCode.GYM:
                        shopBean = (GymFormNetBean.ResultBean.ShopBean) object;
                        Intent infoGymIntent = new Intent(context, InfoGymActivity.class);
                        infoGymIntent.putExtra("GYMId", shopBean.getShopid());
                        context.startActivity(infoGymIntent);
                        break;
                }
                break;
            case R.id.sign_up:
                switch (flagStatus) {
                    case EventCode.COURSE:
                        coursesBean = (CourseFormNetBean.ResultBean.CoursesBean) object;
                        if (Integer.valueOf(coursesBean.getYuyuetotal()) != Integer.valueOf(coursesBean.getTotal())) {
                            Intent courseInfomationIntent = new Intent(context, CourseInformationActivity.class);
                            Bundle courseInfomationBundle = new Bundle();
                            courseInfomationBundle.putString("Shopname", coursesBean.getShopname());
                            courseInfomationBundle.putString("Price", coursesBean.getPrice());
                            courseInfomationBundle.putString("Photo", coursesBean.getListimg());
                            courseInfomationBundle.putString("Coursesid", coursesBean.getCoursesid());
                            courseInfomationBundle.putString("Coursesname", coursesBean.getCoursesname());
                            courseInfomationBundle.putString("Date_start", coursesBean.getDate_start());
                            courseInfomationBundle.putString("Date_end", coursesBean.getDate_end());
                            courseInfomationBundle.putString("Time_start", coursesBean.getTime_start());
                            courseInfomationBundle.putString("Time_end", coursesBean.getTime_end());
                            courseInfomationIntent.putExtra("CourseInfo", courseInfomationBundle);
                            context.startActivity(courseInfomationIntent);
                        } else {
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.full_error), EventCode.ONSTART);
                        }
                        break;
                    case EventCode.COACH_PRIVATE:
                        coursesPrivateBean = (CoachPrivateNetBean.ResultBean.CoursesBean) object;
                        Intent infoCoachPrivateIntent = new Intent(context, CoachPrivateInformationActivity.class);
                        Bundle infoCoachPrivateBundle = new Bundle();
                        infoCoachPrivateBundle.putString("Date_start_txt", coursesPrivateBean.getDate_start());
                        infoCoachPrivateBundle.putString("Time_start_txt", coursesPrivateBean.getTime_start());
                        infoCoachPrivateBundle.putString("Time_end_txt", coursesPrivateBean.getTime_end());
                        infoCoachPrivateBundle.putString("Secondname", coursesPrivateBean.getSecondname());
                        infoCoachPrivateBundle.putString("Price", coursesPrivateBean.getPrice());
                        infoCoachPrivateBundle.putString("Photo", coursesPrivateBean.getPhoto());
                        infoCoachPrivateBundle.putString("CoachId", coursesPrivateBean.getCoursesid());
                        infoCoachPrivateIntent.putExtra("CoachPrivate", infoCoachPrivateBundle);
                        context.startActivity(infoCoachPrivateIntent);
                        break;
                    case EventCode.GYM:
                        shopBean = (GymFormNetBean.ResultBean.ShopBean) object;
                        Intent gymInfomationIntent = new Intent(context, GymInfomationActivity.class);
                        Bundle gymInfomationBundle = new Bundle();
                        gymInfomationBundle.putString("Shopname", shopBean.getShopname());
                        gymInfomationBundle.putString("Price", shopBean.getPrice());
                        gymInfomationBundle.putString("Photo", shopBean.getPhoto());
                        gymInfomationBundle.putString("Shopid", shopBean.getShopid());
                        gymInfomationBundle.putString("Fulladdress", shopBean.getFulladdress());
                        gymInfomationIntent.putExtra("GYMInfo", gymInfomationBundle);
                        context.startActivity(gymInfomationIntent);
                        break;
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        switch (flagStatus) {
            case EventCode.COURSE:
                NetCourse();
                break;
            case EventCode.COACH_PRIVATE:
                NetCoachPrivateForm();
                break;
            case EventCode.GYM:
                NetGymForm();
                break;
        }
    }

    /**
     * 健身分类
     */
    public void NetClassType(int type) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.GET_CATE);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("typeid", type);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.GymType(map)
                .compose(RxUtil.<GymTypeNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<GymTypeNetBean>(toastUtil) {
                    @Override
                    public void onNext(GymTypeNetBean gymTypeNetBean) {
                        if (gymTypeNetBean.getStatus() == 1) {
                            classList.clear();
                            List<GymTypeNetBean.ResultBean.ShopclassBean> shopclass = gymTypeNetBean.getResult().getShopclass();
                            classList.add(new GymTypeNetBean.ResultBean.ShopclassBean("", context.getString(R.string.all), "0", true));
                            classList.addAll(shopclass);
                            refreshView(0);
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
     * 课程列表
     *
     * @param collect 收藏 1 其他为空
     * @param keyword 检索内容
     */
    public void NetCourse() {
        swipe_layout.setRefreshing(true);
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.KECHENG_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("userid_view", "");
        linkedHashMap.put("shopid", "");
        linkedHashMap.put("if_collect", "");
        linkedHashMap.put("keyword", "");
        linkedHashMap.put("datewhere", time);
        linkedHashMap.put("courseclassid", category);
        linkedHashMap.put("orderbytype", reason);
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
                                formList.clear();
                                if (courses.size() == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            formList.addAll(courses);
                            refreshView(1);
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

    /**
     * 健身房列表
     *
     * @param collect 收藏 1 其他为空
     * @param keyword 检索内容
     */
    public void NetGymForm() {
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
        linkedHashMap.put("orderbytype", reason);
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
                            newDataSize = shop.size();
                            if (pageNumber == 1) {
                                formList.clear();
                                if (shop.size() == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            formList.addAll(shop);
                            refreshView(1);
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

    /**
     * 私教列表
     *
     * @param datewhere 指定私教
     */
    public void NetCoachPrivateForm() {
        swipe_layout.setRefreshing(true);
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SIJIAO_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("userid_view", "");
        linkedHashMap.put("datewhere", time);
        linkedHashMap.put("courseclassid", category);
        linkedHashMap.put("orderbytype", reason);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.CoachPrivate(map)
                .compose(RxUtil.<CoachPrivateNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CoachPrivateNetBean>(toastUtil) {
                    @Override
                    public void onNext(CoachPrivateNetBean coachPrivateNetBean) {
                        if (coachPrivateNetBean.getStatus() == 1) {
                            CoachPrivateNetBean.ResultBean result = coachPrivateNetBean.getResult();
                            List<CoachPrivateNetBean.ResultBean.CoursesBean> courses = result.getCourses();
                            newDataSize = courses.size();
                            if (pageNumber == 1) {
                                formList.clear();
                                if (courses.size() == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);
                                }
                            }
                            formList.addAll(courses);
                            refreshView(1);
                        } else {
                            toastUtil.showToast(coachPrivateNetBean.getMessage());
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
