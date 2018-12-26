package com.example.administrator.sportsFitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerInfoGym;
import com.example.administrator.sportsFitness.ui.view.ShinyView;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/25.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class InfoGymActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_img)
    ImageView title_about_img;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @BindView(R.id.banner)
    ImageView banner;
    @BindView(R.id.img_tag)
    TextView img_tag;

    @BindView(R.id.title_content)
    TextView title_content;
    @BindView(R.id.standard)
    TextView standard;
    @BindView(R.id.shiny_view)
    ShinyView shiny_view;
    @BindView(R.id.people_count_consumption)
    TextView people_count_consumption;
    @BindView(R.id.coordinates)
    TextView coordinates;
    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.course_recycler_view)
    RecyclerView course_recycler_view;
    @BindView(R.id.instructions)
    TextView instructions;
    @BindView(R.id.evaluation_recycler_view)
    RecyclerView evaluation_recycler_view;

    @BindView(R.id.reservations_now)
    TextView reservations_now;

    private int bannerTopHeight;
    private int reservationsHeight;
    private RelativeLayout.LayoutParams reservationsNowLayoutParams;
    private int magin;
    private ControllerInfoGym controllerInfoGym;


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_info_gym;
    }

    @Override
    protected void initClass() {
        reservationsNowLayoutParams = (RelativeLayout.LayoutParams) reservations_now.getLayoutParams();
        controllerInfoGym = new ControllerInfoGym(course_recycler_view, evaluation_recycler_view);

    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerInfoGym;
    }

    @Override
    protected void initData() {
        bannerTopHeight = SystemUtil.dp2px(this, 180);
        reservationsHeight = SystemUtil.dp2px(this, 50);
        magin = SystemUtil.dp2px(this, 30);

    }

    @Override
    protected void initView() {
        refreshView();
        course_recycler_view.setFocusable(false);
        evaluation_recycler_view.setFocusable(false);
    }

    @Override
    protected void initListener() {
        scrollView.setOnScrollChangeListener(this);

    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.img_btn_black, R.id.navigation, R.id.course_more, R.id.evaluation_more, R.id.reservations_now})
    @Override
    protected void onClickAble(View view) {
        Intent intent = new Intent(this, GeneralFormActivity.class);
        switch (view.getId()) {
            case R.id.img_btn_black:

                break;
            case R.id.navigation:

                break;
            case R.id.course_more:
                intent.setFlags(EventCode.COURSE_ZOO);
                intent.putExtra("gymId", "");
                intent.putExtra("gymName", "欧时莱健身房");
                startActivity(intent);
                break;
            case R.id.evaluation_more:
                intent.setFlags(EventCode.COMMENTS);
                intent.putExtra("gymId", "");
                intent.putExtra("gymName", "欧时莱健身房");
                intent.putExtra("userName", "欧时莱健身房");
                startActivity(intent);
                break;
            case R.id.reservations_now:

                break;
        }
    }

    public void refreshView() {
        title_content.setText("欧时莱健身房（光谷风情街店）");

        SystemUtil.textMagicTool(this, standard, "20", "元/次", R.dimen.dp15, R.dimen.dp10, R.color.red_text, R.color.red_text, "");

        SystemUtil.textMagicTool(this, coordinates, "武汉市洪山区广告SBI创业街10栋A座", "距离您1.5km", R.dimen.dp14, R.dimen.dp10, R.color.black_overlay, R.color.gray_light_text, "\n");

        title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.like_icon));

    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int difference = bannerTopHeight - scrollY;
        if (difference < 0)
            difference = 0;
        float proportion = (float) difference / (float) bannerTopHeight;
        reservations_now.setAlpha(1 * proportion);
        reservationsNowLayoutParams.setMargins(-magin, 0, -magin, -(int) (reservationsHeight - proportion * reservationsHeight));
        reservations_now.setLayoutParams(reservationsNowLayoutParams);

    }


}
