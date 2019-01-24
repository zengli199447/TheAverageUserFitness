package com.example.administrator.sportsFitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.GymFormNetBean;
import com.example.administrator.sportsFitness.model.bean.InfoCoachPrivateNetBean;
import com.example.administrator.sportsFitness.model.bean.PayObject;
import com.example.administrator.sportsFitness.model.bean.TopUpNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.ui.controller.ControllerCoachPrivateInformation;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.CustomPayPopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/26.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CoachPrivateInformationActivity extends BaseActivity implements CustomPayPopupWindow.OnItemSelectClickListener, CustomPayPopupWindow.OnDismissListener,
        ControllerCoachPrivateInformation.OnPayActionListener {

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
    private Bundle bundle;
    private String gymId = "";
    private ShowDialog instance;
    TopUpNetBean.ResultBean result;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.GYM_CLASS_SELECT:
                GymFormNetBean.ResultBean.ShopBean busObject = (GymFormNetBean.ResultBean.ShopBean) commonevent.getBusObject();
                gymId = busObject.getShopid();
                select_training_camp.setText(busObject.getShopname());
                break;
            case EventCode.COACH_CONVENTION:
                instance.showHelpfulHintsDialog(this, getString(R.string.convention_successful), EventCode.COACH_CONVENTION_SUCCESSFUL);
                break;
            case EventCode.COACH_CONVENTION_SUCCESSFUL:
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
        return R.layout.activity_coach_private_information;
    }

    @Override
    protected void initClass() {
        customPayPopupWindow = new CustomPayPopupWindow(this);
        controllerCoachPrivateInformation = new ControllerCoachPrivateInformation();
        instance = ShowDialog.getInstance();
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerCoachPrivateInformation;
    }

    @Override
    protected void initData() {
        bundle = getIntent().getBundleExtra("CoachPrivate");
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.coach_private));
        refreshView();
    }

    @Override
    protected void initListener() {
        customPayPopupWindow.setOnSelectItemClickListener(this);
        customPayPopupWindow.setOnDismissListener(this);
        controllerCoachPrivateInformation.setOnPayActionListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.pay_order, R.id.select_training_camp, R.id.select_student, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.pay_order:
                if (!gymId.isEmpty()) {
                    controllerCoachPrivateInformation.NetConvention(bundle.getString("CoachId"), gymId);
                } else {
                    instance.showHelpfulHintsDialog(this, getString(R.string.empty_error), EventCode.ONSTART);
                }
                break;
            case R.id.select_training_camp:
                startActivity(new Intent(this, GymClassSelectActivity.class));
                break;
            case R.id.select_student:

                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    private void refreshView() {

        String timeContent = new StringBuffer().append(bundle.getString("Date_start_txt")).append("   ").append(bundle.getString("Time_start_txt")).append("~").append(bundle.getString("Time_end_txt")).toString();

        SystemUtil.textMagicTool(this, user_name, bundle.getString("Secondname"), timeContent,
                R.dimen.dp14, R.dimen.dp13, R.color.black, R.color.black_overlay, "\n");

        SystemUtil.textMagicTool(this, standard, getString(R.string.money), bundle.getString("Price"),
                R.dimen.dp10, R.dimen.dp15, R.color.red_text, R.color.red_text, "");

        Glide.with(this).load(SystemUtil.JudgeUrl(bundle.getString("Photo"))).error(R.drawable.banner_off).into(user_img);

        SystemUtil.textMagicTool(this, total_content, bundle.getString("Price"), getString(R.string.price_unit),
                R.dimen.dp15, R.dimen.dp13, R.color.red_text, R.color.black_overlay, "");

        select_student.setText(DataClass.UNAME);

    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    @Override
    public void setOnItemClick(int index) {
        toastUtil.showToast("index : " + index);
        PayObject payObject = new PayObject(index, EventCode.COACH_CONVENTION, result.getOrdercode());
        RxBus.getDefault().post(new CommonEvent(EventCode.PAY_ACTION, payObject));
    }

    @Override
    public void onPayActionListener(TopUpNetBean.ResultBean result) {
        this.result = result;
        customPayPopupWindow.refreshPageView(Double.valueOf(bundle.getString("Price")), Double.valueOf(DataClass.MONEY));
        customPayPopupWindow.showAtLocation(total_content, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        SystemUtil.windowToDark(this);
    }


}
