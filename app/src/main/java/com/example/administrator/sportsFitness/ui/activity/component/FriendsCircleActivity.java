package com.example.administrator.sportsFitness.ui.activity.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.adapter.TabPageIndicatorAdapter;
import com.example.administrator.sportsFitness.ui.fragment.im.FriendsCircleRelatedFragment;
import com.example.administrator.sportsFitness.ui.fragment.search.SearchTypeFragment;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class FriendsCircleActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_img)
    ImageView title_about_img;
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
    private int flags = 0;

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
        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), titleList, mFragments, false);
        view_pager.setAdapter(tabPageIndicatorAdapter);
        tab_layout.setupWithViewPager(view_pager);

    }

    @Override
    protected void initData() {
        flags = getIntent().getFlags();
    }

    @Override
    protected void initView() {
        title_name.setText(getText(R.string.friends_circle));
        title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.message_icon));
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

        view_pager.setCurrentItem(flags);
        group_view.check(ids[flags]);
    }

    @Override
    protected void initListener() {
        group_view.setOnCheckedChangeListener(this);
        tab_layout.addOnTabSelectedListener(this);
    }

    @OnClick({R.id.img_btn_black, R.id.title_about_img})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.title_about_img:
                Intent webIntent = new Intent(this, WebConcentratedActivity.class);
                webIntent.putExtra("link", new StringBuffer().append("do=friend&userid=").append(DataClass.USERID).toString());
                webIntent.putExtra("titleName", getString(R.string.friends_apply_form));
                startActivity(webIntent);
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
