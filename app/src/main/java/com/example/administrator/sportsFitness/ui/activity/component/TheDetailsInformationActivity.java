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
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.HomePageInfoNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.adapter.TabPageIndicatorAdapter;
import com.example.administrator.sportsFitness.ui.controller.ControllerTheDetailsInformation;
import com.example.administrator.sportsFitness.ui.dialog.ProgressDialog;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.fragment.detailsinfo.IntroduceFragment;
import com.example.administrator.sportsFitness.ui.fragment.search.SearchTypeFragment;
import com.example.administrator.sportsFitness.ui.fragment.social.FriendsCircleFragment;
import com.example.administrator.sportsFitness.ui.fragment.social.MessageFragment;
import com.example.administrator.sportsFitness.utils.LogUtil;
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
public class TheDetailsInformationActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, AppBarLayout.OnOffsetChangedListener,
        ControllerTheDetailsInformation.OnHomePageDataListener {

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

    @BindView(R.id.controller_life)
    TextView controller_life;
    @BindView(R.id.controller_right)
    TextView controller_right;
    @BindView(R.id.center_line)
    View center_line;
    @BindView(R.id.pen)
    View pen;
    @BindView(R.id.certification_tag)
    ImageView certification_tag;

    private String userId;
    private String userName;
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
    private ControllerTheDetailsInformation controllerTheDetailsInformation;
    private int userType;
    private ShowDialog instance;
    private HomePageInfoNetBean.ResultBean resultBean;
    private String collectionStatus;
    private ProgressDialog progressDialog;
    private List<HomePageInfoNetBean.ResultBean.UserBean.ZizhiBean> certificationTagList;


    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.CERTIFICATION_TAG:
                if (certificationTagList != null) {
                    ArrayList<String> strings = new ArrayList<>();
                    for (HomePageInfoNetBean.ResultBean.UserBean.ZizhiBean zizhiBean : certificationTagList) {
                        strings.add(SystemUtil.JudgeUrl(zizhiBean.getImgpath()));
                    }
                    albumBuilder.ImageTheExhibition(strings, false, 0);
                }
                break;
        }
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
        instance = ShowDialog.getInstance();
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");
        controllerTheDetailsInformation = new ControllerTheDetailsInformation(userId);
        controllerLayoutParams = (RelativeLayout.LayoutParams) controller_layout.getLayoutParams();
        progressDialog = instance.showProgressStatus(this);
        progressDialog.show();
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerTheDetailsInformation;
    }

    @Override
    protected void initData() {
        interval = SystemUtil.dp2px(this, 70);
        controllerHeight = SystemUtil.dp2px(this, 50);
        magin = SystemUtil.dp2px(this, -30);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        tab_layout.setOnTabSelectedListener(this);
        bar_layout.addOnOffsetChangedListener(this);
        controllerTheDetailsInformation.setOnHomePageDataListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.img_btn_black, R.id.title_about_img, R.id.user_img, R.id.controller_life, R.id.controller_right, R.id.edit_data,
            R.id.the_details_img, R.id.user_content, R.id.pen, R.id.certification_tag})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.title_about_img:
                switch (userType == 1 ? EventCode.OTHERS_PEOPLE : EventCode.COACH) {
                    case EventCode.OTHERS_PEOPLE:
                        startActivity(new Intent(this, ShowGeneraActivity.class).setFlags(EventCode.MY_QR_CODE));
                        break;
                    case EventCode.COACH:
                        controllerTheDetailsInformation.NetCollection(collectionStatus);
                        break;
                }
                break;
            case R.id.user_img:
                ArrayList<String> list = new ArrayList<>();
                list.add(SystemUtil.JudgeUrl(resultBean.getUser().getPhoto()));
                albumBuilder.ImageTheExhibition(list, false, 0);
                break;
            case R.id.controller_life:
                Intent addFriendsIntent = new Intent(this, ShowGeneraActivity.class);
                addFriendsIntent.addFlags(EventCode.ADD_VALIDATION);
                addFriendsIntent.putExtra("addFriendsId", resultBean.getUser().getUserid());
                addFriendsIntent.putExtra("addFriendsUserName", resultBean.getUser().getSecondname());
                addFriendsIntent.putExtra("addFriendsPhoto", resultBean.getUser().getPhoto());
                startActivity(addFriendsIntent);
                break;
            case R.id.controller_right:
//                if (controller_right.getText().toString().equals(getString(R.string.chat))) {
                Intent webIntent = new Intent(this, WebConcentratedActivity.class);
                webIntent.putExtra("link_all", new StringBuffer().append(DataClass.URL).append("chat/chat.php?fromuid=").append(DataClass.USERID).append("&touid=").append(resultBean.getUser().getUserid()).toString());
                webIntent.putExtra("titleName", resultBean.getUser().getSecondname());
                startActivity(webIntent);
