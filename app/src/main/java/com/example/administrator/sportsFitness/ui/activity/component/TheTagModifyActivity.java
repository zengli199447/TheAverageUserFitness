package com.example.administrator.sportsFitness.ui.activity.component;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.controller.ControllerTheTagModify;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.FlowLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/24.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TheTagModifyActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.personal_tag_layout)
    FlowLayout personal_tag_layout;
    @BindView(R.id.add_corners)
    TextView add_corners;
    @BindView(R.id.hot_tag_layout)
    FlowLayout hot_tag_layout;
    private ControllerTheTagModify controllerTheTagModify;
    private ShowDialog instance;


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_the_tag_modify;
    }

    @Override
    protected void initClass() {
        controllerTheTagModify = new ControllerTheTagModify(personal_tag_layout, hot_tag_layout);
        instance = ShowDialog.getInstance();
    }

    @Override
    protected BaseLifecycleObserver initLifecycleObserver() {
        return controllerTheTagModify;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.add_tag));
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.add_corners, R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.add_corners:
                instance.showInputDialog(this, EventCode.CORNERS_TAG);
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

}
