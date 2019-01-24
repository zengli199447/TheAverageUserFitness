package com.example.administrator.sportsFitness.ui.activity.component;

import android.annotation.SuppressLint;
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
import com.example.administrator.sportsFitness.model.bean.PayObject;
import com.example.administrator.sportsFitness.model.bean.TopUpNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.ui.controller.ControllerCourseInfomation;
import com.example.administrator.sportsFitness.ui.controller.ControllerGymInfomation;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.CustomPayPopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/1/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CourseInformationActivity extends BaseActivity implements CustomPayPopupWindow.OnItemSelectClickListener, CustomPayPopupWindow.OnDismissListener,
        ControllerCourseInfomation.OnRefreshCountListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.course_img)
    ImageView course_img;
    @BindView(R.id.course_content)
    TextView course_content;
    @BindView(R.id.creat_time)
    TextView creat_time;
    @BindView(R.id.standard)
    TextView standard;
    @BindView(R.id.people_count)
    TextView people_count;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView recycler_view;
    @BindView(R.id.total_content)
    TextView total_content;
    private ControllerCourseInfomation controllerCourseInfomation;
    private CustomPayPopupWindow customPayPopupWindow;
    private Bundle CourseInfo;
    private Integer price;
    private String allPrice;
    TopUpNetBean.ResultBean result;
    private ShowDialog instance;


    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.COURSE_CONVENTION:
                instance.showHelpfulHintsDialog(this, getString(R.string.convention_successful), EventCode.COURSE_CONVENTION_SUCCESSFUL);
                break;
            case EventCode.COURSE_CONVENTION_SUCCESSFUL:
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
        return R.layout.activity_course_information;
    }

    @Override
    protected void initClass() {
        CourseInfo = getIntent().getBundleExtra("CourseInfo");
        controllerCourseInfomation = new ControllerCourseInfomation(recycler_view, CourseInfo);
        customPayPopupWindow = new CustomPayPopupWindow(this);
        instance = ShowDialog.getInstance();
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerCourseInfomation;
    }

    @Override
    protected void initData() {
        price = Integer.valueOf(CourseInfo.getString("Price"));
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.convention_course));
        recycler_view.setFocusable(false);
        refreshView();
    }

    @Override
    protected void initListener() {
        customPayPopupWindow.setOnSelectItemClickListener(this);
        customPayPopupWindow.setOnDismissListener(this);
        controllerCourseInfomation.setOnRefreshCountListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.pay_order, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.pay_order:
                controllerCourseInfomation.NetConvention();
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
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

    @Override
    public void setOnItemClick(int index) {
        PayObject payObject = new PayObject(index, EventCode.COURSE_CONVENTION, result.getOrdercode());
        RxBus.getDefault().post(new CommonEvent(EventCode.PAY_ACTION, payObject));
    }

    private void refreshView() {

        SystemUtil.textMagicTool(this, course_content, CourseInfo.getString("Coursesname"), CourseInfo.getString("Shopname"),
                R.dimen.dp14, R.dimen.dp13, R.color.black, R.color.black_overlay, "\n");

        SystemUtil.textMagicTool(this, standard, getString(R.string.money), CourseInfo.getString("Price"),
                R.dimen.dp10, R.dimen.dp15, R.color.red_text, R.color.red_text, "");

        Glide.with(this).load(SystemUtil.JudgeUrl(CourseInfo.getString("Photo"))).error(R.drawable.banner_off).into(course_img);

        refreshStatistical(1);

        String time = new StringBuffer().append(CourseInfo.getString("Date_start")).append("-").
                append(CourseInfo.getString("Date_end")).append("   ").append(CourseInfo.getString("Time_start"))
                .append("-").append(CourseInfo.getString("Time_end")).toString();

        creat_time.setText(time);

        people_count.setText("1");

    }

    private void refreshStatistical(int count) {
        people_count.setText(String.valueOf(count));
        allPrice = String.valueOf(count * price);
        SystemUtil.textMagicTool(this, total_content, allPrice, getString(R.string.price_unit),
                R.dimen.dp15, R.dimen.dp13, R.color.red_text, R.color.black_overlay, "");
    }

}
