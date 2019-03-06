package com.example.administrator.sportsfitness.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.example.administrator.sportsfitness.R;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout {


    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setProgressViewOffset(true, -20, 100);
        setColorSchemeResources(R.color.blue_bar);
    }


}
