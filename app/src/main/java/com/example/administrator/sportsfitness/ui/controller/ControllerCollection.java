package com.example.administrator.sportsfitness.ui.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.CoachFormNetBean;
import com.example.administrator.sportsfitness.model.bean.CourseFormNetBean;
import com.example.administrator.sportsfitness.model.bean.GymFormNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.activity.component.CourseInformationActivity;
import com.example.administrator.sportsfitness.ui.activity.component.GymInfomationActivity;
import com.example.administrator.sportsfitness.ui.activity.component.InfoCourseActivity;
import com.example.administrator.sportsfitness.ui.activity.component.InfoGymActivity;
import com.example.administrator.sportsfitness.ui.activity.component.TheDetailsInformationActivity;
import com.example.administrator.sportsfitness.ui.adapter.CoachFormAdapter;
import com.example.administrator.sportsfitness.ui.adapter.FitnessCourseAdapter;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.example.administrator.sportsfitness.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerCollection extends ControllerClassObserver implements FitnessCourseAdapter.onFitnessCourseClickListener,
        CoachFormAdapter.OnCoachClickListener {

    RecyclerView recycler_view;
    String type;
    private List<Object> formList = new ArrayList<>();
    private int pageNumber = 1;
    private FitnessCourseAdapter fitnessCourseAdapter;
    private int newDataSize;
    private GymFormNetBean.ResultBean.ShopBean shopBean;
    private CourseFormNetBean.ResultBean.CoursesBean coursesBean;
    private ShowDialog instance;
    private CoachFormAdapter coachFormAdapter;

    public ControllerCollection(RecyclerView recycler_view, String type) {
        this.recycler_view = recycler_view;
        this.type = type;
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
        if (context.getString(R.string.gym).equals(type)) {
            NetGymForm(pageNumber);
        } else if (context.getString(R.string.course).equals(type)) {
            NetCourse(pageNumber);
        } else if (context.getString(R.string.coach).equals(type)) {
            NetCoachForm(pageNumber);
        }
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context, false));
        if (context.getString(R.string.gym).equals(type) || context.getString(R.string.course).equals(type)) {
            fitnessCourseAdapter = new FitnessCourseAdapter(context, formList, true);
            recycler_view.setAdapter(fitnessCourseAdapter);
            fitnessCourseAdapter.setOnFitnessCourseClickListener(this);
            fitnessCourseAdapter.setLoadState(-1);
        } else if (context.getString(R.string.coach).equals(type)) {
            coachFormAdapter = new CoachFormAdapter(context, formList);
            recycler_view.setAdapter(coachFormAdapter);
            coachFormAdapter.setOnCoachClickListener(this);
        }
    }

    @Override
    public void onFitnessCourseClickListener(View view, int position) {
        Object object = formList.get(position);
        switch (view.getId()) {
            case -1:
                if (context.getString(R.string.gym).equals(type)) {
                    shopBean = (GymFormNetBean.ResultBean.ShopBean) object;
                    Intent infoGymIntent = new Intent(context, InfoGymActivity.class);
                    infoGymIntent.putExtra("GYMId", shopBean.getShopid());
                    context.startActivity(infoGymIntent);
                } else if (context.getString(R.string.course).equals(type)) {
                    coursesBean = (CourseFormNetBean.ResultBean.CoursesBean) object;
                    Intent infoCourseIntent = new Intent(context, InfoCourseActivity.class);
                    infoCourseIntent.putExtra("CoursesId", coursesBean.getCoursesid());
                    context.startActivity(infoCourseIntent);
                } else if (context.getString(R.string.coach).equals(type)) {

                }
                break;
            case R.id.sign_up:
                if (context.getString(R.string.gym).equals(type)) {
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
                } else if (context.getString(R.string.course).equals(type)) {
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
                }
        }
    }

    @Override
    public void onCoachClickListener(int position) {
        CoachFormNetBean.ResultBean.ShopBean shopBean = (CoachFormNetBean.ResultBean.ShopBean) formList.get(position);
        Intent theDetailsInformationIntent = new Intent(context, TheDetailsInformationActivity.class);
        theDetailsInformationIntent.putExtra("userId", shopBean.getUserid());
        theDetailsInformationIntent.putExtra("userName", shopBean.getSecondname());
        context.startActivity(theDetailsInformationIntent);
    }

    private void refreshView() {
        if (context.getString(R.string.gym).equals(type) || context.getString(R.string.course).equals(type)) {
            if (pageNumber != 1) {
                fitnessCourseAdapter.notifyItemRangeInserted(formList.size() - newDataSize, newDataSize);
            } else {
                fitnessCourseAdapter.notifyDataSetChanged();
            }
        } else if (context.getString(R.string.coach).equals(type)) {
            if (pageNumber != 1) {
                coachFormAdapter.notifyItemRangeInserted(formList.size() - newDataSize, newDataSize);
            } else {
                coachFormAdapter.notifyDataSetChanged();
            }
        }
    }


    /**
     * 课程列表
     *
     * @param collect 收藏 1 其他为空
     * @param keyword 检索内容
     */
    public void NetCourse(final int pageNumber) {
        this.pageNumber = pageNumber;
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.KECHENG_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("userid_view", "");
        linkedHashMap.put("if_collect", 1);
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
                            if (pageNumber == 1)
                                formList.clear();
                            formList.addAll(courses);
                            refreshView();
                            if (onGeneralFormRefreshListener != null)
                                onGeneralFormRefreshListener.onGeneralFormRefreshListener(result);
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
    public void NetGymForm(final int pageNumber) {
        this.pageNumber = pageNumber;
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SHOP_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("if_collect", 1);
        linkedHashMap.put("keyword", "");
        linkedHashMap.put("shopclassid", "");
        linkedHashMap.put("orderbytype", "");
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
                            if (pageNumber == 1)
                                formList.clear();
                            formList.addAll(shop);
                            refreshView();
                            if (onGeneralFormRefreshListener != null)
                                onGeneralFormRefreshListener.onGeneralFormRefreshListener(result);
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
     * 教练列表
     *
     * @param collect 收藏 1 其他为空
     * @param keyword 检索内容
     */
    public void NetCoachForm(final int pageNumber) {
        this.pageNumber = pageNumber;
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.TEACHER_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("pagenum", pageNumber);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.CoachForm(map)
                .compose(RxUtil.<CoachFormNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CoachFormNetBean>(toastUtil) {
                    @Override
                    public void onNext(CoachFormNetBean coachFormNetBean) {
                        if (coachFormNetBean.getStatus() == 1) {
                            CoachFormNetBean.ResultBean result = coachFormNetBean.getResult();
                            List<CoachFormNetBean.ResultBean.ShopBean> shop = result.getShop();
                            newDataSize = shop.size();
                            if (pageNumber == 1)
                                formList.clear();
                            formList.addAll(shop);
                            refreshView();
                            if (onGeneralFormRefreshListener != null)
                                onGeneralFormRefreshListener.onGeneralFormRefreshListener(result);
                        } else {
                            toastUtil.showToast(coachFormNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface OnGeneralFormRefreshListener {
        void onGeneralFormRefreshListener(Object object);
    }

    private OnGeneralFormRefreshListener onGeneralFormRefreshListener;

    public void setOnGeneralFormRefreshListener(OnGeneralFormRefreshListener onGeneralFormRefreshListener) {
        this.onGeneralFormRefreshListener = onGeneralFormRefreshListener;
    }

}
