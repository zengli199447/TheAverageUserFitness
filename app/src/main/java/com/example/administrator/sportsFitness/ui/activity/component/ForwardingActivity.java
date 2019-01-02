package com.example.administrator.sportsFitness.ui.activity.component;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.widget.StickyBoContentTextBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2019/1/2.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ForwardingActivity extends BaseActivity implements StickyBoContentTextBuilder.OnStickyBoContentTextClickListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_text)
    TextView title_about_text;
    @BindView(R.id.insights)
    TextView insights;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.text_content)
    TextView text_content;

    private String forwardingId;
    private StickyBoContentTextBuilder stickyBoContentTextBuilder;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_forward;
    }

    @Override
    protected void initClass() {
        stickyBoContentTextBuilder = new StickyBoContentTextBuilder();
    }

    @Override
    protected void initData() {
        forwardingId = getIntent().getStringExtra("forwardingId");
        String string = new StringBuffer().append(DataClass.FORWARDING_TAG).append(DataClass.UNAME).append(" ").append("是的,我也这么觉得...").toString();
        insights.setText(stickyBoContentTextBuilder.getWeiBoContent(string, this, insights));

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.forwarding));
        title_about_text.setText(getString(R.string.release));
        title_about_text.setTextColor(getResources().getColor(R.color.blue_bar));
        refreshView();
    }

    @Override
    protected void initListener() {
        stickyBoContentTextBuilder.setOnStickyBoContentTextClickListener(this);

    }

    @OnClick({R.id.img_btn_black, R.id.title_about_text})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.title_about_text:

                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    private void refreshView() {
        Glide.with(this).load(DataClass.USERPHOTO).error(R.drawable.banner_off).into(img);
        text_content.setText(":武藤游戏 一组超棒的按摩指南，累了、睡前都可以做，做完非常舒服~~ get ...");
        text_content.setText(stickyBoContentTextBuilder.getWeiBoContent(text_content.getText().toString(), this, insights));
    }

    @Override
    public void onStickyBoContentTextClickListener(String content) {
        toastUtil.showToast(content);
    }

}
