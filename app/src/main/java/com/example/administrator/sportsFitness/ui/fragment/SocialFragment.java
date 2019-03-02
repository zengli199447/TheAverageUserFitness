package com.example.administrator.sportsFitness.ui.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseFragment;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.ui.activity.component.ReleaseNewDynamicActivity;
import com.example.administrator.sportsFitness.ui.activity.component.ReleaseNewVideoDynamicActivity;
import com.example.administrator.sportsFitness.ui.adapter.TabPageIndicatorAdapter;
import com.example.administrator.sportsFitness.ui.controller.ControllerSocial;
import com.example.administrator.sportsFitness.ui.fragment.social.FriendsCircleFragment;
import com.example.administrator.sportsFitness.ui.fragment.social.MessageFragment;
import com.example.administrator.sportsFitness.ui.view.CustomConditionsPopupWindow;
import com.example.administrator.sportsFitness.ui.view.CustomSelectFriendsPopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class SocialFragment extends BaseFragment implements TabLayout.OnTabSelectedListener, CustomSelectFriendsPopupWindow.OnItemSelectClickListener, PopupWindow.OnDismissListener
        ,CustomConditionsPopupWindow.OnItemClickListener {

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_page)
    ViewPager view_page;

    @BindView(R.id.dynamic_select)
    TextView dynamic_select;
    @BindView(R.id.message_select)
    TextView message_select;

    private List<String> titleList;
    private List<Fragment> mFragments = new ArrayList<>();
    private TabPageIndicatorAdapter tabPageIndicatorAdapter;
    private boolean viewStatus;
    private ControllerSocial controllerSocial;
    private CustomSelectFriendsPopupWindow customSelectFriendsPopupWindow;
    private CustomConditionsPopupWindow customConditionsPopupWindow;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_social;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initClass() {
        mFragments.add(new FriendsCircleFragment());
        mFragments.add(new MessageFragment());
        controllerSocial = new ControllerSocial();
        customSelectFriendsPopupWindow = new CustomSelectFriendsPopupWindow(getActivity());
        customConditionsPopupWindow = new CustomConditionsPopupWindow(getActivity());
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerSocial;
    }

    @Override
    protected void initData() {
        titleList = Arrays.asList(getResources().getStringArray(R.array.social));

    }

    @Override
    protected void initView() {
        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getChildFragmentManager(), titleList, mFragments, true);
        view_page.setAdapter(tabPageIndicatorAdapter);
        tab_layout.setupWithViewPager(view_page);
        ViewBuilder.setIndicator(tab_layout, getResources().getInteger(R.integer.title_bar_margin), getResources().getInteger(R.integer.title_bar_margin));
        customConditionsPopupWindow.refreshView();
    }

    @Override
    protected void initListener() {
        tab_layout.setOnTabSelectedListener(this);
        customSelectFriendsPopupWindow.setOnSelectItemClickListener(this);
        customSelectFriendsPopupWindow.setOnDismissListener(this);
        customConditionsPopupWindow.setOnItemClickListener(this);
        customConditionsPopupWindow.setOnDismissListener(this);
    }

    @OnClick({R.id.title_about_img, R.id.dynamic_select, R.id.message_select})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.title_about_img:
                customConditionsPopupWindow.showAtLocation(view_page, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(getActivity());
                break;
            case R.id.dynamic_select:
                refreshView(true);
                selectGroupRefreshView(0);
                view_page.setCurrentItem(0);
                break;
            case R.id.message_select:
                selectGroupRefreshView(1);
                view_page.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        view_page.setCurrentItem(tab.getPosition());
        selectGroupRefreshView(tab.getPosition());
        refreshView(false);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void selectGroupRefreshView(int index) {
        switch (index) {
            case 0:
                dynamic_select.setTextColor(getResources().getColor(R.color.black));
                dynamic_select.setTextSize(16);
                message_select.setTextColor(getResources().getColor(R.color.gray_light_text));
                message_select.setTextSize(14);
                break;
            case 1:
                message_select.setTextColor(getResources().getColor(R.color.black));
                message_select.setTextSize(16);
                dynamic_select.setTextColor(getResources().getColor(R.color.gray_light_text));
                dynamic_select.setTextSize(14);
                break;
        }
    }

    private void refreshView(boolean viewStatus) {
        if (viewStatus) {
            customSelectFriendsPopupWindow.showAtLocation(tab_layout, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, SystemUtil.dp2px(getActivity(),70));
            SystemUtil.windowToDark(getActivity());
        } else {
            customSelectFriendsPopupWindow.dismiss();
            SystemUtil.windowToLight(getActivity());
        }
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(getActivity());
    }

    @Override
    public void setOnItemClick(String content) {
        RxBus.getDefault().post(new CommonEvent(EventCode.DYNAMIC_SELECT, content));
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.release_img:
                startActivity(new Intent(getActivity(), ReleaseNewDynamicActivity.class));
                break;
            case R.id.release_video:
                startActivity(new Intent(getActivity(), ReleaseNewVideoDynamicActivity.class));
                break;
        }
    }

}
