package com.example.administrator.sportsFitness.ui.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.CoachPrivateNetBean;
import com.example.administrator.sportsFitness.model.bean.CourseFormNetBean;
import com.example.administrator.sportsFitness.model.bean.GymFormNetBean;
import com.example.administrator.sportsFitness.model.db.entity.SearchDBInfo;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.activity.component.CoachPrivateInformationActivity;
import com.example.administrator.sportsFitness.ui.activity.component.CourseInformationActivity;
import com.example.administrator.sportsFitness.ui.activity.component.GymInfomationActivity;
import com.example.administrator.sportsFitness.ui.activity.component.InfoCoachPrivateActivity;
import com.example.administrator.sportsFitness.ui.activity.component.InfoCourseActivity;
import com.example.administrator.sportsFitness.ui.activity.component.InfoGymActivity;
import com.example.administrator.sportsFitness.ui.adapter.FitnessCourseAdapter;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.example.administrator.sportsFitness.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerSearchType extends ControllerClassObserver implements FitnessCourseAdapter.onFitnessCourseClickListener {

    TextView empty_layout;
    RecyclerView recycler_view;
    String typeId;
    String relatedId;
    private FitnessCourseAdapter fitnessCourseAdapter;
    private String content = "";
    private List<Object> formList = new ArrayList<>();
    List<SearchDBInfo> searchDBInfos = null;
    private int flagStatus;
    private CourseFormNetBean.ResultBean.CoursesBean coursesBean;
    private CoachPrivateNetBean.ResultBean.CoursesBean coursesPrivateBean;
    private GymFormNetBean.ResultBean.ShopBean shopBean;
    private ShowDialog instance;

    public ControllerSearchType(TextView empty_layout, RecyclerView recycler_view, String typeId, String relatedId) {
        this.empty_layout = empty_layout;
        this.recycler_view = recycler_view;
        this.typeId = typeId;
        this.relatedId = relatedId;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.SEARCH_ACTION:
                content = commonevent.getTemp_str();
                initSerchAction();
                break;
        }
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
        if (relatedId != null && relatedId.isEmpty()) {
            if (DataClass.USERID.isEmpty()) {
                searchDBInfos = dataManager.querySearchDBInfo(DataClass.STANDARD_USER);
            } else {
                searchDBInfos = dataManager.querySearchDBInfo(DataClass.USERID);
            }
            if (searchDBInfos.size() > 0)
                content = searchDBInfos.get(searchDBInfos.size() - 1).getSearchContent();
        }
        initSerchAction();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    private void initAdapter() {
        recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(context,false));
        fitnessCourseAdapter = new FitnessCourseAdapter(context, formList, true);
        recycler_view.setAdapter(fitnessCourseAdapter);
        fitnessCourseAdapter.setOnFitnessCourseClickListener(this);
    }

    private void initSerchAction() {
        if (typeId.equals(context.getString(R.string.the_gym))) {
            NetGymForm();
            flagStatus = EventCode.GYM;
        } else if (typeId.equals(context.getString(R.string.course))) {
            NetCourse();
            flagStatus = EventCode.COURSE;
        } else if (typeId.equals(context.getString(R.string.cp))) {
            NetCoachPrivateForm();
            flagStatus = EventCode.COACH_PRIVATE;
        }
    }

    @Override
    public void onFitnessCourseClickListener(View view, int position) {
        toastUtil.showToast("position : " + position);
        Object object = formList.get(position);
        switch (view.getId()) {
            case -1:
                toastUtil.showToast("position : " + position);
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

    private void refreshView() {
        fitnessCourseAdapter.notifyDataSetChanged();
    }

    /**
     * 课程列表
     *
     * @param collect 收藏 1 其他为空
     * @param keyword 检索内容
     */
    public void NetCourse() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.KECHENG_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("pagenum", 1);
        linkedHashMap.put("userid_view", relatedId);
        linkedHashMap.put("if_collect", "");
        linkedHashMap.put("keyword", content);
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
                            formList.clear();
                            if (courses.size() == 0) {
                                empty_layout.setVisibility(View.VISIBLE);
                            } else {
                                empty_layout.setVisibility(View.GONE);
                            }
                            formList.addAll(courses);
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

    /**
     * 健身房列表
     *
     * @param collect 收藏 1 其他为空
     * @param keyword 检索内容
     */
    public void NetGymForm() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SHOP_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("pagenum", 1);
        linkedHashMap.put("if_collect", "");
        linkedHashMap.put("keyword", content);
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
                            formList.clear();
                            if (shop.size() == 0) {
                                empty_layout.setVisibility(View.VISIBLE);
                            } else {
                                empty_layout.setVisibility(View.GONE);
                            }
                            formList.addAll(shop);
                            refreshView();
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
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SIJIAO_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        linkedHashMap.put("pagenum", 1);
        linkedHashMap.put("userid_view", relatedId);
        linkedHashMap.put("datewhere", "");
        linkedHashMap.put("keyword", content);
        linkedHashMap.put("courseclassid", "");
        linkedHashMap.put("orderbytype", "");
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
                            formList.clear();
                            if (courses.size() == 0) {
                                empty_layout.setVisibility(View.VISIBLE);
                            } else {
                                empty_layout.setVisibility(View.GONE);
                            }
                            formList.addAll(courses);
                            refreshView();
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
