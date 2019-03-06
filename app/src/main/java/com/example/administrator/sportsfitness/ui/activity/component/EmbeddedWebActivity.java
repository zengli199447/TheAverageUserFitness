package com.example.administrator.sportsfitness.ui.activity.component;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseActivity;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.ui.dialog.ProgressDialog;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.widget.WebViewBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class EmbeddedWebActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    private String titleName;
    private String url;
    private WebViewBuilder webViewBuilder;
    private int urlType;
    boolean status;

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
        webViewBuilder = new WebViewBuilder(webView, progressbar);
        webViewBuilder.initWebView();
    }

    @Override
    protected void initData() {
        titleName = getIntent().getStringExtra("titleName");
        urlType = getIntent().getFlags();
        switch (urlType) {
            case 0:
                url = getIntent().getStringExtra("link");
                break;
            case 1:
                url = new StringBuffer().append(DataClass.WEB_URL).append(getIntent().getStringExtra("link")).toString();
                status = true;
                break;
        }
    }

    @Override
    protected void initView() {
        title_name.setText(titleName);
        webViewBuilder.loadWebView(url, status);
        progressbar.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
        webView.getSettings().setLightTouchEnabled(false);
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

    private void onKeyDownActiion() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onKeyDownActiion();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null)
            webView.destroy();
        super.onDestroy();
    }

}
