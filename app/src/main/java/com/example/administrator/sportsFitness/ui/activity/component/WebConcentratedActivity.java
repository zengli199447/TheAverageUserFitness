package com.example.administrator.sportsFitness.ui.activity.component;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.BaseActivity;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.ui.dialog.ProgressDialog;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.ui.view.X5WebView;
import com.example.administrator.sportsFitness.widget.UmShareBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/12/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class WebConcentratedActivity extends BaseActivity implements X5WebView.X5LoadingListener {

    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_img)
    ImageView title_about_img;
    @BindView(R.id.webView)
    X5WebView webView;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    private String titleName;
    private String url;
    private ProgressDialog progressDialog;
    private String titleAbout;
    private String Uid;
    private UmShareBuilder umShareBuilder;

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
        progressDialog = ShowDialog.getInstance().showProgressStatus(this);
        progressDialog.show();
        umShareBuilder = new UmShareBuilder(this);
    }

    @Override
    protected void initData() {
        titleName = getIntent().getStringExtra("titleName");
        titleAbout = getIntent().getStringExtra("titleAbout");
        Uid = getIntent().getStringExtra("Uid");
        if (getIntent().getStringExtra("link") == null || getIntent().getStringExtra("link").isEmpty()) {
            url = getIntent().getStringExtra("link_all");
        } else {
            url = new StringBuffer().append(DataClass.WEB_URL).append(getIntent().getStringExtra("link")).toString();
        }
    }

    @Override
    protected void initView() {
        title_name.setText(titleName);
        webView.loadUrl(url);//"https://home.firefoxchina.cn/"
        if (titleAbout != null) {
            if (titleAbout.equals(getString(R.string.details))) {
                title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.user_icon));
            } else if (titleAbout.equals(getString(R.string.the_share))) {
                title_about_img.setImageDrawable(getResources().getDrawable(R.drawable.share_icon));
            }
        }
    }

    @Override
    protected void initListener() {
        webView.setX5LoadingListener(this);
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

    @OnClick({R.id.img_btn_black, R.id.title_about_img})
    @Override
    protected void onClickAble(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                onKeyDownActiion();
                break;
            case R.id.title_about_img:
                if (titleAbout.equals(getString(R.string.details))) {
                    Intent theDetailsInformationIntent = new Intent(this, TheDetailsInformationActivity.class);
                    theDetailsInformationIntent.putExtra("userId", Uid);
                    theDetailsInformationIntent.putExtra("userName", titleName);
                    this.startActivity(theDetailsInformationIntent);
                } else if (titleAbout.equals(getString(R.string.the_share))) {
                    umShareBuilder.initUmUrlShare(1,"",url,getString(R.string.app_name),"健身周报");
                }
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
    public void onPageFinished() {
//        progressbar.setVisibility(View.GONE);
        progressDialog.dismiss();
    }

    @Override
    public void onPageStarted() {
//        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressChanged(int newProgress) {
//        progressbar.setProgress(newProgress);
    }

    @Override
    public void onActionListener(int type, String firstContent, String lastContent) {
        Intent webIntent = new Intent(this, WebConcentratedActivity.class);
        switch (type) {
            case 1:
                webIntent.putExtra("link_all", new StringBuffer().append(DataClass.URL).append("chat/chat.php?historylist=1&userid=").append(DataClass.USERID).append("&chatid=").append(lastContent).toString());
                webIntent.putExtra("titleName", getString(R.string.about_chat_log));
                startActivity(webIntent);
                break;
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
