package com.example.administrator.sportsFitness.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseFragment;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.ui.controller.ControllerCourse;
import com.example.administrator.sportsFitness.ui.view.CustomTimeChoicePopupWindow;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.CalendarBuilder;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CourseFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, CustomTimeChoicePopupWindow.OnItemClickListener, PopupWindow.OnDismissListener {

    @BindView(R.id.location_city)
    ImageView location_city;
    @BindView(R.id.qr_code)
    ImageView qr_code;
    @BindView(R.id.search_layout)
    RelativeLayout search_layout;
    @BindView(R.id.time_select)
    TextView time_select;
    @BindView(R.id.group_view)
    RadioGroup group_view;
    @BindView(R.id.time_select_content)
    TextView time_select_content;

    @BindView(R.id.category_recycler)
    RecyclerView category_recycler;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.fitness_course_recycler)
    RecyclerView fitness_course_recycler;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;

    @BindView(R.id.layout_title_bar)
    RelativeLayout layout_title_bar;
    @BindView(R.id.number_of_people)
    RadioButton number_of_people;
    @BindView(R.id.price)
    RadioButton price;
    @BindView(R.id.score)
    RadioButton score;
    @BindView(R.id.time_select_layout)
    RelativeLayout time_select_layout;

    boolean typeSelectOpenStatus;
    private ControllerCourse controllerCourse;
    private CustomTimeChoicePopupWindow customTimeChoicePopupWindow;
    private String selectTime = "";
    private int flagStatus;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course;
    }

    @Override
    protected void initClass() {
        Bundle bundle = getArguments();
        flagStatus = bundle != null ? bundle.getInt("flagStatus") : -1;
        controllerCourse = new ControllerCourse(category_recycler, empty_layout, fitness_course_recycler, swipe_layout, flagStatus);
        customTimeChoicePopupWindow = new CustomTimeChoicePopupWindow(getActivity(), new CalendarBuilder(Calendar.getInstance()), false, true);
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerCourse;
    }

    @Override
    protected void initData() {

        toastUtil.showToast("flagStatus : " + flagStatus);

    }

    @Override
    protected void initView() {
        switch (flagStatus) {
            case EventCode.COURSE:
                location_city.setVisibility(View.GONE);
                qr_code.setVisibility(View.GONE);
                ViewBuilder.ChangeRelativeLayoutViewMagin(getActivity(), search_layout, 15, 0, 15, 0);
                break;
            case EventCode.COACH_PRIVATE:
                layout_title_bar.setVisibility(View.GONE);
                number_of_people.setVisibility(View.GONE);

                break;
            case EventCode.GYM:
                layout_title_bar.setVisibility(View.GONE);
                number_of_people.setVisibility(View.GONE);
                time_select_layout.setVisibility(View.GONE);
                score.setVisibility(View.VISIBLE);

                break;
        }
        ViewBuilder.ProgressStyleChange(swipe_layout, SystemUtil.dp2px(getActivity(), 130));
    }

    @Override
    protected void initListener() {
        group_view.setOnCheckedChangeListener(this);
        customTimeChoicePopupWindow.setOnItemClickListener(this);
        customTimeChoicePopupWindow.setOnDismissListener(this);

    }

    @OnClick({R.id.search_layout, R.id.time_select})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.search_layout:
                RxBus.getDefault().post(new CommonEvent(EventCode.OPEN_SEARCH, false));
                break;
            case R.id.time_select:
                if (typeSelectOpenStatus) {
                    refreshSelectView(0);
                } else {
                    refreshSelectView(1);
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        controllerCourse.changePageNumber();
        switch (checkedId) {
            case R.id.price:
                controllerCourse.changeReason("1");
                break;
            case R.id.near:
                controllerCourse.changeReason("2");
                break;
            case R.id.number_of_people:
                controllerCourse.changeReason("3");
                break;
            case R.id.score:
                controllerCourse.changeReason("4");
                break;
        }
        switch (flagStatus) {
            case EventCode.COURSE:

                break;
            case EventCode.COACH_PRIVATE:

                break;
            case EventCode.GYM:

                break;
        }
        controllerCourse.RefreshNetWorkData(selectTime);
    }

    //类型栏状态刷新
    public void refreshSelectView(int status) {
        switch (status) {
            case 0:
                ViewBuilder.textDrawable(time_select, getActivity(), R.drawable.black_down_icon, 2);
                customTimeChoicePopupWindow.dismiss();
                break;
            case 1:
                ViewBuilder.textDrawable(time_select, getActivity(), R.drawable.black_up_icon, 2);
                customTimeChoicePopupWindow.refreshTitle(getString(R.string.appointment_time), 0);
                customTimeChoicePopupWindow.showAtLocation(group_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                SystemUtil.windowToDark(getActivity());
                break;
        }
        typeSelectOpenStatus = !typeSelectOpenStatus;
    }

    @Override
    public void onDismiss() {
        refreshSelectView(0);
    }

    @Override
    public void setOnItemClick(View v, String year, String month, String day, String hour, String minute, int selectType) {
        selectTime = new StringBuffer().append(year).append(".").append(month).append(".").append(day).toString();
        time_select_content.setText(selectTime);
        controllerCourse.RefreshNetWorkData(selectTime);
    }

}
