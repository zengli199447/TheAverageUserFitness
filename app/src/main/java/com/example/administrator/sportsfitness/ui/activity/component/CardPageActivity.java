package com.example.administrator.sportsfitness.ui.activity.component;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.ui.controller.ControllerCardPage;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CardPageActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_img)
    ImageView title_about_img;
    @BindView(R.id.card_bind_phone_number)
    TextView card_bind_phone_number;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    private ControllerCardPage controllerCardPage;


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_card_page;
    }

    @Override
    protected void initClass() {
        controllerCardPage = new ControllerCardPage(recycler_view);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerCardPage;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.card));
        title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.doubt_icon));
        card_bind_phone_number.setText(DataClass.PHONE);
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.img_btn_black, R.id.title_about_img})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.title_about_img:

                break;
        }
    }

}
