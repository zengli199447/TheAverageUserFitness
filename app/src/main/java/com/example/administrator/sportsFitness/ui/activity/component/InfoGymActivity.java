package com.example.administrator.sportsFitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.InfoGymNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerInfoGym;
import com.example.administrator.sportsFitness.ui.dialog.ProgressDialog;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.ShinyView;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.AlbumBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/25.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class InfoGymActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener, ControllerInfoGym.OnInfoGymListener {

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
    private ArrayList<String> photoList = new ArrayList<>();
    private AlbumBuilder albumBuilder;
    InfoGymNetBean.ResultBean.DetailBean detail;
    private ProgressDialog progressDialog;

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
        String gymId = getIntent().getStringExtra("GYMId");
        reservationsNowLayoutParams = (RelativeLayout.LayoutParams) reservations_now.getLayoutParams();
        controllerInfoGym = new ControllerInfoGym(course_recycler_view, evaluation_recycler_view, gymId);
        albumBuilder = new AlbumBuilder(this);
        progressDialog = ShowDialog.getInstance().showProgressStatus(this);
        progressDialog.show();
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
        title_name.setText(getString(R.string.gym));
        course_recycler_view.setFocusable(false);
        evaluation_recycler_view.setFocusable(false);

    }

    @Override
    protected void initListener() {
        scrollView.setOnScrollChangeListener(this);
        controllerInfoGym.setOnInfoGymListener(this);
    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.img_btn_black, R.id.navigation, R.id.course_more, R.id.evaluation_more, R.id.reservations_now, R.id.banner,
            R.id.title_about_img})
    @Override
    protected void onClickAble(View view) {
        Intent intent = new Intent(this, MoreDataReferenceActivity.class);
        try {
            switch (view.getId()) {
                case R.id.banner:
                    albumBuilder.ImageTheExhibition(photoList, false, 0);
                    break;
                case R.id.img_btn_black:
                    finish();
                    break;
                case R.id.navigation:
                    Intent webIntent = new Intent(this, WebConcentratedActivity.class);
                    webIntent.putExtra("link", new StringBuffer().append("do=map&lng=").append(detail.getLongitude()).append("&lat=").append(detail.getLatitude()).append("&type=2").toString());
                    webIntent.putExtra("titleName", getString(R.string.gym_navigation));
                    startActivity(webIntent);
                    break;
                case R.id.course_more:
                    intent.setFlags(EventCode.COURSE_ZOO);
                    intent.putExtra("relatedId", detail.getShopid());
                    intent.putExtra("relatedName", detail.getShopname());
                    startActivity(intent);
                    break;
                case R.id.evaluation_more:
                    intent.setFlags(EventCode.COMMENTS);
                    intent.putExtra("relatedId", detail.getShopid());
                    intent.putExtra("relatedName", detail.getShopname());
                    intent.putExtra("relatedType", "1");
                    startActivity(intent);
                    break;
                case R.id.reservations_now:
                    Intent gymInfomationIntent = new Intent(this, GymInfomationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Shopname", detail.getShopname());
                    bundle.putString("Price", detail.getPrice());
                    bundle.putString("Photo", detail.getPhoto());
                    bundle.putString("Shopid", detail.getShopid());
                    bundle.putString("Fulladdress", detail.getFulladdress());
                    gymInfomationIntent.putExtra("GYMInfo", bundle);
                    startActivity(gymInfomationIntent);
                    break;
                case R.id.title_about_img:
                    controllerInfoGym.NetCollection();
                    break;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
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

    @Override
    public void onInfoGymListener(InfoGymNetBean.ResultBean.DetailBean detail) {
        progressDialog.dismiss();

        List<InfoGymNetBean.ResultBean.DetailBean.ImgBean> img = detail.getImg();

        this.detail = detail;

        title_content.setText(detail.getShopname());

        SystemUtil.textMagicTool(this, standard, detail.getPrice(), getString(R.string.price_prompt), R.dimen.dp15, R.dimen.dp10, R.color.red_text, R.color.red_text, "");

        SystemUtil.textMagicTool(this, coordinates, detail.getFulladdress(), new StringBuffer().append(getString(R.string.distance)).append(SystemUtil.JudgeFormatDistance(detail.getDistance())).toString(),
                R.dimen.dp14, R.dimen.dp10, R.color.black_overlay, R.color.gray_light_text, "\n");


        refreshCollection(detail.getIfcollect().equals("0") ? false : true);

        time.setText(new StringBuffer().append(detail.getOpeningtime_start()).append("-").append(detail.getOpeningtime_end()).toString());

        shiny_view.setStarMark(Float.valueOf(detail.getScore()));

        people_count_consumption.setText(new StringBuffer().append(SystemUtil.JudgeFormatCount(Integer.valueOf(detail.getPeopletotal())))
                .append(getString(R.string.people_total_consumption)).toString());

        instructions.setText(detail.getRemark());

        img_tag.setText(String.valueOf(img.size()));

        if (img.size() > 0) {
            Glide.with(this).load(SystemUtil.JudgeUrl(img.get(0).getImgpath())).error(R.drawable.banner_off).into(banner);
            for (InfoGymNetBean.ResultBean.DetailBean.ImgBean imgBean : img) {
                photoList.add(SystemUtil.JudgeUrl(imgBean.getImgpath()));
            }
        }
    }

    @Override
    public void onCollectionSuccessfulListener(boolean status) {
        refreshCollection(status);
    }

    private void refreshCollection(boolean status) {
        if (status) {
            title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.like_icon));
        } else {
            title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.like_off_icon));
        }
    }

}
