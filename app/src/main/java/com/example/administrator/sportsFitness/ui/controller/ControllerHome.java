package com.example.administrator.sportsFitness.ui.controller;

import android.app.Activity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.InfoAboutNetBean;
import com.example.administrator.sportsFitness.model.bean.PayObject;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.bean.WechatPayContentNetBean;
import com.example.administrator.sportsFitness.model.bean.ZfbPayContentNetBean;
import com.example.administrator.sportsFitness.model.db.entity.SearchDBInfo;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxBus;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.view.FlowLayout;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.example.administrator.sportsFitness.widget.OnLinePayBuilder;
import com.example.administrator.sportsFitness.widget.ViewBuilder;
import com.example.administrator.sportsFitness.widget.download.DownloadProgressListener;
import com.example.administrator.sportsFitness.widget.download.DownloadUtil;
import com.google.gson.Gson;


import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 作者：真理 Created by Administrator on 2018/12/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerHome extends ControllerClassObserver implements View.OnKeyListener, DownloadProgressListener {

    EditText search_edit;
    FlowLayout history_search_layout;
    FrameLayout frameLayout;
    private LayoutInflater mInflater;
    private OnLinePayBuilder onLinePayBuilder;
    private DownloadUtil downloadUtil;

    public ControllerHome(EditText search_edit, FlowLayout history_search_layout, FrameLayout frameLayout) {
        this.search_edit = search_edit;
        this.history_search_layout = history_search_layout;
        this.frameLayout = frameLayout;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.SEARCH_CLEAR_ALL_COMMITE:
                if (DataClass.USERID.isEmpty()) {
                    dataManager.deleteSearchDBInfo(DataClass.STANDARD_USER);
                } else {
                    dataManager.deleteSearchDBInfo(DataClass.USERID);
                }
                HistorySearchView();
                break;
            case EventCode.REFRESH_MONEY_ABOUT:
                NetInfoAbout();
                break;
            case EventCode.PAY_ACTION:
                PayObject payObject = (PayObject) commonevent.getBusObject();
                int payType = payObject.getPayType();
                String code = payObject.getOrderCode();
                int payStatus = payObject.getPayStatus();
                PayNetWork(code, payType, payStatus);
                break;
            case EventCode.DOWNLOAD:
                File outFile = null;
                if (SystemUtil.JudgeNetFilePathType(DataClass.DOWNLOAD_URL)) {
                    outFile = new File(SystemUtil.createFile(context, context.getString(R.string.app_name))
                            , new StringBuffer().append(new Date().getTime()).append(SystemUtil.getNetFilePathTypeSuffix(DataClass.DOWNLOAD_URL)).toString());
                } else if (DataClass.DOWNLOAD_URL.contains(".gif")) {
                    outFile = new File(SystemUtil.createFile(context, context.getString(R.string.app_name))
                            , new StringBuffer().append(new Date().getTime()).append(".gif").toString());
                } else {
                    outFile = new File(SystemUtil.createFile(context, context.getString(R.string.app_name))
                            , new StringBuffer().append(new Date().getTime()).append(".jpg").toString());
                }
                downloadUtil.downloadFile(DataClass.DOWNLOAD_URL.split(".com/")[1], outFile.getPath());
                break;
        }
    }

    @Override
    protected void initInject() {
        getControllerComponent().inject(this);
    }

    @Override
    protected void onClassCreate() {
        super.onClassCreate();
        downloadUtil = new DownloadUtil(this);
        search_edit.setOnKeyListener(this);
        mInflater = LayoutInflater.from(context);
        onLinePayBuilder = new OnLinePayBuilder(context);
        HistorySearchView();
        NetInfoAbout();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            ((InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            String inputContent = search_edit.getText().toString();
            if (inputContent.isEmpty())
                return true;
            searchAction(inputContent);
        }
        return false;
    }

    @Override
    public void onFinish(File file) {
        toastUtil.showToast(context.getString(R.string.download_successfl));
    }

    @Override
    public void onProgress(int progress) {
        LogUtil.e(TAG, "onProgress : " + progress);
    }

    @Override
    public void onFailed(String errMsg) {
        toastUtil.showToast(context.getString(R.string.download_error));
    }

    //历史搜索
    private void HistorySearchView() {
        history_search_layout.removeAllViews();
        List<SearchDBInfo> searchDBInfos = null;
        if (DataClass.USERID.isEmpty()) {
            searchDBInfos = dataManager.querySearchDBInfo(DataClass.USERID);
        } else {
            searchDBInfos = dataManager.querySearchDBInfo(DataClass.USERID);
        }
        for (int i = 0; i < searchDBInfos.size(); i++) {
            if (i > context.getResources().getInteger(R.integer.search_history_log))
                return;
            TextView searchContent = (TextView) mInflater.inflate(R.layout.item_search_label, history_search_layout, false);
            searchContent.setText(searchDBInfos.get(searchDBInfos.size() - 1 - i).getSearchContent());
            history_search_layout.addView(searchContent);
            searchContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = ((TextView) v).getText().toString();
                    search_edit.setText("");
                    searchAction(s);
                    ViewBuilder.closeKeybord(search_edit);
                }
            });
        }
    }

    //搜索操作
    private void searchAction(String content) {
        RxBus.getDefault().post(new CommonEvent(EventCode.SEARCH_ACTION, content));
        frameLayout.setVisibility(View.VISIBLE);
        if (DataClass.USERID.isEmpty()) {
            dataManager.insertSearchDBInfo(new SearchDBInfo(DataClass.STANDARD_USER, content));
        } else {
            dataManager.insertSearchDBInfo(new SearchDBInfo(DataClass.USERID, content));
        }
        HistorySearchView();
    }

    //用户相关数据获取
    private void NetInfoAbout() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_CENTER_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.InfoAbout(map)
                .compose(RxUtil.<InfoAboutNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<InfoAboutNetBean>(toastUtil) {
                    @Override
                    public void onNext(InfoAboutNetBean infoAboutNetBean) {
                        if (infoAboutNetBean.getStatus() == 1) {
                            DataClass.MONEY = infoAboutNetBean.getResult().getMoneytotal();
                        } else {
                            toastUtil.showToast(infoAboutNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    /**
     * 支付
     */
    public void PayNetWork(String orderCode, int payType, final int payStaus) {
        String payTypeText = "";
        switch (payType) {
            case 0:
                payTypeText = context.getString(R.string.zfb_pay);
                break;
            case 1:
                payTypeText = context.getString(R.string.wechat_pay);
                break;
            case 2:
                payTypeText = context.getString(R.string.balance_pay);
                break;
        }
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.ORDER_PAY_SET);
        linkedHashMap.put("ordercode", orderCode);
        linkedHashMap.put("paytype", payTypeText);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        switch (payType) {
            case 0:
                addSubscribe(dataManager.ZfbPayContent(map)
                        .compose(RxUtil.<ZfbPayContentNetBean>rxSchedulerHelper())
                        .subscribeWith(new CommonSubscriber<ZfbPayContentNetBean>(toastUtil) {
                            @Override
                            public void onNext(ZfbPayContentNetBean zfbPayContentNetBean) {
                                if (zfbPayContentNetBean.getStatus() == 1) {
                                    String content = new StringBuffer().append(context.getString(R.string.app_name)).append(context.getString(R.string.pay_prompt)).toString();
                                    ZfbPayContentNetBean.PrepayinfoBean prepayinfo = zfbPayContentNetBean.getPrepayinfo();
                                    onLinePayBuilder.doAlipay(content, content,
                                            prepayinfo.getOrderCode(),
                                            prepayinfo.getUrl_notify(),
                                            prepayinfo.getPartner(),
                                            prepayinfo.getSeller(),
                                            prepayinfo.getPrivate_key(),
                                            prepayinfo.getTotalmoney(), payStaus);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Throwable : " + e.toString());
                                super.onError(e);
                            }
                        }));
                break;
            case 1:
                addSubscribe(dataManager.WechatPayContent(map)
                        .compose(RxUtil.<WechatPayContentNetBean>rxSchedulerHelper())
                        .subscribeWith(new CommonSubscriber<WechatPayContentNetBean>(toastUtil) {
                            @Override
                            public void onNext(WechatPayContentNetBean wechatPayContentNetBean) {
                                if (wechatPayContentNetBean.getStatus() == 1) {
                                    WechatPayContentNetBean.PrepayinfoBean prepayinfo = wechatPayContentNetBean.getPrepayinfo();
                                    DataClass.PAY_RETURN_STATUS = payStaus;
                                    onLinePayBuilder.doWeiXinPay(prepayinfo.getAppid(),
                                            prepayinfo.getPartnerid(),
                                            prepayinfo.getPrepayid(),
                                            prepayinfo.getNoncestr(),
                                            prepayinfo.getTimestamp(),
                                            prepayinfo.getPackageX(),
                                            prepayinfo.getSign());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Throwable : " + e.toString());
                                super.onError(e);
                            }
                        }));
                break;
            case 2:
                addSubscribe(dataManager.UpLoadStatusNetBean(map)
                        .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                        .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                            @Override
                            public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                                if (upLoadStatusNetBean.getStatus() == 1) {
                                    RxBus.getDefault().post(new CommonEvent(payStaus));
                                    RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_MONEY_ABOUT));
                                } else {
                                    toastUtil.showToast(upLoadStatusNetBean.getMessage());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Throwable : " + e.toString());
                                super.onError(e);
                            }
                        }));
                break;
        }
    }


}
