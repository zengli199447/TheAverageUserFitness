package com.example.administrator.sportsfitness.ui.controller;

import android.util.Log;
import android.view.View;

import com.example.administrator.sportsfitness.R;
import com.example.administrator.sportsfitness.base.ControllerClassObserver;
import com.example.administrator.sportsfitness.global.DataClass;
import com.example.administrator.sportsfitness.model.bean.TagNetBean;
import com.example.administrator.sportsfitness.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sportsfitness.model.event.CommonEvent;
import com.example.administrator.sportsfitness.model.event.EventCode;
import com.example.administrator.sportsfitness.rxtools.RxBus;
import com.example.administrator.sportsfitness.rxtools.RxUtil;
import com.example.administrator.sportsfitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsfitness.ui.view.FlowLayout;
import com.example.administrator.sportsfitness.widget.CommonSubscriber;
import com.example.administrator.sportsfitness.widget.FlowLayoutBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/25.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ControllerTheTagModify extends ControllerClassObserver implements FlowLayoutBuilder.FlowClickListener{

    FlowLayout personal_tag_layout;
    FlowLayout hot_tag_layout;
    private FlowLayoutBuilder tagFlowLayoutBuilder;
    private FlowLayoutBuilder hotTagFlowLayoutBuilder;
    private List<TagNetBean.ResultBean.TagSystemBean> tagHotBeanList;
    private String tags;
    private List<String> tagsList = new ArrayList<>();
    private List<String> tagHotList = new ArrayList<>();
    private ShowDialog instance;
    private boolean status;

    public ControllerTheTagModify(FlowLayout personal_tag_layout, FlowLayout hot_tag_layout) {
        this.personal_tag_layout = personal_tag_layout;
        this.hot_tag_layout = hot_tag_layout;
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.CORNERS_TAG:
                refreshTag(commonevent.getTemp_str());
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
        tagFlowLayoutBuilder = new FlowLayoutBuilder(context, personal_tag_layout, 0);
        hotTagFlowLayoutBuilder = new FlowLayoutBuilder(context, hot_tag_layout, 1);
        instance = ShowDialog.getInstance();
        initListener();
        NetInfoAbout();
    }

    @Override
    protected void onClassResume() {
        super.onClassResume();
    }

    @Override
    protected void onClassDestroy() {
        super.onClassDestroy();
        if (status)
            RxBus.getDefault().post(new CommonEvent(EventCode.TAG_REFRSH));
    }

    private void refreshView() {
        tagsList.clear();
        tagHotList.clear();
        if (tags != null && !tags.isEmpty()) {
            String[] split = tags.split(",");
            for (int i = 0; i < split.length; i++) {
                tagsList.add(split[i]);
            }
            tagFlowLayoutBuilder.initLayout(tagsList, false, true);
        }

        for (TagNetBean.ResultBean.TagSystemBean tagSystemBean : tagHotBeanList) {
            tagHotList.add(tagSystemBean.getTitle());
        }
        hotTagFlowLayoutBuilder.initLayout(tagHotList, false, false);

    }

    private void initListener() {
        tagFlowLayoutBuilder.setFlowClickListener(this);
        hotTagFlowLayoutBuilder.setFlowClickListener(this);
    }

    @Override
    public void onFlowClickListener(View view, int position, int type) {
        status = true;
        switch (type) {
            case 0:
                tagsList.remove(position);
                String sizeLine = "";
                String theComma = "";
                for (int i = 0; i < tagsList.size(); i++) {
                    if (i > 0) {
                        theComma = ",";
                    }
                    sizeLine = new StringBuffer().append(sizeLine).append(theComma).append(tagsList.get(i)).toString();
                }
                NetModifyHomePage("", "", sizeLine, 3);
                break;
            case 1:
                refreshTag(tagHotList.get(position));
                break;
        }
    }

    private void refreshTag(String tag) {
        if (!tagsList.contains(tag)) {
            if (tags == null | tags.isEmpty()) {
                tags = tag;
            } else {
                tags = new StringBuffer().append(tags).append(",").append(tag).toString();
            }
            NetModifyHomePage("", "", tags, 3);
        } else {
            instance.showHelpfulHintsDialog(context, context.getString(R.string.tag_existing), EventCode.ONSTART);
        }
    }


    //获取标签
    private void NetInfoAbout() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.TAG_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.Tag(map)
                .compose(RxUtil.<TagNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TagNetBean>(toastUtil) {
                    @Override
                    public void onNext(TagNetBean tagNetBean) {
                        if (tagNetBean.getStatus() == 1) {
                            TagNetBean.ResultBean result = tagNetBean.getResult();
                            TagNetBean.ResultBean.TagUserBean tag_user = result.getTag_user();
                            tags = tag_user.getTags();
                            tagHotBeanList = result.getTag_system();
                            refreshView();
                        } else {
                            toastUtil.showToast(tagNetBean.getMessage());
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
                            NetInfoAbout();
                            instance.showHelpfulHintsDialog(context, context.getString(R.string.modify_successful), EventCode.ONSTART);
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



}
