package com.example.administrator.sportsFitness.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/9/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TabPageIndicatorAdapter extends FragmentStatePagerAdapter {

    private List<String> strings;
    private List<Fragment> FragmentList;

    public TabPageIndicatorAdapter(FragmentManager supportFragmentManager, List<String> strings, List<Fragment> FragmentList) {
        super(supportFragmentManager);
        this.strings = strings;
        this.FragmentList = FragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = FragmentList.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position % strings.size());

    }



}
