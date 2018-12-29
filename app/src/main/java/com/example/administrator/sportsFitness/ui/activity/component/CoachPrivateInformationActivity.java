package com.example.administrator.sportsFitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.administrator.sportsFitness.ui.controller.ControllerCoachPrivateInformation;
import com.example.administrator.sportsFitness.ui.view.CustomPayPopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/26.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CoachPrivateInformationActivity extends BaseActivity implements CustomPayPopupWindow.OnItemSelectClickListener, CustomPayPopupWindow.OnDismissListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.user_img)
    ImageView user_img;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.standard)
    TextView standard;

    @BindView(R.id.select_training_camp)
    TextView select_training_camp;
    @BindView(R.id.select_student)
    TextView select_student;

    @BindView(R.id.total_content)
    TextView total_content;
    private CustomPayPopupWindow customPayPopupWindow;
    private ControllerCoachPrivateInformation controllerCoachPrivateInformation;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_coach_private_information;
    }

    @Override
    protected void initClass() {
        customPayPopupWindow = new CustomPayPopupWindow(this);
        controllerCoachPrivateInformation = new ControllerCoachPrivateInformation();

    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerCoachPrivateInformation;
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
        customPayPopupWindow.setOnSelectItemClickListener(this);
        customPayPopupWindow.setOnDismissListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.pay_order, R.id.select_training_camp, R.id.select_student, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        Intent intent = new Intent(this, SelectDiversifiedFormActivity.class);
        switch (view.getId()) {
            case R.id.pay_order:
                customPayPopupWindow.refreshPageView(20, 0);
                customPayPopupWindow.showAtLocation(total_content, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(this);
                break;
            case R.id.select_training_camp:
                intent.setFlags(1);
                intent.putExtra("typeId", "");
                startActivity(intent);
                break;
            case R.id.select_student:
                intent.setFlags(2);
                intent.putExtra("typeId", "");
                startActivity(intent);
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    private void refreshView() {

        SystemUtil.textMagicTool(this, user_name, DataClass.UNAME, "11月06日  16:00~18:00",
                R.dimen.dp14, R.dimen.dp13, R.color.black, R.color.black_overlay, "\n");

        SystemUtil.textMagicTool(this, standard, getString(R.string.money), "159",
                R.dimen.dp10, R.dimen.dp15, R.color.red_text, R.color.red_text, "");

        Glide.with(this).load(SystemUtil.JudgeUrl(DataClass.USERPHOTO)).error(R.drawable.banner_off).into(user_img);

        SystemUtil.textMagicTool(this, total_content, "20", "元",
                R.dimen.dp15, R.dimen.dp13, R.color.red_text, R.color.black_overlay, "");

    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    @Override
    public void setOnItemClick(int index) {
        toastUtil.showToast("index : " + index);
    }


}
