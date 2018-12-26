package com.example.administrator.sportsFitness.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseFragment;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.adapter.TabPageIndicatorAdapter;
import com.example.administrator.sportsFitness.ui.fragment.search.SearchTypeFragment;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/12/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class SearchFragment extends BaseFragment implements TabLayout.OnTabSelectedListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.group_view)
    RadioGroup group_view;
    @BindView(R.id.layout_table_layout)
    RelativeLayout layout_table_layout;

    private List<Fragment> mFragments = new ArrayList<>();
    private TabPageIndicatorAdapter tabPageIndicatorAdapter;
    private List<String> titleList = new ArrayList<>();
    private int[] ids = {R.id.the_gym, R.id.course, R.id.coach};

    private boolean OPEN_SEARCH_STATUS;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.OPEN_SEARCH:
                OPEN_SEARCH_STATUS = commonevent.isTemp_boolean();
                initDiversifiedFragment();
                initViewLayout();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        group_view.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onClickAble(View view) {

    }

    private void initViewLayout() {
        view_pager.removeAllViews();
        tab_layout.removeAllTabs();
        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getChildFragmentManager(), titleList, mFragments);
        view_pager.setAdapter(tabPageIndicatorAdapter);
        tab_layout.setupWithViewPager(view_pager);
        tab_layout.addOnTabSelectedListener(this);
        MeasurementIndicator();
    }

    private void initDiversifiedFragment() {
        titleList.clear();
        if (OPEN_SEARCH_STATUS) {
            titleList.addAll(Arrays.asList(getResources().getStringArray(R.array.search_type)));
            layout_table_layout.setVisibility(View.VISIBLE);
            for (int i = 0; i < titleList.size(); i++) {
                SearchTypeFragment searchTypeFragment = new SearchTypeFragment();
                Bundle data = new Bundle();
                data.putString("typeId", titleList.get(i));
                searchTypeFragment.setArguments(data);
                mFragments.add(searchTypeFragment);
            }
        } else {
            titleList.add(getString(R.string.course));
            layout_table_layout.setVisibility(View.GONE);
            SearchTypeFragment searchTypeFragment = new SearchTypeFragment();
            Bundle data = new Bundle();
            data.putString("typeId", getString(R.string.course));
            searchTypeFragment.setArguments(data);
            mFragments.add(searchTypeFragment);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        view_pager.setCurrentItem(tab.getPosition());
        group_view.check(ids[tab.getPosition()]);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void MeasurementIndicator() {
        ViewBuilder.setIndicator(tab_layout, getResources().getInteger(R.integer.title_bar_margin_max), getResources().getInteger(R.integer.title_bar_margin_max));
        tabPageIndicatorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.the_gym:
                view_pager.setCurrentItem(0);
                break;
            case R.id.course:
                view_pager.setCurrentItem(1);
                break;
            case R.id.coach:
                view_pager.setCurrentItem(2);
                break;
        }
        group_view.check(checkedId);
    }


}
