package com.example.administrator.sportsFitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.adapter.TabPageIndicatorAdapter;
import com.example.administrator.sportsFitness.ui.fragment.detailsinfo.IntroduceFragment;
import com.example.administrator.sportsFitness.ui.fragment.search.SearchTypeFragment;
import com.example.administrator.sportsFitness.ui.fragment.social.FriendsCircleFragment;
import com.example.administrator.sportsFitness.ui.fragment.social.MessageFragment;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;
import com.example.administrator.sportsFitness.widget.ViewBuilder;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/22.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TheDetailsInformationActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_img)
    ImageView title_about_img;
    @BindView(R.id.title_about_round_img)
    ImageView title_about_round_img;
    @BindView(R.id.the_details_img)
    ImageView the_details_img;
    @BindView(R.id.user_content)
    TextView user_content;
    @BindView(R.id.user_img)
    ImageView user_img;

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.view_page)
    ViewPager view_page;
    @BindView(R.id.bar_layout)
    AppBarLayout bar_layout;
    @BindView(R.id.controller_layout)
    RelativeLayout controller_layout;

    @BindView(R.id.controller_all_layout)
    LinearLayout controller_all_layout;
    @BindView(R.id.edit_data)
    TextView edit_data;

    private String userId;
    private int flags;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private TabPageIndicatorAdapter tabPageIndicatorAdapter;
    int[] location = new int[2];
    private int interval;
    private int controllerHeight;
    private String titleName;
    private RelativeLayout.LayoutParams controllerLayoutParams;
    private int magin;
    private AlbumBuilder albumBuilder;
    private SearchTypeFragment courseFragment;
    private SearchTypeFragment personalTrainingFragment;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_the_details_infor;
    }

    @Override
    protected void initClass() {
        albumBuilder = new AlbumBuilder(this);

        Intent intent = getIntent();
        flags = intent.getFlags();
        userId = intent.getStringExtra("userId");

        IntroduceFragment introduceFragment = new IntroduceFragment();
        Bundle introduceTrainingData = new Bundle();
        introduceTrainingData.putString("userId", userId);
        introduceTrainingData.putInt("userType", flags);
        introduceFragment.setArguments(introduceTrainingData);
        mFragments.add(introduceFragment);

        switch (flags) {
            case EventCode.PERSONLA:

                break;
            case EventCode.COACH:
                personalTrainingFragment = new SearchTypeFragment();
                Bundle personalTrainingData = new Bundle();
                personalTrainingData.putString("typeId", "");
                personalTrainingData.putString("relatedId", "");
                personalTrainingFragment.setArguments(personalTrainingData);

                courseFragment = new SearchTypeFragment();
                Bundle courseFragmentData = new Bundle();
                courseFragmentData.putString("typeId", "");
                personalTrainingData.putString("relatedId", "");
                courseFragment.setArguments(courseFragmentData);

                mFragments.add(personalTrainingFragment);
                mFragments.add(courseFragment);
                break;
            case EventCode.OTHERS_PEOPLE:

                break;
        }
    }

    @Override
    protected void initData() {
        interval = SystemUtil.dp2px(this, 70);
        controllerHeight = SystemUtil.dp2px(this, 50);
        magin = SystemUtil.dp2px(this, -30);

        switch (flags) {
            case EventCode.PERSONLA:
                titleList.add("");
                break;
            case EventCode.COACH:
                titleList.addAll(Arrays.asList(getResources().getStringArray(R.array.personal_details_info)));
                break;
            case EventCode.OTHERS_PEOPLE:
                titleList.add("");
                break;
        }
    }

    @Override
    protected void initView() {
        controllerLayoutParams = (RelativeLayout.LayoutParams) controller_layout.getLayoutParams();
        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), titleList, mFragments);
        view_page.setAdapter(tabPageIndicatorAdapter);
        tab_layout.setupWithViewPager(view_page);
        tab_layout.addOnTabSelectedListener(this);
        ViewBuilder.setIndicator(tab_layout, getResources().getInteger(R.integer.title_bar_margin_max), getResources().getInteger(R.integer.title_bar_margin_max));
        tabPageIndicatorAdapter.notifyDataSetChanged();

        switch (flags) {
            case EventCode.PERSONLA:
                controller_all_layout.setVisibility(View.GONE);
                edit_data.setVisibility(View.VISIBLE);
                break;
            case EventCode.COACH:

                break;
            case EventCode.OTHERS_PEOPLE:

                break;
        }

        refreshView();
    }

    @Override
    protected void initListener() {
        tab_layout.setOnTabSelectedListener(this);
        bar_layout.addOnOffsetChangedListener(this);

    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.img_btn_black, R.id.title_about_img, R.id.user_img, R.id.controller_life, R.id.controller_right, R.id.edit_data})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.title_about_img:
                switch (flags) {
                    case EventCode.PERSONLA:
                        startActivity(new Intent(this, ShowGeneraActivity.class).setFlags(EventCode.MY_QR_CODE));
                        break;
                    case EventCode.COACH:
                        title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.like_icon));
                        break;
                    case EventCode.OTHERS_PEOPLE:

                        break;
                }
                break;
            case R.id.user_img:
                ArrayList<String> list = new ArrayList<>();
                list.add(DataClass.USERPHOTO);
                albumBuilder.ImageTheExhibition(list, false, 0);
                break;
            case R.id.controller_life:

                break;
            case R.id.controller_right:

                break;
            case R.id.edit_data:
                startActivity(new Intent(this, PersonalActivity.class));
                break;
        }
    }

    public void refreshView() {
        switch (flags) {
            case EventCode.PERSONLA:
                title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.code_content_icon));
                tab_layout.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                break;
            case EventCode.COACH:
                title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.like_off_icon));
                break;
            case EventCode.OTHERS_PEOPLE:
                tab_layout.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                break;
        }

        SystemUtil.textMagicTool(this, user_content, DataClass.UNAME, "身体是革命的本钱，全民健身动起来！加油加油加油", R.dimen.dp16, R.dimen.dp12,
                R.color.black, R.color.gray_light_text, "\n");

        Glide.with(this).load(SystemUtil.JudgeUrl(DataClass.USERPHOTO)).error(R.drawable.banner_off).into(user_img);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        view_page.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float top = user_img.getTop();
        float abs = Math.abs(verticalOffset);
        float proportion = abs / top;
        title_about_round_img.setAlpha(1 * proportion);
        user_img.getLocationOnScreen(location);
        float topHight = proportion * ((float) controllerHeight) * (-1);
        controllerLayoutParams.setMargins(magin, 0, magin, (int) topHight);
        controller_layout.setLayoutParams(controllerLayoutParams);
    }

}
