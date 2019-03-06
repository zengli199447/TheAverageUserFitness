package com.example.administrator.sportsfitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.InfoGymNetBean;
import com.example.administrator.sportsfitness.model.bean.PayObject;
import com.example.administrator.sportsfitness.model.bean.TopUpNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxBus;
import com.example.administrator.sportsfitness.ui.controller.ControllerGymInfomation;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.ui.view.CustomPayPopupWindow;
import com.example.administrator.sportsfitness.ui.view.SlideRecyclerView;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GymInfomationActivity extends BaseActivity implements CustomPayPopupWindow.OnItemSelectClickListener, CustomPayPopupWindow.OnDismissListener,
        ControllerGymInfomation.OnRefreshCountListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.gym_img)
    ImageView gym_img;
    @BindView(R.id.gym_content)
    TextView gym_content;
    @BindView(R.id.standard)
    TextView standard;
    @BindView(R.id.people_count)
    TextView people_count;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView recycler_view;
    @BindView(R.id.total_content)
    TextView total_content;
    private ControllerGymInfomation controllerGymInfomation;
    private CustomPayPopupWindow customPayPopupWindow;
    private Bundle gymInfo;
    private int price = 1;
    private String allPrice;
    TopUpNetBean.ResultBean result;
    private ShowDialog instance;


    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.GYM_CONVENTION:
                instance.showHelpfulHintsDialog(this, getString(R.string.convention_successful), EventCode.GYM_CONVENTION_SUCCESSFUL);
                break;
            case EventCode.GYM_CONVENTION_SUCCESSFUL:
                finish();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_gym_infomation;
    }

    @Override
    protected void initClass() {
        gymInfo = getIntent().getBundleExtra("GYMInfo");
        controllerGymInfomation = new ControllerGymInfomation(recycler_view, this.gymInfo);
        customPayPopupWindow = new CustomPayPopupWindow(this);
        instance = ShowDialog.getInstance();
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerGymInfomation;
    }

    @Override
    protected void initData() {
        price = Integer.valueOf(gymInfo.getString("Price"));

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.convention_gym));
        recycler_view.setFocusable(false);
        refreshView();
    }

    @Override
    protected void initListener() {
        customPayPopupWindow.setOnSelectItemClickListener(this);
        customPayPopupWindow.setOnDismissListener(this);
        controllerGymInfomation.setOnRefreshCountListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.pay_order, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.pay_order:
                controllerGymInfomation.NetConvention();

                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    private void refreshView() {

        SystemUtil.textMagicTool(this, gym_content, gymInfo.getString("Shopname"), gymInfo.getString("Fulladdress"),
                R.dimen.dp14, R.dimen.dp13, R.color.black, R.color.black_overlay, "\n");

        SystemUtil.textMagicTool(this, standard, gymInfo.getString("Price"), getString(R.string.price_prompt),
                R.dimen.dp15, R.dimen.dp10, R.color.red_text, R.color.red_text, "");

        Glide.with(this).load(SystemUtil.JudgeUrl(gymInfo.getString("Photo"))).error(R.drawable.banner_off).into(gym_img);

        refreshStatistical(1);

        people_count.setText("1");
    }


    @Override
    public void setOnItemClick(int index) {
        PayObject payObject = new PayObject(index, EventCode.GYM_CONVENTION, result.getOrdercode());
        RxBus.getDefault().post(new CommonEvent(EventCode.PAY_ACTION, payObject));
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }


    @Override
    public void onRefreshCountListener(int count) {
        refreshStatistical(count);
    }

    @Override
    public void onPayActionListener(TopUpNetBean.ResultBean result) {
        this.result = result;
        customPayPopupWindow.refreshPageView(Double.valueOf(allPrice), Double.valueOf(DataClass.MONEY));
        customPayPopupWindow.showAtLocation(recycler_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        SystemUtil.windowToDark(this);
    }

    private void refreshStatistical(int count) {
        people_count.setText(String.valueOf(count));
        allPrice = String.valueOf(count * price);
        SystemUtil.textMagicTool(this, total_content, allPrice, getString(R.string.price_unit),
                R.dimen.dp15, R.dimen.dp13, R.color.red_text, R.color.black_overlay, "");
    }

}
