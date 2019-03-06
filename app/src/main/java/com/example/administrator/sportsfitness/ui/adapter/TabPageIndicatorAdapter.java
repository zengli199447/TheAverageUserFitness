package com.example.administrator.sportsfitness.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

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
    boolean save;

    public TabPageIndicatorAdapter(FragmentManager supportFragmentManager, List<String> strings, List<Fragment> FragmentList, boolean save) {
        super(supportFragmentManager);
        this.strings = strings;
        this.FragmentList = FragmentList;
        this.save = save;
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

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (save)
            super.destroyItem(container, position, object);
    }
}
