package com.example.administrator.sportsfitness.ui.activity.component;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.ui.adapter.TabPageIndicatorAdapter;
import com.example.administrator.sportsfitness.ui.fragment.collection.CollectionFragment;
import com.example.administrator.sportsfitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/1/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CollectionActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.title_name)
    TextView title_name;
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
    private int[] ids = {R.id.life_radio_button, R.id.center_radio_button, R.id.right_radio_button};

    private boolean OPEN_SEARCH_STATUS;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initClass() {
        titleList.addAll(Arrays.asList(getResources().getStringArray(R.array.collection_type)));
        layout_table_layout.setVisibility(View.VISIBLE);
        for (int i = 0; i < titleList.size(); i++) {
            CollectionFragment collectionFragment = new CollectionFragment();
            Bundle data = new Bundle();
            data.putString("typeId", titleList.get(i));
            data.putString("relatedId", "");
            collectionFragment.setArguments(data);
            mFragments.add(collectionFragment);
            RadioButton radioButton = (RadioButton) group_view.getChildAt(i);
            radioButton.setText(titleList.get(i));
        }


        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), titleList, mFragments, true);
        view_pager.setAdapter(tabPageIndicatorAdapter);
        tab_layout.setupWithViewPager(view_pager);
        ViewBuilder.setIndicator(tab_layout, getResources().getInteger(R.integer.title_bar_margin_max), getResources().getInteger(R.integer.title_bar_margin_max));
        tabPageIndicatorAdapter.notifyDataSetChanged();
        view_pager.setOffscreenPageLimit(mFragments.size());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.my_collection));
    }

    @Override
    protected void initListener() {
        group_view.setOnCheckedChangeListener(this);
        tab_layout.addOnTabSelectedListener(this);
    }

    @OnClick({R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
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
