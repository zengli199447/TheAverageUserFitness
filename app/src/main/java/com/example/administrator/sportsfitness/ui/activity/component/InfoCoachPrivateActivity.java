package com.example.administrator.sportsfitness.ui.activity.component;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.InfoCoachPrivateNetBean;
import com.example.administrator.sportsfitness.model.bean.InfoGymNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.ui.controller.ControllerInfoCoachPrivate;
import com.example.administrator.sportsfitness.ui.dialog.ProgressDialog;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.example.administrator.sportsfitness.widget.AlbumBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/25.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class InfoCoachPrivateActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener, ControllerInfoCoachPrivate.OnNetInfoCoachPrivateListener {

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

    @BindView(R.id.student_form_layout)
    RelativeLayout student_form_layout;

    private int bannerTopHeight;
    private int controllerHeight;
    private int magin;
    private RelativeLayout.LayoutParams controllerLayoutLayoutParams;
    private ControllerInfoCoachPrivate controllerInfoCoachPrivate;
    private ArrayList<String> photoList = new ArrayList<>();
    private AlbumBuilder albumBuilder;
    private int[] ids = {R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.img5};
    private InfoCoachPrivateNetBean.ResultBean.DetailBean detail;
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
        return R.layout.activity_info_coach_private;
    }

    @Override
    protected void initClass() {
        String coachId = getIntent().getStringExtra("CoachId");
        controllerInfoCoachPrivate = new ControllerInfoCoachPrivate(coachId);
        controllerLayoutLayoutParams = (RelativeLayout.LayoutParams) controller_layout.getLayoutParams();
        albumBuilder = new AlbumBuilder(this);
        progressDialog = ShowDialog.getInstance().showProgressStatus(this);
        progressDialog.show();
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerInfoCoachPrivate;
    }

    @Override
    protected void initData() {
        bannerTopHeight = SystemUtil.dp2px(this, 180);
        controllerHeight = SystemUtil.dp2px(this, 50);
        magin = SystemUtil.dp2px(this, 30);

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.coach_private));
    }

    @Override
    protected void initListener() {
        scrollView.setOnScrollChangeListener(this);
        controllerInfoCoachPrivate.setOnNetInfoCoachPrivateListener(this);

    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.img_btn_black, R.id.user_img, R.id.controller_life, R.id.controller_right, R.id.banner, R.id.title_about_img, R.id.student_form_layout})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.banner:
                albumBuilder.ImageTheExhibition(photoList, false, 0);
                break;
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.user_img:
                Intent theDetailsInformationIntent = new Intent(this, TheDetailsInformationActivity.class);
                theDetailsInformationIntent.putExtra("userId", detail.getUserid());
                theDetailsInformationIntent.putExtra("userName", detail.getSecondname());
                startActivity(theDetailsInformationIntent);
                break;
            case R.id.controller_life:
                Intent webIntent = new Intent(this, WebConcentratedActivity.class);
                webIntent.putExtra("link_all", new StringBuffer().append(DataClass.URL).append("chat/chat.php?fromuid=").append(DataClass.USERID).append("&touid=").append(detail.getUserid()).toString());
                webIntent.putExtra("titleName", detail.getSecondname());
                startActivity(webIntent);
                break;
            case R.id.controller_right:
                Intent coachPrivateInformationIntent = new Intent(this, CoachPrivateInformationActivity.class);
                Bundle infoCoachPrivateBundle = new Bundle();
                infoCoachPrivateBundle.putString("Date_start_txt", detail.getDate_start());
                infoCoachPrivateBundle.putString("Time_start_txt", detail.getTime_start());
                infoCoachPrivateBundle.putString("Time_end_txt", detail.getTime_end());
                infoCoachPrivateBundle.putString("Secondname", detail.getSecondname());
                infoCoachPrivateBundle.putString("Price", detail.getPrice());
                infoCoachPrivateBundle.putString("Photo", detail.getPhoto());
                infoCoachPrivateBundle.putString("CoachId", detail.getCoursesid());
                coachPrivateInformationIntent.putExtra("CoachPrivate", infoCoachPrivateBundle);
                startActivity(coachPrivateInformationIntent);
                break;
            case R.id.student_form_layout:
                Intent intent = new Intent(this, PrivateGeneralFormActivity.class);
                intent.setFlags(EventCode.COACH_STUDENT_FORM);
                intent.putExtra("relatedId", detail.getUserid());
                startActivity(intent);
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

    @Override
    public void onNetInfoCoachPrivateListener(InfoCoachPrivateNetBean.ResultBean result) {
        progressDialog.dismiss();

        detail = result.getDetail();

        List<InfoCoachPrivateNetBean.ResultBean.DetailBean.ImgBean> img = detail.getImg();

        List<InfoCoachPrivateNetBean.ResultBean.DetailBean.StudentBean> student = detail.getStudent();

        img_tag.setText(String.valueOf(img.size()));

        title_content.setText(detail.getCoursesname());

        SystemUtil.textMagicTool(this, standard, getString(R.string.money), detail.getPrice(), R.dimen.dp10, R.dimen.dp15, R.color.red_text, R.color.red_text, "");

        SystemUtil.textMagicTool(this, user_content, detail.getSecondname(), detail.getSignature(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.black_overlay, "\n");

        time.setText(new StringBuffer().append(detail.getDate_start_txt()).append("   ").
                append(detail.getTime_start_txt()).append("-").append(detail.getTime_end_txt()).toString());

        coordinates.setText(new StringBuffer().append(" ").append(detail.getFulladdress()).toString());

        instructions.setText(detail.getRemark());

        Glide.with(this).load(SystemUtil.JudgeUrl(detail.getPhoto())).error(R.drawable.banner_off).into(user_img);

        if (img.size() > 0) {
            Glide.with(this).load(SystemUtil.JudgeUrl(img.get(0).getImgpath())).error(R.drawable.banner_off).into(banner);
            for (InfoCoachPrivateNetBean.ResultBean.DetailBean.ImgBean imgBean : img) {
                photoList.add(SystemUtil.JudgeUrl(imgBean.getImgpath()));
            }
        }

        for (int i = 0; i < student.size(); i++) {
            if (i < ids.length) {
                ImageView imageView = findViewById(ids[i]);
                imageView.setVisibility(View.VISIBLE);
                Glide.with(this).load(SystemUtil.JudgeUrl(student.get(i).getPhoto())).error(R.drawable.banner_off).into(imageView);
            }
        }

    }

    @Override
    public void onCollectionSuccessfulListener(boolean status) {

    }


}
