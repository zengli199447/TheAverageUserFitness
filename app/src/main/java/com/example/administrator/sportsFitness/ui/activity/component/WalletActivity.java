package com.example.administrator.sportsFitness.ui.activity.component;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.controller.ConreollerWallet;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class WalletActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.user_img)
    ImageView user_img;
    @BindView(R.id.user_content)
    TextView user_content;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.top_up_recycler_view)
    RecyclerView top_up_recycler_view;
    @BindView(R.id.confirm_top_up)
    TextView confirm_top_up;
    @BindView(R.id.billing_recycler_view)
    RecyclerView billing_recycler_view;
    private ConreollerWallet conreollerWallet;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initClass() {
        conreollerWallet = new ConreollerWallet(top_up_recycler_view, billing_recycler_view);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return conreollerWallet;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        refreshView();
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.confirm_top_up})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.confirm_top_up:

                break;
        }
    }

    private void refreshView() {

        Glide.with(this).load(SystemUtil.JudgeUrl(DataClass.USERPHOTO)).error(R.drawable.banner_off).into(user_img);

        SystemUtil.textMagicTool(this, user_content, DataClass.UNAME, "1507208080", R.dimen.dp14, R.dimen.dp12
                , R.color.white, R.color.white, "\n");

        SystemUtil.textMagicToolTypeFace(this, money, getString(R.string.money), "124.50", R.dimen.dp10, R.dimen.dp20
                , R.color.white, R.color.white, "", false);
    }

}
