package com.example.administrator.sportsFitness.ui.activity.component;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerCommentsForm;
import com.example.administrator.sportsFitness.ui.controller.ControllerCourseForm;
import com.example.administrator.sportsFitness.ui.controller.ControllerFriendsCircle;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/1/17.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MoreDataReferenceActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private int flags;
    private String relatedId;
    private String relatedName;
    private String relatedType;
    private ControllerFriendsCircle controllerFriendsCircle;
    private ControllerCommentsForm controllerCommentsForm;
    private ControllerCourseForm controllerCourseForm;


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_more_data_reference;
    }

    @Override
    protected void initClass() {
        Intent intent = getIntent();
        flags = intent.getFlags();
        relatedId = intent.getStringExtra("relatedId");
        relatedName = intent.getStringExtra("relatedName");
        relatedType = intent.getStringExtra("relatedType");
        switch (flags) {
            case EventCode.DYNAMIC:
                controllerFriendsCircle = new ControllerFriendsCircle(swipe_layout, empty_layout, recycler_view, Integer.valueOf(relatedType), relatedId);
                break;
            case EventCode.COMMENTS:
                controllerCommentsForm = new ControllerCommentsForm(swipe_layout, empty_layout, recycler_view, Integer.valueOf(relatedType), relatedId);
                break;
            case EventCode.COURSE_ZOO:
                controllerCourseForm = new ControllerCourseForm(swipe_layout, empty_layout, recycler_view, relatedId);
                break;
        }
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        switch (flags) {
            case EventCode.DYNAMIC:
                return controllerFriendsCircle;
            case EventCode.COMMENTS:
                return controllerCommentsForm;
            case EventCode.COURSE_ZOO:
                return controllerCourseForm;
        }
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        swipe_layout.setBackgroundColor(getResources().getColor(R.color.gray_));
        switch (flags) {
            case EventCode.DYNAMIC:
                if (relatedId.equals(DataClass.USERID)) {
                    title_name.setText(getString(R.string.my_dynamic));
                } else {
                    title_name.setText(new StringBuffer().append(relatedName).append(getString(R.string.the_personal)).append(getString(R.string.dynamic)).toString());
                }
                break;
            case EventCode.COMMENTS:
                title_name.setText(new StringBuffer().append(relatedName).append(getString(R.string.the_related)).append(getString(R.string.comments)).toString());
                break;
            case EventCode.COURSE_ZOO:
                title_name.setText(new StringBuffer().append(relatedName).append(getString(R.string.the_related)).append(getString(R.string.course)).toString());
                break;
        }
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

}
