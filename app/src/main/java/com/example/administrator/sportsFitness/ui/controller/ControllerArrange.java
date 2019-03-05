package com.example.administrator.sportsFitness.ui.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.ArrangeBean;
import com.example.administrator.sportsFitness.model.bean.HomePageNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.activity.component.EmbeddedWebActivity;
import com.example.administrator.sportsFitness.ui.activity.component.FriendsCircleActivity;
import com.example.administrator.sportsFitness.ui.activity.component.GeneralFormActivity;
import com.example.administrator.sportsFitness.ui.activity.component.InfoCoachPrivateActivity;
import com.example.administrator.sportsFitness.ui.activity.component.InfoCourseActivity;
import com.example.administrator.sportsFitness.ui.activity.component.InfoGymActivity;
import com.example.administrator.sportsFitness.ui.activity.component.PrivateGeneralFormActivity;
import com.example.administrator.sportsFitness.ui.activity.component.ShowGeneraActivity;
import com.example.administrator.sportsFitness.ui.activity.component.WebConcentratedActivity;
import com.example.administrator.sportsFitness.ui.adapter.ArrangeAdapter;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.ImageSlideshow;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.example.administrator.sportsFitness.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    private List<HomePageNetBean.ResultBean.BannerBean> banner;
    private ShowDialog instance;

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
        instance = ShowDialog.getInstance();
        initDate();
        initAdapter();
        NetHomePage();

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
        for (int i = 0; i < banner.size(); i++) {
            easyBanner.addImageUrl((SystemUtil.JudgeUrl(banner.get(i).getPhoto()).toString()));
        }
        easyBanner.commit();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void OnItenClickListener(int position) {
        switch (arrangeBeanArrayList.get(position).getIndex()) {
            case 0:
                context.startActivity(new Intent(context, FriendsCircleActivity.class).setFlags(2));
                break;
            case 1:
                context.startActivity(new Intent(context, ShowGeneraActivity.class).setFlags(EventCode.TREAT));
                break;
            case 2:
                context.startActivity(new Intent(context, GeneralFormActivity.class).setFlags(EventCode.COACH_PRIVATE));
                break;
            case 3:
                context.startActivity(new Intent(context, GeneralFormActivity.class).setFlags(EventCode.GYM));
                break;
            case 4:
                context.startActivity(new Intent(context, PrivateGeneralFormActivity.class).setFlags(EventCode.MY_TRIP));
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        HomePageNetBean.ResultBean.BannerBean bannerBean = banner.get(position);
        switch (bannerBean.getIntamount()) {
            case 1:
                Intent webIntent = new Intent(context, EmbeddedWebActivity.class);
                webIntent.setFlags(0);
                webIntent.putExtra("link", bannerBean.getContent());
                webIntent.putExtra("titleName", context.getString(R.string.news));
                context.startActivity(webIntent);
                break;
            case 2:
                Intent infoCoachPrivateIntent = new Intent(context, InfoCoachPrivateActivity.class);
                infoCoachPrivateIntent.putExtra("CoachId", bannerBean.getIntamount2());
                context.startActivity(infoCoachPrivateIntent);
                break;
            case 3:
                Intent infoCourseIntent = new Intent(context, InfoCourseActivity.class);
                infoCourseIntent.putExtra("CoursesId", bannerBean.getIntamount2());
                context.startActivity(infoCourseIntent);
                break;
            case 4:
                Intent infoGymIntent = new Intent(context, InfoGymActivity.class);
                infoGymIntent.putExtra("GYMId", bannerBean.getIntamount2());
                context.startActivity(infoGymIntent);
                break;
        }
    }

    /**
     * 新版本提示
     */
    private void NewVersionPrompt(HomePageNetBean.ResultBean.NewversionBean newversion) {
        if (newversion.getVersionvalue() != null && !SystemUtil.getAppVersionName(context, false).equals(newversion.getVersionvalue())) {
            instance.showHelpfulHintsDialog(context, new StringBuffer().append(context.getString(R.string.news_version)).append(":").append(newversion.getVersionvalue()).toString(), EventCode.ONSTART);
        }
    }

    /**
     * 首页
     */
    public void NetHomePage() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.HOMEPAGE_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("versionvalue", SystemUtil.getAppVersionName(context, false));
        linkedHashMap.put("systemtype", 1);
        linkedHashMap.put("city", DataClass.CITY);
        linkedHashMap.put("longitude", DataClass.LONGITUDE);
        linkedHashMap.put("latitude", DataClass.LATITUDE);
        map.put("version", "v1");
        map.put("vars", new Gson().toJson(linkedHashMap));
        addSubscribe(dataManager.HomePage(map)
                .compose(RxUtil.<HomePageNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<HomePageNetBean>(toastUtil) {
                    @Override
                    public void onNext(HomePageNetBean homePageNetBean) {
                        if (homePageNetBean.getStatus() == 1) {
                            HomePageNetBean.ResultBean result = homePageNetBean.getResult();
                            banner = result.getBanner();
                            NewVersionPrompt(result.getNewversion());
//                            DataClass.URL = result.getRootPath();
                            initImageSlideshow();
                            if (onHomePageListener != null)
                                onHomePageListener.OnHomePageListener(result);
                        } else {
                            toastUtil.showToast(homePageNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    public interface OnHomePageListener {
        void OnHomePageListener(HomePageNetBean.ResultBean result);
    }

    private OnHomePageListener onHomePageListener;

    public void setOnHomePageListener(OnHomePageListener onHomePageListener) {
        this.onHomePageListener = onHomePageListener;
    }

}
