package com.example.administrator.sportsFitness.ui.fragment.search;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseFragment;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.controller.ControllerSearchType;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/12/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class SearchTypeFragment extends BaseFragment {

    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    private ControllerSearchType controllerSearchType;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_type;
    }

    @Override
    protected void initClass() {
        Bundle bundle = getArguments();
        String typeId = bundle != null ? bundle.getString("typeId") : "";
        String relatedId = bundle != null ? bundle.getString("relatedId") : "";
        controllerSearchType = new ControllerSearchType(empty_layout, recycler_view, typeId, relatedId);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerSearchType;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        scrollView.setBackgroundColor(getResources().getColor(R.color.gray_));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onClickAble(View view) {

    }


}
