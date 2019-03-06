package com.example.administrator.sportsfitness.ui.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;

import com.example.administrator.sportsfitness.widget.AlbumBuilder;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.net.URI;

/**
 * 作者：真理 Created by Administrator on 2018/12/19.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class X5WebView extends WebView implements AlbumBuilder.OnReturnPhotoListener {

    private AlbumBuilder albumBuilder;
    private ValueCallback<Uri[]> valueCallbacks;

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        this.setWebViewClient(webViewClient);
        initWebViewSettings();
        albumBuilder = new AlbumBuilder(getContext());
        albumBuilder.setOnReturnPhotoListener(this);
    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(false);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
//        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setDisplayZoomControls(false);
        this.addJavascriptInterface(new JSInterface(), "fitness");
        this.setWebChromeClient(webChromeClient);


    }

    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定", null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();
            //注意:必须要这一句代码:result.confirm()表示: 处理结果为确定状态同时唤醒WebCore线程 否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (x5LoadingListener != null)
                x5LoadingListener.onProgressChanged(newProgress);
        }

        @Override
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            super.openFileChooser(valueCallback, acceptType, capture);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
            albumBuilder.ImageSingleSelection();
            valueCallbacks = valueCallback;
            return true; //super.onShowFileChooser(webView, valueCallback, fileChooserParams)

        }
    };

    @Override
    public void onReturnPhotoListener(String photo) {
        Uri[] results = null;
        if (photo != null) {
            Uri uriData = Uri.parse(photo);
            results = new Uri[]{uriData};
        }
        valueCallbacks.onReceiveValue(results);
    }

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            if (x5LoadingListener != null)
                x5LoadingListener.onPageFinished();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            if (x5LoadingListener != null)
                x5LoadingListener.onPageStarted();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };


    private final class JSInterface {

        @JavascriptInterface
        public void gochat(String fromUserid, String toUserid) {
            x5LoadingListener.onActionListener(0, fromUserid, toUserid);
        }

        @JavascriptInterface
        public void chathistory(String userid, String chat_id) {
            x5LoadingListener.onActionListener(1, userid, chat_id);
        }


    }

    public interface X5LoadingListener {
        void onPageFinished();

        void onPageStarted();

        void onProgressChanged(int newProgress);

        void onActionListener(int type, String firstContent, String lastContent);

    }

    private X5LoadingListener x5LoadingListener;

    public void setX5LoadingListener(X5LoadingListener x5LoadingListener) {
        this.x5LoadingListener = x5LoadingListener;
    }

}
