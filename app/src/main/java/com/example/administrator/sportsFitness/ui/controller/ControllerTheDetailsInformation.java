package com.example.administrator.sportsFitness.ui.controller;

import android.util.Log;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.base.ControllerClassObserver;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.model.bean.HomePageInfoNetBean;
import com.example.administrator.sportsFitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsFitness.model.event.CommonEvent;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.rxtools.RxUtil;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.widget.CommonSubscriber;
import com.example.administrator.sportsFitness.widget.MultipartBuilder;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2019/1/17.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerTheDetailsInformation extends ControllerClassObserver implements MultipartBuilder.UpLoadFileListener {

    String userId;
    private MultipartBuilder multipartBuilder;
    private ShowDialog instance;
    private boolean refreshStatus;

    public ControllerTheDetailsInformation(String userId) {
        this.userId = userId;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.PICTURE:
                multipartBuilder.commitFile(commonevent.getTemp_str());
                break;
            case EventCode.SIGNATURE:
                NetModifyHomePage("", commonevent.getTemp_str(), "", 2);
                break;
            case EventCode.PERSONAL_CHANGE_REFRESH:
                NetHomePage();
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
        multipartBuilder = new MultipartBuilder(context, 0);
        instance = ShowDialog.getInstance();
        initListener();
        NetHomePage();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
        refreshStatus = true;
    }

    private void initListener() {
        multipartBuilder.setUpLoadFileListener(this);
    }

    @Override
    protected void onClassPause() {
        super.onClassPause();
        refreshStatus = false;
    }

    @Override
    public void onUpLoadFileListener(String url, List<String> list) {
        if (refreshStatus)
            NetModifyHomePage(url, "", "", 1);
    }

    //用户主页
    private void NetHomePage() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_HOMEPAGE_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("userid_view", userId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.HomePageInfo(map)
                .compose(RxUtil.<HomePageInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<HomePageInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(HomePageInfoNetBean homePageInfoNetBean) {
                        if (homePageInfoNetBean.getStatus() == 1) {
                            if (onHomePageDataListener != null)
                                onHomePageDataListener.onHomePageDataListener(homePageInfoNetBean.getResult());
                        } else {
                            toastUtil.showToast(homePageInfoNetBean.getMessage());
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
     * 个人主页修改
     *
     * @param img
     * @param signature
     * @param tag
     * @param type      1.修改背景图 2.修改签名 3.修改标签
     */
    public void NetModifyHomePage(final String img, String signature, String tag, final int type) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_HOMEPAGE_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("img_center", img);
        linkedHashMap.put("signature", signature);
        linkedHashMap.put("tags", tag);
        linkedHashMap.put("opttype", type);
        map.put("version", "v1");
        map.put("vars", new Gson().toJson(linkedHashMap));
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.modify_successful), EventCode.ONSTART);
                            NetHomePage();
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

    }

    /**
     * 收藏
     *
     * @param datatype 1.场馆 2.课程 3.教练
     * @param refid    1.场馆id 2.课程id 3.教练id
     */
    public void NetCollection(final String collectionStatus) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.COLLECT_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("datatype", 3);
        linkedHashMap.put("refid", userId);
        map.put("version", "v1");
        map.put("vars", new Gson().toJson(linkedHashMap));
        addSubscribe(dataManager.UpLoadStatusNetBean(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            instance.showHelpfulHintsDialog(context, upLoadStatusNetBean.getMessage(), EventCode.ONSTART);
                            if (onHomePageDataListener != null)
                                onHomePageDataListener.onCollectionSuccessfulListener(collectionStatus.equals("0") ? true : false);
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

    }

    public interface OnHomePageDataListener {
        void onHomePageDataListener(HomePageInfoNetBean.ResultBean resultBean);

        void onCollectionSuccessfulListener(boolean status);
    }

    private OnHomePageDataListener onHomePageDataListener;

    public void setOnHomePageDataListener(OnHomePageDataListener onHomePageDataListener) {
        this.onHomePageDataListener = onHomePageDataListener;
    }

}
