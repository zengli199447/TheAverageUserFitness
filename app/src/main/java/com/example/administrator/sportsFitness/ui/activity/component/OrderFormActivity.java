package com.example.administrator.sportsFitness.ui.activity.component;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.adapter.TabPageIndicatorAdapter;
import com.example.administrator.sportsFitness.ui.fragment.order.OrderFormFragment;
import com.example.administrator.sportsFitness.ui.fragment.search.SearchTypeFragment;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/1/3.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class OrderFormActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.group_view)
    RadioGroup group_view;

    private List<Fragment> mFragments = new ArrayList<>();
    private TabPageIndicatorAdapter tabPageIndicatorAdapter;
    private int[] ids = {R.id.one_radio_button, R.id.two_radio_button, R.id.three_radio_button, R.id.four_radio_button, R.id.five_radio_button};
    private List<String> titleList;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order_form;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {
        titleList = Arrays.asList(getResources().getStringArray(R.array.order_type));
        for (int i = 0; i < titleList.size(); i++) {
            OrderFormFragment orderFormFragment = new OrderFormFragment();
            Bundle data = new Bundle();
            data.putString("typeId", titleList.get(i));
            orderFormFragment.setArguments(data);
            mFragments.add(orderFormFragment);
            RadioButton radioButton = (RadioButton) group_view.getChildAt(i);
            radioButton.setText(titleList.get(i));
        }

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.order));
        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), titleList, mFragments,false);
        view_pager.setAdapter(tabPageIndicatorAdapter);
        tab_layout.setupWithViewPager(view_pager);
        ViewBuilder.setIndicator(tab_layout, getResources().getInteger(R.integer.title_bar_margin_min), getResources().getInteger(R.integer.title_bar_margin_min));
        tabPageIndicatorAdapter.notifyDataSetChanged();
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
            case R.id.one_radio_button:
                view_pager.setCurrentItem(0);
                break;
            case R.id.two_radio_button:
                view_pager.setCurrentItem(1);
                break;
            case R.id.three_radio_button:
                view_pager.setCurrentItem(2);
                break;
            case R.id.four_radio_button:
                view_pager.setCurrentItem(3);
                break;
            case R.id.five_radio_button:
                view_pager.setCurrentItem(4);
                break;
        }
        group_view.check(checkedId);
    }

}
