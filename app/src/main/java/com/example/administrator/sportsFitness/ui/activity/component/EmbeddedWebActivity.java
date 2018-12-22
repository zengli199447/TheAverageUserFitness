package com.example.administrator.sportsFitness.ui.activity.component;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.view.X5WebView;
import com.example.administrator.sportsFitness.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class EmbeddedWebActivity extends BaseActivity implements X5WebView.X5LoadingListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.web_view)
    X5WebView web_view;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_embedded_web;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        web_view.loadUrl("https://home.firefoxchina.cn/");
    }

    @Override
    protected void initListener() {
        web_view.setX5LoadingListener(this);
    }

    @OnClick({R.id.img_btn_black})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:

                break;
        }
    }

    @Override
    public void onPageFinished() {
        LogUtil.e(TAG, "onPageFinished");
    }

    @Override
    public void onPageStarted() {
        LogUtil.e(TAG, "onPageStarted");
    }

    @Override
    public void onProgressChanged() {
        LogUtil.e(TAG, "onProgressChanged");
    }

}