//                } else {
//
//                }
                break;
            case R.id.edit_data:
                startActivity(new Intent(this, PersonalActivity.class));
                break;
            case R.id.the_details_img:
                if (DataClass.USERID.equals(resultBean.getUser().getUserid()))
                    albumBuilder.ImageSingleSelection();
                break;
            case R.id.user_content:
            case R.id.pen:
                if (DataClass.USERID.equals(resultBean.getUser().getUserid()))
                    instance.showInputDialog(this, EventCode.SIGNATURE);
                break;
            case R.id.certification_tag:
                instance.showConfirmOrNoDialog(this, getString(R.string.certification_tag), EventCode.ONSTART, EventCode.CERTIFICATION_TAG, EventCode.ONSTART);
                break;
        }
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

    @Override
    public void onHomePageDataListener(HomePageInfoNetBean.ResultBean resultBean) {
        progressDialog.dismiss();

        this.resultBean = resultBean;
        userType = Integer.valueOf(resultBean.getUser().getRole());

        certificationTagList = resultBean.getUser().getZizhi();

        SystemUtil.textMagicTool(this, user_content, resultBean.getUser().getSecondname(), resultBean.getUser().getSignature(), R.dimen.dp16, R.dimen.dp12,
                R.color.black, R.color.gray_light_text, "\n");

        Glide.with(this).load(SystemUtil.JudgeUrl(resultBean.getUser().getPhoto())).error(R.drawable.banner_off).into(user_img);

        Glide.with(this).load(SystemUtil.JudgeUrl(resultBean.getUser().getImg_center())).error(R.drawable.banner_off).into(the_details_img);

        if (userId.equals(DataClass.USERID)) {
            title_name.setText(getString(R.string.my_home_page));
        } else {
            title_name.setText(new StringBuffer().append(resultBean.getUser().getSecondname()).append(getString(R.string.the_home_page)));
        }

        if (titleList.size() == 0) {
            swichType();
        }

        refreshView();

    }

    @Override
    public void onCollectionSuccessfulListener(boolean status) {
        refreshCollectionStatus(status);
    }

    private void swichType() {

        IntroduceFragment introduceFragment = new IntroduceFragment();
        Bundle introduceTrainingData = new Bundle();
        introduceTrainingData.putString("userId", userId);
        introduceTrainingData.putString("userName", userName);
        introduceFragment.setArguments(introduceTrainingData);
        mFragments.add(introduceFragment);

        switch (userType == 1 ? EventCode.OTHERS_PEOPLE : EventCode.COACH) {
            case EventCode.OTHERS_PEOPLE:
                titleList.add("");
                break;
            case EventCode.COACH:
                personalTrainingFragment = new SearchTypeFragment();
                Bundle personalTrainingData = new Bundle();
                personalTrainingData.putString("typeId", getString(R.string.cp));
                personalTrainingData.putString("relatedId", userId);
                personalTrainingFragment.setArguments(personalTrainingData);

                courseFragment = new SearchTypeFragment();
                Bundle courseFragmentData = new Bundle();
                courseFragmentData.putString("typeId", getString(R.string.course));
                courseFragmentData.putString("relatedId", userId);
                courseFragment.setArguments(courseFragmentData);

                mFragments.add(personalTrainingFragment);
                mFragments.add(courseFragment);
                titleList.addAll(Arrays.asList(getResources().getStringArray(R.array.personal_details_info)));

                if (getString(R.string.audit_sucessful).equals(resultBean.getUser().getState_check()))
                    certification_tag.setVisibility(View.VISIBLE);

                break;
        }

        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getSupportFragmentManager(), titleList, mFragments, false);
        view_page.setAdapter(tabPageIndicatorAdapter);
        tab_layout.setupWithViewPager(view_page);
        tab_layout.addOnTabSelectedListener(this);
        ViewBuilder.setIndicator(tab_layout, getResources().getInteger(R.integer.title_bar_margin_max), getResources().getInteger(R.integer.title_bar_margin_max));
        tabPageIndicatorAdapter.notifyDataSetChanged();

    }

    public void refreshView() {
        switch (userType == 1 ? EventCode.OTHERS_PEOPLE : EventCode.COACH) {
            case EventCode.OTHERS_PEOPLE:
                if (userId.equals(DataClass.USERID)) {
                    title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.code_content_icon));
                    controller_all_layout.setVisibility(View.GONE);
                    pen.setVisibility(View.VISIBLE);
                    edit_data.setVisibility(View.VISIBLE);
                }
                tab_layout.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                break;
            case EventCode.COACH:
                collectionStatus = resultBean.getIfcollect();
                refreshCollectionStatus(collectionStatus.equals("0") ? false : true);
                break;
        }

        if (resultBean.getIffriend().equals("1")) {
            controller_life.setVisibility(View.GONE);
            center_line.setVisibility(View.GONE);
            controller_right.setText(getString(R.string.chat));
        } else {
            controller_life.setVisibility(View.VISIBLE);
            center_line.setVisibility(View.VISIBLE);
            controller_right.setText(getString(R.string.say_hello));
        }

    }

    private void refreshCollectionStatus(boolean status) {
        if (status) {
            collectionStatus = "1";
            title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.like_icon));
        } else {
            collectionStatus = "0";
            title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.like_off_icon));
        }
    }

}
