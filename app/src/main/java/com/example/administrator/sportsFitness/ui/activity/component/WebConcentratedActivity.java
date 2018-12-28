package com.example.administrator.sportsFitness.ui.activity.component;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.widget.WebViewBuilder;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class WebConcentratedActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.webView)
    WebView webView;
    private WebViewBuilder webViewBuilder;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_web_concentrated;
    }

    @Override
    protected void initClass() {
        webViewBuilder = new WebViewBuilder(webView, null);
        webViewBuilder.initWebView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText("http://jianshen.027perfect.com/pf_wx.php?act=xiong&do=list");
//        webView.loadUrl("https://home.firefoxchina.cn/");
        webViewBuilder.loadWebView("http://jianshen.027perfect.com/pf_wx.php?act=xiong&do=list", true);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onClickAble(View view) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
