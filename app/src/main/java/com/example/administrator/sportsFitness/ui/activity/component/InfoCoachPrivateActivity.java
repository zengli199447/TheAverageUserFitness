package com.example.administrator.sportsFitness.ui.activity.component;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.utils.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/25.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class InfoCoachPrivateActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener {

    @BindView(R.id.title_name)
    TextView title_name;
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

    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.user_img)
    ImageView user_img;
    @BindView(R.id.user_content)
    TextView user_content;
    @BindView(R.id.coordinates)
    TextView coordinates;
    @BindView(R.id.instructions)
    TextView instructions;

    @BindView(R.id.layout_part_of_the_item)
    RelativeLayout layout_part_of_the_item;
    @BindView(R.id.controller_layout)
    RelativeLayout controller_layout;
    private int bannerTopHeight;
    private int controllerHeight;
    private int magin;
    private RelativeLayout.LayoutParams controllerLayoutLayoutParams;


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_info_coach_private;
    }

    @Override
    protected void initClass() {
        controllerLayoutLayoutParams = (RelativeLayout.LayoutParams) controller_layout.getLayoutParams();

    }

    @Override
    protected void initData() {
        bannerTopHeight = SystemUtil.dp2px(this, 180);
        controllerHeight = SystemUtil.dp2px(this, 50);
        magin = SystemUtil.dp2px(this, 30);

    }

    @Override
    protected void initView() {
        refreshView();

    }

    @Override
    protected void initListener() {
        scrollView.setOnScrollChangeListener(this);

    }

    @OnClick({R.id.img_btn_black, R.id.user_img, R.id.controller_life, R.id.controller_right})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:

                break;
            case R.id.user_img:

                break;
            case R.id.controller_life:

                break;
            case R.id.controller_right:
                startActivity(new Intent(this, CoachPrivateInformationActivity.class));
                break;
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int difference = bannerTopHeight - scrollY;
        if (difference < 0)
            difference = 0;
        float proportion = (float) difference / (float) bannerTopHeight;
        controller_layout.setAlpha(1 * proportion);
        controllerLayoutLayoutParams.setMargins(-magin, 0, -magin, -(int) (controllerHeight - proportion * controllerHeight));
        controller_layout.setLayoutParams(controllerLayoutLayoutParams);
    }

    public void refreshView() {
        title_content.setText("瑜伽一对一私人教练");

        SystemUtil.textMagicTool(this, standard, getString(R.string.money), "159", R.dimen.dp10, R.dimen.dp15, R.color.red_text, R.color.red_text, "");

        SystemUtil.textMagicTool(this, user_content, DataClass.UNAME, "激情、自信、坚持；我有一定的经验和能力，但我需要一个成功的方向，一个发展自己的空间，用自己的能力去创造财富！", R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.black_overlay, "\n");


    }

}
