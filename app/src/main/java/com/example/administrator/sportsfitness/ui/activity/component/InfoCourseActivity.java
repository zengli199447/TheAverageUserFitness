package com.example.administrator.sportsfitness.ui.activity.component;

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
import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.InfoCourseNetBean;
import com.example.administrator.sportsfitness.model.bean.InfoGymNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.ui.controller.ControllerInfoCourse;
import com.example.administrator.sportsfitness.ui.dialog.ProgressDialog;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.utils.LogUtil;
import com.example.administrator.sportsfitness.utils.SystemUtil;
import com.example.administrator.sportsfitness.widget.AlbumBuilder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/1/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class InfoCourseActivity extends BaseActivity implements NestedScrollView.OnScrollChangeListener, ControllerInfoCourse.OnInfoCourseListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_img)
    ImageView title_about_img;

    @BindView(R.id.banner)
    ImageView banner;
    @BindView(R.id.img_tag)
    TextView img_tag;
    @BindView(R.id.title_content)
    TextView title_content;
    @BindView(R.id.standard)
    TextView standard;
    @BindView(R.id.user_img)
    ImageView user_img;
    @BindView(R.id.user_content)
    TextView user_content;

    @BindView(R.id.calendar)
    TextView calendar;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.people_count)
    TextView people_count;
    @BindView(R.id.nervous_tag)
    TextView nervous_tag;
    @BindView(R.id.heat)
    TextView heat;
    @BindView(R.id.store)
    TextView store;

    @BindView(R.id.course_description)
    TextView course_description;
    @BindView(R.id.course_instructions)
    TextView course_instructions;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @BindView(R.id.controller_layout)
    RelativeLayout controller_layout;
    @BindView(R.id.course_student_layout)
    RelativeLayout course_student_layout;

    private RelativeLayout.LayoutParams controllerLayoutLayoutParams;
    private ArrayList<String> photoList = new ArrayList<>();
    private AlbumBuilder albumBuilder;
    private String collectionStatus;
    private int[] ids = {R.id.img5, R.id.img4, R.id.img3, R.id.img2, R.id.img1};
    private int bannerTopHeight;
    private int controllerHeight;
    private int magin;
    private ControllerInfoCourse controllerInfoCourse;
    InfoCourseNetBean.ResultBean.DetailBean detail;
    private ShowDialog instance;
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
        return R.layout.activity_info_course;
    }

    @Override
    protected void initClass() {
        String coursesId = getIntent().getStringExtra("CoursesId");
        controllerLayoutLayoutParams = (RelativeLayout.LayoutParams) controller_layout.getLayoutParams();
        albumBuilder = new AlbumBuilder(this);
        controllerInfoCourse = new ControllerInfoCourse(recycler_view, coursesId);
        progressDialog = ShowDialog.getInstance().showProgressStatus(this);
        progressDialog.show();
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerInfoCourse;
    }

    @Override
    protected void initData() {
        bannerTopHeight = SystemUtil.dp2px(this, 180);
        controllerHeight = SystemUtil.dp2px(this, 50);
        magin = SystemUtil.dp2px(this, 30);
        instance = ShowDialog.getInstance();
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.course_info));

    }

    @Override
    protected void initListener() {
        scrollView.setOnScrollChangeListener(this);
        controllerInfoCourse.setOnInfoCourseListener(this);

    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.img_btn_black, R.id.comments_more, R.id.user_img, R.id.controller_life, R.id.controller_right, R.id.banner, R.id.title_about_img,
            R.id.course_student_layout})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.comments_more:
                if (detail != null) {
                    Intent intent = new Intent(this, MoreDataReferenceActivity.class);
                    intent.setFlags(EventCode.COMMENTS);
                    intent.putExtra("relatedId", detail.getShopid());
                    intent.putExtra("relatedName", detail.getShopname());
                    intent.putExtra("relatedType", "2");
                    startActivity(intent);
                }
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
                if (detail != null && Integer.valueOf(detail.getYuyuetotal()) != Integer.valueOf(detail.getTotal())) {
                    Intent courseInfomationIntent = new Intent(this, CourseInformationActivity.class);
                    Bundle courseInfomationBundle = new Bundle();
                    courseInfomationBundle.putString("Shopname", detail.getShopname());
                    courseInfomationBundle.putString("Price", detail.getPrice());
                    courseInfomationBundle.putString("Photo", detail.getListimg());
                    courseInfomationBundle.putString("Coursesid", detail.getCoursesid());
                    courseInfomationBundle.putString("Coursesname", detail.getCoursesname());
                    courseInfomationBundle.putString("Date_start", detail.getDate_start_txt());
                    courseInfomationBundle.putString("Date_end", detail.getDate_end_txt());
                    courseInfomationBundle.putString("Time_start", detail.getTime_start_txt());
                    courseInfomationBundle.putString("Time_end", detail.getTime_end_txt());
                    courseInfomationIntent.putExtra("CourseInfo", courseInfomationBundle);
                    startActivity(courseInfomationIntent);
                } else {
                    instance.showHelpfulHintsDialog(this, getString(R.string.full_error), EventCode.ONSTART);
                }
                break;
            case R.id.banner:
                albumBuilder.ImageTheExhibition(photoList, false, 0);
                break;
            case R.id.title_about_img:
                controllerInfoCourse.NetCollection();
                break;
            case R.id.course_student_layout:
                Intent intent = new Intent(this, PrivateGeneralFormActivity.class);
                intent.setFlags(EventCode.STUDENT_FORM);
                intent.putExtra("relatedId", detail.getCoursesid());
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
    public void onInfoCourseListener(InfoCourseNetBean.ResultBean.DetailBean detail) {
        progressDialog.dismiss();

        this.detail = detail;
        List<InfoCourseNetBean.ResultBean.DetailBean.ImgBean> img = detail.getImg();
        List<InfoCourseNetBean.ResultBean.DetailBean.StudentBean> student = detail.getStudent();

        String peopleneed = detail.getPeopleneed();

        SystemUtil.textMagicTool(this, standard, getString(R.string.money), detail.getPrice(), R.dimen.dp10, R.dimen.dp15, R.color.red_text, R.color.red_text, "");

        SystemUtil.textMagicTool(this, title_content, detail.getCoursesname(),
                new StringBuffer().append(Integer.valueOf(detail.getTotal()) - Integer.valueOf(peopleneed.isEmpty() ? "0" : peopleneed)).append(getString(R.string.people_perform)).toString(), R.dimen.dp15, R.dimen.dp10, R.color.black, R.color.gray_light_text, "\n");

        Glide.with(this).load(SystemUtil.JudgeUrl(detail.getPhoto())).error(R.drawable.banner_off).into(user_img);

        SystemUtil.textMagicTool(this, user_content, detail.getSecondname(),
                detail.getSignature(), R.dimen.dp15, R.dimen.dp12, R.color.black, R.color.gray_light_text, "\n");

        calendar.setText(new StringBuffer().append(detail.getDate_start()).append("-").append(detail.getDate_end()));
        time.setText(new StringBuffer().append(detail.getTime_start_txt()).append("-").append(detail.getTime_end_txt()));
        people_count.setText(new StringBuffer().append(detail.getTotal()).append(getString(R.string.people_class)).toString());
        nervous_tag.setText(new StringBuffer().append("(仅剩").append(detail.getPeopleneed()).append("名额)"));
        heat.setText(new StringBuffer().append(detail.getMeat()).append(getString(R.string.calories)).toString());
        store.setText(detail.getShopname());
        course_description.setText(detail.getRemark());
        course_instructions.setText(detail.getMustread());

        refreshCollection(detail.getIfcollect().equals("0") ? false : true);

        img_tag.setText(String.valueOf(img.size()));

        if (img.size() > 0) {
            Glide.with(this).load(SystemUtil.JudgeUrl(img.get(0).getImgpath())).error(R.drawable.banner_off).into(banner);
            for (InfoCourseNetBean.ResultBean.DetailBean.ImgBean imgBean : img) {
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
        refreshCollection(status);
        LogUtil.e(TAG, "status : " + status);
    }

    private void refreshCollection(boolean status) {
        if (status) {
            title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.like_icon));
        } else {
            title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.like_off_icon));
        }
    }


}
