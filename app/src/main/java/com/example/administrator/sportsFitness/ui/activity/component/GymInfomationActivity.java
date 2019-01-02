package com.example.administrator.sportsFitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.controller.ControllerGymInfomation;
import com.example.administrator.sportsFitness.ui.view.CustomPayPopupWindow;
import com.example.administrator.sportsFitness.ui.view.SlideRecyclerView;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class GymInfomationActivity extends BaseActivity implements CustomPayPopupWindow.OnItemSelectClickListener, CustomPayPopupWindow.OnDismissListener {

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


    @Override
    protected void registerEvent(CommonEvent commonevent) {

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
        controllerGymInfomation = new ControllerGymInfomation(recycler_view);
        customPayPopupWindow = new CustomPayPopupWindow(this);

    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerGymInfomation;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        recycler_view.setFocusable(false);
        refreshView();
    }

    @Override
    protected void initListener() {
        customPayPopupWindow.setOnSelectItemClickListener(this);
        customPayPopupWindow.setOnDismissListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.pay_order, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        Intent intent = new Intent(this, SelectDiversifiedFormActivity.class);
        switch (view.getId()) {
            case R.id.pay_order:
                customPayPopupWindow.refreshPageView(20, 0);
                customPayPopupWindow.showAtLocation(recycler_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    private void refreshView() {

        SystemUtil.textMagicTool(this, gym_content, "HKOCH", "武汉市洪山区光谷创业街",
                R.dimen.dp14, R.dimen.dp13, R.color.black, R.color.black_overlay, "\n");

        SystemUtil.textMagicTool(this, standard, "20", "元/次",
                R.dimen.dp15, R.dimen.dp10, R.color.red_text, R.color.red_text, "");

        Glide.with(this).load(SystemUtil.JudgeUrl(DataClass.USERPHOTO)).error(R.drawable.banner_off).into(gym_img);

        SystemUtil.textMagicTool(this, total_content, "20", "元",
                R.dimen.dp15, R.dimen.dp13, R.color.red_text, R.color.black_overlay, "");

        people_count.setText("");
    }

    @Override
    public void setOnItemClick(int index) {
        toastUtil.showToast("index : " + index);
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

}
