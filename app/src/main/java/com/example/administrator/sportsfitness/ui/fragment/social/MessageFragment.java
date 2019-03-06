package com.example.administrator.sportsfitness.ui.fragment.social;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.BaseFragment;
import com.example.administrator.sportsfitness.base.BaseLifecycleObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.ui.activity.component.WebConcentratedActivity;
import com.example.administrator.sportsfitness.ui.controller.ControllerMessage;
import com.example.administrator.sportsfitness.ui.view.X5WebView;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/12/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MessageFragment extends BaseFragment implements X5WebView.X5LoadingListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.webView)
    X5WebView webView;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    private String titleName;
    private String url;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {
        url = new StringBuffer().append(DataClass.URL).append("chat/im.php?action=index&userid=").append(DataClass.USERID).toString();

    }

    @Override
    protected void initView() {
        webView.loadUrl(url);
        swipe_layout.setRefreshing(true);
    }

    @Override
    protected void initListener() {
        webView.setX5LoadingListener(this);
        swipe_layout.setOnRefreshListener(this);
    }

    @Override
    protected void onClickAble(View view) {

    }

    @Override
    public void onResume() {
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

    @Override
    public void onDestroy() {
        if (webView != null)
            webView.destroy();
        super.onDestroy();
    }

    @Override
    public void onPageFinished() {
        swipe_layout.setRefreshing(false);
    }

    @Override
    public void onPageStarted() {

    }

    @Override
    public void onProgressChanged(int newProgress) {

    }

    @Override
    public void onActionListener(int type, String firstContent, String lastContent) {
        Intent webIntent = new Intent(getActivity(), WebConcentratedActivity.class);
        switch (type) {
            case 0:
                webIntent.putExtra("link_all", new StringBuffer().append(DataClass.URL).append("chat/chat.php?fromuid=").append(DataClass.USERID).append("&touid=").append(lastContent).toString());
                webIntent.putExtra("titleName", firstContent);
                webIntent.putExtra("titleAbout", getString(R.string.details));
                webIntent.putExtra("Uid", lastContent);
                startActivity(webIntent);
                break;
        }
    }

    @Override
    public void onRefresh() {
        swipe_layout.setRefreshing(false);
    }

}
