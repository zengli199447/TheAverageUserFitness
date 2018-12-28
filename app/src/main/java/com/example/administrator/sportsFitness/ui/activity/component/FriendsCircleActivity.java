package com.example.administrator.sportsFitness.ui.activity.component;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.adapter.TabPageIndicatorAdapter;
import com.example.administrator.sportsFitness.ui.fragment.im.FriendsCircleRelatedFragment;
import com.example.administrator.sportsFitness.ui.fragment.search.SearchTypeFragment;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FriendsCircleActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.group_view)
    RadioGroup group_view;

    private List<Fragment> mFragments = new ArrayList<>();
    private TabPageIndicatorAdapter tabPageIndicatorAdapter;
    private List<String> titleList = new ArrayList<>();
    private int[] ids = {R.id.life_radio_button, R.id.center_radio_button, R.id.right_radio_button};

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_friends_circle;
    }

    @Override
    protected void initClass() {
        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), titleList, mFragments);
        view_pager.setAdapter(tabPageIndicatorAdapter);
        tab_layout.setupWithViewPager(view_pager);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        titleList.addAll(Arrays.asList(getResources().getStringArray(R.array.friends_circle_all_type)));
        for (int i = 0; i < titleList.size(); i++) {
            FriendsCircleRelatedFragment friendsCircleRelatedFragment = new FriendsCircleRelatedFragment();
            Bundle data = new Bundle();
            data.putInt("type", i);
            friendsCircleRelatedFragment.setArguments(data);
            mFragments.add(friendsCircleRelatedFragment);
            RadioButton radioButton = (RadioButton) group_view.getChildAt(i);
            radioButton.setText(titleList.get(i));
        }
        ViewBuilder.setIndicator(tab_layout, getResources().getInteger(R.integer.title_bar_margin_max), getResources().getInteger(R.integer.title_bar_margin_max));
        tabPageIndicatorAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        group_view.setOnCheckedChangeListener(this);
        tab_layout.addOnTabSelectedListener(this);
    }

    @Override
    protected void onClickAble(View view) {

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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.life_radio_button:
                view_pager.setCurrentItem(0);
                break;
            case R.id.center_radio_button:
                view_pager.setCurrentItem(1);
                break;
            case R.id.right_radio_button:
                view_pager.setCurrentItem(2);
                break;
        }
        group_view.check(checkedId);
    }


}
